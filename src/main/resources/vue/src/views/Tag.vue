<template>
  <HeadBar></HeadBar>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-row class="tag-container">
          <el-card class="post-card" v-for="tag in tagData" :key="tag.tagId" @click="handleViewTag(tag.tagId)">
            <el-image
                class="card-img"
                :src="tag.tagCover"
                fit="fill"
            />
            <p class="card-text" style="height: 50px;line-height: 50px">{{ tag.tagName }}</p>
          </el-card>
        </el-row>

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
    <el-button type="primary" circle style="height: 40px;width: 40px" :icon="Plus"
               @click="drawerTag = true"></el-button>
  </el-affix>
  <el-drawer v-model="drawerTag" :direction="'btt'" :with-header="false">
    <el-input
        v-model="tagTitle"
        maxlength="10"
        placeholder="话题标题"
        show-word-limit
        type="text"
    />
    <input class="custom-upload-button" type="file" ref="fileInput" accept=".jpg,.jpeg,.png" @change="onFileChange">
    <br>
    <el-button type="primary" @click="uploadTag">发布</el-button>
  </el-drawer>
</template>

<script setup>
import {onMounted, ref} from "vue";
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import axios from "axios";
import {ElLoading, ElMessage} from "element-plus";
import HeadBar from "@/components/HeadBar"

//点击跳转对应帖子
const handleViewTag = (postId) => {
  router.push('/detail?id=' + postId)
}

/**
 * 新增标签
 */
const drawerTag = ref(false)
const tagTitle = ref('')
//文件信息
const files = ref([]);
//接收到图片选中
const onFileChange = (event) => {
  files.value = event.target.files[0];
};
const uploadTag = async () => {
  const formData = new FormData()
  formData.append('file', files.value)
  formData.append('tagName', tagTitle.value)
  await axios({
    method: 'POST',
    url: '/api/tag/save',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '新增成功',
          type: 'success',
        })
        //收起抽屉
        drawerTag.value=false
        //清除数据
        tagTitle.value=''
        //刷新
        currentPage.value = 1
        tagData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '新增失败',
          type: 'error',
        })
      })
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
 * 刷新方法
 */
const tagData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(50)
const total = ref(0)
const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/tag/page', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }
      , {
        headers: {Authorization: localStorage.getItem('token')}
      })
  tagData.value = [...tagData.value, ...data.rows]
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
.card-img {
  width: 80px;
  height: 80px;
  border-radius: 10px;
}

.el-card {
  background-color: transparent !important;
  margin: 10px;
  --el-card-padding: 0 !important;
  --el-card-border-color: none !important;
}

.el-card:hover {
  transform: translateY(-2px);
}

.el-card.is-always-shadow {
  box-shadow: none;
}

.tag-container{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.post-card {
  text-align: center;
  width: 145px;
  height: 145px;
  border-radius: 20px;
  cursor: pointer;
}

.card-text {
  text-align: center;
  width: 145px;
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
  cursor: pointer;
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