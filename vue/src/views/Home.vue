<template>
  <div class="app">
    <div class="nav-bar"></div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      activeName: 'first',
      count: 0
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    load () {
      this.count += 10
    }
  }
}
</script>

<style scoped>
.el-tabs__header{
  margin: 0;
}
.el-tab-pane{
  background-color: rgba(255,255,255,0.5);
  overflow: visible;
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
  height: 100px;
  border-radius: 20px;
  background: rgba(255,255,255,0.75);
  backdrop-filter: saturate(200%) blur(20px);
  margin: 10px;
  color: var(--el-color-primary);
}
.infinite-list .infinite-list-item + .list-item {
  margin-top: 10px;
}
</style>