<template>
  <div style="width: 1400px;margin: 0 auto">
    <div
      id="top-header"
      style="width: 100%;position: relative;height: 70px;text-align: center"
    >
      <el-button
        type="primary"
        @click="prev"
        style="position: absolute;left: 2%;width: 130px;height: 50px;font-size: 30px"
        round
        ><strong>返回</strong>
      </el-button>
      <div
        style="font-size: 40px;white-space: nowrap; text-overflow: ellipsis;overflow:hidden;width: 730px;position: absolute;left: 23%;"
      >
        《{{ reportName }}》
      </div>
    </div>
    <div
      id="info-box"
      style="width: 800px;height: 950px;background-color: white;float: left;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); overflow: auto"
    >
      <div
        id="content-header"
        style="width: 800px;height:80px;position: relative; "
      >
        <div
          id="content-title"
          style="position: absolute;font-size: 40px;top:10%;left: 8%"
        >
          <strong>报告详情</strong>
        </div>
        <div
          id="report-auto-score"
          style="position: absolute;font-size: 20px;bottom:8%;right: 3%"
        >
          图文匹配评分：<span style="font-size: 30px;color: blue"
            ><strong>{{ this.report_auto_score }}/100.0</strong></span
          >
        </div>
        <div
          id="report-score"
          style="position: absolute;font-size: 20px;bottom:8%;right: 43%"
        >
          综合评分：<span style="font-size: 30px;color: blue"
            ><strong>{{ this.Avgscoce }}/5.0</strong></span
          >
        </div>
        <hr
          id="hr1"
          width="780px"
          SIZE="1"
          style="position: absolute;bottom: 1%;left: 1%;border: 1px solid"
        />
      </div>
      <div
        id="content-author"
        style="width: 800px;height:50px;position: relative; "
      >
        <div
          id="report-author"
          style="position: absolute;font-size: 20px;top:10%;left: 3%"
        >
          报告作者：
          <div style="display:inline">
            <el-link type="primary" style="">{{
              this.userInfo.username
            }}</el-link>
          </div>
        </div>
      </div>
      <div
        id="content"
        style="width: 700px;height: 650px;margin: 0 auto; border:2px solid #cceff5;background:#fafcfd"
      >
        <div id="real-title" style="height: 80px">
          <div class="titles">报告标题</div>
          <hr width="500px" SIZE="1" class="content-hr" />
          <div>{{ reportName }}</div>
        </div>
        <div id="defect-description" style="height: 195px;margin-top: 20px;">
          <div class="titles">缺陷情况说明</div>
          <hr width="500px" SIZE="1" class="content-hr" />
          <div class="long-content">{{ defectDescription }}</div>
          <div>
            <a :href="getFileLink(this.reportId)"
              ><el-button type="text">下载缺陷截图</el-button></a
            >
          </div>
        </div>
        <div id="recurrence-steps" style="height: 160px;margin-top: 20px;">
          <div class="titles">缺陷复现步骤</div>
          <hr width="500px" SIZE="1" class="content-hr" />
          <div class="long-content">{{ recurrenceSteps }}</div>
        </div>
        <div id="device-type" style="height: 220px;margin-top: 20px;">
          <div class="titles">测试设备类型</div>
          <hr width="500px" SIZE="1" class="content-hr" />
          <div class="long-content">{{ deviceType }}</div>
        </div>
      </div>
      <div
        id="evaluate-box"
        style="width: 700px;height: 120px;margin: 15px auto;border:2px solid #cceff5;background:#fafcfd"
      >
        <div
          id="score-box"
          style="width: 280px;height: 120px;position: relative;float: left"
        >
          <span style="position: absolute;font-size: 20px;left: 30%;top:10%"
            >给报告打分：</span
          >
          <el-rate
            v-model="value2"
            :disabled="scoreJudge"
            style="position: absolute;left: 17%;top:33%"
            class="star-rate"
          />
          <el-button
            type="success"
            :disabled="scoreJudge"
            style="position: absolute;font-size: 20px;left: 35%;top:65%;"
            @click="submitScore"
            >提交</el-button
          >
        </div>
      </div>
    </div>
    <div
      id="supplement-box"
      style="width: 590px;height: 950px;background-color: white;float: right;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); overflow: auto"
    >
      <div
        id="list-header"
        style="width: 550px;height:80px;position: relative; "
      >
        <div
          id="list-title"
          style="position: absolute;font-size: 40px;top:10%;left: 5%"
        >
          <strong>补充报告</strong>
        </div>
        <hr
          id="hr2"
          width="560px"
          SIZE="1"
          style="position: absolute;bottom: 1%;left: 1%;border: 1px solid"
        />
        <el-button
          type="success"
          @click="supplementdialogVisible = true"
          style="position: absolute;width: 200px;height: 50px;font-size: 30px;right: 2%;top:15%;"
          round
          >填写补充报告</el-button
        >
      </div>
      <div
        id="supplement-reports"
        style="width: 550px;height: 850px;margin: 10px auto"
      >
        <el-collapse
          v-model="activeNames"
          accordion
          style="width: 540px;margin: 10px auto;"
        >
          <el-collapse-item
            v-for="(item, i) in supplementreport"
            :title="item.supplementName"
            style=""
          >
            <el-descriptions class="margin-top" title="" :column="1" border>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    补充报告标题
                  </div>
                </template>
                {{ item.supplementName }}
              </el-descriptions-item>
              {{ findusername(item.employeeId) }}
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    补充人
                  </div>
                </template>

                {{ supplenmentusername }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    缺陷情况说明
                  </div>
                </template>
                {{ item.defectDescription }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    缺陷复现步骤
                  </div>
                </template>
                {{ item.recurrenceSteps }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    测试设备类型
                  </div>
                </template>
                {{ item.deviceType }}
              </el-descriptions-item>
            </el-descriptions>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <el-divider
      id="hr3"
      width="100%"
      SIZE="10"
      style="clear: both;margin-top: 960px"
    />
    <div
      id="comment-box"
      style="width: 70%;height: auto;margin: 20px auto; background-color: white;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);"
    >
      <div
        id="my-box"
        style="width: 750px;margin:0 auto;height: 210px;padding-top: 20px"
      >
        <div id="input-box" style="width: 750px;height: 180px;">
          <div
            class="head-icon"
            style="float: left;height:100%;width: 20%;position: relative"
          >
            <el-avatar
              class="avatars"
              size="large"
              v-if="this.role === 'employee'"
              style="position: absolute;top:15%;left:20%"
              ><img src="../../assets/employee_avatar.png"
            /></el-avatar>
            <el-avatar
              class="avatars"
              size="large"
              v-if="this.role === 'employer'"
              style="position: absolute;top:15%;left:20%"
              ><img src="../../assets/employer_avatar.png"
            /></el-avatar>
            <el-button
              type="success"
              round
              v-if="this.role === 'employee'"
              class="user-type"
              >众包工人</el-button
            >
            <el-button
              type="warning"
              round
              v-if="this.role === 'employer'"
              class="user-type"
              >发包方</el-button
            >
          </div>
          <div id="comment-input" style="float: left;height:100%;width: 65%;">
            <el-input
              v-model="description"
              show-word-limit
              maxlength="60"
              type="textarea"
              :rows="5"
              style="margin-top: 6%; "
              placeholder="请输入你对本报告的评价"
            ></el-input>
          </div>
          <div
            class="submit-button"
            style="float: left;height:100%;width: 15%;"
          >
            <el-button
              type="primary"
              style="font-size: 30px;width: 80%;height: 60%;margin-top: 28%"
              @click="submitDescription"
              >发表<br />评价</el-button
            >
          </div>
        </div>
        <hr width="750px" SIZE="1" style="border: 1px solid" />
      </div>

      <div id="all-comments" style="width: 750px;margin: 0 auto;height: auto;">
        <ul
          class="infinite-list"
          style="overflow: auto;list-style: none;padding-left: 0"
        >
          <li v-for="(item, i) in comments" class="infinite-list-item">
            <div
              class="comment-item"
              style="width: 100%;height: 130px;margin: 0 auto"
            >
              <div
                class="real-icon"
                style="width: 20%;height: 100%;float: left;position: relative"
              >
                <el-avatar
                  class="avatars"
                  size="large"
                  v-if="item.role === 'employee'"
                  style="position: absolute; left:19%;top:10%"
                  ><img src="../../assets/employee_avatar.png"
                /></el-avatar>
                <el-avatar
                  class="avatars"
                  size="large"
                  v-if="item.role === 'employer'"
                  style="position: absolute; left:19%;top:10%"
                  ><img src="../../assets/employer_avatar.png"
                /></el-avatar>
              </div>
              <div
                class="comment -info"
                style="width: 80%;height: 100%;float: left;"
              >
                <div class="others" style="width: 100%;height:30%;">
                  <div
                    class="commenter-name"
                    style="float: left;margin-left: 10px;font-size: 20px;font-weight: bolder;color: #808080"
                  >
                    {{ item.employeeName }}
                  </div>
                  <el-button
                    type="success"
                    round
                    v-if="item.role === 'employee'"
                    style="float: left;margin-bottom: 6px;margin-left: 20px"
                    >众包工人</el-button
                  >
                  <el-button
                    type="warning"
                    round
                    v-if="item.role === 'employer'"
                    style="float: left;margin-bottom: 6px;margin-left: 20px"
                    >发包方</el-button
                  >
                  <div
                    class="comment-time"
                    style="float: right;margin-left: 50px;margin-top:5px;font-size: 15px;font-weight: bolder;color: #808080"
                  >
                    {{ item.time }}
                  </div>
                </div>
                <div
                  class="comment-content"
                  style="width: 100%;height:60%;text-align: left; "
                >
                  {{ item.discription }}
                </div>
                <el-divider
                  width="100%"
                  style="margin-bottom: 0;margin-top: 0"
                />
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <el-dialog v-model="supplementdialogVisible" title="报告补充" width="50%">
    <el-form ref="formRef" label-width="120px" class="report">
      <el-form-item label="报告标题">
        <el-input v-model="supplementName" type="text"></el-input>
      </el-form-item>
      <br />
      <el-form-item label="缺陷情况补充说明">
        <el-input
          v-model="supplementdefectDescription"
          type="textarea"
        ></el-input>
      </el-form-item>
      <br />
      <el-form-item label="缺陷复现步骤">
        <el-input
          v-model="supplementrecurrenceSteps"
          type="textarea"
        ></el-input>
      </el-form-item>
      <br />
      <el-form-item label="测试环境">
        <el-select v-model="supplementdeviceType">
          <el-option label="Windows" value="Windows"></el-option>
          <el-option label="Android" value="Android"></el-option>
          <el-option label="IOS" value="IOS"></el-option>
          <el-option label="MacOS" value="MacOS"></el-option>
          <el-option label="Linux" value="Linux"></el-option>
        </el-select>
      </el-form-item>
      <br />
    </el-form>
    <el-button @click="supplementdialogVisible = false">取消创建</el-button>
    <el-button type="primary" @click="supplement">确认补充</el-button>
  </el-dialog>
</template>
<script>
import { ElMessage } from "element-plus";
import { TARGET } from "@/api/_prefix";
import { getImageAndTextSimilarity } from "@/api/report";
const axios = require("axios");
export default {
  name: "Report",
  data() {
    return {
      employeeId: parseInt(window.localStorage.getItem("userId")),
      reportName: window.localStorage.getItem("reportName"),
      defectDescription: window.localStorage.getItem("defectDescription"),
      recurrenceSteps: window.localStorage.getItem("recurrenceSteps"),
      deviceType: window.localStorage.getItem("deviceType"),
      value2: 0,
      description: "",
      reportId: parseInt(window.localStorage.getItem("reportId")),
      report: {},
      authorId: parseInt(window.localStorage.getItem("authorId")),
      role: window.localStorage.getItem("role"),

      activeNames: false,
      Avgscoce: 0.0,
      report_auto_score: 0.0,
      userInfo: {},
      scoreJudge: false,
      supplementreport: [],
      supplementdialogVisible: false,
      supplementName: "",
      supplementdefectDescription: "",
      supplementrecurrenceSteps: "",
      supplementdeviceType: "",

      comments: []
    };
  },

  methods: {
    prev() {
      this.$router.go(-1);
    },
    getFileLink(val) {
      console.log(val);
      return `${TARGET}/downloadScreenshots?reportId=` + val;
    },
    submitScore() {
      console.log(this.value2);
      this.scoreJudge = true;
      axios
        .post(`${TARGET}/score/newScore`, {
          employeeId: this.employeeId,
          reportId: window.localStorage.getItem("reportId"),
          score: this.value2
        })
        .then(res => {
          console.log(res);
          ElMessage({ message: "提交成功", type: "success" });
        });
    },
    findusername(id) {
      axios.post(`${TARGET}/user/getInfoByUserId?userId=${id}`).then(res => {
        this.supplenmentusername = res.data.username;
      });
    },

    handleDetail(i) {},
    supplement() {
      axios
        .post(`${TARGET}/supplement/completeSupplementInfo`, {
          employeeId: this.employeeId,
          reportId: window.localStorage.getItem("reportId"),
          supplementName: this.supplementName,
          defectDescription: this.supplementdefectDescription,
          recurrenceSteps: this.supplementrecurrenceSteps,
          deviceType: this.supplementdeviceType
        })
        .then(res => {
          console.log(res);
        });
      this.supplementdialogVisible = false;
      ElMessage({ message: "提交成功", type: "success" });
    },
    submitDescription() {
      console.log(this.description);
      axios
        .post(`${TARGET}/discription/newDiscription`, {
          employeeId: this.employeeId,
          reportId: window.localStorage.getItem("reportId"),
          discription: this.description
        })
        .then(res => {
          console.log(res);
          ElMessage({ message: "提交成功", type: "success" });
        });
    }
  },

  created: function (x) {
    axios
      .post(`${TARGET}/user/getInfoByUserId?userId=${this.authorId}`)
      .then(res => {
        this.userInfo = res.data;
        console.log(this.userInfo);
      });
    axios
      .post(
        `${TARGET}/supplement/getSupplementByReportId?reportId=${this.reportId}`
      )
      .then(res => {
        console.log(res);
        this.supplementreport = res.data;
      });
    axios
      .post(`${TARGET}/report/getAvgScoreByReportId?reportId=${this.reportId}`)
      .then(res => {
        console.log(res);
        this.Avgscoce = res.data.toFixed(1);
      });
    axios
      .post(
        `${TARGET}/discription/getDiscriptionByReportId?reportId=${this.reportId}`
      )
      .then(res => {
        this.comments = res.data;
        console.log(this.comments);
      });
    getImageAndTextSimilarity(this.reportId);
  }
};
</script>

<style scoped>
.titles {
  font-size: 30px;
  font-weight: bolder;
  height: 50px;
}
/deep/ .el-collapse-item__header {
  font-size: 18px;
  font-weight: bolder;
  width: 500px;
  height: auto;
  line-height: 30px;
  padding: 10px 0;
  text-align: left;
}
.long-content {
  height: 100px;
  overflow: auto;
  width: 650px;
  margin: 0 auto;
}
.content-hr {
  margin-top: 0;
}
.el-rate /deep/ .el-rate__icon {
  font-size: 30px;
}
.avatars {
  width: 100px;
  height: 100px;
}
.user-type {
  position: absolute;
  top: 75%;
  left: 28%;
}
</style>
