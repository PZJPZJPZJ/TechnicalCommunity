<template>
  <el-header>
    <el-row class="header-box">
      <el-col :span="6">
        <h3>科技论坛</h3>
      </el-col>
      <el-col :span="6">
        <router-link to="/home">热门</router-link>
        <router-link to="/tag">分类</router-link>
        <router-link to="/news">新闻</router-link>
      </el-col>
      <el-col :span="6">
        <div class="mt-4">
          <el-input
              v-model="searchBox"
              placeholder="请输入..."
              class="input-with-select"
          >
            <template #append>
              <el-button type="success">搜索</el-button>
            </template>
          </el-input>
        </div>
      </el-col>
      <el-col :span="6">
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">用户</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="editInfo">用户中心</el-dropdown-item>
              <el-dropdown-item @click="toChat">用户私信</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
  </el-header>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card class="post-card" v-for="news in newsData" :key="news.newsId">
          <el-row>
            <el-col :span="18">
              <div class="title">{{ news.newsTitle }}</div>
              <div class="content">{{ news.newsContent }}</div>
              <div class="time">{{ news.newsTime }}</div>
            </el-col>
            <el-col :span="6">
              <el-image
                  class="card-img"
                  :src="news.newsCover"
                  fit="cover"
              />
            </el-col>
          </el-row>
        </el-card>
        <div v-if="loading" style="text-align: center">Loading...</div>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
  <el-affix position="bottom" :offset="20">
    <el-button v-if="isAdmin" type="warning" style="margin-left: 16px" @click="drawerNews = true">发布新闻</el-button>
  </el-affix>
  <el-drawer v-model="drawerNews" :direction="'btt'" :with-header="false">
    <el-input
        v-model="newsTitle"
        maxlength="50"
        placeholder="新闻标题"
        :autosize="true"
        show-word-limit
        type="text"
    />
    <el-input
        v-model="newsArea"
        maxlength="500"
        placeholder="新闻内容"
        :autosize="true"
        show-word-limit
        type="textarea"
    />
    <input class="custom-upload-button" type="file" ref="fileInput" accept=".jpg,.jpeg,.png" @change="onFileChange">
    <br>
    <el-button type="warning" @click="uploadNews">发布</el-button>
  </el-drawer>
</template>

<script setup>
import {onMounted, ref} from "vue";
import router from "@/router";
import axios from "axios";
import {ElLoading} from "element-plus";

/**
 * 顶栏
 */
const searchBox = ref('')
const searchSelect = ref('')

//跳转私信页面
const toChat = () => {
  router.push('/chat')
}

//跳转用户详情页
const editInfo = () => {
  router.push("/info")
}

//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}

/**
 * 发布
 */
const newsTitle = ref('')
const newsArea = ref('')
//文件信息
const files = ref([]);
//接收到图片选中
const onFileChange = (event) => {
  files.value = event.target.files;
};
const uploadNews = async ()=>{
  const formData = new FormData()
  formData.append('file', files.value)
  formData.append('newsTitle', newsTitle.value)
  formData.append('newsContent', newsArea.value)
  try {
    const response = await axios({
      method: 'POST',
      url: '/api/news/save',
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': localStorage.getItem('token')
      }
    })
  } catch (error) {
    console.error(error);
  }
}

/**
 * 抽屉
 */
const drawerNews = ref(false)

/**
 * 刷新方法
 */
const newsData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/news/page', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }
      , {
        headers: {Authorization: localStorage.getItem('token')}
      })
  newsData.value = [...newsData.value, ...data.rows]
  total.value = data.total
  currentPage.value++
  loading.value = false
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

/**
 * 管理员
 */
//管理员标识
const isAdmin = ref(false)
//检查是否为管理员
const checkAdmin = async () =>{
  await axios({
    method: 'GET',
    url: '/api/user/admin?token=' + localStorage.getItem('token'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        isAdmin.value=true
      }
      , error => {
        isAdmin.value=false
      })
}

/**
 * 加载方法
 */
onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
  checkAdmin()
  loadMoreData().finally(() => {
    loadingInstance.close()
  })
})
</script>

<style scoped>
.post-card {
  margin-bottom: 20px;
  text-align: left;
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
.time {
  font-size: 14px;
}
.custom-upload-button {
  display: inline-block;
  padding: 4px;
  margin: 8px;
  font-size: 14px;
  box-shadow: 0 0 0 1px var(--el-input-border-color, var(--el-border-color)) inset;
  border-radius: 4px;
  cursor: pointer;
}
</style>