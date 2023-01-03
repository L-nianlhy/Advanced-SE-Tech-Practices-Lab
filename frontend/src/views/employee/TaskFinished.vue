<template>
  <div>
    <div v-for="(item, i) in tasks" style="margin:10px;" align="middle">
      <el-card class="box-card">
        <div class="card-header">
          <span>{{ item.taskName }}</span>
          <el-button class="button" type="text" @click="getreport(item.id)"
            >查看报告</el-button
          >
          <!--        <el-button class="button" type="text" @click="fillReport(item.taskId)">填写报告</el-button>-->
        </div>
      </el-card>
    </div>

    <el-dialog v-model="dialogTableVisible" title="报告列表">
      <div v-for="(item, i) in reports" class="card-header">
        <span>{{ item.reportName }}</span>
        <el-button class="button" type="text" @click="handleDetail(i)"
          >查看详情</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { ref } from "vue";
import { TARGET } from "@/api/_prefix";
const value2 = ref(null);
const colors = ref(["#99A9BF", "#F7BA2A", "#FF9900"]); // same as { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
export default {
  name: "TaskFinished",
  data() {
    return {
      employeeId: parseInt(window.localStorage.getItem("userId")),
      tasks: [],
      reports: [],
      flag: 0,
      dialogTableVisible: false,
      reportVisible: false,
      value2: 0,
      description: ""
    };
  },
  created() {
    axios
      .post(`${TARGET}/task/getSubmitTasks?employeeId=${this.employeeId}`)
      .then(res => {
        console.log(res);
        this.tasks = res.data;
      });
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
      this.$router.push("/employee/report");
    }
  }
};
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.box-card {
  width: 480px;
}

.demo-rate-block {
  padding: 30px 0;
  text-align: center;

  display: inline-block;
  width: 49%;
  box-sizing: border-box;
}
.demo-rate-block:last-child {
  border-right: none;
}
.demo-rate-block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
