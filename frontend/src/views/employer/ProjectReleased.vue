<template>
  <div style="margin: 0 auto">
    <img src="../../assets/released.png" style="height: auto;width: 30%;float: left;margin-left: 10%;margin-top: 1%;margin-bottom: 2%">
    <div id="outer-list" style=" background-color: white; width: 80%; height: auto;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);float: left;margin-left: 10%">
      <div
        v-for="item in projects"
        class="item"
        align="left"
        style="width: 97%">

        <div class="info-box">
          <div style="font-size: 25px;position: absolute;top:15%; white-space: nowrap; text-overflow: ellipsis; width:100%; overflow:hidden;">{{item.projectName}}</div>
          <!--<p>{{ item.taskName }} 报名详情 {{item.signedNum}}/{{item.needWorkers}}</p>-->
        </div>
        <div class="info-box-2">
          <p style="margin-top: 0;margin-bottom: 2px">
            项目简介：<span style="color: #909399">{{
              item.introduction
            }}</span>
          </p>
          <p style="margin-top: 0">
            任务数量：<span style="color: #909399">{{ item.subtaskNum }}</span>
          </p>
        </div>
        <div class="button-box">
          <el-button
            class="button"
            v-if="item.finished === 0"
            type="primary"
            round
            @click="handleDetails(item.id)"
            style="position: absolute; right:14%; bottom: 10%; width: 130px;height: 50px; font-size: 20px;"
            >正在进行</el-button
          >
          <el-button
            class="button"
            v-if="item.finished === 1"
            type="info"
            round
            @click="handleDetails(item.id)"
            style="position: absolute; right:14%; bottom: 10%; width: 130px;height: 50px; font-size: 20px;"
            >已结束</el-button
          >
        </div>
        <hr
          align="center"
          width="100%"
          SIZE="1"
          style="position: absolute;bottom: 1%"
        />
        <!--        <hr  align=center width=100%  SIZE=1 style="position: absolute;bottom: 1%" />-->
      </div>
    </div>
  </div>
</template>

<script>
import { getAllProjects } from "@/api/project";

export default {
  name: "ProjectReleased",
  created() {
    this.initData();
  },
  methods: {
    handleDetails(id) {
      // this.flag=i;
      // this.dialogVisible=true;
      this.$router.push({
        path: "/employer/taskRelease",
        query: { projectId: id }
      });
    },
    initData() {
      this.uid = parseInt(window.localStorage.getItem("userId"));
      getAllProjects(this.uid).then(res =>{
        console.log(res)
        if(res.data!=null){
          this.projects = res.data;
        }
      })
      console.log(this.projects);
    }
  },
  data() {
    return {
      flag: 0,
      iconColor: ["#9fe9ff", "#808080"],
      innerVisible: false,
      dialogVisible: false,
      uid: 0,
      projects: [
        {
          id: 1,
          employerId: 6,
          projectName: "test1",
          introduction: "test1intro",
          subtaskNum: 0,
          finished: 0
        },
        {
          id: 2,
          employerId: 6,
          projectName: "test2",
          introduction: "test2intro",
          subtaskNum: 0,
          finished: 0
        },
        {
          id: 3,
          employerId: 6,
          projectName: "test3",
          introduction: "test3intro",
          subtaskNum: 0,
          finished: 0
        }
      ]
    };
  },
};
</script>

<style scoped>
.item {
  width: 700px;
  height: 130px;
  margin-left: 20px;
  margin-right: 20px;
  position: relative;
  /*background-color: #42b983;*/
}
.info-box {
  position: absolute;
  width: 90%;
  height: 55px;
  /*background-color: #909399;*/
  left: 5%;
  top: 4%;
}
.info-box-2 {
  position: absolute;
  width: 90%;
  height: 55px;
  /*background-color: blue;*/
  left: 6%;
  top: 50%;
}
.button-box {
  position: absolute;
  width: 90%;
  height: 55px;
  /*background-color: aquamarine;*/
  right: -5%;
  top: 50%;
}
.line {
  margin-top: 0;
  margin-bottom: 0;
}
</style>
