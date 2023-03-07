<template>
  <el-header>
    <el-row class="header-box">
      <el-col :span="6">
        <h3>科技论坛</h3>
      </el-col>
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <el-dropdown :hide-on-click="false">
    <span class="el-dropdown-link">用户
    <el-icon class="el-icon--right"><arrow-down/></el-icon>
    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="editInfo">编辑信息</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
              <el-dropdown-item disabled>Action 4</el-dropdown-item>
              <el-dropdown-item divided>Action 5</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
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

      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
</template>

<script setup>
//加载触发
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

const myInfo = ref([])
const changePassword = ref(false)
const myPassword = ref('')

//加载用户信息
const loadMyInfo = async () =>{
  const {data} = await axios({
    method: 'GET',
    url: '/api/user/info?id='+localStorage.getItem('user'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  myInfo.value = data.rows
}

//更新用户信息
const updateMyInfo = async () =>{
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
      }
      , error => {
        ElMessage({
          message: '修改失败',
          type: 'error',
        })
      }
  )
}
//跳转用户详情页
const editInfo = () =>{
  router.push("/info")
}

//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}

onMounted(() => {
  loadMyInfo()
})
</script>

<style scoped>

</style>