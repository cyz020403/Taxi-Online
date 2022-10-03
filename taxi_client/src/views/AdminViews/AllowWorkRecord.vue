<template>
 <div>
   <el-card class="wide">

     <div class="title">
       <div class="left">司机工作权限管理</div>
     </div>

     <el-table
         :data="tableData"
         :key = tableKey
         stripe
         style="width: 100%">

       <el-table-column
           prop="driverUserName"
           label="授予权限的司机用户名"
           width="300">
       </el-table-column>

       <el-table-column
           prop="driverAllowedWork"
           label="是否已拥有接单权限"
           width="300">
       </el-table-column>

       <el-table-column
           prop="adminUserName"
           label="授予权限的管理员用户名"
           width="300">
       </el-table-column>

       <el-table-column
           label="操作">
         <template slot-scope="scope">
           <el-button type="primary" @click="givePermission(scope.row)" :disabled="scope.row.buttonDisable">赋予司机接单权限</el-button>
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
  name: "AllowWorkRecord",
  data() {
    return {
      tableData: [],
      userName: "",
      userType: "",
      tableKey:"",
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
      axios.get("http://localhost:9091/getAllAllowWorkRecord")
          .then(resp=>{
            console.log(resp.data);
            _this.tableData = resp.data;
            for(var i = 0; i < _this.tableData.length; i++) {
              if(_this.tableData[i].driverAllowedWork === true) {
                _this.tableData[i].driverAllowedWork = '是';
                _this.tableData[i].buttonDisable = true;
              } else {
                _this.tableData[i].driverAllowedWork = '否';
                _this.tableData[i].buttonDisable = false;
              }

            }
          });
    },

    givePermission(row) {
      var _this = this;
      let MyData = {"driverUserName": row.driverUserName,
                    "adminUserName":_this.userName};
      axios.post("http://localhost:9091/givePermissionByDriverUserName", MyData)
          .then(resp=>{
            _this.getTableData();
          })
    }
  }

}
</script>

<style scoped>

.wide {
  width: 75%;
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