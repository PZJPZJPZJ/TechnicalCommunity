<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="用户管理" name="first">
        <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
          <li v-for="i in count" class="infinite-list-item">{{ i }}</li>
        </ul>
      </el-tab-pane>
      <el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>
      <el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>
      <el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      activeName: 'first',
      count: 0
    };
  },
  methods: {
    load () {
      this.count += 2
    },
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}

</script>

<style scoped>
.el-tabs{
  backdrop-filter: blur(20px);
  background-color: rgba(255,255,255,0.5);
  position: sticky;
  top: 0;
}
</style>