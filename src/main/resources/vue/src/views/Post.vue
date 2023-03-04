<template>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <el-card class="post-card" v-for="post in postData" :key="post.id" @click="handleViewPost(post.postId)">
          <div class="header">
            <el-avatar class="avatar" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                       :size="30"></el-avatar>
            <div class="user-info">{{ post.userName }}</div>
          </div>
          <div class="title">{{ post.postTitle.substring(0, 20) }}</div>
          <div class="content">{{ post.postContent.substring(0, 100) }}...</div>
          <div class="images">

          </div>
          <div class="post-footer">
            <el-tag class="tag">{{ post.tagName }}</el-tag>
            <div class="time">{{ post.postTime }}</div>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center">Loading...</div>
      </el-col>

      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4">
        <el-card class="side-card">
          <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        </el-card>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
    <el-backtop :right="10" :bottom="10"/>
  </el-main>
</template>

<script>

import {ref, onMounted} from 'vue'
import {ElLoading} from 'element-plus'
import axios from 'axios'

export default {
  methods: {
    handleViewPost(postId) {
      this.$router.push(`/post?id=${postId}`)
    }
  },
  setup() {
    const postData = ref([])
    const pictureUrl = ref([])
    const loading = ref(false)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)

    const loadMoreData = async () => {
      if (loading.value) return
      loading.value = true
      const {data} = await axios.post('/api/post/hot', {
            pageNum: currentPage.value,
            pageSize: pageSize.value,
          }
          , {
            headers: {Authorization: localStorage.getItem('token')}
          })
      postData.value = [...postData.value, ...data.rows]
      total.value = data.total
      currentPage.value++
      loading.value = false
    }

    const loadPicture = async ()=>{
      const {data} = await axios.post('/api/picture/download')
    }

    onMounted(() => {
      const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
      loadMoreData().finally(() => {
        loadingInstance.close()
      })
    })

    return {
      postData,
      loading,
      loadMoreData
    }
  },
  mounted() {
    window.addEventListener('scroll', () => {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
      const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
      if (scrollTop + windowHeight >= scrollHeight) {
        this.loadMoreData()
      }
    })
  }
}
</script>

<style scoped>

</style>