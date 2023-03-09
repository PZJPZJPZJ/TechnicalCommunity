<template>
  <div class="home">
    <el-container height="100%">
      <el-main>
        <div class="chatHome">
          <div class="chatLeft">
            <div class="title">
              <h1><el-icon @click="toHome"><ArrowLeftBold /></el-icon>用户私信</h1>
            </div>
            <div class="online-person">
              <div class="person-cards-wrapper">
                <input class="inputs" v-model="inputUser" @keyup.enter="newChat" placeholder="对方账号"/>
                <div class="personList" v-for="chat in chatList" :key="chat" @click="selectChat(chat.chatId)">
                  <el-badge :value="chat.chatUnread" :max="9" class="item" :hidden=!chat.chatUnread>
                    <div class="person-card">
                      <div class="info">
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
                    <div class="name">顶栏用户名</div>
                    <div class="detail">顶栏描述</div>
                  </div>
                </div>
                <div class="bottom">
                  <div class="chat-content" ref="chatContent">
                    <div class="chat-wrapper" v-for="i in 10" :key="i">
                      <!--                vif-->
                      <div class="chat-friend">
                        <div class="chat-text">对方输入{{ i }}</div>
                        <div class="info-time">
                          <span>对方时间{{ i }}</span>
                        </div>
                      </div>
                      <!--                velse-->
                      <div class="chat-me">
                        <div class="chat-text">我的输入{{ i }}</div>
                        <div class="info-time">
                          <span>我方时间{{ i }}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="chatInputs">
                    <input class="inputs"/>
                    <div class="send boxinput" @click="sendMsg"><el-icon><Promotion /></el-icon></div>
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
import {Promotion, ArrowLeftBold, User, Plus, ChatLineRound, ShoppingCart} from '@element-plus/icons'
import {useRouter} from "vue-router";
import axios from "axios";
import {ElMessage} from "element-plus";

const showChatWindow = ref(true)
const chatList = ref([])
const chatCurrent = ref('')
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
const selectChat = async (chatId)=>{
  chatCurrent.value = chatId
  await axios({
    method: 'GET',
    url: '/api/chat/clear?chatId='+chatId,
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  await getList()

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
        inputUser.value=''
        getList()
      }
      , error => {
        ElMessage({
          message: '输入的账号有误或聊天已存在',
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
  router.push('/home')
}

/**
 * 页面加载
 */
onMounted(() => {
  scrollBottom()
  getList()
});
</script>

<style scoped>
.home {
  width: 90vw;
  height: 90vh;
  background-color: rgb(39, 42, 55, 0.9);
  border-radius: 15px;
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
  background-color: rgb(66, 70, 86);
  border-radius: 15px;
  border: 1px solid rgb(66, 70, 86);
  padding: 10px;
  box-sizing: border-box;
  transition: 0.2s;
  font-size: 20px;
  color: #fff;
  font-weight: 100;
}

.chatHome .chatLeft .online-person .person-cards-wrapper .inputs:focus {
  outline: none;
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
  background-color: rgb(50, 54, 68);
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
  color: #fff;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-bottom: 5px;
}
.person-card .info .info-detail .detail {
  color: #5c6675;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  font-size: 12px;
}
.person-card:hover {
  background-color: #1d90f5;
  transition: 0.3s;
  box-shadow: 0px 0px 10px 0px rgb(0, 136, 255);
}
.person-card:focus {
  background-color: #1d90f5;
  transition: 0.3s;
  box-shadow: 0px 0px 10px 0px rgb(0, 136, 255);
}

.person-card:hover .info .info-detail .detail {
  color: #fff;
}
.activeCard {
  background-color: #1d90f5;
  transition: 0.3s;
  box-shadow: 3px 2px 10px 0px rgb(0, 136, 255);
}
.activeCard .info .info-detail .detail {
  color: #fff;
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
}

.chat-window .top .info-detail .detail {
  color: #9e9e9e;
  font-size: 12px;
  margin-top: 2px;
}

.chat-window .bottom {
  width: 100%;
  height: 70vh;
  background-color: rgb(50, 54, 68);
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
  background-color: rgb(29, 144, 245);
  color: #fff;
}

.chat-window .bottom .chat-content .chat-wrapper .chat-me .chat-text:hover {
  background-color: rgb(26, 129, 219);
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
  background-color: rgb(66, 70, 86);
  border-radius: 15px;
  border: 2px solid rgb(34, 135, 225);
  padding: 10px;
  box-sizing: border-box;
  transition: 0.2s;
  font-size: 20px;
  color: #fff;
  font-weight: 100;
  margin: 0 20px;
}

.chat-window .bottom .chatInputs .inputs:focus {
  outline: none;
}

.chat-window .bottom .chatInputs .send {
  background-color: rgb(29, 144, 245);
  border: 0;
  transition: 0.3s;
  box-shadow: 0px 0px 5px 0px rgb(0, 136, 255);
}

.chat-window .bottom .chatInputs .send:hover {
  box-shadow: 0px 0px 10px 0px rgb(0, 136, 255);
}
</style>