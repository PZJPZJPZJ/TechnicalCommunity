<template>
  <el-header>
    <el-row class="header-box">
      <el-col :xs="0" :sm="0" :md="2" :lg="2" :xl="2"></el-col>
      <el-col :xs="6" :sm="6" :md="4" :lg="4" :xl="4">
        <h3 style="margin-top: 13px">科技论坛</h3>
      </el-col>
      <el-col :xs="12" :sm="6" :md="4" :lg="4" :xl="4">
        <router-link to="/home">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>热门</el-button>
        </router-link>
        <router-link to="/tag">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" type="primary" link>板块</el-button>
        </router-link>
        <router-link to="/news">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>新闻</el-button>
        </router-link>
      </el-col>
      <el-col :xs="2" :sm="2" :md="2" :lg="2" :xl="2">
        <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="Search" circle></el-button>
      </el-col>
      <el-col :xs="2" :sm="2" :md="2" :lg="2" :xl="2">
        <el-dropdown :hide-on-click="false">
          <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="User" circle></el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="editInfo">用户中心</el-dropdown-item>
              <el-dropdown-item @click="toChat">私信列表</el-dropdown-item>
              <el-dropdown-item v-if="isAdmin" @click="toAdmin">用户管理</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
      <el-col :xs="0" :sm="0" :md="2" :lg="2" :xl="2"></el-col>
    </el-row>
  </el-header>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card class="post-card" v-for="tag in tagData" :key="tag.tagId" @click="handleViewTag(tag.tagId)">
          <el-col :span="8">
            <el-image
                class="card-img"
                :src="tag.tagCover"
                fit="fill"
            />
          </el-col>
          <el-col :span="16">
            <p style="height: 50px;line-height: 50px">{{ tag.tagName }}</p>
          </el-col>
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
import {onMounted, ref} from "vue";
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import axios from "axios";
import {ElLoading} from "element-plus";

/**
 * 顶栏
 */
//跳转私信页面
const toChat = () => {
  router.push('/chat')
}

//跳转用户详情页
const editInfo = () => {
  router.push("/info")
}

//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}
//管理员点击跳转用户管理
const toAdmin = () => {
  router.push('/admin')
}

//点击跳转对应帖子
const handleViewTag = (postId) => {
  router.push('/detail?id='+postId)
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
const tagData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/tag/page', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }
      , {
        headers: {Authorization: localStorage.getItem('token')}
      })
  tagData.value = [...tagData.value, ...data.rows]
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
 * 加载方法
 */
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
  checkAdmin()
  loadMoreData().finally(() => {
    loadingInstance.close()
  })
})

</script>

<style scoped>
.top-nav {
  position: relative;
}

.top-nav-header {
  background-color: #ffffff;
  padding: 0;
}

.top-nav-logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
}

.top-nav-right {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.top-nav-items {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.top-nav-dialog {
  padding: 0;
  position: absolute;
  top: 60px;
  right: 0;
}

.top-nav-menu {
  border: none;
}
</style>