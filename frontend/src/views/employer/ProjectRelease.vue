<template>
  <el-form
    id="projectRelease"
    ref="formRef"
    label-width="10%"
    class="projectRelease"
    style="padding-top: 10px;background-color: #FAFCFD"
  >
    <el-dialog v-model="dialogFormVisible" align-center="true">
      <div class="text">您已成功创建项目</div>
      <span class="dialog-footer">
        <el-button @click="jumpBack" class="button" type="warning">稍后再去发布</el-button>
        <el-button type="success" @click="jump" class="button">
          立刻去发布
        </el-button>
      </span>
    </el-dialog>
    <img src="../../assets/ProjectRelease.png" style="width: 50%" alt="" />
    <el-form-item label="项目名称">
      <el-input v-model="projectName" type="text"></el-input>
    </el-form-item>
    <el-form-item label="项目描述">
      <el-input
        v-model="introduction"
        type="textarea"
        :rows="5"
        resize="none"
      ></el-input>
    </el-form-item>
    <el-form-item class="buttons">
      <el-button type="primary" @click="onSubmit" class="button"
        >发布</el-button
      >
      <el-button @click="formReset" class="button">重置</el-button>
    </el-form-item>
    <!--  <input type="file" id="file_upload" name="file" v-model="file">-->
    <!--  <el-button @click="handleSubmit">submit</el-button>-->
  </el-form>
</template>

<script>
import { ElMessage } from "element-plus";
import { newProject } from "@/api/project";

export default {
  name: "ProjectRelease",
  data() {
    return {
      uploadUrl: "http://1.15.237.160:8081",
      projectName: "",
      introduction: "",
      dialogFormVisible: false,
      projectId: ""
    };
  },
  methods: {
    jumpBack() {
        this.dialogFormVisible = false,
        this.$router.push("/employer/employerSquare");
    },
    jump() {
      this.$router.push({
        path: "/employer/taskRelease",
        query: { projectId: this.projectId }
      });
    },
    formReset() {
      document.getElementById("projectRelease").reset();
    },
    onSubmit() {
      this.dialogFormVisible = true;
      newProject({
        employerId: parseInt(window.localStorage.getItem("userId")),
        projectName: this.projectName,
        introduction: this.introduction
      }).then(res => {
        if (res.data.code === 1) {
          ElMessage({ message: "项目创建成功", type: "success" });
          this.dialogFormVisible = true;
          this.projectId = res.data.data.id;
        } else {
          ElMessage.error("项目创建失败");
        }
      });
    }
  }
};
</script>

<style scoped>
.projectRelease {
  background-color: white;
  border: 1px solid #dcdfe6;
  width: 70%;
  margin: 0 auto;
  padding: 0 10%;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}
.buttons {
  margin: 5% 20%;
}
.button {
  width: 30%;
  height: 40px;
  font-size: 130%;
}
.text{
  font-size: 200%;
  margin-bottom: 10%;
}
</style>
