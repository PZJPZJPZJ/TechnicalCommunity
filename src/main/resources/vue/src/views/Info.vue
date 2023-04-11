<template>
  <HeadBar></HeadBar>
  <el-main>
    <el-row>
      <el-col :xs="0" :sm="0" :md="4" :lg="4" :xl="4"></el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="16">
        <el-card :model="myInfo" label-width="100px">
          <el-upload class="avatar-uploader" :http-request="upLoadFile" :show-file-list="false"
                     :before-upload="beforeAvatarUpload">
            <el-avatar class="avatar" :src="myInfo.userCover" :size="100"></el-avatar>
          </el-upload>
          <el-form-item label="账号">
            <el-input v-model="myInfo.userId" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="myInfo.userName"></el-input>
          </el-form-item>
          <el-form-item label="个性签名">
            <el-input v-model="myInfo.userSign"></el-input>
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker v-model="myInfo.userBirth" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="myInfo.userSex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-switch v-model="changePassword" active-text="修改密码" inactive-text="保持密码"/>
          </el-form-item>
          <el-form-item v-if="changePassword" label="修改密码">
            <el-input v-model="myPassword" type="password" show-password></el-input>
          </el-form-item>
          <el-button type="primary" @click="updateMyInfo">保存</el-button>
        </el-card>

        <el-card class="title-card">
          <span v-if="!isAdmin">我的帖子({{ total }})</span>
          <span v-if="isAdmin">全站帖子({{ total }})</span>
        </el-card>

        <el-card class="post-card" v-for="post in postData" :key="post.id">
          <div class="header">
            <el-avatar class="avatar" :src="post.userCover" :size="35"></el-avatar>
            <div class="user-info">{{ post.userName }}</div>
          </div>
          <div class="center" @click="handleViewPost(post.postId)">
            <div class="title">{{ post.postTitle.substring(0, 20) }}</div>
            <div class="content">{{ post.postContent.substring(0, 100) }}...</div>
          </div>
          <div class="near-footer">
            <el-tag class="tag" @click="handleViewTag(post.postTag)">{{ post.tagName }}</el-tag>
            <div class="time">{{ post.postTime }}</div>
          </div>
          <div class="post-footer">
            <div class="topOrBottom" v-if="isAdmin">
              <el-popconfirm v-if="!post.postTop" title="确定要置顶吗？" confirm-button-text="确认"
                             cancel-button-text="取消"
                             @confirm="topPost(post.postId)">
                <template #reference>
                  <el-button type="success">置顶该帖</el-button>
                </template>
              </el-popconfirm>
              <el-popconfirm v-if="post.postTop" title="确定要取消置顶吗？" confirm-button-text="确认"
                             cancel-button-text="取消"
                             @confirm="bottomPost(post.postId)">
                <template #reference>
                  <el-button type="warning">取消置顶</el-button>
                </template>
              </el-popconfirm>
            </div>
            <el-popconfirm title="确定要删除吗？" confirm-button-text="确认" cancel-button-text="取消"
                           @confirm="deletePost(post.postId)">
              <template #reference>
                <el-button type="danger">删除该帖</el-button>
              </template>
            </el-popconfirm>
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
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElLoading, ElMessage} from "element-plus";
import router from "@/router";
import {Search, Loading, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import HeadBar from "@/components/HeadBar"

const myInfo = ref([])
const changePassword = ref(false)
const myPassword = ref('')
const loading = ref(false)
const postData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)


//加载用户信息
const loadMyInfo = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/user/info?id=' + localStorage.getItem('user'),
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  myInfo.value = data.rows
}

//更新用户信息
const updateMyInfo = async () => {
  if (changePassword.value) {
    await axios({
      method: 'POST',
      url: '/api/user/update',
      data: {
        userName: myInfo.value.userName,
        userSign: myInfo.value.userSign,
        userSex: myInfo.value.userSex,
        userBirth: myInfo.value.userBirth,
        userPassword: myPassword.value
      },
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          ElMessage({
            showClose: true,
            message: '修改用户信息成功',
            type: 'success',
          })
          myPassword.value = ''
          changePassword.value = false
        }
        , error => {
          ElMessage({
            showClose: true,
            message: '修改用户信息失败',
            type: 'error',
          })
        }
    )
  } else {
    await axios({
      method: 'POST',
      url: '/api/user/update',
      data: {
        userName: myInfo.value.userName,
        userSign: myInfo.value.userSign,
        userSex: myInfo.value.userSex,
        userBirth: myInfo.value.userBirth
      },
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          ElMessage({
            showClose: true,
            message: '修改用户信息成功',
            type: 'success',
          })
        }
        , error => {
          ElMessage({
            showClose: true,
            message: '修改用户信息失败',
            type: 'error',
          })
        }
    )
  }
}

//用户头像更新
const formData = new FormData()
const upLoadFile = async () => {
  await axios({
    method: 'POST',
    url: '/api/user/cover',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          showClose: true,
          message: '更换头像成功',
          type: 'success',
        })
      }
      , error => {
        ElMessage({
          showClose: true,
          message: '更换头像失败',
          type: 'error',
        })
      })
  await loadMyInfo()
}
const beforeAvatarUpload = (rawFile) => {
  if ((rawFile.type !== 'image/jpeg') && (rawFile.type !== 'image/png')) {
    ElMessage.error('请选择JPG或PNG类型的图片')
    return false;
  } else {
    formData.append('file', rawFile)
    return true;
  }
}

const loadMoreData = async () => {
  if (loading.value) return
  loading.value = true
  const {data} = await axios.post('/api/post/user', {
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

//点击跳转对应帖子
const handleViewPost = (postId) => {
  router.push(`/post?id=${postId}`)
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

//点击跳转对应标签
const handleViewTag = (tagId) => {
  router.push(`/detail?id=${tagId}`)
}


//删除选中帖子
const deletePost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/delete?id=' + postId,
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
        postData.value = []
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

//置顶帖子
const topPost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/top?id=' + postId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          showClose: true,
          message: '置顶成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          showClose: true,
          message: '置顶失败',
          type: 'error',
        })
      })
}

//置顶帖子
const bottomPost = async (postId) => {
  await axios({
    method: 'GET',
    url: '/api/post/bottom?id=' + postId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '取消置顶成功',
          type: 'success',
        })
        //刷新页面
        currentPage.value = 1
        postData.value = []
        loadMoreData()
      }
      , error => {
        ElMessage({
          message: '取消置顶失败',
          type: 'error',
        })
      })
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

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  const loadingInstance = ElLoading.service({text: 'Loading...', fullscreen: true})
  loadMyInfo()
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
  box-shadow: none;
  border: none;
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

.near-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-top: 10px;
  padding-top: 10px;
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

.title-card {
  margin-bottom: 20px;
  text-align: left;
  box-shadow: none;
  border: none;
}
.center{
  cursor: pointer;
}
</style>