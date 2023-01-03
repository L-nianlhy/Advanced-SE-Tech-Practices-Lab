<template>
  <div id="all" style="overflow-y:scroll;overflow-x:hidden;height:100%">
    <div style="display: inline">
      <el-menu
        :default-active="$route.path"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        router
      >
        <img
          src="../../assets/collectLogo.png"
          alt=""
          style="width: 200px;height:40px; margin-top:10px; margin-left: 20px"
          @click="returnToSquare()"
        />
        <el-menu-item
          index="/employer/employerSquare"
          style="font-weight: bolder"
          >动态广场</el-menu-item
        >
        <el-menu-item index="/employer/projectRelease/" style="font-weight: bolder"
          >项目发布</el-menu-item
        >
        <el-menu-item index="/employer/projectReleased" style="font-weight: bolder"
          >已发布</el-menu-item
        >
        <el-menu-item index="/employer/employerRank" style="font-weight: bolder"
          >排行榜</el-menu-item
        >
        <el-menu-item
          index="/employer/employerStatistics"
          style="font-weight: bolder"
          >数据分析</el-menu-item
        >
        <img
          src="../../assets/employer_avatar.png"
          style="width: 40px;height:40px;margin-top:10px;margin-left: 800px"
          alt=""
          @click="turnToCenter()"
        />
      </el-menu>
    </div>
    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
export default {
  name: "Employer",
  // data(){
  //   return {
  //     activeIndex:"/employer/taskRelease"
  //   };
  // }
  methods: {
    turnToCenter() {
      this.$router.push("/employer/employerCenter");
    },
    returnToSquare() {
      this.$router.push("/employer/employerSquare");
    },
    handleSelect(key, keyValue) {
      console.log(key, keyValue);
    }
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
