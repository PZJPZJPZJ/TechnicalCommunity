<template>
  <router-view></router-view>
</template>

<script>
import axios from "axios";

export default {
  name: 'App',
  mounted() {
    axios({
      method: 'POST',
      url: 'http://localhost/api/user/token',
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
  color: #2c3e50;
  padding: 0;
  height: 100vh;
}
</style>
