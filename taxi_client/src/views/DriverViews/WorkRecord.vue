<template>
  <div>
    <el-card class="wide">

      <div class="title">
        <div class="left">已完成的工作记录</div>
      </div>

      <el-table
          :data="tableData"
          stripe
          style="width: 100%">
        <el-table-column
            prop="carPlate"
            label="使用车辆车牌"
            width="330">
        </el-table-column>
        <el-table-column
            prop="startTime"
            label="开始时间"
            width="380">
        </el-table-column>
        <el-table-column
            prop="endTime"
            label="结束时间">
        </el-table-column>
      </el-table>

    </el-card>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "WorkRecord",


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
      let MyData = {"userName":_this.userName};
      axios.post("http://localhost:9091/getFinishedWorkByDriverUserName", MyData)
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
  width: 70%;
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