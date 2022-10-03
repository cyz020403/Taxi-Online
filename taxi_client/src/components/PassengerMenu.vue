<template>
  <div>
    <el-menu :default-active="this.$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect" :router="true">
      <el-menu-item index="/takeATaxi">实时打车</el-menu-item>
      <el-menu-item index="/passengerReverseOrder">预约打车</el-menu-item>
      <el-menu-item index="/passengerOrderRecord">订单记录</el-menu-item>
      <el-menu-item index="/passengerInfo">个人信息</el-menu-item>
    </el-menu>
    <router-view></router-view>

  </div>


</template>

<script>
export default {
  data() {
    return {
      activeIndex: '/takeATaxi',
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
