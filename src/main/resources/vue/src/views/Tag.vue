<template>
  <el-header>
    <el-row class="header-box">
      <el-col :span="6">
        <h3>科技论坛</h3>
      </el-col>
      <el-col :span="6">
        <router-link to="/home">热门</router-link>
        <router-link to="/tag">分类</router-link>
        <router-link to="/news">新闻</router-link>
      </el-col>
      <el-col :span="6">
        <div class="mt-4">
          <el-input
              v-model="searchBox"
              placeholder="请输入..."
              class="input-with-select"
          >
            <template #append>
              <el-button type="success">搜索</el-button>
            </template>
          </el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">用户</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="editInfo">用户中心</el-dropdown-item>
              <el-dropdown-item @click="toChat">用户私信</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
  </el-header>
  <el-main>

  </el-main>
</template>

<script setup>
import {ref} from "vue";

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

</style>