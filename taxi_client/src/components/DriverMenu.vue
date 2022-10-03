<template>
  <div>
    <el-menu :default-active="this.$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect" :router="true">
      <el-menu-item index="/startWork">实时接单</el-menu-item>
      <el-menu-item index="/driverReserveOrder">已接预约单</el-menu-item>
      <el-menu-item index="/orderRecord">订单记录</el-menu-item>
      <el-menu-item index="/workRecord">工作记录</el-menu-item>
      <el-menu-item index="/carManage">我的车辆</el-menu-item>
      <el-menu-item index="/driverInfo">个人信息</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>


</template>

<script>
export default {
  data() {
    return {
      activeIndex: '/startWork',
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      this.activeIndex = key;
    }
  }
}
</script>

<style scoped>
.el-menu-demo {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}
</style>
