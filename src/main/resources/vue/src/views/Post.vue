<template>
  <el-header>
    <el-row class="header-box">
      <el-col :xs="0" :sm="0" :md="2" :lg="4" :xl="4"></el-col>
      <el-col :xs="6" :sm="6" :md="4" :lg="4" :xl="4">
        <h3 style="margin-top: 13px">科技论坛</h3>
      </el-col>
      <el-col :xs="12" :sm="12" :md="12" :lg="8" :xl="8">
        <router-link to="/home">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>热门</el-button>
        </router-link>
        <router-link to="/tag">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>板块</el-button>
        </router-link>
        <router-link to="/news">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>新闻</el-button>
        </router-link>
      </el-col>
      <el-col :xs="3" :sm="3" :md="2" :lg="2" :xl="2">
        <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="Search" circle></el-button>
      </el-col>
      <el-col :xs="3" :sm="3" :md="2" :lg="2" :xl="2">
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
      <el-col :xs="0" :sm="0" :md="2" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-header>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">

        <el-card class="post-card">
          <div class="header">
            <el-avatar class="avatar" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                       :size="30"></el-avatar>
            <div class="user-info">{{ thisPost.userName }}</div>
          </div>
          <div v-if="thisPost.postPrice" class="price">￥{{ thisPost.postPrice }}</div>
          <div class="title">{{ thisPost.postTitle }}</div>
          <div class="content">{{ thisPost.postContent }}</div>
          <el-badge v-if="pictureUrl.length" :value="pictureUrl.length" :max="99" class="item">
            <div class="images">
              <el-image
                  v-for="i in 3"
                  class="card-img"
                  :src="pictureUrl[i-1]"
                  :zoom-rate="1.2"
                  :preview-src-list="pictureUrl"
                  :initial-index="i"
                  fit="cover"
              />
            </div>
          </el-badge>
          <div class="post-footer">
            <el-tag class="tag">{{ thisPost.tagName }}</el-tag>
            <div class="time">{{ thisPost.postTime }}</div>
          </div>
        </el-card>

        <el-card class="title-card">
          <span>全部评论({{ commentData.length }})</span>
        </el-card>
        <el-card class="comment-card" v-for="comment in commentData" :key="comment.id">
          <div class="header">
            <el-avatar class="avatar" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                       :size="30"></el-avatar>
            <div class="user-info">{{ comment.userName }}</div>
          </div>
          <div class="content">{{ comment.commentContent }}</div>
          <div class="post-footer">
            <div class="time">{{ comment.commentTime }}</div>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center"><el-icon><Loading/></el-icon></div>

      </el-col>

      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4">
        <el-card class="side-card">
          <h3 style="margin: 15px">为你推荐</h3>
          <div v-for="tag in tagData" :key="tag.tagId" @click="toTag(tag.tagId)">
            <el-row>
              <el-col :span="8">
                <el-image
                    class="introduce-img"
                    :src="tag.tagCover"
                    fit="fill"
                />
              </el-col>
              <el-col :span="16">
                <p style="height: 50px;line-height: 50px">{{tag.tagName}}</p>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
  <el-backtop :right="15" :bottom="15"/>
  <el-affix position="bottom" :offset="20">
    <el-button type="primary" circle style="height: 40px;width: 40px" :icon="ChatLineRound" @click="drawerComment = true"></el-button>
    <el-button v-if="thisPost.postPrice" type="danger" circle style="height: 40px;width: 40px" :icon="ShoppingCart" @click="drawerBuy = true"></el-button>
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
  <el-drawer v-model="drawerBuy" :direction="'btt'" :with-header="false">
    <el-button @click="submitBuy()">结算</el-button>
  </el-drawer>
</template>

<script setup>

import {ref, onMounted} from 'vue'
import {ElLoading, ElMessage} from 'element-plus'
import axios from 'axios'
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'

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
const drawerBuy = ref(false)
//新增评论文本框
const commentArea = ref('')
//这是我的帖子
const isMyPost = ref(false)

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

/**
 * 标签加载
 */
const tagData = ref([])
//加载随机标签
const loadRandTag = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/tag/introduce',
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  tagData.value = data.rows
}
const toTag = (tagId)=>{
  router.push('/detail?id='+tagId)
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

//提交购买
const submitBuy = () => {
  ElMessage({
    message: '交易成功',
    type: 'success',
  })
  router.push('/home')
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

//跳转私信页面
const toChat = () => {
  router.push('/chat')
}

//跳转用户详情页
const editInfo = () => {
  router.push("/info")
}
//管理员点击跳转用户管理
const toAdmin = ()=>{
  router.push('/admin')
}

/**
 * 管理员判断
 */
//管理员标识
const isAdmin = ref(false)
//检查是否为管理员
const checkAdmin = async () =>{
  await axios({
    method: 'GET',
    url: '/api/user/admin?token=' + localStorage.getItem('token'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        isAdmin.value=true
      }
      , error => {
        isAdmin.value=false
      })
}

//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
  loadThisPost()
  loadRandTag()
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

.introduce-img{
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