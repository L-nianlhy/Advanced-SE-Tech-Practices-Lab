<template>
  <div style="overflow-y:scroll;overflow-x:hidden;height:100%">
    <el-page-header @click="prev" />
    <el-form
      ref="formRef"
      label-width="120px"
      class="report"
      style="padding-top: 0;background-color: #FAFCFD"
    >
      <img
        src="../../assets/reportFill.png"
        style="width: 300px;height: 130px"
      />
      <el-form-item label="报告标题">
        <el-input v-model="reportName" type="text"></el-input>
      </el-form-item>
      <br />
      <el-form-item label="缺陷情况说明">
        <el-input
          v-model="defectDescription"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <br />
      <el-form-item label="缺陷复现步骤">
        <el-input
          v-model="recurrenceSteps"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <br />
      <el-form-item label="测试环境">
        <el-select v-model="deviceType">
          <el-option label="Windows" value="Windows"></el-option>
          <el-option label="Android" value="Android"></el-option>
          <el-option label="IOS" value="IOS"></el-option>
          <el-option label="MacOS" value="MacOS"></el-option>
          <el-option label="Linux" value="Linux"></el-option>
        </el-select>
      </el-form-item>
      <br />
      <!--  <el-upload>-->
      <!--    -->
      <!--  </el-upload>-->

      <el-form-item label="缺陷截图">
        <el-upload
          class="avatar-uploader el-upload--text"
          :drag="true"
          action="http://1.15.237.160:8081/uploadScreenshots"
          multiple
          :data="{ reportId: this.rId + 1 }"
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
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="formReset">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { fillReport, hasSoonFinishTaskOrNot } from "@/api/user";
import { ElMessage } from "element-plus";
import { TARGET } from "@/api/_prefix";

export default {
  name: "FillReport",
  data() {
    return {
      rId: 0,
      reportName: "",
      defectDescription: "",
      recurrenceSteps: "",
      deviceType: "",
      URL: "",
      Plus: true,
      videoFlag: false,
      videoUploadPercent: 0,
      fileList: []
    };
  },
  methods: {
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

    handleBeforeUpload() {
      // this.rId=parseInt(window.localStorage.getItem("rId"));
      // console.log(this.rId)
    },

    prev() {
      this.$router.go(-1);
    },
    formReset() {
      document.getElementById("taskRelease").reset();
    },
    onSubmit() {
      let reportId = parseInt(window.localStorage.getItem("rId"));
      fillReport({
        rId: reportId,
        reportName: this.reportName,
        defectDescription: this.defectDescription,
        recurrenceSteps: this.recurrenceSteps,
        deviceType: this.deviceType
      }).then(res => {
        console.log(res.data);

        ElMessage({ message: "提交报告成功！", type: "success" });
        hasSoonFinishTaskOrNot(
          parseInt(window.localStorage.getItem("userId"))
        ).then(res => {
          if (res === false) {
            document.getElementById("bell").style.display = "none";
          }
          this.$router.go(-1);
        });
      });
      //   axios.post(`http://1.15.237.160:8081/report/completeReport`,
      //       {rId:this.rId,
      //         reportName:this.reportName,
      //       defectDescription:this.defectDescription,
      //       recurrenceSteps:this.recurrenceSteps,
      //       deviceType:this.deviceType}).then(res=>{
      //         console.log(res.data);
      //         return res.data;
      //   })
    }
  },
  created() {
    this.rId = parseInt(window.localStorage.getItem("rId"));
  }
};
</script>

<style scoped>
.cognizance-box {
  width: 100%;
  height: 100vh;
  overflow-y: scroll;
  margin: 0 auto;
}
.report {
  background-color: white;
  border: 1px solid #dcdfe6;
  width: 800px;
  margin: 30px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}
</style>
