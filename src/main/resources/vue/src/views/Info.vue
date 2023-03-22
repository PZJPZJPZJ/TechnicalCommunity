<template>
  <el-header>
    <el-row class="header-box">
      <el-col :xs="0" :sm="0" :md="2" :lg="4" :xl="4"></el-col>
      <el-col :xs="6" :sm="6" :md="4" :lg="4" :xl="4">
        <h3 style="margin-top: 13px">科技社区</h3>
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
        <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="Search" circle @click="changeSearch"></el-button>
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
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card :model="myInfo" label-width="100px">
          <el-form-item label="账号">
            <el-input v-model="myInfo.userId" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="myInfo.userName"></el-input>
          </el-form-item>
          <el-form-item label="个性签名">
            <el-input v-model="myInfo.userSign"></el-input>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="myInfo.userBirth" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="myInfo.userSex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-switch
              v-model="changePassword"
              active-text="修改密码"
              inactive-text="保持密码"
          />
          <el-form-item v-if="changePassword" label="修改密码">
            <el-input v-model="myPassword" type="password" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateMyInfo">保存</el-button>
          </el-form-item>
        </el-card>

        <el-card class="title-card">
          <span v-if="!isAdmin">我的帖子({{total}})</span>
          <span v-if="isAdmin">全站帖子({{total}})</span>
        </el-card>

        <el-card class="post-card" v-for="post in postData" :key="post.id">
          <div class="header">
            <el-avatar class="avatar" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                       :size="30"></el-avatar>
            <div class="user-info">{{ post.userName }}</div>
          </div>
          <div class="center" @click="handleViewPost(post.postId)">
            <div class="title">{{ post.postTitle.substring(0, 20) }}</div>
            <div class="content">{{ post.postContent.substring(0, 100) }}...</div>
          </div>
          <div class="near-footer">
            <el-tag class="tag" @click="handleViewTag(post.postTag)">{{ post.tagName }}</el-tag>
            <div class="time">{{ post.postTime }}</div>
          </div>
          <div class="post-footer">
            <div class="topOrBottom" v-if="isAdmin">
              <el-popconfirm v-if="!post.postTop" title="确定要置顶吗？" confirm-button-text="确认" cancel-button-text="取消"
                              @confirm="topPost(post.postId)">
                <template #reference>
                  <el-button type="success">置顶该帖</el-button>
                </template>
              </el-popconfirm>
              <el-popconfirm v-if="post.postTop" title="确定要取消置顶吗？" confirm-button-text="确认" cancel-button-text="取消"
                              @confirm="bottomPost(post.postId)">
                <template #reference>
                  <el-button type="warning">取消置顶</el-button>
                </template>
              </el-popconfirm>
            </div>
            <el-popconfirm title="确定要删除吗？" confirm-button-text="确认" cancel-button-text="取消"
                           @confirm="deletePost(post.postId)">
              <template #reference>
                <el-button type="danger">删除该帖</el-button>
              </template>
            </el-popconfirm>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center"><el-icon><Loading/></el-icon></div>

      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
</template>

<script setup>
//加载触发
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElLoading, ElMessage} from "element-plus";
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'

const myInfo = ref([])
const changePassword = ref(false)
const myPassword = ref('')
const loading = ref(false)
const postData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)


//加载用户信息
const loadMyInfo = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/user/info?id=' + localStorage.getItem('user'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  myInfo.value = data.rows
}

//更新用户信息
const updateMyInfo = async () => {
  if (changePassword.value){
    await axios({
      method: 'POST',
      url: '/api/user/update',
      data: {
        userName: myInfo.value.userName,
        userSign: myInfo.value.userSign,
        userSex: myInfo.value.userSex,
        userBirth: myInfo.value.userBirth,
        userPassword: myPassword.value
      },
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          ElMessage({
            message: '修改成功',
            type: 'success',
          })
          myPassword.value=''
          changePassword.value = false
        }
        , error => {
          ElMessage({
            message: '修改失败',
            type: 'error',
          })
        }
    )
  }
  else {
    await axios({
      method: 'POST',
      url: '/api/user/update',
      data: {
        userName: myInfo.value.userName,
        userSign: myInfo.value.userSign,
        userSex: myInfo.value.userSex,
        userBirth: myInfo.value.userBirth
      },
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          ElMessage({
            message: '修改成功',
            type: 'success',
          })
        }
        , error => {
          ElMessage({
            message: '修改失败',
            type: 'error',
          })
        }
    )
  }
}
//跳转用户详情页
const editInfo = () => {
  router.push("/info")
}

const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/post/user', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }
      , {
        headers: {Authorization: localStorage.getItem('token')}
      })
  postData.value = [...postData.value, ...data.rows]
  total.value = data.total
  currentPage.value++
  loading.value = false
}

//点击跳转对应帖子
const handleViewPost = (postId) => {
  router.push(`/post?id=${postId}`)
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

/**
 * 顶栏
 */
//管理员点击跳转用户管理
const toAdmin = ()=>{
  router.push('/admin')
}
const changeSearch = ()=>{
  router.push('/search')
}

//跳转私信页面
const toChat = () => {
  router.push('/chat')
}
//点击跳转对应标签
const handleViewTag = (tagId) => {
  router.push(`/detail?id=${tagId}`)
}

//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}

//删除选中帖子
const deletePost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/delete?id=' + postId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '删除成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '删除失败',
          type: 'error',
        })
      })
}

//置顶帖子
const topPost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/top?id=' + postId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '置顶成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '置顶失败',
          type: 'error',
        })
      })
}

//置顶帖子
const bottomPost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/bottom?id=' + postId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '取消置顶成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '取消置顶失败',
          type: 'error',
        })
      })
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
  loadMyInfo()
  checkAdmin()
  loadMoreData().finally(() => {
    loadingInstance.close()
  })
})
</script>

<style scoped>
.post-card {
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
  text-overflow: ellipsis;
  white-space: nowrap;
}

.near-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 10px;
  padding-top: 10px;
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

.title-card {
  margin-bottom: 20px;
  text-align: left;
  box-shadow: none;
  border: none;
}
</style>