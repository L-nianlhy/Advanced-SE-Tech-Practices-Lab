<template>
  <div id="all" style="overflow-y:scroll;overflow-x:hidden;height:100%">
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
      <el-menu-item index="/admin/adminSquare" style="font-weight: bolder">
        动态广场
      </el-menu-item>
      <el-menu-item index="/admin/adminUserManage" style="font-weight: bolder">
        用户管理
      </el-menu-item>
      <el-menu-item index="/admin/adminRank" style="font-weight: bolder"
        >排行榜</el-menu-item
      >
      <el-menu-item index="/admin/adminStatistics" style="font-weight: bolder"
        >数据分析</el-menu-item
      >
      <img
        src="../../assets/admin_avatar.png"
        style="width: 40px;height:40px;margin-top:10px;margin-left: 1000px"
        alt=""
        @click="turnToCenter()"
      />
    </el-menu>

    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
export default {
  name: "Admin",
  methods: {
    turnToCenter(){
      this.$router.push("/admin/adminCenter");
    },
    returnToSquare() {
      this.$router.push("/admin/adminSquare");
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
