<template>
  <el-header>
    <el-row class="header-box">
      <el-col :span="6">
        <h3>科技论坛</h3>
      </el-col>
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <el-dropdown :hide-on-click="false">
    <span class="el-dropdown-link">用户
    <el-icon class="el-icon--right"><arrow-down/></el-icon>
    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout">注销</el-dropdown-item>
              <el-dropdown-item>Action 2</el-dropdown-item>
              <el-dropdown-item disabled>Action 4</el-dropdown-item>
              <el-dropdown-item divided>Action 5</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
  </el-header>
  <el-main>
    <el-row>
      <el-col :span="24">
        <el-carousel height="150px" arrow="never">
          <el-carousel-item v-for="news in newsData" :key="news" @click="handleViewNews(news.newsId)">
            <el-image
                class="news-img"
                :src="news.newsCover"
                fit="cover"
            />
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
          <el-skeleton :rows="2" />
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
  </el-main>
  <el-affix position="bottom" :offset="20">
    <el-button type="success" style="margin-left: 16px" @click="drawer = true">发帖</el-button>
  </el-affix>
  <el-drawer v-model="drawer" :direction="'btt'" :with-header="false">
    <el-input
        v-model="commentArea"
        maxlength="500"
        placeholder="写评论"
        :autosize="true"
        show-word-limit
        type="textarea"
    />
    <el-button @click="uploadComment()">发布</el-button>
  </el-drawer>
  <el-backtop :right="15" :bottom="15"/>
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
    const newsData = ref([])
    const loading = ref(false)
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    //抽屉开启状态
    const drawer = ref(false)

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

    //加载新闻
    const loadNews = async ()=>{
      const {data} = await axios({
        method: 'GET',
        url: '/api/news/list',
        headers: {
          Authorization: localStorage.getItem('token')
        }
      })
      newsData.value =  data.rows
      console.log(newsData.value)
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

    //加载随机标签
    const loadRandTag = () =>{

    }

    //点击跳转对应帖子
    const handleViewPost = (postId) => {
      router.push(`/post?id=${postId}`)
    }

    //点击跳转对应新闻
    const handleViewNews = (newsId) =>{
      router.push(`/news?id=${newsId}`)
    }

    //加载触发
    onMounted(() => {
      window.addEventListener('scroll', handleScroll)
      const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
      loadNews()
      loadMoreData().finally(() => {
        loadingInstance.close()
      })
    })

    //处理登出
    const logout = () => {
      localStorage.setItem('token', null)
      window.location.reload();
    }

    return {
      postData,
      loading,
      loadMoreData,
      handleViewPost,
      drawer,
      newsData,
      handleViewNews,
      logout
    }
  }
}
</script>

<style scoped>
.side-card {
  margin: 10px;
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

/*自定义顶栏*/
.header-box {
  justify-content: space-between;
}
</style>