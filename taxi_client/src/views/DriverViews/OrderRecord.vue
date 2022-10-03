<template>
  <div>
    <el-card class="wide">

      <div class="title">
        <div class="left">已完成的订单</div>
      </div>

      <el-table
          :data="tableData"
          :key = tableKey
          stripe
          style="width: 100%">

        <el-table-column
            prop="plate"
            label="使用车辆车牌"
            width="140">
        </el-table-column>

        <el-table-column
            prop="userName"
            label="乘客用户名"
            width="140">
        </el-table-column>

        <el-table-column
            prop="startPosition"
            label="上车地点"
            width="280">
        </el-table-column>

        <el-table-column
            prop="endPosition"
            label="下车地点"
            width="280">
        </el-table-column>

        <el-table-column
            prop="startTime"
            label="开始时间"
            width="170">
        </el-table-column>

        <el-table-column
            prop="endTime"
            label="结束时间"
            width="170">
        </el-table-column>

        <el-table-column
            prop="price"
            label="付款金额（元）">
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "OrderRecord",

  data() {
    return {
      tableData: [],
      tableKey: 0,
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
      axios.post("http://localhost:9091/getOrderRecordByDriverUserName", MyData)
          .then(resp=>{
            _this.tableData = resp.data;
            for(let i = 0; i < _this.tableData.length; i++) {
              axios.get('https://restapi.amap.com/v3/geocode/regeo?location='+_this.tableData[i].startLng+','+_this.tableData[i].startLat+
                  '&key=' + MyKey)
                  .then(resp1 => {
                    _this.tableData[i].startPosition = resp1.data.regeocode.formatted_address;
                  })
                  .finally(()=>{
                    _this.tableKey++;
                  })
              axios.get('https://restapi.amap.com/v3/geocode/regeo?location='+_this.tableData[i].endLng+','+_this.tableData[i].endLat+
                  '&key=' + MyKey)
                  .then(resp1 => {
                    _this.tableData[i].endPosition = resp1.data.regeocode.formatted_address;
                  })
                  .finally(()=>{
                    _this.tableKey++;
                  })
            }
          });
    },
  }
}
</script>

<style scoped>

.wide {
  width: 90%;
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