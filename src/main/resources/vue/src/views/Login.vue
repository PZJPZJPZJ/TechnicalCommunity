<template>
  <div class="bigBox">
    <div class="box" ref="box">
      <div class="pre-box">
        <h1>科技社区</h1>
        <p>构建良好科技社区新氛围</p>
      </div>
      <!-- 注册盒子 -->
      <div class="register-form">
        <!-- 标题盒子 -->
        <div class="title-box">
          <h1>注册</h1>
        </div>
        <!-- 输入框盒子 -->
        <el-form
            ref="registerFormRef"
            :model="registerForm"
            :rules="rules"
            label-with="5px"
        >
          <el-form-item prop="name" label=" ">
            <el-input
                type="text"
                placeholder="用户名"
                v-model="registerForm.name"
            />
          </el-form-item>
          <el-form-item prop="password" label=" ">
            <el-input
                type="password"
                placeholder="密码"
                v-model="registerForm.password"
            />
          </el-form-item>
          <el-form-item prop="confirmPassword" label=" ">
            <el-input
                type="password"
                placeholder="确认密码"
                v-model="registerForm.confirmPassword"
            />
          </el-form-item>
        </el-form>
        <div class="btn-box">
          <el-button type="warning" @click="register">注册</el-button>
          <p @click="mySwitch">已有账号?</p>
        </div>
      </div>
      <!-- 登录盒子 -->
      <div class="login-form">
        <!-- 标题盒子 -->
        <div class="title-box">
          <h1>登录</h1>
        </div>
        <!-- 输入框盒子 -->
        <el-form
            ref="loginFormRef"
            :model="loginForm"
            :rules="rules"
            label-with="5px"
        >
          <el-form-item prop="id" label=" ">
            <el-input
                type="text"
                placeholder="账号"
                v-model="loginForm.id"
            />
          </el-form-item>
          <el-form-item prop="password" label=" ">
            <el-input
                type="password"
                placeholder="密码"
                v-model="loginForm.password"
            />
          </el-form-item>
        </el-form>
        <div class="btn-box">
          <el-button type="primary" @click="login">登录</el-button>
          <p @click="mySwitch">没有账号?</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {reactive, ref} from 'vue'
import {ElMessage} from 'element-plus'
import {useRouter} from 'vue-router'
import axios from "axios";

export default {
  setup() {
    const router = useRouter()
    const loginFormRef = ref('')
    const registerFormRef = ref('')
    const loginForm = reactive({
      id: '',
      password: ''
    })
    const registerForm = reactive({
      name: '',
      password: '',
      confirmPassword: ''
    })

    const rules = reactive({
      id: [
        {required: true, message: '请输入账号', trigger: 'blur'},
        {min: 6, message: '请输入正确的账号', trigger: 'blur'},
      ],
      name: [
        {required: true, message: '请输入用户名', trigger: 'blur'},
        {min: 3, message: '用户名过短', trigger: 'blur'},
      ],
      password: [
        {required: true, message: '请输入密码', trigger: 'blur'},
        {min: 6, message: '密码长度应该大于6位且小于20位', trigger: 'blur'},
      ],
      confirmPassword: [
        {required: true, message: '请输入确认密码', trigger: 'blur'},
        {min: 6, message: '密码长度应该大于6位且小于20位', trigger: 'blur'},
        {
          validator: (rule, value, callback) => {
            if (value !== registerForm.password) {
              callback(new Error('两次密码输入不一致'));
            } else {
              callback();
            }
          }, trigger: 'blur'
        }
      ],
    })

    let flag = ref(true)
    const mySwitch = () => {
      const pre_box = document.querySelector('.pre-box')
      const img = document.querySelector("#avatar")
      if (flag.value) {
        pre_box.style.transform = "translateX(100%)"
      } else {
        pre_box.style.transform = "translateX(0%)"
      }
      flag.value = !flag.value
    }


    const login = () => {
      loginFormRef.value.validate((valid) => {
        if (valid) {
          axios({
            method: 'POST',
            url: '/api/login',
            data: loginForm
          })
              .then(response => {
                    ElMessage({
                      message: '登录成功',
                      type: 'success',
                    })
                    localStorage.setItem('token', response.headers['authorization'])
                    localStorage.setItem('user', response.data.userinfo.username)
                    router.push('/home')
                  }
                  , error => {
                    ElMessage({
                      message: '用户名或密码错误',
                      type: 'warning',
                    })
                    localStorage.setItem('token', null)
                  })
        } else {
          ElMessage({
            message: '登录信息填写错误',
            type: 'error',
          })
        }
      })
    }

    const register = () => {
      registerFormRef.value.validate((valid) => {
        if (valid) {
          axios({
            method: 'POST',
            url: '/api/user/save',
            data: {
              userName: registerForm.name,
              userPassword: registerForm.password
            }
          }).then(
              response => {
                ElMessage({
                  showClose: true,
                  duration: 0,
                  message: '注册成功，您的账号为' + response.data.rows,
                  type: 'success',
                })
                //切换窗口
                mySwitch()
              }
              , error => {
                ElMessage({
                  showClose: true,
                  message: '注册失败，网络错误',
                  type: 'warning',
                })
                localStorage.setItem('token', null)
              })
        } else {
          ElMessage({
            showClose: true,
            message: '注册信息填写错误，请重新填写',
            type: 'error',
          })
        }
      })
    }
    return {
      loginForm,
      registerForm,
      loginFormRef,
      registerFormRef,
      mySwitch,
      login,
      register,
      rules
    }
  }
}
</script>

<style scoped>
.common-layout {
  height: 40vh;
  background-color: #f0f2f5;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.login-container {
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  margin: auto;
}


input {
  outline: none;
}

.bigBox {
  height: 100vh;
  overflow-x: hidden;
  display: flex;
  background: linear-gradient(to right, #f8e3c5, #d9ecff);
}

/* 最外层的大盒子 */
.box {
  width: 900px;
  height: 500px;
  display: flex;
  position: relative;
  z-index: 2;
  margin: auto;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.6);
  box-shadow: 2px 1px 19px rgba(0, 0, 0, 0.1);
}

/* 滑动的盒子 */
.pre-box {
  width: 50%;
  height: 100%;
  position: absolute;
  left: 0;
  top: 0;
  z-index: 99;
  border-radius: 4px;
  background-color: rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px) saturate(200%);
  box-shadow: 2px 1px 19px rgba(0, 0, 0, 0.1);
  transition: 0.5s ease-in-out;
}

/* 滑动盒子的标题 */
.pre-box h1 {
  margin-top: 150px;
  text-align: center;
  letter-spacing: 5px;
  color: white;
  user-select: none;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

/* 滑动盒子的文字 */
.pre-box p {
  height: 30px;
  line-height: 30px;
  text-align: center;
  margin: 20px 0;
  user-select: none;
  font-weight: bold;
  color: white;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

/* 登录和注册盒子 */
.login-form,
.register-form {
  flex: 1;
  height: 100%;
}

/* 标题盒子 */
.title-box {
  height: 230px;
  line-height: 230px;
}

/* 标题 */
.title-box h1 {
  text-align: center;
  color: white;
  user-select: none;
  letter-spacing: 5px;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.1);
}

/* 输入框盒子 */
.el-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.el-form-item {
  width: 65%;
}

/* 输入框 */
input {
  height: 40px;
  margin-bottom: 20px;
  text-indent: 10px;
  border: 1px solid #fff;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 120px;
  backdrop-filter: blur(10px);
}

input:focus {
  color: #b0cfe9;
}

input:focus::placeholder {
  opacity: 0;
}

/* 按钮盒子 */
.btn-box {
  display: flex;
  justify-content: center;
}

/* 按钮 */
button {
  width: 100px;
  height: 30px;
  margin: 0 7px;
}

/* 按钮文字 */
.btn-box p {
  height: 30px;
  line-height: 30px;
  user-select: none;
  font-size: 14px;
  color: white;
  text-shadow: 0 0 10px rgb(0, 0, 0, 0.5);
}

.btn-box p:hover {
  cursor: pointer;
  border-bottom: 1px solid white;
}
</style>