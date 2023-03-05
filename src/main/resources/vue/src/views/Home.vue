<template>
  <el-main>
    <el-row>
      <el-col :span="24">
        <el-carousel height="150px" arrow="never">
          <el-carousel-item v-for="item in 4" :key="item">
            <img src="" alt="">
          </el-carousel-item>
        </el-carousel>
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
            <el-image
                class="card-img"
                :src="'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'"
                :zoom-rate="1.2"
                :preview-src-list="srcList"
                :initial-index="4"
                fit="cover"
            />
            <el-image
                class="card-img"
                :src="'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'"
                :zoom-rate="1.2"
                :preview-src-list="srcList"
                :initial-index="4"
                fit="cover"
            />
            <el-image
                class="card-img"
                :src="'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'"
                :zoom-rate="1.2"
                :preview-src-list="srcList"
                :initial-index="4"
                fit="cover"
            />
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
import {useRouter} from "vue-router";

export default {
  setup() {
    const router = useRouter()
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

    //加载帖子图片
    const loadPicture = async ()=>{
      const {data} = await axios.post('/api/picture/download')
    }

    //滚动到底部执行自动刷新
    const handleScroll = () => {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      const windowHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
      const scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
      if (scrollTop + windowHeight >= scrollHeight) {
        loadMoreData()
      }
    }

    //点击跳转对应帖子
    const handleViewPost = (postId) => {
      router.push(`/post?id=${postId}`)
    }

    //加载触发
    onMounted(() => {
      window.addEventListener('scroll', handleScroll)
      const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
      loadMoreData().finally(() => {
        loadingInstance.close()
      })
    })

    return {
      postData,
      loading,
      loadMoreData,
      handleViewPost
    }
  }
}
</script>

<style scoped>
.el-card {
  background-color: rgba(255, 255, 255, 0.25);
  margin-top: 10px;
  height: 300px;
}

.el-card:hover {
  background-color: rgba(255, 255, 255, 0.75);
  transform: translateY(-1px);
  cursor: pointer;
}

.side-card {
  background-color: rgba(255, 255, 255, 0.25);
  margin: 10px;
  height: 500px;
}

.card-img{
  width: 80px;
  height: 80px;
  margin: 8px;
}

.post-card {
  margin-bottom: 20px;
  text-align: left;
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.avatar {
  margin-right: 10px;
}

.user-info {
  font-size: 14px;
  font-weight: bold;
}

.title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.content {
  font-size: 16px;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.images {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 10px;
}

.image {
  width: 100px;
  height: 100px;
  margin-right: 10px;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid #e4e7ed;
}

.tag {
  font-size: 14px;
  margin-right: 10px;
}

.time {
  font-size: 14px;
}
</style>