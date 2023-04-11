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
</template>

<script setup>
import {onMounted, ref} from 'vue'
import axios from 'axios'
import {useRouter} from "vue-router";
import {Search, User} from '@element-plus/icons'

const router = useRouter()
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
//跳转搜索
const changeSearch = ()=>{
  router.push('/search')
}
//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}
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
onMounted(() => {
  checkAdmin()
})
</script>

<style scoped>
.header-box {
  justify-content: space-between;
}
</style>