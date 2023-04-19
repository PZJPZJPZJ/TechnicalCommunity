<template>
  <el-card class="side-card">
    <h3 style="margin: 15px">为你推荐</h3>
    <div style="cursor: pointer" v-for="tag in tagData" :key="tag.tagId" @click="toTag(tag.tagId)">
      <el-row>
        <el-col :span="8">
          <el-image
              class="card-img"
              :src="tag.tagCover"
              fit="fill"
          />
        </el-col>
        <el-col :span="16">
          <p style="height: 50px;line-height: 50px">{{tag.tagName}}</p>
        </el-col>
      </el-row>
    </div>
  </el-card>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

/**
 * 标签加载
 */
const tagData = ref([])
//加载随机标签
const loadRandTag = async () => {
  const {data} = await axios({
    method: 'GET',
    url: '/api/tag/introduce',
    headers: {
      Authorization: localStorage.getItem('token')
    }
  })
  tagData.value = data.rows
}
const toTag = (tagId)=>{
  router.push('/detail?id='+tagId)
}
onMounted(() => {
  loadRandTag()
})
</script>

<style scoped>
.side-card {
  margin: 10px;
}

.card-img {
  width: 35px;
  height: 35px;
  margin: 8px;
  border-radius: 10px;
}
</style>