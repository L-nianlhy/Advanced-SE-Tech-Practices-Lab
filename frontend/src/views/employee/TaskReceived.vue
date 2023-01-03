<template>
  <div style="width: 1400px;margin: 0 auto">
    <div id="notFinished-box" style="  width: 600px; height: auto;float: left">
      <div class="word-png">
        <img
          src="../../assets/notFinished.png"
          style="width: 420px;height: 90px;"
        />
      </div>
      <div
        class="tasks-box"
        style="background-color: #FAFCFD; width: 600px; height: auto;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);"
      >
        <div v-for="item in tasks" class="item" align="left">
          <div class="icon-box">
            <el-icon
              class="icon"
              v-if="item.taskType === '功能测试'"
              :style="{ color: iconColor[item.available] }"
            >
              <Setting />
            </el-icon>
            <el-icon
              class="icon"
              v-if="item.taskType === '性能测试'"
              :style="{ color: iconColor[item.available] }"
            >
              <Bicycle />
            </el-icon>
            <el-icon
              class="icon"
              v-if="item.taskType === '其他'"
              :style="{ color: iconColor[item.available] }"
            >
              <PieChart />
            </el-icon>
          </div>
          <div class="info-box">
            <div
              style="font-size: 25px;position: absolute;top:20%;white-space: nowrap;text-overflow: ellipsis; width:320px ; overflow:hidden;"
            >
              {{ item.taskName }}
            </div>
            <div style="position: absolute;left: 63%;top:28%">
              <span style="font-size: 20px; color: #99a9bf"
                >类型：{{ item.taskType }}</span
              >
            </div>
            <!--<p>{{ item.taskName }} 报名详情 {{item.signedNum}}/{{item.needWorkers}}</p>-->
          </div>
          <div class="info-box-2">
            <p style="margin-top: 0;margin-bottom: 2px">
              任务开始时间：<span style="color: #909399">{{
                item.startTime
              }}</span>
            </p>
            <p style="margin-top: 0">
              任务结束时间：<span style="color: #909399">{{
                item.endTime
              }}</span>
            </p>
          </div>
          <div class="button-box">
            <el-button
              class="button"
              v-if="item.available === 1"
              type="primary"
              round
              @click="fillReport(item.id)"
              style="position: absolute; right:14%; bottom: 15%; width: 130px;height: 50px; font-size: 20px;"
              >填写报告</el-button
            >
            <el-button
              class="button"
              v-if="item.available === 0"
              type="info"
              round
              style="position: absolute; right:14%; bottom: 15%; width: 130px;height: 50px; font-size: 20px;"
              disabled
              >已结束</el-button
            >
          </div>
          <hr
            align="center"
            width="580px"
            SIZE="1"
            style="position: absolute;bottom: 1%"
          />
        </div>
      </div>
    </div>
    <div
      id="finished-box"
      style="width: 600px;height: auto;float: left;margin-left: 100px;"
    >
      <div class="word-png">
        <img
          src="../../assets/finished.png"
          style="width: 420px;height: 90px;"
        />
      </div>
      <div
        class="tasks-box"
        style="background-color: #FAFCFD; width: 600px; height: auto;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);"
      >
        <div v-for="item in tasksFinished" class="item" align="left">
          <div class="icon-box">
            <el-icon
              class="icon"
              v-if="item.taskType === '功能测试'"
              :style="{ color: iconColor[item.available] }"
            >
              <Setting />
            </el-icon>
            <el-icon
              class="icon"
              v-if="item.taskType === '性能测试'"
              :style="{ color: iconColor[item.available] }"
            >
              <Bicycle />
            </el-icon>
            <el-icon
              class="icon"
              v-if="item.taskType === '其他'"
              :style="{ color: iconColor[item.available] }"
            >
              <PieChart />
            </el-icon>
          </div>
          <div class="info-box">
            <div
              style="font-size: 25px;position: absolute;top:20%;white-space: nowrap;text-overflow: ellipsis; width:320px ; overflow:hidden;"
            >
              {{ item.taskName }}
            </div>
            <div style="position: absolute;left: 63%;top:28%">
              <span style="font-size: 20px; color: #99a9bf"
                >类型：{{ item.taskType }}</span
              >
            </div>
            <!--<p>{{ item.taskName }} 报名详情 {{item.signedNum}}/{{item.needWorkers}}</p>-->
          </div>
          <div class="info-box-2">
            <p style="margin-top: 0;margin-bottom: 2px">
              任务开始时间：<span style="color: #909399">{{
                item.startTime
              }}</span>
            </p>
            <p style="margin-top: 0">
              任务结束时间：<span style="color: #909399">{{
                item.endTime
              }}</span>
            </p>
          </div>
          <div class="button-box">
            <el-button
              class="button"
              type="success"
              round
              @click="getreport(item.id)"
              style="position: absolute; right:14%; bottom: 15%; width: 130px;height: 50px; font-size: 20px;"
              >查看报告</el-button
            >
          </div>
          <hr
            align="center"
            width="580px"
            SIZE="1"
            style="position: absolute;bottom: 1%"
          />
        </div>
      </div>
    </div>

    <el-dialog v-model="dialogTableVisible" title="报告列表" style="width: 70%">
      <div v-for="(item, i) in reports" class="card-header" style="width: 80%;height: 50px;position: relative;margin: 0 auto">
        <span style="position:absolute;left: 2px;">《{{ item.reportName }}》</span>
        <el-button type="warning" v-if="item.employeeId===this.employeeId" round size="small" style="position: absolute;right: 75px">我的报告</el-button>
        <el-button class="button" type="text" @click="handleDetail(i)" style="position: absolute;right: 2px">查看详情</el-button>
        <el-divider style="margin-top: 0;margin-bottom: 0;position:absolute;bottom: 7px"></el-divider>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { TARGET } from "@/api/_prefix";
import {
  User,
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  Edit,
  Setting,
  Bicycle,
  PieChart
} from "@element-plus/icons";

export default {
  name: "TaskReceived",
  components: {
    User,
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    Edit,
    Setting,
    Bicycle,
    PieChart
  },

  data() {
    return {
      employeeId: parseInt(window.localStorage.getItem("userId")),
      tasks: [],
      tasksFinished: [],
      dialogTableVisible: false,
      iconColor: ["#808080", "#9fe9ff"],
      reports: []
    };
  },

  methods: {
    getreport(Id) {
      axios
        .post(`${TARGET}/report/findReportsByTaskId?taskId=${Id}`)
        .then(res => {
          console.log(res);
          this.reports = res.data;
        });
      this.dialogTableVisible = true;
    },
    fillReport(taskId) {
      axios
        .post(
          `${TARGET}/report/newReport?employeeId=${this.employeeId}&taskId=${taskId}`
        )
        .then(res => {
          console.log(res);
          window.localStorage.setItem("rId", res.data.data.id);
          console.log(window.localStorage.getItem("rId"));
          return res.data;
        });
      this.$router.push("/employee/fillReport");
    },
    handleDetail(i) {
      this.flag = i;
      this.reportVisible = true;
      window.localStorage.setItem(
        "reportName",
        this.reports[this.flag].reportName
      );
      window.localStorage.setItem(
        "defectDescription",
        this.reports[this.flag].defectDescription
      );
      window.localStorage.setItem(
        "recurrenceSteps",
        this.reports[this.flag].recurrenceSteps
      );
      window.localStorage.setItem(
        "deviceType",
        this.reports[this.flag].deviceType
      );
      window.localStorage.setItem("reportId", this.reports[this.flag].id);
      window.localStorage.setItem("authorId",this.reports[this.flag].employeeId);
      this.$router.push("/employee/report");
    }
  },

  created: function() {
    axios
      .post(`${TARGET}/task/getTaskByEmployeeIdInOrder?employeeId=${this.employeeId}`)
      .then(res => {
        console.log(res);
        this.tasks = res.data;
      });
    axios
      .post(`${TARGET}/task/getSubmitTasks?employeeId=${this.employeeId}`)
      .then(res => {
        console.log(res);
        this.tasksFinished = res.data;
      });
  }
};
</script>

<style scoped>
.word-png {
  margin-bottom: 15px;
}
.item {
  width: 600px;
  height: 130px;
  margin-left: 5px;
  margin-right: 5px;
  position: relative;
  /*background-color: #42b983;*/
}
.icon {
  font-size: 90px;
  /*color: #9fe9ff;*/
}
.icon-box {
  position: absolute;
  top: 7%;
  left: 1%;
}
.info-box {
  position: absolute;
  width: 520px;
  height: 55px;
  /*background-color: #909399;*/
  left: 19%;
  top: 4%;
}
.info-box-2 {
  position: absolute;
  width: 300px;
  height: 55px;
  /*background-color: blue;*/
  right: 30%;
  top: 50%;
}
.button-box {
  position: absolute;
  width: 220px;
  height: 55px;
  /*background-color: aquamarine;*/
  right: 1%;
  top: 50%;
}

</style>
