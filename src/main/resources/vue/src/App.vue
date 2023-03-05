<template>
  <el-header>
    <el-row type="flex" justify="space-between">
      <el-col :span="6">
        <h3>科技论坛</h3>
      </el-col>
      <el-col :span="6">
        <el-dropdown :hide-on-click="false">
    <span class="el-dropdown-link">用户
    <el-icon class="el-icon--right"><arrow-down /></el-icon>
    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>
                <el-button @click="logout">注销</el-button>
              </el-dropdown-item>
              <el-dropdown-item>Action 2</el-dropdown-item>
              <el-dropdown-item disabled>Action 4</el-dropdown-item>
              <el-dropdown-item divided>Action 5</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
  </el-header>
  <router-view></router-view>
</template>

<script>
import axios from "axios";
import {onMounted} from "vue";
import {useRouter} from "vue-router";

export default {
  setup(){
    const router = useRouter()

    //检查登陆状态
    const checkLoginStatus = () => {
      axios({
        method: 'POST',
        url: '/api/user/token',
        data: {
          'token': localStorage.getItem('token')
        }
      }).then(response => {
        router.push('/home')
      }, error => {
        router.push('/login')
      })
    }

    //处理登出
    const logout = () =>{
      localStorage.setItem('token',null)
      window.location.reload();
    }

    onMounted(() => {
      checkLoginStatus()
    })

    return{
      logout
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  background-color: #F5F5F5;
  color: #2c3e50;
  padding: 0;
}

.el-header {
  position: sticky;
  top: 0;
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(20px);
  z-index: 120;
}

.el-overlay{
  background-color: transparent !important;
}
.el-drawer{
  background-color: rgba(255,255,255,0.25) !important;
  backdrop-filter: blur(10px);
  border-radius: 20px;
}

.el-image-viewer__mask{
  background-color: rgba(255,255,255,0.25) !important;
  backdrop-filter: blur(10px);
  opacity: 1 !important;
}

.el-textarea__inner{
  background-color: rgba(255,255,255,0.5) !important;
}

::-webkit-scrollbar {
  width: 6px;
  height: 8px;
  background-color: #ebeef5;
}
::-webkit-scrollbar-thumb {
  box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
  background-color: #ccc;
}
::-webkit-scrollbar-track{
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 3px;
  background: rgba(255, 255, 255, 1);
}
</style>
