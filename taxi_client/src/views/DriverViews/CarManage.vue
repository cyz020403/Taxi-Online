<template>
  <div>
    <el-card class="wide">

      <div class="title">
        <div class="left">车辆管理菜单</div>
        <div class="right">
          <el-button type="primary" @click="dialogFormVisible = true">添加车辆</el-button>
        </div>
      </div>

      <el-table
          :data="tableData"
          :key = tableKey
          stripe
          style="width: 100%"
          >
        <el-table-column prop="plate"  label="车牌号" width="180">
        </el-table-column>
        <el-table-column prop="position"  label="位置" width="700">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="danger" @click="deleteCar(scope.$index,scope.row)">删除该车辆信息</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>


    <el-dialog title="添加车辆" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="addCarForm">
        <el-form-item label="车牌号" label-width = "80px">
          <el-input v-model="addCarForm.plate"></el-input>
        </el-form-item>
        <el-form-item label="位置" label-width = "80px">
          <el-input v-model="addCarForm.position"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCar">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  data() {
    return {
      tableData: [],
      tableKey: 0,
      userName: "",
      userType: "",
      dialogFormVisible: false,
      addCarForm: [{
        plate: "",
        position: "",
        lng: "",
        lat: "",
      }]
    }
  },

  created() {
    this.userName = localStorage.getItem("userName");
    this.userType = localStorage.getItem("userType"); // 用1 2 3 表示
    this.getTableDate();
  },
  methods: {
    getTableDate() {
      var _this = this;
      let MyData = {"userName": _this.userName};
      axios.post("http://localhost:9091/getAllCarsByManager", MyData)
          .then(resp => {
            // console.log(resp);
            _this.tableData = resp.data;
            for(let i = 0; i < _this.tableData.length; i++) {
              axios.get('https://restapi.amap.com/v3/geocode/regeo?location='+_this.tableData[i].lng+','+_this.tableData[i].lat+
                  '&key=' + MyKey)
                  .then(resp1 => {
                    // console.log(resp1.data.regeocode.formatted_address);
                    _this.tableData[i].position = resp1.data.regeocode.formatted_address;
                    // console.log(_this.tableData[i].position);
                  })
                  .finally(()=>{
                    _this.tableKey++;
                  })
            }
          })
          .finally(()=>{
            _this.tableKey++;
          })
    },
    addCar() {
      var _this = this;
      axios.post("https://restapi.amap.com/v3/geocode/geo?address="+this.addCarForm.position+"&key="+MyKey+"&city=济南")
          .then(resp=>{
            console.log(resp.data.geocodes[0].location);
            var location = resp.data.geocodes[0].location;
            var lng = location.split(',')[0];
            var lat = location.split(',')[1];
            let MyData = {"plate": _this.addCarForm.plate,
                          "manager": _this.userName,
                          "lng": lng,
                          "lat": lat}
            axios.post("http://localhost:9091/insertCar", MyData)
                .then(res=>{
                  var judge = res.data;
                  if(judge === 1) {
                    _this.$notify({
                      title: '提示',
                      message: '添加完成',
                      duration: 1500,
                      type: 'success'
                    });
                    _this.dialogFormVisible = false;
                    _this.getTableDate();
                    _this.addCarForm = [];
                  } else {
                    _this.$notify.error({
                      title: '提示',
                      message: '输入为空或车辆已存在',
                      duration: 1500,
                    });
                  }
                })
          })
    },
    deleteCar(index, row) {
      var _this  = this;
      var plate = row.plate;
      let MyData = {"plate": plate};
      axios.post("http://localhost:9091/deleteCarByPlate", MyData)
          .then(resp=>{
            _this.getTableDate();
          })
    },
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

.right {
  float: right;
}
</style>