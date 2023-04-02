<template>
  <el-header>
    <el-row class="header-box">
      <el-col :xs="0" :sm="0" :md="2" :lg="4" :xl="4"></el-col>
      <el-col :xs="6" :sm="6" :md="4" :lg="4" :xl="4">
        <h3 style="margin-top: 13px">科技社区</h3>
      </el-col>
      <el-col :xs="12" :sm="12" :md="12" :lg="8" :xl="8">
        <router-link to="/home">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>热门</el-button>
        </router-link>
        <router-link to="/tag">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>板块</el-button>
        </router-link>
        <router-link to="/news">
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" type="primary" link>新闻</el-button>
        </router-link>
      </el-col>
      <el-col :xs="3" :sm="3" :md="2" :lg="2" :xl="2">
        <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="Search" circle @click="changeSearch"></el-button>
      </el-col>
      <el-col :xs="3" :sm="3" :md="2" :lg="2" :xl="2">
        <el-dropdown :hide-on-click="false">
          <el-button style="height: 35px;width: 35px; margin-top: 13px" :icon="User" circle></el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="editInfo">用户中心</el-dropdown-item>
              <el-dropdown-item @click="toChat">私信列表</el-dropdown-item>
              <el-dropdown-item v-if="isAdmin" @click="toAdmin">用户管理</el-dropdown-item>
              <el-dropdown-item @click="logout">注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
      <el-col :xs="0" :sm="0" :md="2" :lg="4" :xl="4"></el-col>
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
              <div class="news-footer">
                <el-popconfirm v-if="isAdmin" title="确定要删除吗？" confirm-button-text="确认" cancel-button-text="取消"
                               @confirm="deleteNews(news.newsId)">
                  <template #reference>
                    <el-button type="danger">删除新闻</el-button>
                  </template>
                </el-popconfirm>
              </div>
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
        <div v-if="loading" style="text-align: center"><el-icon><Loading/></el-icon></div>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
  <el-affix position="bottom" :offset="20">
    <el-button v-if="isAdmin" type="warning" circle style="height: 40px;width: 40px" :icon="Plus" @click="drawerNews = true"></el-button>
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
import {ElLoading, ElMessage} from "element-plus";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'

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
const changeSearch = ()=>{
  router.push('/search')
}


//处理登出
const logout = () => {
  localStorage.setItem('token', null)
  window.location.reload();
}
//管理员点击跳转用户管理
const toAdmin = ()=>{
  router.push('/admin')
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
  files.value = event.target.files[0];
};
const uploadNews = async () => {
  const formData = new FormData()
  formData.append('file', files.value)
  formData.append('newsTitle', newsTitle.value)
  formData.append('newsContent', newsArea.value)
  await axios({
    method: 'POST',
    url: '/api/news/save',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          showClose: true,
          message: '发布成功',
          type: 'success',
        })
        //收起抽屉
        drawerNews.value=false
        //清除数据
        newsTitle.value=''
        newsArea.value=''
        //刷新首页
        currentPage.value = 1
        newsData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          showClose: true,
          message: '发布失败',
          type: 'error',
        })
      })
}

/**
 * 管理
 */
const deleteNews = async (newsId)=>{
  await axios({
    method: 'GET',
    url: '/api/news/delete?id=' + newsId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          showClose: true,
          message: '删除成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        newsData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          showClose: true,
          message: '删除失败',
          type: 'error',
        })
      })
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
const checkAdmin = async () => {
  await axios({
    method: 'GET',
    url: '/api/user/admin?token=' + localStorage.getItem('token'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        isAdmin.value = true
      }
      , error => {
        isAdmin.value = false
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
.news-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 10px;
  margin-right: 10px;
  padding-top: 10px;
  border-top: 1px solid #e4e7ed;
}
</style>