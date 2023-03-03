<template>
  <div class="container">

    <el-main>
      <el-row type="flex" justify="space-between">
        <el-col :span="24">
          <el-carousel height="150px">
            <el-carousel-item v-for="item in 4" :key="item">
              <h3 class="small justify-center" text="2xl">{{ item }}</h3>
            </el-carousel-item>
          </el-carousel>
        </el-col>
        <el-col :span="24">
          <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
            <li v-for="i in count" :key="i" class="infinite-list-item">{{ postData[i] }}</li>
          </ul>
        </el-col>
        <el-col :span="4"></el-col>
        <el-col :span="16">
<!--          <el-table-->
<!--              v-infinite-scroll="loadMoreData"-->
<!--              :data="postData"-->
<!--              :row-key="row => row.id"-->
<!--              :max-height="tableHeight"-->
<!--              @row-dblclick="handleRowDblclick"-->
<!--          >-->
<!--            <el-table-column prop="postTitle"></el-table-column>-->
<!--            <el-table-column prop="postContent"></el-table-column>-->
<!--            <el-table-column prop="postTime"></el-table-column>-->
<!--            <el-table-column>-->
<!--              <template #default="{ row }">-->
<!--                <el-button @click="handleViewPost(row.id)">查看</el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--          </el-table>-->
          <div v-if="loading" class="loading">
            <span class="el-icon-loading"></span>
          </div>
        </el-col>
        <el-col :span="4"></el-col>
      </el-row>



    </el-main>
  </div>
</template>

<script>
import {ref} from 'vue'
import axios from 'axios'
import {ElHeader, ElMain, ElRow, ElCol, ElTable, ElTableColumn, ElButton} from 'element-plus'
import {useRouter} from 'vue-router'

export default {
  components: {
    ElHeader,
    ElMain,
    ElRow,
    ElCol,
    ElTable,
    ElTableColumn,
    ElButton,
  },
  setup() {
    const postData = ref([])
    const currentPage = ref(1)
    const pageSize = ref(5)
    const total = ref(0)
    const tableHeight = ref(1000)
    const loading = ref(false)

    const router = useRouter()

    // const loadMoreData = async () => {
    //   if (postData.value.length < total.value) {
    //     currentPage.value++
    //     const {data} = await axios.post('/api/post/hot', {
    //       pageNum: currentPage.value,
    //       pageSize: pageSize.value,
    //     }, {
    //       headers: {Authorization: localStorage.getItem('token')}
    //     })
    //     postData.value = [...postData.value, ...data.rows]
    //   }
    // }
    // const handleRowDblclick = (row) => {
    //   console.log(row)
    // }
    // const handleViewPost = (postId) => {
    //   router.push(`/post/${postId}`)
    // }

    const count = ref(0)
    const load = async () =>{
      loading.value = true
      const {data} = await axios.post('/api/post/hot', {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
      }, {
        headers: {Authorization: localStorage.getItem('token')}
      })
      postData.value = [...postData.value, ...data.rows]
      console.log({data}.data.total)
      count.value += 5
      currentPage.value++
      loading.value = false
    }

    return {
      postData,
      tableHeight,
      // handleRowDblclick,
      // handleViewPost,
      // loadMoreData,
      loading,
      count,
      load
    }
  },
}
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #f0f2f5;
}



.el-main {

}
.infinite-list {
  height: 80vh;
  padding: 0;
  margin: 0;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  width: 80%;
  background: var(--el-color-primary-light-8);
  margin: 30px;
  border-radius: 20px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>