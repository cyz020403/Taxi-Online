<template>
  <div class="myChartCSS">

    <el-row :gutter="24">
      <el-col :span="24">
        <el-card class = "myTitle">
          系统数据分析
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-row>
          <el-card>
            <div id="myChart1" :style="{width: '500px', height: '400px'}"></div>
          </el-card>
        </el-row>
        <el-row>
          <el-card>
            <div id="myChart3" :style="{width: '500px', height: '400px'}"></div>
          </el-card>
        </el-row>
      </el-col>

      <el-col :span="12">
        <el-row>
          <el-card>
            <div id="myChart2" :style="{width: '500px', height: '300px'}"></div>
          </el-card>
        </el-row>
        <el-row>
          <el-card>
            <div id="myChart4" :style="{width: '500px', height: '400px'}"></div>
          </el-card>
        </el-row>
      </el-col>
    </el-row>


    <div>

    </div>
  </div>
</template>

<script>
import axios from "axios";
var MyKey = 'be88d1f8d42a0fcf4cb657a423661d59';

export default {
  name: 'dataAnalysis',

  data() {
    return {

    }
  },

  mounted() {
    this.drawChart1();
    this.drawChart2();
    this.drawChart3();
    this.drawChart4();
  },

  methods: {
    drawChart1() {
      // 基于刚刚准备好的 DOM 容器，初始化 EChart 实例

      var passengerCount;
      var driverCount;
      var adminCount;
      axios.get("http://localhost:9091/dataAnalysis")
          .then(resp=>{
            passengerCount = resp.data.passengerCount;
            adminCount = resp.data.adminCount;
            driverCount = resp.data.driverCount;

            let myChart = this.$echarts.init(document.getElementById('myChart1'))

            var option = {
              title: { text: '系统用户分布情况' },
              legend: {
                top: 'bottom'
              },
              toolbox: {
                show: true,
                feature: {
                  mark: { show: true },
                  // dataView: { show: true, readOnly: false },
                  // restore: { show: true },
                  // saveAsImage: { show: true }
                }
              },
              series: [
                {
                  name: 'Nightingale Chart',
                  type: 'pie',
                  radius: [20, 100],
                  center: ['50%', '50%'],
                  roseType: 'area',
                  itemStyle: {
                    borderRadius: 8
                  },
                  data: [
                    { value: passengerCount, name: '乘客用户' },
                    { value: adminCount, name: '管理员用户' },
                    { value: driverCount, name: '司机用户' },
                  ]
                }
              ]
            };

            myChart.setOption(option);
          })
    },

    drawChart2() {
      axios.get("http://localhost:9091/getOrderPrice")
          .then(resp=>{
            let myChart = this.$echarts.init(document.getElementById('myChart2')),
                option = {
                  title: { text: '系统订单收入分布（元）' },
                  xAxis: {
                    type: 'category',
                    data: ['< 10', '10 - 15', '16 - 20', '21 - 25', '> 25']
                  },
                  yAxis: {
                    type: 'value'
                  },
                  series: [
                    {
                      data: [resp.data.int1, resp.data.int2, resp.data.int3, resp.data.int4, resp.data.int5],
                      type: 'bar'
                    }
                  ]
                };
            myChart.setOption(option);
          })
    },

    drawChart3() {
      axios.get("http://localhost:9091/getCarCountGroupByDriver")
          .then(resp=>{
            let myChart = this.$echarts.init(document.getElementById('myChart3')),
                option = {
                  title: { text: '司机管理车辆数量统计' },
                  xAxis: {
                    type: 'category',
                    data: ['1 辆', '2 辆', '3 辆', '4 辆', '4 辆以上']
                  },
                  yAxis: {
                    type: 'value'
                  },
                  series: [
                    {
                      data: [resp.data.int1, resp.data.int2, resp.data.int3, resp.data.int4, resp.data.int5],
                      type: 'bar'
                    }
                  ]
                };

            myChart.setOption(option);
          })
    },

    drawChart4() {
      axios.get("http://localhost:9091/getOrderCountGroupByPassenger")
          .then(resp=>{
            let myChart = this.$echarts.init(document.getElementById('myChart4')),
                option = {
                  title: {
                    text: '每个乘客订单数量统计',
                    left: 'center'
                  },
                  tooltip: {
                    trigger: 'item'
                  },
                  legend: {
                    orient: 'vertical',
                    left: 'left'
                  },
                  series: [
                    {
                      name: 'Access From',
                      type: 'pie',
                      radius: '50%',
                      data: [
                        { value: resp.data.int1, name: '0 - 5 单' },
                        { value: resp.data.int2, name: '6 - 10 单' },
                        { value: resp.data.int3, name: '11 - 15 单' },
                        { value: resp.data.int4, name: '16 - 20 单' },
                        { value: resp.data.int5, name: '> 20 单' }
                      ],
                      emphasis: {
                        itemStyle: {
                          shadowBlur: 10,
                          shadowOffsetX: 0,
                          shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                      }
                    }
                  ]
                };

            myChart.setOption(option);
          })
    },
  }
}

</script>

<style>
.el-row {
    margin-bottom: 20px;
}
.el-col {
  border-radius: 20px;
}
.myChartCSS {
  width: 85%;
  margin: 0 auto;
}

.myTitle {
  font-weight: bolder;
  font-size: larger;
}
</style>