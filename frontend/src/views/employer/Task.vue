<template>
  <el-page-header @click="prev" />

  <!--    <el-row :gutter="24">-->
  <!--<el-col :span="12">-->
  <!--    <el-card class="card-row" style="margin-right: 30px">-->

  <div style="float: left; margin-left: 80px;margin-right: 50px; width:600px">
    <h1>任务详情</h1>
    <el-descriptions
      class="task-details"
      title=""
      :size="large"
      :column="1"
      border
    >
      <el-descriptions-item width="100px">
        <template #label>
          <div class="cell-item">
            <el-icon :style="iconStyle">
              <user />
            </el-icon>
            任务名称
          </div>
        </template>
        {{ task.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <iphone />
            </el-icon>
            任务类型
          </div>
        </template>
        {{ task.type }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <location />
            </el-icon>
            人数需求
          </div>
        </template>
        {{ task.need_workers }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <location />
            </el-icon>
            测试设备
          </div>
        </template>
        {{ task.testDevice }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <tickets />
            </el-icon>
            开始时间
          </div>
        </template>
        <!--      <el-tag size="small">School</el-tag>-->
        {{ task.startTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <tickets />
            </el-icon>
            结束时间
          </div>
        </template>
        <!--      <el-tag size="small">School</el-tag>-->
        {{ task.endTime }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            <el-icon>
              <office-building />
            </el-icon>
            任务简介
          </div>
        </template>
        {{ task.introduction }}
      </el-descriptions-item>

      <el-descriptions-item label="文件上传">
        <el-upload
          class="avatar-uploader el-upload--text"
          :drag="true"
          action="http://1.15.237.160:8081/upload"
          multiple
          :data="{ taskId: this.tId }"
          :before-upload="handleBeforeUpload"
          :on-error="handleUploadError"
          :on-success="handleVideoSuccess"
          :before-remove="beforeRemove"
          :limit="5"
          :on-exceed="handleExceed"
          :on-progress="uploadVideoProcess"
          :file-list="fileList"
        >
          <i v-if="Plus" class="el-icon-upload"></i>
          <div v-if="Plus" class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
          <el-progress
            v-if="videoFlag === true"
            type="circle"
            :percentage="videoUploadPercent"
            style="margin-top:30px;"
          ></el-progress>
          <!--	<div class="el-upload__tip" slot="tip">只能上传mp4/flv/avi文件，且不超过300M</div>-->
        </el-upload>
      </el-descriptions-item>
    </el-descriptions>
  </div>
  <!--      </el-card>-->
  <!--  </el-col>-->
  <!--      <el-col :span="12">-->
  <div style="float: left;margin-left: 50px">
    <h1>参加人员</h1>
    <div v-for="(item, i) in users" align="middle">
      <el-card class="box-card">
        <div class="card-header">
          <span
            style="white-space: nowrap;text-overflow: ellipsis; overflow:hidden;"
            >{{ item.username }}</span
          >
          <el-button class="button" type="text" @click="openReport(i)"
            >查看报告</el-button
          >
        </div>
      </el-card>
    </div>
  </div>

  <!--        </el-col>-->

  <!--    <el-dialog-->
  <!--    v-model="reportVisible"-->
  <!--    title="报告详情"-->
  <!--    width="60%"-->
  <!--  >-->
  <!--&lt;!&ndash;    <span>This is a message</span>&ndash;&gt;-->
  <!--    <el-descriptions-->
  <!--    class="margin-top"-->
  <!--    title=""-->
  <!--    :column="1"-->
  <!--    border-->
  <!--  >-->
  <!--    <el-descriptions-item>-->
  <!--      <template #label>-->
  <!--        <div class="cell-item">-->
  <!--          <el-icon>-->
  <!--            <user />-->
  <!--          </el-icon>-->
  <!--          报告标题-->
  <!--        </div>-->
  <!--      </template>-->
  <!--      {{reports[flag].reportName}}-->
  <!--    </el-descriptions-item>-->
  <!--    <el-descriptions-item>-->
  <!--      <template #label>-->
  <!--        <div class="cell-item">-->
  <!--          <el-icon>-->
  <!--            <iphone />-->
  <!--          </el-icon>-->
  <!--          缺陷情况说明-->
  <!--        </div>-->
  <!--      </template>-->
  <!--      {{reports[flag].defectDescription }}-->
  <!--    </el-descriptions-item>-->
  <!--    <el-descriptions-item>-->
  <!--      <template #label>-->
  <!--        <div class="cell-item">-->
  <!--          <el-icon>-->
  <!--            <location />-->
  <!--          </el-icon>-->
  <!--          缺陷复现步骤-->
  <!--        </div>-->
  <!--      </template>-->
  <!--      {{reports[flag].recurrenceSteps}}-->
  <!--    </el-descriptions-item>-->
  <!--    <el-descriptions-item>-->
  <!--      <template #label>-->
  <!--        <div class="cell-item">-->
  <!--          <el-icon>-->
  <!--            <tickets />-->
  <!--          </el-icon>-->
  <!--          测试设备类型-->
  <!--        </div>-->
  <!--      </template>-->
  <!--&lt;!&ndash;      <el-tag size="small">School</el-tag>&ndash;&gt;-->
  <!--      {{reports[flag].deviceType}}-->
  <!--    </el-descriptions-item>-->
  <!--      <el-descriptions-item>-->
  <!--        <template #label>-->
  <!--          <div class="cell-item">-->
  <!--            <el-icon>-->
  <!--              <tickets />-->
  <!--            </el-icon>-->
  <!--            综合评分-->
  <!--          </div>-->
  <!--        </template>-->
  <!--        &lt;!&ndash;      <el-tag size="small">School</el-tag>&ndash;&gt;-->
  <!--        <el-rate-->
  <!--            v-model="reports[flag].AvgSore"-->
  <!--            disabled-->
  <!--            show-score-->
  <!--            text-color="#ff9900"-->
  <!--            score-template="{value} points"-->
  <!--        />-->
  <!--      </el-descriptions-item>-->
  <!--      <el-descriptions-item>-->
  <!--        {{supplementreport}}-->
  <!--      </el-descriptions-item>-->
  <!--  </el-descriptions>-->
  <!--      <a :href="getFileLink(this.reports[flag].id)">下载缺陷截图</a>-->
  <!--    </el-dialog>-->

  <!--</el-row>-->
</template>

<script>
import {
  User,
  Iphone,
  Location,
  OfficeBuilding,
  Tickets
} from "@element-plus/icons";
import axios from "axios";
import { TARGET } from "@/api/_prefix";
import { findReportByTaskIdAndEmployeeId } from "@/api/report";
import {getUser} from "@/api/task";
export default {
  name: "Task",
  components: {
    User,
    Iphone,
    Location,
    OfficeBuilding,
    Tickets
  },
  data() {
    return {
      srcList: [],
      src: "",
      flag: 0,
      reportVisible: false,
      users: [
        {
          id: 1,
          username: "lhy",
          password: "123456",
          userRole: "employee"
        },
        {
          id: 2,
          username: "zzd",
          password: "123456",
          userRole: "employee"
        }
      ],
      Plus: true,
      videoFlag: false,
      videoUploadPercent: 0,
      tId: parseInt(window.localStorage.getItem("taskId")),
      fileList: [],
      supplementreport: [],
      // dataForm:{
      //   role:'Employer',
      //   username: '超级管理员',
      //   phone: '173567777777',
      //   homeUrl: 'http://www.baidu.com'
      // },

      task: {
        name: window.localStorage.getItem("taskName"),
        introduction: window.localStorage.getItem("introduction"),
        need_workers: window.localStorage.getItem("needWorkers"),
        type: window.localStorage.getItem("taskType"),
        startTime: window.localStorage.getItem("startTime"),
        endTime: window.localStorage.getItem("endTime"),
        testDevice: window.localStorage.getItem("testDevice")
      }
    };
  },

  methods: {
    getFileLink(val) {
      return `${TARGET}/downloadScreenshots?reportId=` + val;
    },
    prev() {
      this.$router.go(-1);
    },

    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 5 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },

    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    handleUploadError(error, file) {
      console.log("文件上传出错：" + error);
    },

    uploadVideoProcess(event, file, fileList) {
      this.Plus = false;
      this.videoFlag = true;
      this.videoUploadPercent = parseInt(file.percentage.toFixed(0));
    },

    handleVideoSuccess(res, file) {
      this.Plus = true;
      this.videoUploadPercent = 100;
      console.log(res);
      this.videoFlag = false;
      if (res.code === 1) {
        this.$message.success("文件上传成功！");
      } else {
        this.$message.error("文件上传失败，请重新上传！");
      }
    },

    handleBeforeUpload(file) {},

    openReport(i) {
      findReportByTaskIdAndEmployeeId({
        taskId: this.tId,
        userId: this.users[i].id
      }).then(res => {
        if (res.code === 0) {
          this.$message.error("该用户暂时未提交报告");
        }
        else{
          window.localStorage.setItem("reportId", res.data.id);
          window.localStorage.setItem("authorId", this.users[i].id);
          this.$router.push("/employer/employerReport");
        }
      });
    }
    // handleDetail(i) {
    //   this.flag = i;
    //
    //   window.localStorage.setItem(
    //     "reportName",
    //     this.reports[this.flag].reportName
    //   );
    //   window.localStorage.setItem(
    //     "defectDescription",
    //     this.reports[this.flag].defectDescription
    //   );
    //   window.localStorage.setItem(
    //     "recurrenceSteps",
    //     this.reports[this.flag].recurrenceSteps
    //   );
    //   window.localStorage.setItem(
    //     "deviceType",
    //     this.reports[this.flag].deviceType
    //   );
    //   window.localStorage.setItem("reportId", this.reports[this.flag].id);
    //   window.localStorage.setItem("authorId",this.reports[this.flag].employeeId);
    //   this.$router.push("/employer/employerReport");
    // }
  },
  created() {
    getUser(this.tId).then(res => {
      this.users = res.data;
    });
  }
};
</script>

<style scoped>
.card-row {
  width: 700px;
  margin-left: 10px;
  margin-right: 10px;
}
.box-card {
  width: 500px;
  margin-top: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.task-details {
  margin-top: 20px;
}
</style>
