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
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" type="primary" link>分类</el-button>
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
              <el-dropdown-item @click="toChat">用户私信</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
      <el-col :xs="0" :sm="0" :md="2" :lg="2" :xl="2"></el-col>
    </el-row>
  </el-header>
  <el-main>

  </el-main>
</template>

<script setup>
import {ref} from "vue";
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'

/**
 * 顶栏
 */
const searchBox = ref('')
const searchSelect = ref('')

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