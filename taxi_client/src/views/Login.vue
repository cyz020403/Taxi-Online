<template>
  <div class="img">
    <el-card class="box-card" shadow="hover">
      <div class="title">Online Taxi</div>
      <div class="body">
        <el-form
            :model="ruleForm"
            :rules="rules"
            ref="ruleForm"
            label-width="100px"
            class="demo-ruleForm">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="ruleForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" type="password"></el-input>
          </el-form-item>
          <div class="MySwitch">
            <span class="MyFont">
              用户类型
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
            >登录</el-button
            >
            <el-button @click="register">注册</el-button>
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
      },
      rules: {
        userName: [
          {
            required: true,
            message: "请输入账号",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "change" },
        ],
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
    submitForm(formName) {
      /*定义一个this存储当前vue的对象*/
      var _this = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log(_this.ruleForm);
          if(_this.value === "1") { // 乘客
            axios
                .post("http://localhost:9091/passengerLogin", _this.ruleForm)
                .then(function (resp) {
                  var back = resp.data;
                  console.log(back)

                  if (back === false) {
                    _this.$notify.error({
                      title: '提示',
                      message: '用户名或密码错误',
                      duration: 1500,
                    });
                  } else if (back === true) {
                    //将用户的id存储下来
                    // this.$store.commit("userLogin", this.ruleForm.userName);
                    // this.$store.state.userStore.usernum;
                    localStorage.setItem("userName", _this.ruleForm.userName)
                    localStorage.setItem("userType", _this.value)
                    _this.$notify({
                      title: '提示',
                      message: '登录成功',
                      duration: 1500,
                      type: 'success'
                    });
                    _this.$router.push({ path: "/takeATaxi" });
                  }
                });
          }
          else if(_this.value === "2") {
            axios.post("http://localhost:9091/driverLogin", _this.ruleForm)
                .then(resp=>{
                  var back = resp.data;
                  if(back === false) {
                    _this.$notify.error({
                      title: '提示',
                      message: '用户名或密码错误',
                      duration: 1500,
                    })
                  } else if(back === true) {
                    localStorage.setItem("userName", _this.ruleForm.userName)
                    localStorage.setItem("userType", _this.value)
                    _this.$notify({
                      title: '提示',
                      message: '登录成功',
                      duration: 1500,
                      type: 'success'
                    });
                    _this.$router.push({ path: "/startWork" });
                  }
                })
          }
          else {
            axios.post("http://localhost:9091/adminLogin", _this.ruleForm)
                .then(resp=>{
                  var back = resp.data;
                  if(back === false) {
                    _this.$notify.error({
                      title: '提示',
                      message: '用户名或密码错误',
                      duration: 1500,
                    })
                  } else if(back === true) {
                    localStorage.setItem("userName", _this.ruleForm.userName)
                    localStorage.setItem("userType", _this.value)
                    _this.$notify({
                      title: '提示',
                      message: '登录成功',
                      duration: 1500,
                      type: 'success'
                    });
                    _this.$router.push({ path: "/settleOrder" });
                  }
                })
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    register() {
      this.$router.push({ path: "/register" });
    }
  },
};
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
  margin-bottom: 30px;
  font-size: 24px;
  text-align: center;
  font-weight: 600;
  color: #409eff;
}

.box-card {
  width: 550px;
  margin-left: 830px;
  margin-top: 180px;
}

.body {
  margin-right: 50px;
}
.MySwitch {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 30px;
}
.MyFont {
  font-size: 14px;
  color: #606266;
  margin-right: 10px;
}
</style>