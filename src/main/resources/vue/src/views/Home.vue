<template>
  <div class="container">
    <el-main>
      <div class="post-list">
        <el-col :xs="0" :sm="4" :md="4" :lg="4" :xl="6"></el-col>
        <el-col :xs="24" :sm="16" :md="16" :lg="16" :xl="12">
          <el-card v-for="post in postData" :key="post.id" @click.native="handleViewPost(post.id)">
            <h3>{{ post.postTitle }}</h3>
            <p>{{ post.postContent }}</p>
            <span>{{ post.postTime }}</span>
          </el-card>
          <div v-if="loading">Loading...</div>
        </el-col>
        <el-col :xs="0" :sm="4" :md="4" :lg="4" :xl="6"></el-col>
      </div>
    </el-main>
  </div>
</template>

<script>

import {ref, reactive, onMounted} from 'vue'
import {ElLoading} from 'element-plus'
import axios from 'axios'

export default {

  setup() {
    const postData = ref([])
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

    const handleViewPost = (postId) => {
      window.location.href = `/post/${postId}`
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
      loadMoreData,
      handleViewPost,
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
  },
}
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
}

.post-list {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.el-card{
  background-color: rgba(255,255,255,0.25);
  border-radius: 20px;
  margin: 10px 0;
}
.el-card:hover{
  background-color: rgba(255,255,255,0.75);
}

.post-item {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ddd;
  cursor: pointer;
}
</style>