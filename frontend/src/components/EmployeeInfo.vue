<template>
  <el-page-header @click="prev" />
  <div>
    <el-card style="margin-top: 30px; margin-left:20%;margin-right: 20%">
      <h2>基本资料</h2>
      <div>
        <span style="float: left;margin-left: 100px;font-size: 20px"
          >用户名：</span
        >
        <span style="float:left;font-size: 20px">{{
          this.userName
        }}</span>
      </div>
      <br /><br />
      <div>
        <span style="float: left;margin-left: 100px;font-size: 20px;"
          >综合评分：</span
        >
        <span style="float:left;font-size: 20px;">
          {{ Math.round(this.employeeInfo.totalScore) }}
        </span>
      </div>
      <br /><br />
      <div>
        <span style="float: left;margin-left: 100px;font-size: 20px"
          >专业能力：</span
        >
        <span
          v-for="item in this.employeeInfo.professionalAbility"
          style="float:left;font-size: 20px; margin-right: 20px"
          >{{ item }}</span
        >
      </div>
      <br /><br />
      <div>
        <span style="float: left;margin-left: 100px;font-size: 20px"
          >任务偏好：</span
        >
        <span style="float:left;font-size: 20px;">{{
          this.employeeInfo.taskPreference
        }}</span>
      </div>
      <br /><br />
      <div>
        <span style="float: left;margin-left: 100px;font-size: 20px"
          >测试设备：</span
        >
        <span
          v-for="item in this.employeeInfo.testDevice"
          style="float:left;font-size: 20px; margin-right: 20px"
          >{{ item }}</span
        >
      </div>
      <br /><br />

      <el-divider />

      <h2>综合评测</h2>
      <div>
        <span style="float: left;width: 100px">报告平均分：</span>
        <el-progress
          text-inside="true"
          :color="statusChoose(Number(this.employeeInfo.avgScore) * 20)"
          style="margin-bottom: 30px"
          :percentage="(Number(this.employeeInfo.avgScore) * 20).toFixed(1)"
          stroke-width="24"
        ></el-progress>
        <span style="float: left;width: 100px">活跃度：</span>
        <el-progress
          text-inside="true"
          :color="statusChoose(this.employeeInfo.activityLevel)"
          style="margin-bottom: 30px"
          :percentage="this.employeeInfo.activityLevel"
          stroke-width="24"
        ></el-progress>
        <span style="float: left;width: 100px">协作能力：</span>
        <el-progress
          text-inside="true"
          :color="statusChoose(Number(this.employeeInfo.suppNum) * 5)"
          style="margin-bottom: 30px"
          :percentage="Number(this.employeeInfo.suppNum) * 5"
          stroke-width="24"
        ></el-progress>
        <span style="float: left;width: 100px">审查能力：</span>
        <el-progress
          text-inside="true"
          :color="statusChoose(this.employeeInfo.reviewAbility)"
          style="margin-bottom: 30px"
          :percentage="this.employeeInfo.reviewAbility"
          stroke-width="24"
        ></el-progress>

        <el-progress
          type="dashboard"
          :color="statusChoose(Number(this.employeeInfo.finishRate) * 100)"
          :percentage="Number(this.employeeInfo.finishRate) * 100"
          stroke-width="24"
          width="250"
        >
          <span class="percentage-label">完成率</span>
          <span class="percentage-num"
            >{{ (Number(this.employeeInfo.finishRate) * 100).toFixed(1) }}%</span
          >
        </el-progress>
        <el-progress
          style="margin-top: 20px;margin-left: 100px"
          :color="
            statusChoose2(Number(this.employeeInfo.reportSimilarity) * 100)
          "
          type="dashboard"
          :percentage="Number(this.employeeInfo.reportSimilarity) * 100"
          stroke-width="24"
          width="250"
        >
          <span class="percentage-label">报告重复度</span>
          <span class="percentage-num"
            >{{ (Number(this.employeeInfo.reportSimilarity) * 100).toFixed(1) }}%</span
          >
        </el-progress>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import { TARGET } from "@/api/_prefix";

export default {
  name: "EmployeeInfo",
  data() {
    return {
      employeeId: parseInt(window.localStorage.getItem("authorId")),
      userId: parseInt(window.localStorage.getItem("authorId")),
      userName: "",
      employeeInfo: {
        totalScore: 0,
        professionalAbility: [],
        taskPreference: "功能测试",
        testDevice: [],
        activityLevel: 0,
        avgScore: 0,
        finishRate: 0,
        suppNum: 0,
        reviewAbility: 0,
        reportSimilarity: 0
      }
    };
  },
  methods: {
    prev() {
      this.$router.go(-1);
    },
    statusChoose(num) {
      if (num >= 90) return "#67C23A";
      else if (num >= 80 && num < 90) return "#409EFF";
      else if (num >= 60 && num < 80) return "#E6A23C";
      else return "#F56C6C";
    },
    statusChoose2(num) {
      if (num >= 90) return "#F56C6C";
      else if (num >= 80 && num < 90) return "#E6A23C";
      else if (num >= 60 && num < 80) return "#409EFF";
      else return "#67C23A";
    }
  },
  created: function() {
    axios
      .post(
        `${TARGET}/employee/getInfoByEmployeeId?employeeId=${this.employeeId}`
      )
      .then(res => {
        console.log(res);
        this.employeeInfo = res.data;
        // this.Professional_Ability = res.data.professionalAbility;
        // this.Task_Preference = res.data.taskPreference;
        // this.Test_Device = res.data.testDevice;
      });
    axios
      .post(`${TARGET}/user/getInfoByUserId?userId=${this.userId}`)
      .then(res => {
        console.log(res);
        this.userName = res.data.username;
      });
  }
};
</script>

<style scoped>
.percentage-label {
  display: block;
  margin-top: 10px;
  font-size: 18px;
}
</style>
