<template>
  <div style="width: 90%;margin: 0 auto">
    <el-card class="card">
      <div slot="header" class="clearfix">
        <span class="font">任务偏好分布</span>
      </div>
      <div id="task" class="pie-wrap"></div>
    </el-card>
    <el-card class="card">
      <div slot="header" class="clearfix">
        <span class="font" >任务类型分布</span>
      </div>
      <div id="type" class="pie-wrap"></div>
    </el-card>
    <el-card class="card">
      <div slot="header" class="clearfix">
        <span class="font">用户活跃度分布</span>
      </div>
      <div id="level" class="pie-wrap"></div>
    </el-card>
    <el-card class="card">
      <div slot="header" class="clearfix">
        <span class="font">测试设备分布</span>
      </div>
      <div id="device" class="pie-wrap"></div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getAllEmployeeTaskPreference, getAllTaskDevice, getAllTaskType, getAllUserActivityLevel } from "@/api/admin";
export default {
  name: "Statistics",
  data() {
    return {
      level: [0.33, 0.33, 0.33],
      task: [0.25, 0.25, 0.25, 0.25],
      type: [0.33, 0.33, 0.33],
      device: [0.2, 0.2, 0.2, 0.2, 0.2]
    };
  },
  mounted() {
    window.addEventListener("resize", () => this.Refresh());
    //console.log(this.level);
    this.$nextTick(() => {
      //测试时请将下一排注释取消
      this.initData();
    });
  },
  methods: {
    drawActivityLevel() {
      this.chartPie = echarts.init(
        document.getElementById("level")
      );
      this.chartPie.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{b}: <br/>{c}({d}%))",
        },
        legend: {
          // data: ["较活跃", "一般", "潜水"],
          // right: 500,
          // orient: "vertical",
          data: ["较活跃", "一般", "潜水"],
          left: "center",
          top: "bottom",
          orient: "horizontal",
          textStyle: {
            fontSize: "20px",
            fontWeight: 700
          }
        },
        series: [
          {
            name: "来源",
            type: "pie",
            radius: ["50%", "80%"],
            center: ["50%", "50%"],
            data: [
              {
                value: this.level[0],
                name: "潜水"
              },
              {
                value: this.level[1],
                name: "一般"
              },
              {
                value: this.level[2],
                name: "较活跃"
              }
            ],
            animationDuration: 2000,
            //动画持续时间
            label: {
              show: false,
              position: "center"
            }
          }
        ]
      });
    },
    drawTaskPreference() {
      this.chartPie = echarts.init(
        document.getElementById("task")
      );
      this.chartPie.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{b}: <br/>{c}({d}%))",
        },
        legend: {
          data: ["未设置", "功能测试", "性能测试", "其他"],
          left: "center",
          top: "bottom",
          orient: "horizontal",
          textStyle: {
            fontSize: "20px",
            fontWeight: 700
          }
        },
        series: [
          {
            name: "来源",
            type: "pie",
            radius: ["50%", "80%"],
            center: ["50%", "50%"],
            data: [
              {
                value: this.task[0],
                name: "未设置"
              },
              {
                value: this.task[1],
                name: "功能测试"
              },
              {
                value: this.task[2],
                name: "性能测试"
              },
              {
                value: this.task[3],
                name: "其他"
              }
            ],
            animationDuration: 2000,
            //动画持续时间
            label: {
              show: false,
              position: "center"
            }
          }
        ]
      });
    },
    drawTaskType() {
      this.chartPie = echarts.init(
        document.getElementById("type")
      );
      this.chartPie.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{b}: <br/>{c}({d}%))",
        },
        legend: {
          data: ["功能测试", "性能测试", "其他"],
          left: "center",
          top: "bottom",
          orient: "horizontal",
          textStyle: {
            fontSize: "20px",
            fontWeight: 700
          }
        },
        series: [
          {
            name: "来源",
            type: "pie",
            radius: ["50%", "80%"],
            center: ["50%", "50%"],
            data: [
              {
                value: this.type[0],
                name: "功能测试"
              },
              {
                value: this.type[1],
                name: "性能测试"
              },
              {
                value: this.type[2],
                name: "其他"
              }
            ],
            animationDuration: 2000,
            //动画持续时间
            label: {
              show: false,
              position: "center",
            }
          }
        ]
      });
    },
    drawDevice() {
      this.chartPie = echarts.init(
        document.getElementById("device")
      );
      this.chartPie.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{b}: <br/>{c}({d}%))",
        },
        legend: {
          data: ["Linux", "Android", "鸿蒙", "Mac", "Windows"],
          left: "center",
          top: "bottom",
          orient: "horizontal",
          textStyle: {
            fontSize: "20px",
            fontWeight: 700
          }
        },
        series: [
          {
            name: "来源",
            type: "pie",
            radius: ["50%", "80%"],
            center: ["50%", "50%"],
            data: [
              {
                value: this.device[0],
                name: "Linux"
              },
              {
                value: this.device[1],
                name: "Android"
              },
              {
                value: this.device[2],
                name: "鸿蒙"
              },
              {
                value: this.device[3],
                name: "Mac"
              },
              {
                value: this.device[4],
                name: "Windows"
              }
            ],
            animationDuration: 2000,
            //动画持续时间
            label: {
              show: false,
              position: "center",
            }
          }
        ]
      });
    },
    initData() {
      getAllUserActivityLevel().then(res => {
        this.level = res;
        this.drawActivityLevel();
      });
      getAllEmployeeTaskPreference().then(res => {
        this.task = res;
        this.drawTaskPreference();
      });
      getAllTaskDevice().then(res =>{
        this.device = res;
        this.drawDevice();
      });
      getAllTaskType().then(res =>{
        this.type = res;
        this.drawTaskType();
      });
    },
    Refresh() {
      this.$router.go(0);
    }
  }
};
</script>

<style scoped>
.card{
  float: left;
  width: 45%;
  margin: 2% 2%;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.font{
  font-size: 30px;
}
.pie-wrap {
  width: 100%;
  height: 500px;
}
</style>