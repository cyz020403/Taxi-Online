<template>
 <div>
   <el-card class="wide">

     <div class="title">
       <div class="left">订单结算记录</div>
     </div>

     <el-table
         :data="tableData"
         stripe
         style="width: 100%">

       <el-table-column
           prop="driverUserName"
           label="订单结算司机用户名"
           width="250">
       </el-table-column>

       <el-table-column
           prop="adminUserName"
           label="订单结算管理员用户名"
           width="250">
       </el-table-column>

       <el-table-column
           prop="orderCount"
           label="结算订单数量"
           width="250">
       </el-table-column>

       <el-table-column
           prop="sumPrice"
           label="结算订单总金额"
           width="250">
       </el-table-column>

       <el-table-column
           prop="settleTime"
           label="结算时间">
       </el-table-column>
     </el-table>
   </el-card>
 </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "SettleRecord",
  data() {
    return {
      tableData: [],
      userName: "",
      userType: "",
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
      axios.get("http://localhost:9091/getAllSettleRecord")
          .then(resp=>{
            console.log(resp.data);
            _this.tableData = resp.data;
          });
    },
  }

}
</script>

<style scoped>

.wide {
  width: 80%;
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