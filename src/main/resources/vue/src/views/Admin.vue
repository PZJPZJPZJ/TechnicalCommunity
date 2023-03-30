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
        <el-card class="post-card" v-for="user in userData" :key="user.userId">
          <div class="header">
            <el-avatar class="avatar" :src="user.userCover" :size="35"></el-avatar>
          </div>
          <div class="center">
            <div class="user-info">账号：{{ user.userId }}</div>
            <div class="user-info">用户名：{{ user.userName }}</div>
            <div class="user-info">个性签名：{{ user.userSign }}</div>
            <div class="user-info">性别：{{ user.userSex }}</div>
            <div class="user-info">生日：{{ user.userBirth }}</div>
          </div>
          <div class="post-footer">
            <el-popconfirm v-if="!user.userAdmin" title="管理员具有操作所有账号的权限，请谨慎操作，确定要授权吗？" confirm-button-text="确认" cancel-button-text="取消"
                           @confirm="authorityUser(user.userId)">
              <template #reference>
                <el-button type="success">授权为管理员</el-button>
              </template>
            </el-popconfirm>
            <el-popconfirm v-if="user.userAdmin" title="确定要取消授权吗？" confirm-button-text="确认" cancel-button-text="取消"
                           @confirm="unauthorityUser(user.userId)">
              <template #reference>
                <el-button type="warning">取消授权为管理员</el-button>
              </template>
            </el-popconfirm>
            <el-popconfirm v-if="isAdmin" title="如非违反社区规则，不建议注销账户，确定要继续吗？" confirm-button-text="确认" cancel-button-text="取消"
                           @confirm="deleteUser(user.userId)">
              <template #reference>
                <el-button type="danger">移除该用户</el-button>
              </template>
            </el-popconfirm>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center"><el-icon><Loading/></el-icon></div>
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElLoading, ElMessage, ElMessageBox} from "element-plus";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import router from "@/router";

/**
 * 刷新方法
 */
const userData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/user/page', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }
      , {
        headers: {Authorization: localStorage.getItem('token')}
      })
  userData.value = [...userData.value, ...data.rows]
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
 * 提权与降权
 */
const authorityUser = async (userId)=>{
  await axios({
    method: 'GET',
    url: '/api/user/authority?id=' + userId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '提权成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        userData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '提权失败',
          type: 'error',
        })
      })
}
const unauthorityUser = async (userId)=>{
  await axios({
    method: 'GET',
    url: '/api/user/unauthority?id=' + userId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '降权成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        userData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '降权失败',
          type: 'error',
        })
      })
}
const deleteUser = async (userId)=>{
  ElMessageBox.confirm('该操作不可逆，是否继续执行？')
      .then(() => {
        axios({
          method: 'GET',
          url: '/api/user/delete?id=' + userId,
          headers: {
            Authorization: localStorage.getItem('token')
          }
        }).then(
            response => {
              ElMessage({
                message: '注销用户成功',
                type: 'success',
              })
              //刷新页面
              currentPage.value = 1
              userData.value = []
              loadMoreData()
            }
            , error => {
              ElMessage({
                message: '注销用户失败',
                type: 'error',
              })
            })
      })
      .catch(() => {
        ElMessage({
          message: '用户取消操作',
          type: 'warning',
        })
      })
}

/**
 * 删除用户
 */


/**
 * 顶栏
 */
//管理员点击跳转用户管理
const toAdmin = ()=>{
  router.push('/admin')
}

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
const changeSearch = ()=>{
  router.push('/search')
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
</style>