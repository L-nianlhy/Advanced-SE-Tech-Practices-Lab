<template>
  <el-container>
    <el-header height="15%">
      <img src="../../assets/ProjectDetail.png" alt="" id="project">
      <el-button
        type="primary"
        round
        class="establish"
        @click="dialogFormVisible_submit = true"
        v-if="employer_role === 'employer'"
        >发布任务</el-button
      >
      <el-dialog v-model="dialogFormVisible_submit">
        <el-form
          id="taskRelease"
          ref="formRef"
          label-width="20%"
          class="taskRelease"
          style="padding-top: 10px;background-color: #FAFCFD;margin-right: 4%;"
        >
          <img src="../../assets/taskRelease.png" style="width: 60%;margin-left: 5%;">
          <el-form-item label="任务名称">
            <el-input v-model="task_name" type="text" ></el-input>
          </el-form-item>

          <el-form-item label="人数需求">
            <el-input-number v-model="need_workers"></el-input-number>
          </el-form-item>

          <el-form-item label="任务描述">
            <el-input v-model="introduction" type="textarea" :rows="5"  resize="none"></el-input>
          </el-form-item>
          <el-form-item label="任务类型">
            <el-select v-model="task_type" placeholder="请选择测试类型">
              <el-option label="功能测试" value="1"></el-option>
              <el-option label="性能测试" value="2"></el-option>
              <el-option label="其他" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="测试设备">
            <el-select
              v-model="testDevice"
              placeholder="请选择测试设备"
            >
              <el-option label="Android设备" value="Android设备"></el-option>
              <el-option label="Linux设备" value="Linux设备"></el-option>
              <el-option label="鸿蒙设备" value="鸿蒙设备"></el-option>
              <el-option label="Mac设备" value="Mac设备"></el-option>
              <el-option label="Windows设备" value="Windows设备"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="开始时间">
            <el-date-picker
              v-model="startTime"
              type="datetime"
              placeholder="请选择开始时间"
              format="YYYY/MM/DD hh:mm:ss"
              value-format="YYYY-MM-DD hh:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker
              v-model="endTime"
              type="datetime"
              placeholder="请选择结束时间"
              format="YYYY/MM/DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item class="buttons">
            <el-button type="primary" @click="onSubmit" class="button">发布</el-button>
            <el-button @click="formReset" class="button">取消</el-button>
          </el-form-item>
          <!--  <input type="file" id="file_upload" name="file" v-model="file">-->
          <!--  <el-button @click="handleSubmit">submit</el-button>-->
        </el-form>
      </el-dialog>
    </el-header>
    <el-container>
      <el-aside width="70%">
        <div>
          <img src="../../assets/allTasks.png" alt="" id="detailImg" />
        </div>
        <el-card v-for="(task, index) in tasks" class="box" :key="index">
          <div>
            <div class="context_box">任务名:{{ task.taskName }}</div>
            <div class="context_box">
              <el-button type="primary" round>{{ task.taskType }}</el-button>
            </div>
            <div class="context_box">
            <el-button type="success" round>{{ task.testDevice }}</el-button>
            </div>
            <div class="context_box">任务人数:{{ task.needWorkers }}</div>
          </div>
          <div class="right">
            <el-button type="warning" @click="subTaskDetail(index)"
              >查看详情</el-button
            >
            <el-button type="danger" v-if="employer_role === 'employee'" @click = "handleSignUp(task.id)"
              >参加任务</el-button
            >
          </div>
<!--          此对话框用于显示任务详情-->
          <el-dialog
            v-model="dialogTableVisible_detail"
          >
            <el-descriptions
              title="任务详情"
              direction="vertical"
              :column="2"
              :size="size"
              border
            >
              <el-descriptions-item label="任务名">{{
                this.taskDetail.taskName
              }}</el-descriptions-item>
              <el-descriptions-item label="任务所需人数">{{
                this.taskDetail.needWorkers
              }}</el-descriptions-item>
              <el-descriptions-item label="任务简介" :span="2">{{
                this.taskDetail.introduction
              }}</el-descriptions-item>
              <el-descriptions-item label="开始时间" :span="2">{{
                  this.taskDetail.startTime
                }}</el-descriptions-item>
              <el-descriptions-item label="结束时间" :span="2">{{
                  this.taskDetail.endTime
                }}</el-descriptions-item>
              <el-descriptions-item label="任务设备">
                <el-tag size="small">{{ this.taskDetail.testDevice }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="任务类型"
              ><el-tag size="small">{{this.taskDetail.taskType}}</el-tag>
              </el-descriptions-item>
            </el-descriptions>

          </el-dialog>
        </el-card>
      </el-aside>
      <el-main width="30%">
        <div style="margin-top: 15%;">
        </div>
        <el-card >
          <div slot="header" class="clearfix">
            <span style="font-size: 150%;">项目简介</span>
          </div>
          <el-card style="margin-top: 10px;">
            <div class="context" >
              项目子任务数量: {{ this.ProjectSubTasksNum }}
            </div>
          </el-card>
          <el-card>
            <div class="context">
              项目名称: {{this.projectName}}
            </div>
          </el-card>
          <el-card>
            <div class="context">
              项目介绍: {{this.ProjectIntroduction}}
            </div>
          </el-card>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { ElMessage } from "element-plus";
import {release, signUp} from "@/api/user";
import { getProjectByID, getTasksByProjectID } from "@/api/project";

export default {
  name: "TaskRelease",
  data() {
    return {
      employer_role: "",
      formLabelWidth: "70px",
      dialogFormVisible_submit: false,
      dialogTableVisible_detail: false,
      projectId: 0,
      userName: "刘二狗",
      projectName: "啊哈哈",
      ProjectIntroduction: "ababcdcd",
      ProjectSubTasksNum: 0,
      tasks:[
        {
          taskName: 123,
          taskType: "性能测试",
          taskDevice: "Mac",
          needWorkers: 4,
          taskStartTime: "yyyy-MM-dd HH:mm:ss",
          taskEndTime: "yyyy-MM-dd HH:mm:ss",
          taskIntroduction: "123123"
        },
        {
          taskName: 123,
          taskType: "功能测试",
          taskDevice: "Linux",
          needWorkers: 4,
          taskStartTime: "yyyy-MM-dd HH:mm:ss",
          taskEndTime: "yyyy-MM-dd HH:mm:ss",
          taskIntroduction: "123123"
        }
      ],
      uploadUrl: "http://1.15.237.160:8081",
      fileList: [],
      employer_id: 1,
      task_name: "",
      need_workers: 0,
      introduction: "",
      startTime: "",
      endTime: "",
      task_type: "",
      testDevice: "",
      taskId: "",
      taskDetail: {}
    };
  },
  mounted() {
    this.initData();
  },
  methods: {
    subTaskDetail(taskId){
      this.dialogTableVisible_detail = true;
      this.taskDetail = this.tasks.at(taskId);
      console.log(this.taskDetail);
    },
    initData() {
      this.projectId = parseInt(this.$route.query.projectId);
      getProjectByID(this.projectId).then(res => {
        if(res.data.code===1){
          this.projectName = res.data.data.projectName;
          this.ProjectIntroduction = res.data.data.introduction;
          this.ProjectSubTasksNum = res.data.data.subtasksNum;
        }else{
          ElMessage.error("获取信息错误！");
        }
      });
      this.employer_role = window.localStorage.getItem("role");
      //console.log(this.employer_role);
      //查询函数
      //todo:增加重新加载任务方法->实时刷新
      getTasksByProjectID(this.projectId).then(res =>{
        if(res.data!=null){
          this.tasks = res.data;
        }
        else{
          this.tasks = "";
        }
      });

    },
    formReset() {
      document.getElementById("taskRelease").reset();
      this.dialogFormVisible_submit = false;
    },
    onSubmit() {
      release({
        employerId: parseInt(window.localStorage.getItem("userId")),
        taskName: this.task_name,
        needWorkers: this.need_workers,
        introduction: this.introduction,
        startTime: this.startTime,
        endTime: this.endTime,
        taskType: this.task_type,
        testDevice: this.testDevice,
        filePath: "123",
        projectId: this.projectId
      }).then(res => {
        console.log(res);
        window.localStorage.setItem("taskId", res.data.id);
        window.localStorage.setItem("taskName", res.data.taskName);
        window.localStorage.setItem("taskType", res.data.taskType);
        window.localStorage.setItem("test_device", res.data.testDevice);
        window.localStorage.setItem("needWorkers", res.data.needWorkers);
        window.localStorage.setItem("startTime", res.data.startTime);
        window.localStorage.setItem("endTime", res.data.endTime);
        window.localStorage.setItem("introduction", res.data.introduction);
        if (res.code === 1) {
          ElMessage({ message: "任务创建成功", type: "success" });
          this.dialogFormVisible_submit = false;
          this.$router.go(0);
        } else {
          ElMessage.error("任务创建失败");
        }
      });
    },
    handleSignUp(taskId){
      let employeeId = window.localStorage.getItem("userId");
      let payload = {taskId:taskId,employeeId:employeeId};
      signUp(payload).then(res => {
        console.log(res)
        if (res.data.code === 1) {
          ElMessage({ message: "接收任务成功", type: "success" });
        } else {
          ElMessage.error(res.data.msg);
        }
      })
    }
    // handleBeforeUpload(file){
    //   let taskId = window.localStorage.getItem("taskId")
    //   this.uploadUrl = `http://1.15.237.160:8081/file/${taskId}`
    // 			console.log("开始上传，上传的文件为："+file)
    // 			let formData = new FormData();
    // 			formData.append("multipartFiles", file);
    // 			axios({
    // 				  method: 'post',
    // 				  url: `http://1.15.237.160:8081/file/${taskId}`,
    // 				  data: formData,
    // 				  headers: {'Content-Type': 'multipart/form-data' }
    // 			  }).then((res) => {
    // 				  console.log("文件上传返回："+res)
    // 			  }).catch(error => {
    // 				  console.log("文件上传异常:"+error)
    // 			  })
    // }
  }
};
</script>

<style scoped>

#detailImg {
  width: 20%;
  text-align: left;
}

#project {
  width: 20%;
  float: left;
}
.establish{
  width: 15%;
  height: 60px;
  font-size: 150%;
  margin-top: 6%;
  float: right;
  margin-right: 7%;
;
}
.context_box{
  float: left;
  height: 50px;
  line-height: 30px;
  margin: 0 2%;
}
.box{
  margin: 2% 5%;
}

ul{
  list-style: none;
}
li{
  display: inline-block;
  text-align: center;
  float: left;
}
.left{
  float: left;
}
.right{
  float:right;
  margin-right: 5%;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}
.buttons{
  margin-top: 10%;
  margin-right: -150%;
  margin-bottom: -5%;
}
.button{
  width: 10%;
  height: 40px;
  font-size: 130%;
}
</style>
