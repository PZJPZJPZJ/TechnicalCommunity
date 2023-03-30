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
          <el-button style="height: 35px;width: 35px; margin: 13px 5px" link>新闻</el-button>
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
        <el-card class="post-card">
          <el-row>
            <el-col :span="6">
              <el-image
                  class="card-img"
                  :src="thisTag.tagCover"
                  fit="cover"
              />
            </el-col>
            <el-col :span="12">
              <h2 style="height: 100px;line-height: 100px">#{{ thisTag.tagName }}#</h2>
            </el-col>
            <el-col :span="6">
              <h4 style="height: 100px;line-height: 100px">总帖数：{{ total }}</h4>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card class="post-card" v-for="post in postData" :key="post.postId">
          <div class="header">
            <el-avatar class="avatar" :src="post.userCover"
                       :size="30"></el-avatar>
            <div class="user-info">{{ post.userName }}</div>
          </div>
          <div class="center" @click="handleViewPost(post.postId)">
            <div class="title">{{ post.postTitle.substring(0, 20) }}</div>
            <div class="content">{{ post.postContent.substring(0, 100) }}...</div>
          </div>
          <div class="post-footer">
            <el-tag class="tag">{{ post.tagName }}</el-tag>
            <div class="time">{{ post.postTime }}</div>
          </div>
        </el-card>
        <div v-if="loading" style="text-align: center">
          <el-icon>
            <Loading/>
          </el-icon>
        </div>
      </el-col>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
    </el-row>
  </el-main>
  <el-affix position="bottom" :offset="20">
    <el-button type="danger" circle style="height: 40px;width: 40px" :icon="Plus" @click="drawer = true"></el-button>
  </el-affix>
  <el-drawer v-model="drawer" :direction="'btt'" :with-header="false" size="50%">
    <el-input
        v-model="newPost.title"
        maxlength="50"
        placeholder="帖子标题"
        :autosize="true"
        show-word-limit
        type="text"
    />
    <el-input
        v-model="newPost.content"
        maxlength="500"
        placeholder="帖子内容"
        :autosize="true"
        show-word-limit
        type="textarea"
    />
    <input class="custom-upload-button" type="file" ref="fileInput" accept=".jpg,.jpeg,.png,.gif" multiple
           @change="onFileChange">
    <br>
    <el-switch
        v-model="isGoods"
        active-text="物品价格"
        inactive-text="发布帖子"
        class="ml-2"
        style="--el-switch-on-color: #13ce66; --el-switch-off-color: #409EFF"
    />
    <el-input-number
        v-if="isGoods"
        v-model="newPost.price"
        :precision="2"
        :step="1"
        :max="9999.99"
        :min="0.01"
        controls-position="right"
    />
    <br>
    <el-button type="danger" @click="uploadPost">发布</el-button>
  </el-drawer>
</template>

<script setup>
import {ref, onMounted, reactive} from 'vue'
import {ElLoading, ElMessage} from 'element-plus'
import axios from 'axios'
import {useRouter} from "vue-router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'

//获取当前url参数
const queryString = window.location.search;
const queryParams = new URLSearchParams(queryString);
//获取当前标签
const thisTag = ref([])
const loadThisTag = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/tag/show?id=' + queryParams.get('id'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  //直接覆盖写入数组
  thisTag.value = data.rows
}

/**
 * 抽屉
 */
const drawer = ref(false)

/**
 * 文件上传
 */
//文件信息
const files = ref([]);
//接收到图片选中
const onFileChange = (event) => {
  files.value = event.target.files;
};
//上传图片
const uploadFiles = async (postId) => {
  // 创建 FormData 对象
  const formData = new FormData();
  // 将选择的文件添加到 FormData 对象中
  for (let i = 0; i < files.value.length; i++) {
    formData.append('files', files.value[i]);
  }
  // 发送请求
  try {
    const response = await axios.post('/api/picture/upload?id=' + postId, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': localStorage.getItem('token')
      },
    });
  } catch (error) {
    console.error(error);
  }
};

/**
 * 刷新方法
 */
const postData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/post/tag?id=' + queryParams.get('id'), {
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
 * 提交表单
 */
//上传新帖子数据
const newPost = reactive({
  title: '',
  content: '',
  tag: '',
  price: ''
})
//判断是否新建帖子为商品
const isGoods = ref(false)
//上传帖子
const uploadPost = async () => {
  //根据token获取用户ID
  const {data} = await axios({
    method: 'GET',
    url: '/api/user/name?token=' + localStorage.getItem('token'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  await axios({
    method: 'POST',
    url: '/api/post/save',
    data: {
      postUser: data,
      postTag: queryParams.get('id'),
      postTitle: newPost.title,
      postContent: newPost.content,
      postPrice: newPost.price
    },
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        //获取到ID后上传图片
        uploadFiles(response.data.rows)
        ElMessage({
          message: '发布成功',
          type: 'success',
        })
        //清除信息
        drawer.value = false
        files.value = []
        newPost.title = ''
        newPost.content = ''
        newPost.tag = ''
        newPost.price = ''
        isGoods.value = false
        //刷新首页
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '输入有误',
          type: 'error',
        })
      }
  )
}

/**
 * 跳转
 */
const router = useRouter()
//点击跳转对应帖子
const handleViewPost = (postId) => {
  router.push('/post?id=' + postId)
}
const changeSearch = ()=>{
  router.push('/search')
}


//管理员点击跳转用户管理
const toAdmin = () => {
  router.push('/admin')
}

/**
 * 顶栏
 */
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
 * 管理员判断
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
  loadThisTag()
  checkAdmin()
  loadMoreData().finally(() => {
    loadingInstance.close()
  })
})
</script>

<style scoped>
.side-card {
  margin: 10px;
}

.card-img {
  width: 80px;
  height: 80px;
  margin: 8px;
  border-radius: 20px;
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