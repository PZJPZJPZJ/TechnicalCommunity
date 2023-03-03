<template>
  <div class="common-layout">
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="2">
            <div class="grid-content ep-bg-purple"/>
          </el-col>
          <el-col :span="20">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                     class="demo-ruleForm">
              <el-form-item label="账号" prop="id">
                <el-input type="text" v-model="ruleForm.id" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                <el-button type="warning" @click="register">注册</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="2">
            <div class="grid-content ep-bg-purple"/>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data() {
    const validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'));
      }
      else{
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      }
      else{
        callback();
      }
    };
    return {
      ruleForm: {
        id: '',
        password: ''
      },
      rules: {
        id: [
          {validator: validateId, trigger: 'blur'}
        ],
        password: [
          {validator: validatePassword, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios({
            method: 'POST',
            url: '/api/login',
            data: this.ruleForm
          })
          .then(response => {
            this.$message.success('登录成功')
            localStorage.setItem('token',response.headers['authorization'])
            this.$router.push('/home')
          }
          ,error=>{
            this.$message.error('用户名或密码错误')
            localStorage.setItem('token',null)
          })
        }
        else {
          this.$message.warning('表单错误')
        }
      });
    },
    register() {
      console.log('注册')
    }
  }
}
</script>

<style scoped>
.common-layout {
  height: 100vh;
  background-color: #f0f2f5;
}
.el-form{
  padding: 0;
  background-color: rgba(255,255,255,0.5);
  backdrop-filter: blur(20px);
  border-radius: 20px;
}
</style>