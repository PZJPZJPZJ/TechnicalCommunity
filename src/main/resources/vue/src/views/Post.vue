<template>
  <el-main>
    <el-row>
      <el-col :span="24">
        <el-card class="post-card">
          <div class="header">
            <el-avatar class="avatar" :src="'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'"
                       :size="30"></el-avatar>
            <div class="user-info">{{ thisPost.userName }}</div>
          </div>
          <div class="title">{{ thisPost.postTitle }}</div>
          <div class="content">{{ thisPost.postContent }}...</div>
          <div class="images">

          </div>
          <div class="post-footer">
            <el-tag class="tag">{{ thisPost.tagName }}</el-tag>
            <div class="time">{{ thisPost.postTime }}</div>
          </div>
        </el-card>
      </el-col>

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
  setup() {
    //获取当前url参数
    const queryString = window.location.search;
    const queryParams = new URLSearchParams(queryString);
    //当前帖子信息
    const thisPost = ref([])
    //帖子图片
    const pictureUrl = ref([])
    //全部评论信息
    const postData = ref([])
    //评论加载状态
    const loading = ref(false)
    //评论分页
    const currentPage = ref(1)
    const pageSize = ref(10)
    //评论总数
    const total = ref(0)
    //新增评论
    const addComment = ref([])

    //获取当前帖子
    const loadThisPost = async () => {
      const {data} = await axios({
        method: 'GET',
        url: '/api/post/show?id=' + queryParams.get('id'),
        headers: {
          Authorization: localStorage.getItem('token')
        }
      })
      //直接覆盖写入数组
      thisPost.value = data.rows
    }

    //加载当前帖子图片
    const loadPicture = async () => {
      const {data} = await axios({
        method: 'GET',
        url: '/api/picture/download?id=' + queryParams.get('id'),
        headers: {
          Authorization: localStorage.getItem('token')
        }
      })
      //直接覆盖写入数组
      pictureUrl.value = data.rows
      console.log(pictureUrl.value)
    }

    const loadMoreData = async () => {
      //正在加载时不再提交请求防止一直处于页面底部刷新
      if (loading.value) return
      //开启加载状态
      loading.value = true
      //提交请求
      const {data} = await axios({
        method: 'POST',
        url: '/api/post/hot',
        data: {
          pageNum: currentPage.value,
          pageSize: pageSize.value,
        },
        headers: {
          Authorization: localStorage.getItem('token')
        }
      })
      //在数组后增加数据
      postData.value = [...postData.value, ...data.rows]
      total.value = data.total
      currentPage.value++
      //禁用加载状态
      loading.value = false
    }

    const handleScroll = () => {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
      const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
      if (scrollTop + windowHeight >= scrollHeight) {
        loadMoreData()
      }
    }

    onMounted(() => {
      window.addEventListener('scroll', handleScroll)
      const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
      loadThisPost()
      loadPicture()
      loadMoreData().finally(() => {
        loadingInstance.close()
      })
    })
    return {
      thisPost,
      postData,
      loading,
      loadMoreData
    }
  }
}
</script>

<style scoped>

</style>