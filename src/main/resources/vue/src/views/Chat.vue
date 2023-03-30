<template>
  <div class="home">
    <el-container height="100%">
      <el-main>
        <div class="chatHome">
          <div class="chatLeft">
            <div class="title">
              <el-button type="success" @click="toHome"><el-icon><ArrowLeftBold /></el-icon></el-button>
              <h1>用户私信</h1>
            </div>
            <div class="online-person">
              <div class="person-cards-wrapper">
                <input class="inputs" v-model="inputUser" @keyup.enter="newChat" placeholder="对方账号"/>
                <div class="personList" v-for="chat in chatList" :key="chat" @click="selectChat(chat.chatId,chat.userName,chat.userCover,chat.userSign)">
                  <el-badge :value="chat.chatUnread" :max="9" class="item" :hidden=!chat.chatUnread>
                    <div class="person-card">
                      <div class="info">
                        <el-avatar class="avatar" :src="chat.userCover" :size="50"></el-avatar>
                        <div class="info-detail">
                          <div class="name">{{ chat.userName }}</div>
                          <div class="detail">{{ chat.userSign }}</div>
                        </div>
                      </div>
                    </div>
                  </el-badge>
                </div>
              </div>
            </div>
          </div>
          <div class="chatRight">
            <div v-if="showChatWindow">
              <div class="chat-window">
                <div class="top">
                  <div class="info-detail">
                    <div class="name">
                      <el-avatar class="avatar" v-if="nameCurrent!==''" :src="coverCurrent" :size="25"></el-avatar>
                      {{ nameCurrent }}
                      <el-popconfirm v-if="nameCurrent!==''" title="对方列表也会同时删除，确定要继续吗？" @confirm="deleteChat">
                        <template #reference>
                          <el-icon><Delete /></el-icon>
                        </template>
                      </el-popconfirm>
                    </div>
                    <div class="detail">{{ signCurrent }}</div>
                  </div>
                </div>
                <div class="bottom">
                  <div class="chat-content" ref="chatContent">
                    <div class="chat-wrapper" v-for="message in messageList" :key="message.messageId">
                      <div class="chat-friend" v-if="!(message.messageUser == loginUser)">
                        <div class="chat-text">{{message.messageContent}}</div>
                        <div class="info-time"><span>{{ message.messageTime }}</span></div>
                      </div>
                      <div class="chat-me" v-if="message.messageUser == loginUser">
                        <div class="chat-text">{{message.messageContent}}</div>
                        <div class="info-time"><span>{{ message.messageTime }}</span></div>
                      </div>
                    </div>
                  </div>
                  <div class="chatInputs">
                    <input class="inputs" v-model="inputMsg"/>
                    <div class="send boxinput" @click="sendMsg" @keyup.enter="sendMsg"><el-icon><Promotion /></el-icon></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="showIcon" v-else>
              <p>无聊天</p>
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {Promotion, ArrowLeftBold, Delete} from '@element-plus/icons'
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const showChatWindow = ref(true)
const chatList = ref([])
const chatCurrent = ref('')
const nameCurrent = ref('')
const signCurrent = ref('')
const coverCurrent = ref('')
const loginUser = ref('')
//获取当前url参数
const queryString = window.location.search;
const queryParams = new URLSearchParams(queryString);
const getList = async ()=>{
  const {data} = await axios({
    method: 'GET',
    url: '/api/chat/list',
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  chatList.value=data.rows
}
const selectChat = async (chatId,userName,userCover,userSign)=>{
  chatCurrent.value = chatId
  nameCurrent.value = userName
  signCurrent.value = userSign
  coverCurrent.value = userCover
  await axios({
    method: 'GET',
    url: '/api/chat/clear?chatId='+chatId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  await getList()
  await getMessage()
  await scrollBottom()
}
const messageList = ref([])
const getMessage = async ()=>{
  const {data} = await axios({
    method: 'GET',
    url: '/api/message/list?id='+chatCurrent.value,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  messageList.value=data.rows
}

/**
 * 新建聊天
 */
const inputUser = ref('')
const newChat = async ()=>{
  await axios({
    method: 'GET',
    url: '/api/chat/save?id='+inputUser.value,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        getList()
      }
      , error => {
        ElMessage({
          message: '输入的账号有误或聊天已存在',
          type: 'warning',
        })
      }
  )
}

/**
 * 新建消息
 */
const inputMsg = ref('')
const sendMsg = async ()=>{
  if (inputMsg.value===''){
    ElMessage({
      message: '输入不能为空',
      type: 'warning',
    })
  }
  else{
    await axios({
      method: 'POST',
      url: '/api/message/save',
      data: {
        messageChat: chatCurrent.value,
        messageContent: inputMsg.value
      },
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          inputMsg.value=''
          getMessage()
          setTimeout(() => {
            scrollBottom()
          }, 500);
        }
        , error => {
          ElMessage({
            message: '发送失败',
            type: 'error',
          })
        }
    )
  }
}

/**
 * 删除聊天
 */
const deleteChat = async ()=>{
  await axios({
    method: 'GET',
    url: '/api/chat/delete?id='+chatCurrent.value,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  }).then(
      response => {
        ElMessage({
          message: '删除成功',
          type: 'success',
        })
        getList()
        chatCurrent.value = ''
        nameCurrent.value = ''
        signCurrent.value = ''
        coverCurrent.value = ''
        messageList.value = []
      }
      , error => {
        ElMessage({
          message: '删除失败',
          type: 'error',
        })
      }
  )
}

/**
 * 滚动到底部
 */
const chatContent = ref(null);
const scrollBottom = ()=>{
  chatContent.value.scrollTop = chatContent.value.scrollHeight;
}

/**
 * 跳转
 */
const router = useRouter()
const toHome = ()=>{
  router.back()
}

/**
 * 页面加载
 */
onMounted(() => {
  scrollBottom()
  getList()
  loginUser.value=localStorage.getItem('user')
  if (queryParams.get('id')){
    axios({
      method: 'GET',
      url: '/api/chat/save?id='+queryParams.get('id'),
      headers: {
        Authorization: localStorage.getItem('token')
      }
    }).then(
        response => {
          ElMessage({
            showClose: true,
            message: '购买前请先与卖家咨询商品情况,对方账号为'+queryParams.get('id'),
            type: 'info',
          })
          getList()
        }
        , error => {
          ElMessage({
            showClose: true,
            message: '购买前请先与卖家咨询商品情况,对方账号为'+queryParams.get('id'),
            type: 'info',
          })
        }
    )
  }
});
</script>

<style scoped>
.home {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(to right, #95d475, #b3e19d);
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.head-portrait img {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  box-sizing: border-box;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  vertical-align: middle;
}

.chatHome {
  display: flex;
}
.chatHome .chatLeft {
  width: 280px;
}
.chatHome .chatLeft .title {
  color: #fff;
  padding-left: 10px;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.25);
}
.chatHome .chatLeft .online-person {
  margin-top: 85px;
}

.chatHome .chatLeft .online-person .person-cards-wrapper {
  padding-left: 10px;
  height: 65vh;
  margin-top: 20px;
  overflow: hidden;
  overflow-y: scroll;
  box-sizing: border-box;
}
.chatHome .chatLeft .online-person .person-cards-wrapper::-webkit-scrollbar {
  width: 0;
  height: 0;
  display: none;
}

.chatHome .chatLeft .online-person .person-cards-wrapper .inputs {
  width: 90%;
  height: 50px;
  background-color: rgb(255, 255, 255,0.5);
  border-radius: 15px;
  border: 0;
  padding: 10px;
  box-sizing: border-box;
  transition: 0.2s;
  font-size: 20px;
  color: #000000;
  font-weight: 100;
}

.chatHome .chatLeft .online-person .person-cards-wrapper .inputs:focus {
  outline: none;
  border: 2px solid #67C23A;
}

.chatHome .chatRight {
  flex: 1;
  padding-right: 30px;
}
.chatHome .chatRight .showIcon {
  position: absolute;
  top: calc(50% - 150px); /*垂直居中 */
  left: calc(50% - 50px); /*水平居中 */
}
.chatHome .chatRight .showIcon .icon-snapchat {
  width: 300px;
  height: 300px;
  font-size: 300px;
}

.person-card {
  width: 250px;
  height: 80px;
  border-radius: 10px;
  background-color: rgba(255,255,255,0.5);
  position: relative;
  margin: 25px 0;
  cursor: pointer;
}
.person-card .info {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 90%;
  transform: translate(-50%, -50%);
  overflow: hidden;
  display: flex;
}
.person-card .info .info-detail {
  margin-top: 5px;
  margin-left: 20px;
}
.person-card .info .info-detail .name {
  color: #000;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-bottom: 5px;
}
.person-card .info .info-detail .detail {
  color: #000000;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-size: 12px;
}
.person-card:hover {
  background-color: #d1edc4;
  transition: 0.3s;
  box-shadow: 0px 0px 10px 0px #d1edc4;
}
.person-card:focus {
  background-color: #d1edc4;
  transition: 0.3s;
  box-shadow: 0px 0px 10px 0px #d1edc4;
}

.person-card:hover .info .info-detail .detail {
  color: #000;
}
.activeCard {
  background-color: #529b2e;
  transition: 0.3s;
  box-shadow: 3px 2px 10px 0px rgb(0, 136, 255);
}
.activeCard .info .info-detail .detail {
  color: #ffffff;
}

.chat-window {
  width: 100%;
  height: 100%;
  margin-left: 20px;
  position: relative;
}

.chat-window .top {
  margin-bottom: 50px;
}

.chat-window .top::after {
  content: "";
  display: block;
  clear: both;
}

.chat-window .top .info-detail {
  float: left;
  margin: 5px 20px 0;
}

.chat-window .top .info-detail .name {
  font-size: 20px;
  font-weight: 600;
  color: #fff;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.25);
}

.chat-window .top .info-detail .detail {
  color: #ffffff;
  font-size: 12px;
  margin-top: 2px;
  text-shadow: 4px 4px 3px rgba(0, 0, 0, 0.25);
}

.chat-window .bottom {
  width: 100%;
  height: 80vh;
  background-color: rgb(255, 255, 255,0.5);
  border-radius: 20px;
  padding: 20px;
  box-sizing: border-box;
  position: relative;
}

.chat-window .bottom .chat-content {
  width: 100%;
  height: 85%;
  overflow-y: scroll;
  padding: 20px;
  box-sizing: border-box;
  scroll-behavior: smooth;
}

.chat-window .bottom .chat-content::-webkit-scrollbar {
  width: 0;
  height: 0;
  display: none;
}

.chat-window .bottom .chat-content .chat-wrapper {
  position: relative;
  word-break: break-all;
  transition: all 0.3s;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-friend {
  width: 100%;
  float: left;
  margin-bottom: 20px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-friend .chat-text {
  max-width: 90%;
  padding: 20px;
  border-radius: 20px 20px 20px 5px;
  background-color: rgb(56, 60, 75);
  color: #fff;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-friend .chat-text:hover {
  background-color: rgb(39, 42, 55);
}

.chat-window .bottom .chat-content .chat-wrapper .chat-friend .info-time {
  margin: 10px 0;
  color: #fff;
  font-size: 14px;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-friend .info-time span:last-child {
  color: rgb(101, 104, 115);
  margin-left: 10px;
  vertical-align: middle;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me {
  width: 100%;
  float: right;
  margin-bottom: 20px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .chat-text {
  float: right;
  max-width: 90%;
  padding: 20px;
  border-radius: 20px 20px 5px 20px;
  background-color: #67C23A;
  color: #fff;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .chat-text:hover {
  background-color: #529b2e;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .info-time {
  margin: 10px 0;
  color: #fff;
  font-size: 14px;
  display: flex;
  justify-content: flex-end;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .info-time span {
  line-height: 30px;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .info-time span:first-child {
  color: rgb(101, 104, 115);
  margin-right: 10px;
  vertical-align: middle;
}

.chat-window .bottom .chatInputs {
  width: 90%;
  position: absolute;
  bottom: 0;
  margin: 3%;
  display: flex;
}

.chat-window .bottom .chatInputs .boxinput {
  width: 50px;
  height: 50px;
  line-height: 50px;
  background-color: rgb(66, 70, 86);
  border-radius: 15px;
  border: 1px solid rgb(80, 85, 103);
  position: relative;
  cursor: pointer;
}

.chat-window .bottom .chatInputs .inputs {
  width: 90%;
  height: 50px;
  background-color: rgba(255, 255, 255,0.5);
  border-radius: 15px;
  border: 0;
  padding: 10px;
  box-sizing: border-box;
  transition: 0.3s;
  font-size: 20px;
  color: #000000;
  font-weight: 100;
  margin: 0 20px;
}

.chat-window .bottom .chatInputs .inputs:focus {
  outline: none;
  border: 2px solid #67C23A;
}

.chat-window .bottom .chatInputs .send {
  background-color: rgba(255,255,255,0.5);
  border: 0;
  transition: 0.3s;
}

.chat-window .bottom .chatInputs .send:hover {
  box-shadow: 0px 0px 10px 0px  #67C23A;
}
</style>