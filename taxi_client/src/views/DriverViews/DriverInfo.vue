<template>
  <div>
    <el-card class="wide">
      <el-descriptions class="margin-top" title="个人信息" :column="1" :size="size" border>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{this.userName}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户类型
          </template>
          {{this.userTypeToShow}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            余额
          </template>
          {{this.driver.balance}}
        </el-descriptions-item>

        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            是否允许工作
          </template>
          {{this.allowedWorkToShow}}
        </el-descriptions-item>

      </el-descriptions>
    </el-card>
    <div class="wide">
      <el-button type="danger" @click="exit" class="exitButton">退出登录</el-button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DriverInfo",

  data() {
    return {
      userName:'',
      driver: {
        id: "",
        userName: "",
        balance: "",
        allowedWork: "",
      },
      allowedWorkToShow: "",
      userTypeToShow: "",
      userType: "",
    }
  },

  created() {
    var _this = this;
    _this.userName = localStorage.getItem("userName");
    _this.userType = localStorage.getItem("userType"); // 用1 2 3 表示

    if(_this.userType === "1") _this.userTypeToShow = "乘客";
    else if(_this.userType === "2") _this.userTypeToShow = "司机";
    else _this.userTypeToShow = "管理员";

    let MyData = {"userName": _this.userName};
    axios.post("http://localhost:9091/getOneByUsernameDriver", MyData)
        .then(resp=> {
          console.log(resp);
          _this.driver = resp.data;
          if(_this.driver.allowedWork === "1") {
            _this.allowedWorkToShow = "是"
          }else {
            _this.allowedWorkToShow = "否"
          }
        })
  },

  methods: {
    exit() {
      // 清除token
      localStorage.removeItem("userName");
      localStorage.removeItem("userType");
      this.$router.push({ path: "/" });
      this.$notify({
        title: '提示',
        message: '成功退出登录',
        duration: 1500,
        type: 'success'
      });
    }
  }
}
</script>

<style scoped>
.wide {
  width: 50%;
  margin: 0 auto;
}
.exitButton {
  margin-top: 10px;
  width: 100%;
}
</style>