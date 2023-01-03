<template>
  <div style="width: 1400px;margin: 0 auto">
    <div>
      <div style="height: 120px;width: 1100px;margin: 0 auto">
        <img src="../../assets/allUser.png" style="height: 120px;width: 300px;float: left">
      </div>
      <div style="margin-left:1%;margin-right:1%">
        <el-row>
          <el-col :span="4" v-for="user in users" :key="user.id" :offset="1" >
            <el-card class="user-card">
              <div slot="header" class="clearfix">
                <span style="float: left;font-weight: bold">用户名：{{ user.username }}</span>
                <el-button
                  v-if="user.loginFlag === 1"
                  type="text"
                  style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px"
                  @click="forbidden(user.id)"
                  ><div v-show="user.userRole !== 'admin'">封禁用户</div></el-button>
                <el-button
                  v-else-if="user.loginFlag === 0"
                  type="text"
                  style="float: right;color:#2376b7;margin-right: 10px;margin-left: 10px"
                  @click="unforbidden(user.id)"
                >解封用户</el-button>
              </div>
              <div class="text item" style="float: left" v-if="user.userRole === 'admin'">用户角色：管理员</div>
              <div class="text item" style="float: left" v-else-if="user.userRole === 'employee'">用户角色：众包工人</div>
              <div class="text item" style="float: left" v-if="user.userRole === 'employer'">用户角色：发包方</div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import { findAll, forbidden } from "@/api/user";

export default {
  name: "UserManage",
  data() {
    return {
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
      ]
    };
  },

  methods: {
    forbidden(userId) {
      forbidden(userId)
        .then(res => {
          if (res.code === 1) this.$message.success("封禁成功");
          else this.$message.error("封禁失败:" + res.msg);
        })
        .catch(res => {
          this.$message.error("封禁失败:" + res);
        });
    },

    unforbidden(useId) {
      console.log(useId);
      this.$message.warning("暂不支持解封哦");
    }
  },
  mounted() {
    findAll().then(res => {
      console.log(res)
      this.users = res;
    });
  }
};
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.user-card {
  width: 250px;
  height: 100px;
  margin: 25px 50px 25px 80px;
  position: relative;
}
</style>
