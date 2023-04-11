<template>
  <HeadBar></HeadBar>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-input
            v-model="searchText"
            class="w-50 m-2"
            size="large"
            placeholder="搜索"
        >
          <template #append>
            <el-button :icon="Search" @click="searchButton"/>
          </template>
        </el-input>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card class="post-card" v-for="post in postData" :key="post.postId">
          <div class="header">
            <el-avatar class="avatar" :src="post.userCover" :size="35"></el-avatar>
            <div class="user-info">{{ post.userName }}</div>
          </div>
          <div class="center" @click="handleViewPost(post.postId)">
            <div class="title">{{ post.postTitle.substring(0, 20) }}</div>
            <div class="content">{{ post.postContent.substring(0, 100) }}...</div>
          </div>
          <div class="post-footer">
            <el-tag class="tag" @click="handleViewTag(post.postTag)">{{ post.tagName }}</el-tag>
            <div class="time">{{ post.postTime }}</div>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center">
          <el-icon>
            <Loading/>
          </el-icon>
        </div>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
</template>

<script setup>
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";
import HeadBar from "@/components/HeadBar"

//点击跳转对应帖子
const handleViewPost = (postId) => {
  router.push('/post?id='+postId)
}
/**
 * 管理员判断
 */
//管理员标识
const isAdmin = ref(false)
//检查是否为管理员
const checkAdmin = async () => {
  await axios({
    method: 'GET',
    url: '/api/user/admin?token=' + localStorage.getItem('token'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        isAdmin.value = true
      }
      , error => {
        isAdmin.value = false
      })
}
/**
 * 刷新方法
 */
const postData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios({
    method: 'POST',
    url: '/api/post/search',
    data: {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      postTitle: searchText.value
    },
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  postData.value = [...postData.value, ...data.rows]
  total.value = data.total
  currentPage.value++
  loading.value = false
}
//滚动到底部执行自动刷新
const handleScroll = () => {
  const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
  const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
  const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
  if (scrollTop + windowHeight >= scrollHeight) {
    loadMoreData()
  }
}

/**
 * 搜索
 */
const searchText = ref('')
const searchButton = ()=>{
  postData.value=[]
  currentPage.value=1
  total.value=0
  loadMoreData()
}

/**
 * 加载方法
 */
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  checkAdmin()
})
</script>

<style scoped>
.card-img {
  width: 35px;
  height: 35px;
  margin: 8px;
  border-radius: 10px;
}

.post-card {
  margin-bottom: 20px;
  text-align: left;
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.avatar {
  margin-right: 10px;
}

.user-info {
  font-size: 14px;
  font-weight: bold;
}

.title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.content {
  font-size: 16px;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #e4e7ed;
}

.tag {
  font-size: 14px;
  margin-right: 10px;
  cursor: pointer;
}

.time {
  font-size: 14px;
}
</style>