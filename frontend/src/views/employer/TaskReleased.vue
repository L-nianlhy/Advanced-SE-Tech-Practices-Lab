<template>
<div style="width: 1400px;margin: 0 auto">
  <div id="outer-list" style=" background-color: white; width: 740px; height: auto;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);float: left">
    <div v-for="item in tasks" class="item" align="left">
      <div class="icon-box">
        <el-icon class="icon" v-if="item.taskType==='功能测试'" :style="{color:iconColor[item.available]}">
          <Setting />
        </el-icon>
        <el-icon class="icon" v-if="item.taskType==='性能测试'" :style="{color:iconColor[item.available]}">
          <Bicycle />
        </el-icon>
        <el-icon class="icon" v-if="item.taskType==='其他'" :style="{color:iconColor[item.available]}">
          <PieChart />
        </el-icon>
      </div>
      <div class="info-box">
        <div style="font-size: 25px;position: absolute;top:15%; white-space: nowrap; text-overflow: ellipsis; width:360px; overflow:hidden;">{{item.taskName}}</div>
        <div style="position: absolute;left: 70%;">报名详情：<span style="font-size: 30px">{{item.signedNum}}/{{item.needWorkers}}</span></div>
        <!--<p>{{ item.taskName }} 报名详情 {{item.signedNum}}/{{item.needWorkers}}</p>-->
      </div>
      <div class="info-box-2">
        <p style="margin-top: 0;margin-bottom: 2px">任务开始时间：<span style="color: #909399">{{item.startTime}}</span></p>
        <p style="margin-top: 0">任务结束时间：<span style="color: #909399">{{item.endTime}}</span></p>
      </div>
      <div class="button-box">
        <el-button class="button" v-if="item.available===1" type="primary" round @click="handleDetails(item)"  style="position: absolute; right:14%; bottom: 10%; width: 130px;height: 50px; font-size: 20px;">正在进行</el-button>
        <el-button class="button" v-if="item.available===0" type="info" round @click="handleDetails(item)"  style="position: absolute; right:14%; bottom: 10%; width: 130px;height: 50px; font-size: 20px;">已结束</el-button>
      </div>
      <hr  align=center width=650px  SIZE=1 style="position: absolute;bottom: 1%" />
    </div>
  </div>
  <div id="select-box" style="width: 300px;height: 350px;background-color: white;float: left;margin-left: 100px;border-radius: 10px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);border:2px solid #cceff5;">
    <div class="select-title" style="width: 280px;height: 90px;margin: 15px auto;position: relative;">
      <img src="../../assets/select-title.png" style="width: 140px;height: 40px;position: absolute;left: 5px">
      <el-button type="primary" style="width: 80px;height: 30px;position: absolute;left: 15px;top: 50px" round plain @click="selectAlltests">全部任务</el-button>
      <el-divider style="position: absolute;bottom: 1px" class="line"></el-divider>
    </div>
    <div class="select-type" style="width: 280px;height: 70px;margin:10px auto;">
      <div class="intro1" style="text-align: left;color: #808080;">·按照任务类型筛选：</div>
      <el-divider class="line" style="width: 150px"></el-divider>
      <div class="buttons" style="margin-top: 5px">
        <el-button type="success" style="width: 80px;height: 30px;float: left" round plain @click="selectFtests">功能测试</el-button>
        <el-button type="success" style="width: 80px;height: 30px;float: left" round plain @click="selectPtests">性能测试</el-button>
      </div>
    </div>
    <div class="select-type" style="width: 280px;height: 70px;margin:0 auto">
      <div class="intro1" style="text-align: left;color: #808080;">·按照任务状态筛选：</div>
      <el-divider class="line" style="width: 150px"></el-divider>
      <div class="buttons" style="margin-top: 5px">
        <el-button type="warning" style="width: 80px;height: 30px;float: left" round plain @click="selectIntesttests">正在进行</el-button>
        <el-button type="warning" style="width: 80px;height: 30px;float: left" round plain @click="selectEndtests">已结束</el-button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import {User,Iphone, Location ,OfficeBuilding, Tickets, Edit, Setting, Bicycle, PieChart}from "@element-plus/icons"
import axios from "axios";
import {TARGET} from "@/api/_prefix";

export default {
  name: "TaskReleased",
  components:{
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
  methods:{
    handleDetails(item){

      // this.flag=i;
      // this.dialogVisible=true;
      window.localStorage.setItem("taskId",item.id);
      window.localStorage.setItem("taskName",item.taskName);
      window.localStorage.setItem("taskType",item.taskType);
      window.localStorage.setItem("needWorkers",item.needWorkers);
      window.localStorage.setItem("startTime",item.startTime);
      window.localStorage.setItem("endTime",item.endTime);
      window.localStorage.setItem("introduction",item.introduction);
      window.localStorage.setItem("testDevice",item.testDevice);
      this.$router.push("/employer/task");

    },
    selectFtests(){
      axios.post(`${TARGET}/task/getFTaskByEmployerId?employerId=${this.uid}`,).then(res=>{
        console.log(res);
        this.tasks=res.data;
      })
    },
    selectPtests(){
      axios.post(`${TARGET}/task/getPTaskByEmployerId?employerId=${this.uid}`,).then(res=>{
        console.log(res);
        this.tasks=res.data;
      })
    },
    selectEndtests(){
      axios.post(`${TARGET}/task/getEndTaskByEmployerId?employerId=${this.uid}`,).then(res=>{
        console.log(res);
        this.tasks=res.data;
      })
    },
    selectIntesttests(){
      axios.post(`${TARGET}/task/getIntestTaskByEmployerId?employerId=${this.uid}`,).then(res=>{
        console.log(res);
        this.tasks=res.data;
      })
    },
    selectAlltests(){
      axios.post(`${TARGET}/task/getTasksByUserId?uid=${this.uid}`,).then(res=>{
        console.log(res);
        this.tasks=res.data;
      })
    },
  },
  data() {
    return {
      flag: 0,
      iconColor: ["#808080", "#9fe9ff"],
      innerVisible:false,
      dialogVisible: false,
      uid:parseInt(window.localStorage.getItem("userId")),
      tasks: []
    }
  },
  created:function(){
    axios.post(`${TARGET}/task/getTasksByUserId?uid=${this.uid}`,).then(res=>{
      console.log(res);
      this.tasks=res.data;
      return res.data;
    })
  }
}
</script>

<style scoped>
.item{
  width: 700px;
  height: 130px;
  margin-left: 20px;
  margin-right: 20px;
  position: relative;
  /*background-color: #42b983;*/
}
.icon{
  font-size: 90px;
  /*color: #9fe9ff;*/
}
.icon-box{
  position: absolute;
  width: 120px;
  height: 120px;
  top:7%;
  left:10px;
}
.info-box{
  position: absolute;
  width: 520px;
  height:55px;
  /*background-color: #909399;*/
  right: 5%;
  top: 4%
}
.info-box-2{
  position: absolute;
  width: 300px;
  height:55px;
  /*background-color: blue;*/
  right: 36%;
  top:50%;
}
.button-box{
  position: absolute;
  width: 220px;
  height:55px;
  /*background-color: aquamarine;*/
  right:5%;
  top:50%;
}
  .line{
    margin-top: 0;
    margin-bottom: 0;
  }
</style>