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
              <el-dropdown-item>Action 3</el-dropdown-item>
              <el-dropdown-item disabled>Action 4</el-dropdown-item>
              <el-dropdown-item divided>Action 5</el-dropdown-item>
              <el-dropdown-item divided>Action 6</el-dropdown-item>
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

export default {
  name: 'App',
  mounted() {
    axios({
      method: 'POST',
      url: '/api/user/token',
      data: {
        'token': localStorage.getItem('token')
      }
    })
        .then(response => {
              this.$router.push('/home')
            }
            , error => {
              this.$router.push('/login')
            })
  },
  setup(){
    const logout = () =>{
      localStorage.setItem('token',null)
      window.location.reload();
    }
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
