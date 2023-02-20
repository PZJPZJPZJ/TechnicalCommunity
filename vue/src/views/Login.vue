<template>
  <div class="common-layout">
    <el-container>
      <el-header>

      </el-header>
      <el-main>
        <el-row>
          <el-col :span="8">
            <div class="grid-content ep-bg-purple"/>
          </el-col>
          <el-col :span="8">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                     class="demo-ruleForm">
              <el-form-item label="账号" prop="account">
                <el-input type="text" v-model="ruleForm.account" autocomplete="off"></el-input>
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
          <el-col :span="8">
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
    var validateAccount = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('Pass');
        }
        callback();
      }
    };
    var validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    return {
      ruleForm: {
        account: '',
        password: ''
      },
      rules: {
        account: [
          {validator: validateAccount, trigger: 'blur'}
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
          alert('登录')
          axios.post('http://localhost:8080/user/list').then(res =>{
            this.ruleForm.token=res.data.data().token
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    register() {
      alert('注册')
    }
  }
}
</script>

<style scoped>
.common-layout {
  height: 100vh;
  background-image: url("https://bing.img.run/uhd.php");
}
.el-form{
  padding: 50px 20px;
  background-color: rgba(255,255,255,0.5);
}
</style>