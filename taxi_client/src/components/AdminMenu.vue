<template>
  <div>
    <el-menu :default-active="this.$route.path" class="el-menu-demo" mode="horizontal" @select="handleSelect" :router="true">
<!--      <el-menu-item index="/adminDemo">adminDemo</el-menu-item>-->
      <el-menu-item index="/settleOrder">司机未结算订单及管理</el-menu-item>
      <el-menu-item index="/settleRecord">司机已结算订单记录</el-menu-item>
      <el-menu-item index="/allowWorkRecord">司机接单权限管理</el-menu-item>
      <el-menu-item index="/dataAnalysis">系统数据分析</el-menu-item>
      <el-menu-item index="/adminInfo">个人信息</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>


</template>

<script>
export default {
  data() {
    return {
      activeIndex: '/settleOrder',
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
