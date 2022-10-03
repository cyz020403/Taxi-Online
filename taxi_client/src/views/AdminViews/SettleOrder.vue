<template>
  <div>
    <el-card class="wide">

      <div class="title">
        <div class="left">未结算的司机订单</div>
      </div>

      <el-table
          :data="tableData"
          stripe
          style="width: 100%">

        <el-table-column
            prop="driverUserName"
            label="司机用户名"
            width="250">
        </el-table-column>

        <el-table-column
            prop="count"
            label="未结算订单数量"
            width="250">
        </el-table-column>

        <el-table-column
            prop="sumPrice"
            label="未结算总金额（元）"
            width="250">
        </el-table-column>

        <el-table-column
            label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="settle(scope.row)" :disabled="scope.row.buttonDisable">结算当前司机订单</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "SettleOrder",

  data() {
    return {
      tableData: [],
      userName: "",
      userType: ""
    }
  },

  created() {
    this.userName = localStorage.getItem("userName");
    this.userType = localStorage.getItem("userType"); // 用1 2 3 表示
    this.getTableData();
  },

  methods: {
    getTableData() {
      var _this = this;
      axios.get("http://localhost:9091/getAllUnsettledOrderGroupByDriver")
          .then(resp => {
            console.log(resp.data);
            _this.tableData = resp.data;
          });
    },

    settle(row) {
      var _this = this;
      let MyData = {adminUserName: _this.userName, driverUserName: row.driverUserName};
      axios.post("http://localhost:9091/settleByDriverUserName", MyData)
          .then(resp=>{
            _this.getTableData();
          })
    }
  }
}
</script>

<style scoped>

.wide {
  width: 65%;
  margin: 0 auto;
}

.title {
  margin-bottom: 30px;
}

.left {
  margin-left: 10px;
  float: left;
  font-weight: bold;
}
</style>