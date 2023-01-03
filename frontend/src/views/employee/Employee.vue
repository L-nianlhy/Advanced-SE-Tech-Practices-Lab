<template>
  <div id="all" style="overflow-y:scroll;overflow-x:hidden;height:100%">
    <el-menu
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      router
      style="height: 8%"
    >
      <img
        src="../../assets/collectLogo.png"
        alt=""
        style="width: 10%;height:60%; margin-top:1%; margin-left: 1%"
        @click="returnToSquare()"
      />
      <el-menu-item
        index="/employee/employeeSquare"
        style="font-weight: bolder;width: 6.5%"
      >
        动态广场
      </el-menu-item>
      <el-menu-item
        index="/employee/taskReceived"
        style="font-weight: bolder;width: 6.5%"
        >我的任务</el-menu-item
      >
      <el-menu-item
        index="/employee/employeeRank"
        style="font-weight: bolder;width: 6.5%"
        >排行榜</el-menu-item
      >
      <el-menu-item
        index="/employee/employeeStatistics"
        style="font-weight: bolder;width: 6.5%"
        >数据分析</el-menu-item
      >
      <img
        src="../../assets/employee_avatar.png"
        style="width: auto;height:60%;margin-top:0.75%;margin-left: 50%;float: right"
        alt=""
        @click="turnToCenter()"
      />
      <el-icon
        style="margin-top: 1.25%;font-size: 170%;margin-left: 3%"
        @click="dialogVisible = true"
        id="bell"
        ><BellFilled
      /></el-icon>
      <el-dialog v-model="dialogVisible" title="提醒" width="30%">
        <span>您有将要结束任务未完成</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="handleGo()">
              前往查看
            </el-button>
          </span>
        </template>
      </el-dialog>
    </el-menu>

    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
import { BellFilled } from "@element-plus/icons";
import { hasSoonFinishTaskOrNot } from "@/api/user";
export default {
  name: "Employee",
  components: {
    BellFilled
  },
  data() {
    return {
      dialogVisible: false,
      t: null,
      flag: false
    };
  },
  methods: {
    turnToCenter() {
      this.$router.push("/employee/employeeCenter");
    },
    returnToSquare() {
      this.$router.push("/employee/employeeSquare");
    },
    handleSelect(key, keyValue) {
      console.log(key, keyValue);
    },
    handleGo() {
      this.dialogVisible = false;
      this.$router.push("/employee/taskReceived");
    },
    changeColor() {
      if(!this.flag){
        return;
      }
      let bell = document.getElementById("bell");
      bell.style.color = bell.style.color === "black" ? "white" : "black";
    }
  },
  mounted() {
    hasSoonFinishTaskOrNot(
      parseInt(window.localStorage.getItem("userId"))
    ).then(res => {
      this.flag = res;
      if (res === false) {
        document.getElementById("bell").style.display = "none";
      }
    });
    this.t = setInterval(this.changeColor, 400);
  },
  beforeDestroy() {
    clearInterval(this.t);
  }
};
</script>

<style scoped>
/*#all{*/
/*  background:url("../../assets/background.jpg");*/
/*width:100%;*/
/*height:100%;*/
/*position:fixed;*/
/*background-size:100% 100%;*/
/*}*/
</style>
