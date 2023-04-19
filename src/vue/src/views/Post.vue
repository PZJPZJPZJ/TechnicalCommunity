<template>
  <HeadBar></HeadBar>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">

        <el-card class="post-card">
          <div class="header">
            <el-avatar class="avatar" :src="thisPost.userCover" :size="35"></el-avatar>
            <div class="user-info">{{ thisPost.userName }}</div>
          </div>
          <div v-if="thisPost.postPrice" class="price">￥{{ thisPost.postPrice }}</div>
          <div class="title">{{ thisPost.postTitle }}</div>
          <div class="content">{{ thisPost.postContent }}</div>
          <div class="images">
            <el-image
                v-if="pictureUrl[0]"
                class="card-img"
                :src="pictureUrl[0]"
                :zoom-rate="1.2"
                :preview-src-list="pictureUrl"
                :initial-index="0"
                fit="cover"
            />
            <el-image
                v-if="pictureUrl[1]"
                class="card-img"
                :src="pictureUrl[1]"
                :zoom-rate="1.2"
                :preview-src-list="pictureUrl"
                :initial-index="1"
                fit="cover"
            />
            <el-image
                v-if="pictureUrl[2]"
                class="card-img"
                :src="pictureUrl[2]"
                :zoom-rate="1.2"
                :preview-src-list="pictureUrl"
                :initial-index="2"
                fit="cover"
            />
            <el-badge v-if="pictureUrl.length-3 > 0" :value="pictureUrl.length-3" :max="9" type="info" class="item"></el-badge>
          </div>
          <div class="post-footer">
            <el-tag class="tag" @click="handleViewTag">{{ thisPost.tagName }}</el-tag>
            <div class="time">{{ thisPost.postTime }}</div>
          </div>
        </el-card>

        <el-card class="title-card">
          <span>全部评论({{ total }})</span>
        </el-card>
        <el-card class="comment-card" v-for="comment in commentData" :key="comment.commentId">
          <div class="header">
            <el-avatar class="avatar" :src="comment.userCover" :size="35"></el-avatar>
            <div class="user-info">{{ comment.userName }}</div>
          </div>
          <div class="content">{{ comment.commentContent }}</div>
          <div class="post-footer">
            <div class="time">{{ comment.commentTime }}</div>
            <el-popconfirm title="确定要删除吗？" confirm-button-text="确认" cancel-button-text="取消"
                           v-if="loginUser == comment.commentUser" @confirm="deleteComment(comment.commentId)">
              <template #reference>
                <el-button type="danger">删除该评论</el-button>
              </template>
            </el-popconfirm>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center">
          <el-icon>
            <Loading/>
          </el-icon>
        </div>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4">
        <SideCard></SideCard>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
  <el-backtop :right="15" :bottom="15"/>
  <el-affix position="bottom" :offset="20">
    <el-button type="primary" circle style="height: 40px;width: 40px" :icon="ChatLineRound"
               @click="drawerComment = true"></el-button>
    <el-button v-if="thisPost.postPrice" type="danger" circle style="height: 40px;width: 40px" :icon="ShoppingCart"
               @click="toBuy"></el-button>
  </el-affix>
  <el-drawer v-model="drawerComment" :direction="'btt'" :with-header="false">
    <el-input
        v-model="commentArea"
        maxlength="500"
        placeholder="写评论"
        :autosize="true"
        show-word-limit
        type="textarea"
    />
    <el-button @click="uploadComment">发布</el-button>
  </el-drawer>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {ElLoading, ElMessage} from 'element-plus'
import axios from 'axios'
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import HeadBar from "@/components/HeadBar"
import SideCard from "@/components/SideCard"

//获取当前url参数
const queryString = window.location.search;
const queryParams = new URLSearchParams(queryString);
//当前帖子信息
const thisPost = ref([])
//帖子图片
const pictureUrl = ref([])
//全部评论信息
const commentData = ref([])
//评论加载状态
const loading = ref(false)
//评论分页
const currentPage = ref(1)
const pageSize = ref(10)
//评论总数
const total = ref(0)
//抽屉开启状态
const drawerComment = ref(false)
//新增评论文本框
const commentArea = ref('')
//这是我的帖子
const isMyPost = ref(false)
//当前登录账户
const loginUser = ref(localStorage.getItem('user'))

//获取当前帖子
const loadThisPost = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/post/show?id=' + queryParams.get('id'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  //直接覆盖写入数组
  thisPost.value = data.rows
  //判断是否为当前用户的帖子
  if (thisPost.value.postUser.toString() === localStorage.getItem('user').toString()) {
    isMyPost.value = true
  }
}

//加载当前帖子图片
const loadPicture = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/picture/download?id=' + queryParams.get('id'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  //直接覆盖写入数组
  pictureUrl.value = data.rows.map((row) => row.pictureUrl)
}

const loadMoreData = async () => {
  //正在加载时不再提交请求防止一直处于页面底部刷新
  if (loading.value) return
  //开启加载状态
  loading.value = true
  //提交请求
  const {data} = await axios({
    method: 'POST',
    url: '/api/comment/list',
    data: {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      postId: queryParams.get('id')
    },
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  //在数组后增加数据
  commentData.value = [...commentData.value, ...data.rows]
  total.value = data.total
  currentPage.value++
  //禁用加载状态
  loading.value = false
}

//上传评论
const uploadComment = async () => {
  //消息为空则禁止提交
  if (commentArea.value === '') {
    ElMessage({
      showClose: true,
      message: '请输入评论内容',
      type: 'warning',
    })
    return
  }
  //提交请求
  await axios({
    method: 'POST',
    url: '/api/comment/save',
    data: {
      commentContent: commentArea.value,
      commentUser: localStorage.getItem('user'),
      commentPost: queryParams.get('id')
    },
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  commentArea.value = ''
  drawerComment.value = false
  currentPage.value = 1
  commentData.value = []
  await loadMoreData()
}

//删除评论
const deleteComment = async (commentId) => {
  await axios({
    method: 'GET',
    url: '/api/comment/delete?id=' + commentId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          showClose: true,
          message: '删除成功',
          type: 'success',
        })
        currentPage.value = 1
        commentData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          showClose: true,
          message: '删除失败',
          type: 'error',
        })
      })
}

//提交购买
const toBuy = () => {
  router.push('/chat?id='+thisPost.value.postUser)
}

//点击跳转对应标签
const handleViewTag = () => {
  router.push('/detail?id=' + thisPost.value.postTag)
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

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
  loadThisPost()
  loadPicture()
  loadMoreData().finally(() => {
    loadingInstance.close()
  })
})
</script>

<style scoped>
.side-card {
  margin: 10px;
}

.card-img {
  width: 80px;
  height: 80px;
  margin: 8px;
}

.introduce-img {
  width: 35px;
  height: 35px;
  margin: 8px;
  border-radius: 10px;
}

.post-card {
  margin-bottom: 20px;
  text-align: left;
}

.title-card {
  margin-bottom: 20px;
  text-align: left;
  box-shadow: none;
  border: none;
}

.comment-card {
  margin-bottom: 20px;
  text-align: left;
  box-shadow: none;
  border: none;
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
}

.images {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 10px;
}

.image {
  width: 100px;
  height: 100px;
  margin-right: 10px;
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

.price {
  color: red;
  font-size: 30px;
  margin: 5px 0;
  font-weight: bolder;
}
</style>