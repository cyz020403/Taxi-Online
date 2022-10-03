<template>
  <div>
    <el-card class="wide">

      <div class="title">
        <div class="left">已预约订单</div>
        <div class="right">
          <el-button type="primary" @click="dialogFormVisible = true">预约打车出行</el-button>
        </div>
      </div>

      <el-table
          :data= tableData
          :key = tableKey
          stripe
          style="width: 100%">

        <el-table-column
            prop="orderId"
            label="订单号"
            width="140">
        </el-table-column>

        <el-table-column
            prop="plate"
            label="使用车辆车牌"
            width="160">
        </el-table-column>

        <el-table-column
            prop="userName"
            label="司机用户名"
            width="160">
        </el-table-column>

        <el-table-column
            prop="startPosition"
            label="上车地点"
            width="290">
        </el-table-column>

        <el-table-column
            prop="endPosition"
            label="下车地点"
            width="290">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" @click="startOrder(scope.$index,scope.row)">查看预约单</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>

    <el-dialog title="添加预约单" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="addOrderForm">

        <el-form-item label="出行时间" label-width = "80px">
          <el-date-picker
              value-format="yyyy-MM-dd hh:mm:ss"
              v-model="addOrderForm.startTime"
              type="datetime"
              placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出发地点" label-width = "80px">
          <el-input v-model="addOrderForm.startText"></el-input>
        </el-form-item>
        <el-form-item label="目的地" label-width = "80px">
          <el-input v-model="addOrderForm.endText"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrder">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "passengerReverseOrder",

  data() {
    return {
      tableData: [],
      tableKey: 0,
      userName: "",
      userType: "",

      dialogFormVisible: false,

      addOrderForm:[{
        startText: '',
        endText: '',
        startTime: '',
      }],
      startLng: '',
      startLat: '',
      endLng: '',
      endLat: '',
      price: '',
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
      axios.post("http://localhost:9091/passengerGetReserveOrder", MyData)
          .then(resp=>{
            _this.tableData = resp.data;
            console.log(resp.data)
            // 返回的数据。startLng...endLat
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

    addOrder() {
      var _this = this;
      axios.post("https://restapi.amap.com/v3/geocode/geo?address="+this.addOrderForm.startText+"&key="+MyKey+"&city=济南")
          .then(resp=>{
            console.log(resp.data.geocodes[0].location);
            var location = resp.data.geocodes[0].location;
            _this.startLng = location.split(',')[0];
            _this.startLat = location.split(',')[1];
            axios.post("https://restapi.amap.com/v3/geocode/geo?address="+this.addOrderForm.endText+"&key="+MyKey+"&city=济南")
                .then(resp=>{
                  console.log(resp.data.geocodes[0].location);
                  var location = resp.data.geocodes[0].location;
                  _this.endLng = location.split(',')[0];
                  _this.endLat = location.split(',')[1];
                  axios.post("https://restapi.amap.com/v5/direction/driving?origin="+ _this.startLng + ',' + _this.startLat +
                      '&destination=' + _this.endLng + ',' + _this.endLat + '&key=' + MyKey)
                      .then(resp=>{
                        _this.price = resp.data.route.taxi_cost;
                        let myData = {
                          "passengerUserName": _this.userName,
                          "startLng": _this.startLng,
                          "startLat": _this.startLat,
                          "endLng": _this.endLng,
                          "endLat": _this.endLat,
                          "startTime": _this.addOrderForm.startTime,
                          "price": _this.price,
                        }
                        console.log(myData)
                        _this.dialogFormVisible = false
                        axios.post("http://localhost:9091/addReserveOrder", myData)
                            .then(()=>{
                              _this.getTableData();
                            })

                      })
                })
          })
    },

    startOrder(index, row) {
      let myData = {
        "userName": this.userName,
        "lng": this.tableData[index].startLng,
        "lat": this.tableData[index].startLat,
      }
      axios.post("http://localhost:9091/setPassengerPosition", myData)
          .then(()=>{
            this.$router.push({name:"takeATaxi",params: {
                // id:'gagaga'
                reserve:true,
                desLng:this.tableData[index].endLng,
                desLat:this.tableData[index].endLat,
                desText:row.endPosition,
                carLng:this.tableData[index].lng,
                carLat:this.tableData[index].lat,
                price:this.tableData[index].price,
                driverUserName: row.userName,
                carPlate: row.plate,
              }});
          })
    },
  },
}
</script>

<style scoped>

.wide {
  width: 80%;
  margin: 0 auto;
}

.title {
  margin-bottom: 50px;
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