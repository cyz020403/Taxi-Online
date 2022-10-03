<template>
  <div>
    <div className="home_div">
      <div id="container"></div>
    </div>
    <el-card class="MyCard">
      <div class="MyText">
        当前位置
      </div>
<!--      <el-input v-model="nowText" :disabled = true :key = inputKey></el-input>-->
<!--      <el-descriptions :column="1">-->
<!--        <el-descriptions-item>{{this.nowText}}</el-descriptions-item>-->
<!--      </el-descriptions>-->
      <div style="margin-bottom: 20px">
        {{this.nowText}}
      </div>
      <div class="MyText">
        目的地
      </div>
      <el-input placeholder="请输入目的地" v-model="desText"></el-input>

      <div v-show="isWorking" class="driverInfo" :key = tableKey>
        <el-descriptions direction="vertical" :column="1" border v-loading="isSearching" element-loading-text="正在匹配订单">
          <el-descriptions-item label="司机用户名">{{this.driverUserName}}</el-descriptions-item>
          <el-descriptions-item label="车辆车牌号">{{this.carPlate}}</el-descriptions-item>
          <el-descriptions-item label="此单预计费用">{{this.price}}元</el-descriptions-item>
        </el-descriptions>
      </div>

      <div v-show = isTaking class="myImporNotice">
        司机正在前往您的位置。。。
      </div>

      <div v-show = isTransing class="myImporNotice">
        司机正在前往您的目的地。。。
      </div>


      <el-button class="MyButton" type="primary" @click="startQueryCar" v-show=!isWorking>寻找车辆</el-button>
      <el-button style="margin-left: 0px" class="MyButton" type="danger" @click='cancelQueryCar' v-show=isSearching>取消寻找车辆</el-button>
    </el-card>



<!--    <el-dialog-->
<!--        title="提示"-->
<!--        :visible.sync="myDialogVisible"-->
<!--        width="30%"-->
<!--        center>-->
<!--      <span class="myNotice">正在为您智能派单。。。</span>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button type="danger" class="dialogCancelButton" @click='cancelQueryCar'>取消寻找车辆</el-button>-->
<!--      </span>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import axios from "axios";

var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: "takeATaxi",
  data() {
    return {
      // map: null,
      userName: "",
      userType: "",
      nowText: '',
      nowLng: '',
      nowLat: '',
      desText: '',
      desLng: '',
      desLat: '',
      price: '',

      // 匹配到的订单的信息
      carLng: '',
      carLat: '',
      driverUserName: '',
      carPlate: '',

      myTimer: null,
      myDialogVisible: false,
      isWorking: false,
      isSearching: false,
      isTaking: false,
      isTransing: false,
      tableKey: 0,

      lastCarLng: '',

      isReserve: false,
    }
  },
  created() {
    this.userName = localStorage.getItem("userName");
    this.userType = localStorage.getItem("userType"); // 用1 2 3 表示
  },
  mounted() {
    window._AMapSecurityConfig = {
      securityJsCode: '128c1cda226d5b6c4384aabd74e12421',
    }

    this.getPositionNow();

  },
  methods: {

    checkReserve() {
      // 目的地 文字和经纬度
      // 价格
      var _this = this;
      if(this.$route.params.reserve === undefined) {
        console.log("gaga")
      }
      else {
        _this.isReserve = true;
        _this.isWorking = true;
        _this.isTaking = true;
        _this.price = _this.$route.params.price;
        _this.desLat = _this.$route.params.desLat;
        _this.desLng = _this.$route.params.desLng;
        _this.desText = _this.$route.params.desText;
        _this.carLng = _this.$route.params.carLng;
        _this.carLat = _this.$route.params.carLat;
        _this.driverUserName = _this.$route.params.driverUserName;
        _this.carPlate = _this.$route.params.carPlate;
        console.log(_this.carLng, _this.carLat, _this.nowLng, _this.nowLat)
        _this.plantMap(_this.carLng, _this.carLat, _this.nowLng, _this.nowLat);
        _this.searchCarPositionTimer();
      }
    },

    getPositionNow() {
      var _this = this;
      let MyData = {"userName": _this.userName};
      axios.post("http://localhost:9091/getOneByUsernamePassenger", MyData)
          .then(resp => {
            _this.nowLng = resp.data.lng;
            _this.nowLat = resp.data.lat;
          }).finally(() => {

        axios.get('https://restapi.amap.com/v3/geocode/regeo?location=' + _this.nowLng + ',' + _this.nowLat +
            '&key=' + MyKey)
            .then(resp1 => {
              // console.log(resp1)
              _this.nowText = resp1.data.regeocode.formatted_address;
            })

        _this.initAMap();

      })
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

        AMap.plugin('AMap.ToolBar', function () {//异步加载插件
          var toolbar = new AMap.ToolBar();
          _this.map.addControl(toolbar);
        });

        _this.addPositionNow(this.nowLng, this.nowLat);
        _this.checkReserve();
      }).catch(e => {
        console.log(e);
      })
    },

    startQueryCar() {
      var _this = this;
      // 文字地址转换为经纬度，限制济南
      axios.post("https://restapi.amap.com/v3/geocode/geo?address="+_this.desText+"&key="+MyKey+"&city=济南")
          .then(resp=> {
            // console.log(resp.data.geocodes[0].location);
            var location = resp.data.geocodes[0].location;
            _this.desLng = location.split(',')[0];
            _this.desLat = location.split(',')[1];
          })
          .finally(()=>{
            // 查询出来需要多少钱
            axios.post("https://restapi.amap.com/v5/direction/driving?origin="+ _this.nowLng + ',' + _this.nowLat +
                '&destination=' + _this.desLng + ',' + _this.desLat + '&key=' + MyKey)
                .then(resp=>{
                  // console.log(resp);
                  _this.price = resp.data.route.taxi_cost;
                  // console.log(_this.price)

                  _this.startTimer();
                })
          })
    },

    startTimer() {
      // this.myDialogVisible = true;
      this.isWorking = true;
      this.isSearching = true;
      this.myTimer = setInterval(() => {
        this.queryCarFromServer(this.myTimer)
      }, 2000)
    },

    queryCarFromServer(timer) {
      var _this = this;
      let MyData = {
        "userName": this.userName,
        "startLng": this.nowLng,
        "startLat": this.nowLat,
        "endLng": this.desLng,
        "endLat": this.desLat,
        "price": this.price,
      }

      setTimeout(() => {
        axios.post("http://localhost:9091/passengerQueryCar", MyData)
            .then(resp => {
              console.log(resp)
              if (resp.data === "") {
                // 对应没有找到的情况
                console.log('暂时没有找到车');
              } else {
                // 找到了合适的work记录，现在后端已经添加了order记录，开始订单
                clearInterval(timer)
                // _this.myDialogVisible = false;
                _this.isSearching = false;
                _this.isTaking = true;
                _this.carLng = resp.data.lng;
                _this.carLat = resp.data.lat;
                _this.driverUserName = resp.data.driverUserName;
                _this.carPlate = resp.data.carPlate;
                _this.searchCarPositionTimer();
              }
            })
            .finally(()=>{
              if(_this.carLng !== '') {
                _this.plantMap(_this.carLng, _this.carLat, _this.nowLng, _this.nowLat);
              }
            })
      })
    },

    cancelQueryCar() {
      // this.myDialogVisible = false;
      this.isWorking = false;
      this.isSearching = false;
      clearInterval(this.myTimer);
    },

    searchCarPositionTimer() {
      this.myTimer1 = setInterval(() => {
        this.searchCarPosition(this.myTimer1)
      }, 2000)
    },

    searchCarPosition() {
      var _this = this;
      let myData = {"carPlate": _this.carPlate}
      console.log(myData);
      axios.post("http://localhost:9091/searchCarPosition", myData)
          .then(resp=>{
            console.log(resp.data);
            _this.carLng = resp.data.lng;
            _this.carLat = resp.data.lat;
          })
          .finally(()=>{
            // car的坐标改变过， 需要重新规划路线
            if(_this.carLng !== _this.lastCarLng) {
              console.log("修改了")
              console.log(_this.carLng);
              console.log(_this.desLng);
              // 改到开始去了
              if(_this.carLng === _this.nowLng) {
                console.log("到开始去了")
                _this.isTaking = false;
                _this.isTransing = true;
                // 提示司机到达附近，请上车，更新路线，至目的地（更新路线下面的做了）
                this.$alert('司机以到达上车地点附近。。。', '提示', {
                  confirmButtonText: '确定',
                })

              } else if(_this.carLng - _this.desLng < 0.0001) {
                //订单结束
                console.log("到结束去了")
                // 提示已经送达目的地，订单结束
                _this.isWorking = false;
                _this.isTransing = false;
                // 清除路线，更新地图中心
                _this.driving.clear();
                _this.map.setZoomAndCenter(16, [_this.desLng, _this.desLat])
                _this.addPositionNow(_this.desLng, _this.desLat);
                _this.nowText = _this.desText;

                this.$alert('司机已到达目的地', '提示', {
                  confirmButtonText: '确定',
                })
              }
              _this.lastCarLng = _this.carLng;
              if(_this.isTaking === true ) {
                _this.plantMap(_this.carLng, _this.carLat, _this.nowLng, _this.nowLat)
              } else if(_this.isTransing === true) {
                _this.plantMap(_this.carLng, _this.carLat, _this.desLng, _this.desLat)
              }
            }
          })
    },

    addPositionNow(lng, lat) {
      var _this = this;
      _this.map.clearMap();
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
.home_div {
  padding: 0px;
  margin: 0px;
  width: 100%;
  height: 100%;
  position: relative;
}

#container {
  padding: 0px;
  margin: 0px;
  width: 64%;
  height: 86%;
  position: absolute;
}

.map_title {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 50px;
  background-color: black;

}

h3 {
  position: absolute;
  left: 10px;
  z-index: 2;
  color: white;
}

.MyCard {
  margin-left: 1000px;
  width: 34%;
}

.MyText {
  margin-top: 10px;
  margin-bottom: 10px;
  font-weight: bold;
}

.MyButton {
  margin-top: 20px;
  width: 100%;
}

.dialogCancelButton {
  width: 100%;
}

.myNotice {
  font-weight: bold;
  font-size: 20px;
}

.driverInfo {
  margin-top: 20px;
  margin-bottom: 10px;
}

.myImporNotice {
  font-weight: bold;
  font-size: larger;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>