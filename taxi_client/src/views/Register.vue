<template>
  <div class="img">
    <el-card class="box-card" shadow="hover">
      <div class="title">Online Taxi</div>
      <div class="title">账号注册</div>
      <div class="body">
        <el-form
            :model="ruleForm"
            ref="ruleForm"
            label-width="100px"
            class="demo-ruleForm">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="ruleForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="verifyPassword">
            <el-input v-model="ruleForm.verifyPassword" type="password"></el-input>
          </el-form-item>

          <div class="MySwitch">
            <span class="MyFont">
              注册用户类型
            </span>
            <el-select v-model="value" placeholder="请选择">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')"
            >注册</el-button
            >
            <el-button @click="returnLogin">返回登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      ruleForm: {
        userName: "",
        password: "",
        verifyPassword: "",
        userType: ""
      },
      options: [{
        value: '1',
        label: '乘客'
      }, {
        value: '2',
        label: '司机'
      }, {
        value: '3',
        label: '管理员'
      },],
      value: '1'
    };
  },
  methods: {
    submitForm(form) {
      var _this = this;
      _this.ruleForm.userType = _this.value;
      axios.post("http://localhost:9091/register", _this.ruleForm)
          .then(resp => {
            var condition = resp.data;
            console.log(condition);
            if(condition === 0) {
              _this.$notify.error({
                title: '提示',
                message: '两次输入密码不一致',
                duration: 1500,
              });
            }
            else if(condition === 1) {
              _this.$notify.error({
                title: '提示',
                message: '用户名为空或已被占用',
                duration: 1500,
              });
            }
            else if(condition === 2) {
              _this.$notify({
                title: '提示',
                message: '成功注册，返回登录',
                duration: 1500,
                type: 'success'
              });
              this.$router.push({ path: "/" });
            }
          })
    },
    returnLogin() {
      this.$router.push({ path: "/" });
    }
  }
}
</script>

<style scoped>

.img {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url(../assets/images/xx.png);
  background-repeat: no-repeat;
  background-size: cover;
}

.title {
  margin-top: 10px;
  margin-bottom: 10px;
  font-size: 24px;
  text-align: center;
  font-weight: 600;
  color: #409eff;
}

.box-card {
  width: 550px;
  margin-left: 830px;
  margin-top: 150px;
}

.body {
  margin-right: 50px;
}

.MySwitch {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 3px;
}

.MyFont {
  font-size: 14px;
  color: #606266;
  margin-right: 10px;
}
</style>