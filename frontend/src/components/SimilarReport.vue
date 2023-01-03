<template>
  <el-page-header @click="prev" />
  <p align="middle" style="font-size: 30px;font-weight: bold">相似报告列表</p>
  <div class="SR-box" align="middle">
    <div v-for="(item, i) in similar_reports" class="SR-item">
      <el-card style="height: 80px">
        <div style="position:relative;">
          <div
            style="position:absolute;top:50%;font-size: 25px; width:500px;white-space: nowrap;text-overflow: ellipsis; overflow:hidden;text-align: left"
          >
            {{ item.reportName }}
          </div>
          <div style="position: absolute;left:60%;top:20%">
            <span>相似度：</span>
            <span
              v-if="this.similarityList[i] < 0.75"
              style="font-size: 25px;color:#67C23A;font-weight: bolder"
              >{{ toPercent(this.similarityList[i]) }}</span
            >
            <span
              v-if="
                this.similarityList[i] >= 0.75 && this.similarityList[i] < 0.9
              "
              style="font-size: 25px;color:#E6A23C;font-weight: bolder"
              >{{ toPercent(this.similarityList[i]) }}</span
            >
            <span
              v-if="this.similarityList[i] >= 0.9"
              style="font-size: 25px;color:#F56C6C;font-weight: bolder"
              >{{ toPercent(this.similarityList[i]) }}</span
            >
          </div>
          <el-button
            class="button"
            style="position:absolute; top:15%;left:80%"
            @click="handleDetails(item)"
            >查看详情</el-button
          >
        </div>
      </el-card>
      <el-divider />
    </div>
  </div>

  <el-dialog v-model="dialogVisible" width="90%" style="position: relative">
    <div style="margin:0 auto">
      <el-row>
        <el-card
          class="SR-card-box"
          style="margin-left: 30px;margin-right: 30px"
        >
          <h2>原报告</h2>
          <el-descriptions border column="1">
            <el-descriptions-item label="报告标题" min-width="120px">{{
              this.reportName
            }}</el-descriptions-item>
            <el-descriptions-item label="缺陷情况说明">{{
              this.defectDescription
            }}</el-descriptions-item>
            <el-descriptions-item label="缺陷复现步骤">{{
              this.recurrenceSteps
            }}</el-descriptions-item>
            <el-descriptions-item label="测试设备类型">{{
              this.deviceType
            }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
        <el-card class="SR-card-box" style="margin-right: 30px;">
          <h2>相似报告</h2>
          <el-descriptions border column="1">
            <el-descriptions-item label="报告标题" min-width="120px">{{
              this.similar_report.reportName
            }}</el-descriptions-item>
            <el-descriptions-item label="缺陷情况说明">{{
              this.similar_report.defectDescription
            }}</el-descriptions-item>
            <el-descriptions-item label="缺陷复现步骤">{{
              this.similar_report.recurrenceSteps
            }}</el-descriptions-item>
            <el-descriptions-item label="测试设备类型">{{
              this.similar_report.deviceType
            }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-row>
    </div>
  </el-dialog>
</template>

<script>
import { TARGET } from "@/api/_prefix";
import axios from "axios";

export default {
  name: "SimilarReport",
  data() {
    return {
      dialogVisible: false,
      reportId: parseInt(window.localStorage.getItem("reportId")),
      reportName: window.localStorage.getItem("reportName"),
      defectDescription: window.localStorage.getItem("defectDescription"),
      recurrenceSteps: window.localStorage.getItem("recurrenceSteps"),
      deviceType: window.localStorage.getItem("deviceType"),
      similar_report: {},
      similar_reports: [],
      similarityList: []
    };
  },
  methods: {
    toPercent(point) {
      if (point === 0) return 0;
      let str = Number(point * 100).toFixed();
      str += "%";
      return str;
    },
    prev() {
      this.$router.go(-1);
    },
    handleDetails(item) {
      this.dialogVisible = true;
      this.similar_report.reportName = item.reportName;
      this.similar_report.defectDescription = item.defectDescription;
      this.similar_report.recurrenceSteps = item.recurrenceSteps;
      this.similar_report.deviceType = item.deviceType;
    }
  },
  created: function() {
    axios
      .post(`${TARGET}/report/getSimilarByReportId?reportId=${this.reportId}`)
      .then(res => {
        console.log(res);
        this.similar_reports = res.data;
      });
    axios
      .post(
        `${TARGET}/report/getSimilarityByReportId?reportId=${this.reportId}`
      )
      .then(res => {
        console.log(res);
        this.similarityList = res.data;
      });
  }
};
</script>

<style scoped>
.SR-item {
  width: 1000px;
  height: 130px;
}
.SR-card-box {
  width: 550px;
}
.SR-info {
  position: relative;
}
.SR-title {
  width: 400px;
  position: absolute;
  left: 5%;
  top: 20%;
}
.SR-similarity {
  position: absolute;
  width: 100px;
  top: 30%;
  left: 60%;
}
.button-box {
  position: absolute;
  left: 80%;
}
</style>
