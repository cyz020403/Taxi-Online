<template>
 <div>
   <div className="home_div">
     <div id="container"></div>
   </div>

    <el-card class="MyCard">
      <div class="MyText">
        接单管理
      </div>

      <div v-show="isWorking" class="passengerInfo" :key = tableKey>
        <el-descriptions direction="vertical" :column="1" border v-loading=isSearching element-loading-text="正在匹配订单" size = "medium">
          <el-descriptions-item label="乘客用户名">{{this.passengerUserName}}</el-descriptions-item>
          <el-descriptions-item label="乘客上车地点">{{this.startText}}</el-descriptions-item>
          <el-descriptions-item label="乘客目的地">{{this.endText}}</el-descriptions-item>
          <el-descriptions-item label="此单预计收入">{{this.price}}元</el-descriptions-item>
        </el-descriptions>
      </div>

      <div v-show = isTaking class="myImporNotice">
        请根据导航前往乘客上车地点。。。
      </div>

      <div v-show = isTransing class="myImporNotice">
        请根据导航驾驶至目的地。。。
      </div>

      <el-button class="MyButton" type="primary" @click="startWork" v-show=!isWorking>开始工作并寻找乘客</el-button>
      <el-button style="margin-left: 0px" class="MyButton" type="danger" @click="stopWork" v-show=isSearching>停止工作</el-button>
      <el-button style="margin-left: 0px" class="MyButton" type="primary" @click="haveTakenPass" v-show=isTaking>已接到乘客</el-button>
      <el-button style="margin-left: 0px" class="MyButton" type="primary" @click="haveTransedPass" v-show=isTransing>已送达乘客</el-button>
    </el-card>

    <el-card class="MyCard" style="margin-top: 20px">
      <div class="MyText">
        移动车辆至【以下按钮仅供系统调试过程使用】
      </div>

      <el-button type="info" @click="moveToCarPosition">默认位置1</el-button>
      <el-button type="info" @click="moveToStart">乘客上车地点</el-button>
      <el-button type="info" @click="moveToEnd">目的地</el-button>
    </el-card>

   <el-dialog
       title="选择驾驶的车辆"
       :visible.sync="myDialogVisible"
       width="30%"
       center>
     <el-select v-model="selectedCarPlate" placeholder="我的车辆">
       <el-option
           v-for="item in availableCar"
           :key="item.plate"
           :label="item.plate"
           :value="item.plate">
       </el-option>
     </el-select>
     <span slot="footer" class="dialog-footer">
        <el-button type="primary" class="dialogCancelButton" @click='selectCar'>确认选择当前车辆</el-button>
      </span>
   </el-dialog>
 </div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "startWork",

  data() {
    return {
      // map: null,
      userName: "",
      userType: "",
      tableKey: 0,

      isWorking: false,
      isSearching: false,
      isTaking: false,
      isTransing: false,
      myDialogVisible: false,
      availableCar: [{
        plate: "",
      }],
      selectedCarPlate: "",
      carLng: '',
      carLat: '',

      passengerUserName: '',
      startText: '',
      startLng: '',
      startLat: '',
      endText: '',
      endLng: '',
      endLat: '',
      price: '',

      isReserve: false,

      lastCarLng: '',
    }
  },

  created() {
    this.userName = localStorage.getItem("userName");
    this.userType = localStorage.getItem("userType"); // 用1 2 3 表示

  },

  mounted() {
    window._AMapSecurityConfig = {
      securityJsCode:'128c1cda226d5b6c4384aabd74e12421',
    }

    this.initAMap();
    this.checkReserve();
  },

  methods: {

    checkReserve() {
      var _this = this;
      if(this.$route.params.reserve === undefined) {
        console.log("gaga")
      }
      else {
        this.isReserve = true;
        this.isWorking = true;
        this.isTaking = true;
        _this.isSearching = false;
        // console.log(resp);
        _this.passengerUserName = this.$route.params.passengerUserName
        _this.startLng = this.$route.params.startLng
        _this.startLat = this.$route.params.startLat
        _this.endLng = this.$route.params.endLng
        _this.endLat = this.$route.params.endLat
        _this.carLng = this.$route.params.carLng
        _this.carLat = this.$route.params.carLat
        _this.price = this.$route.params.price
        _this.selectedCarPlate = this.$route.params.selectedCarPlate
        axios.get('https://restapi.amap.com/v3/geocode/regeo?location=' + _this.startLng + ',' + _this.startLat +
            '&key=' + MyKey)
            .then(resp => {
              _this.startText = resp.data.regeocode.formatted_address;
            })
        axios.get('https://restapi.amap.com/v3/geocode/regeo?location=' + _this.endLng + ',' + _this.endLat +
            '&key=' + MyKey)
            .then(resp => {
              _this.endText = resp.data.regeocode.formatted_address;
            })
            .finally(()=>{
              _this.tableKey++;
            })
        // _this.addPositionNow()
        console.log(_this.carLng, _this.carLat, _this.startLng, _this.startLat)
        _this.plantMap(_this.carLng, _this.carLat, _this.startLng, _this.startLat);
        _this.searchCarPositionTimer();
      }
    },

    initAMap() {
      var _this = this;
      AMapLoader.load({
        key: '46169c0f45c4e0cef33f0bc44651add4',  //设置您的key
        version: "2.0",
        plugins: ['AMap.ToolBar', 'AMap.Driving'],
        AMapUI: {
          version: "1.1",
          plugins: [],
        },
        Loca: {
          version: "2.0"
        },
      }).then((AMap_) => {
        _this.map = new AMap_.Map("container", {
          viewMode: "3D",
          resizeEnable: true,
          zoom: 9,
          zooms: [2, 22],
          center: [117.143710,36.673571],
        });

        AMap.plugin('AMap.ToolBar',function(){//异步加载插件
          var toolbar = new AMap.ToolBar();
          _this.map.addControl(toolbar);
        });

      }).catch(e => {
        console.log(e);
      })
    },

    startWork() {
      var _this = this;
      let myData = {"driverUserName": _this.userName};
      axios.post("http://localhost:9091/queryAllowedWork", myData)
          .then(resp=>{
            console.log(resp.data)
            if(resp.data.allowedWork === false) {
              this.$alert('您尚未获得接单许可。', '提示', {
                confirmButtonText: '确定',
              })
            }
            else {
              this.myDialogVisible = true;
              let MyData = {"userName": _this.userName};
              // 查询当前司机的全部车辆
              axios.post("http://localhost:9091/getAllCarsByManager", MyData)
                  .then(resp=>{
                    // console.log(resp);
                    _this.availableCar = resp.data;
                  })
            }
          })
    },

    selectCar() {
      var _this = this;
      let MyData = {"plate": _this.selectedCarPlate,
                    "userName": _this.userName};
      // 选择车辆开始工作，需要再work表中insert数据
      // 返回得到现在正在驾驶的车辆信息
      axios.post("http://localhost:9091/selectCarAndCreateWorkRecord", MyData)
          .then(resp=> {
            _this.carLng = resp.data.lng;
            _this.carLat = resp.data.lat;

            _this.myDialogVisible = false;

            _this.isWorking = true;

            _this.addPositionNow(_this.carLng, _this.carLat)

            _this.startTimer();

          })
          .finally(()=>{
            _this.searchCarPositionTimer();
          })
    },

    stopWork() {
      var _this = this;
      let MyData1 = {"userName": _this.userName,
                    "plate": _this.selectedCarPlate}
      clearInterval(this.myTimer);
      clearInterval(this.myTimer1);
      _this.isSearching = false;
      axios.post("http://localhost:9091/stopWorkByDriverUserNameAndPlate", MyData1)
          .then(()=>{
            _this.isWorking = false;
            _this.initAMap()
          })
    },

    startTimer() {
      this.isSearching = true;
      this.myTimer = setInterval(() => {
        this.queryMyOrder(this.myTimer)
      }, 2000)
    },

    queryMyOrder(timer) {
      var _this = this;
      let myData = {"carPlate": _this.selectedCarPlate,
                    "driverUserName": _this.userName}

      setTimeout(() => {
        axios.post("http://localhost:9091/queryMyOrder", myData)
            .then(resp => {
              if(resp.data === ""){
                console.log('暂时没有找到乘客');
              } else {
                clearInterval(timer)
                _this.isSearching = false;
                _this.isTaking = true;
                // console.log(resp);
                _this.passengerUserName = resp.data.passengerUserName;
                _this.startLng = resp.data.startLng;
                _this.startLat = resp.data.startLat;
                _this.endLng = resp.data.endLng;
                _this.endLat = resp.data.endLat;
                _this.price = resp.data.price;
                axios.get('https://restapi.amap.com/v3/geocode/regeo?location=' + _this.startLng + ',' + _this.startLat +
                    '&key=' + MyKey)
                    .then(resp => {
                      _this.startText = resp.data.regeocode.formatted_address;
                    })
                axios.get('https://restapi.amap.com/v3/geocode/regeo?location=' + _this.endLng + ',' + _this.endLat +
                    '&key=' + MyKey)
                    .then(resp => {
                      _this.endText = resp.data.regeocode.formatted_address;
                    })
                    .finally(()=>{
                      _this.tableKey++;
                    })
                _this.plantMap(_this.carLng, _this.carLat, _this.startLng, _this.startLat);
              }
            })
      })
    },

    haveTakenPass() {
      var _this = this;
      _this.isTaking = false;
      _this.isTransing = true;
      _this.plantMap(_this.carLng, _this.carLat, _this.endLng, _this.endLat)
    },

    haveTransedPass() {
      var _this = this;
      _this.isTransing = false;
      let myData = {"driverUserName": _this.userName,
                    "passengerUserName": _this.passengerUserName,
                    "plate": _this.selectedCarPlate,
                    "price": _this.price,
                    "isReserve": _this.isReserve}
      // 需要完成结算，更新work以及order中的内容
      axios.post("http://localhost:9091/finishOrder", myData)
          .then(()=>{
            _this.startTimer();
          })
      _this.driving.clear()
      _this.addPositionNow(_this.carLng, _this.carLat);
    },

    moveToCarPosition() {
      var _this = this;
      _this.map.clearMap();
      _this.addPositionNow(117.135685, 36.669681)
      let myData = {"lng": 117.135685, "lat": 36.669681, "carPlate": _this.selectedCarPlate,
        "isTransing": _this.isTransing, "passengerUserName": _this.passengerUserName};
      axios.post("http://localhost:9091/modifyCarPosition", myData);
    },
    moveToStart() {
      var _this = this;
      _this.map.clearMap();
      _this.addPositionNow(_this.startLng, _this.startLat)
      let myData = {"lng": _this.startLng, "lat": _this.startLat, "carPlate": _this.selectedCarPlate,
        "isTransing": _this.isTransing, "passengerUserName": _this.passengerUserName};
      axios.post("http://localhost:9091/modifyCarPosition", myData);
    },
    moveToEnd() {
      var _this = this;
      _this.map.clearMap();
      _this.addPositionNow(_this.endLng, _this.endLat)
      let myData = {"lng": _this.endLng, "lat": _this.endLat, "carPlate": _this.selectedCarPlate,
                    "isTransing": _this.isTransing, "passengerUserName": _this.passengerUserName};
      axios.post("http://localhost:9091/modifyCarPosition", myData);
      // 如果正在运输，需要同时移动passenger
    },

    searchCarPositionTimer() {
      this.myTimer1 = setInterval(() => {
        this.searchCarPosition(this.myTimer1)
      }, 2000)
    },

    searchCarPosition() {
      var _this = this;
      let myData = {"carPlate": _this.selectedCarPlate}
      // console.log(myData);
      axios.post("http://localhost:9091/searchCarPosition", myData)
          .then(resp=>{
            // console.log(resp.data);
            _this.carLng = resp.data.lng;
            _this.carLat = resp.data.lat;
          })
          .finally(()=>{
            // car的坐标改变过， 需要重新规划路线
            if(_this.carLng !== _this.lastCarLng) {
              // 改到开始去了
              if(_this.carLng === _this.startLng) {
                console.log("到开始去了")
              } else if(_this.carLng === _this.endLng) {
                console.log("到结束去了")
              }
              _this.lastCarLng = _this.carLng;
              if(_this.isTaking === true ) {
                _this.plantMap(_this.carLng, _this.carLat, _this.startLng, _this.startLat)
              } else if(_this.isTransing === true) {
                _this.plantMap(_this.carLng, _this.carLat, _this.endLng, _this.endLat)
              }
            }
          })
    },

    addPositionNow(lng, lat) {
      var _this = this;
      var marker = new AMap.Marker({
        position: new AMap.LngLat(lng, lat),
        offset: new AMap.Pixel(-10, -10),
        // icon: '//vdata.amap.com/icons/b18/1/2.png', // 添加 Icon 图标 URL
        title: 'PositionNow'
      });
      _this.map.add(marker);

      _this.map.setZoomAndCenter(16, [lng, lat])
    },

    plantMap(startLng, startLat, endLng, endLat) {
      var _this = this;
      AMap.plugin('AMap.Driving', function () {
        if (_this.driving) {
          _this.driving.clear();
        }
        _this.driving = new AMap.Driving({
          // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
          policy: AMap.DrivingPolicy.LEAST_TIME,
          map: _this.map,
          extensions: 'all',
        })

        // 用经纬度规划地图，绘制的更合理一点
        _this.driving.search(new AMap.LngLat(startLng, startLat), new AMap.LngLat(endLng, endLat), function (status, result) {
          // 未出错时，result即是对应的路线规划方案
          console.log(result);
          //这里可以得到出租车的价格，但是这里的似乎用不上了，需要价格的时候没有进行到这一步。
        })
      })
    },
  }
}
</script>

<style scoped>


#container {
  padding: 0px;
  margin: 0px;
  width: 64%;
  height: 86%;
  position: absolute;
}

.MyCard {
  margin-left: 1000px;
  width: 34% ;
}

.MyText {
  /*margin-top: 20px;*/
  margin-bottom: 10px;
  font-weight: bold;
}

.MyButton {
  margin-top: 10px;
  width: 100%;
}

.passengerInfo {
  margin-top: 20px;
  margin-bottom: 20px;
}

.myImporNotice {
  font-weight: bold;
  font-size: larger;
  /*margin-bottom: 5px;*/
}
</style>