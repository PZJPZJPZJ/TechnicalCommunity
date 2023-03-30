<template>
  <router-view></router-view>
</template>

<script setup>
import axios from "axios";
import {onMounted} from "vue";
import {useRouter} from "vue-router";

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

onMounted(() => {
  checkLoginStatus()
})

</script>

<style>
/*全局样式*/
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

/*element美化*/
.el-header {
  position: sticky;
  top: 0;
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(20px);
  z-index: 120;
}

.el-overlay {
  background-color: transparent !important;
}

.el-drawer {
  background-color: rgba(255, 255, 255, 0.25) !important;
  backdrop-filter: blur(10px);
  border-radius: 20px;
}

.el-image-viewer__mask {
  background-color: rgba(255, 255, 255, 0.25) !important;
  backdrop-filter: blur(10px);
  opacity: 1 !important;
}

.el-input__wrapper {
  background-color: rgba(255, 255, 255, 0.5) !important;
}

.el-textarea__inner {
  background-color: rgba(255, 255, 255, 0.5) !important;
}

.el-card {
  background-color: rgba(255, 255, 255, 0.25);
  margin-top: 10px;
  height: auto;
}

.el-card:hover {
  background-color: rgba(255, 255, 255, 0.75);
}

.el-input-group__append, .el-input-group__prepend{
  background-color: rgba(255,255,255,0.2) !important;
}

.el-button{
  display: inline-block !important;
}

.el-carousel{
  border-radius: 5px;
}

.el-overlay.is-message-box .el-overlay-message-box{
  backdrop-filter: blur(10px);
  background-color: rgba(0,0,0,0.2);
}

/*滚动指示器美化*/
::-webkit-scrollbar {
  width: 6px;
  background-color: #F5F5F5;
}

::-webkit-scrollbar-thumb {
  background-color: #0003;
  border-radius: 10px;
  transition: all .2s ease-in-out;
}

::-webkit-scrollbar-track {
  border-radius: 10px;
}

/*自定义顶栏*/
.header-box {
  justify-content: space-between;
}
.tag {
  cursor: pointer;
}
.center{
  cursor: pointer;
}
</style>
