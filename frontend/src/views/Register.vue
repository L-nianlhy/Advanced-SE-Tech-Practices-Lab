<template>
  <div id="all">
    <el-form
      ref="registerForm"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="register-box"
    >
      <img
          src="../assets/collectLogo.png"
          alt=""
          style="width: 250px;height:50px; margin-top:10px; margin-left: 20px; margin-bottom: 30px;"
      />
      <h3 class="register-title">欢迎注册</h3>
      <el-form-item label="用户名" prop="username">
        <el-input
          type="text"
          placeholder="请输入账号"
          v-model="form.username"
          maxlength="11"
        />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input
          type="password"
          placeholder="请输入密码"
          v-model="form.password"
          maxlength="11"
        />
      </el-form-item>
      <el-form-item label="类型" prop="role">
        <el-select v-model="form.role" placeholder="请选择账号类型">
<!--          <el-option label="管理者" value="admin"></el-option>-->
          <el-option label="发包方" value="employer"></el-option>
          <el-option label="众包工人" value="employee"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleRegister('registerForm')"
          >注册</el-button
        >
        <el-button
          type="primary"
          @click="
            () => {
              this.$router.push('/login');
            }
          "
          >去登录</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { register } from "@/api/user";
import { ElMessage } from "element-plus";
export default {
  name: "Register",
  data() {
    return {
      form: {
        username: "",
        password: "",
        role: "",
        roles: ["employer", "employee", "admin"]
      },
      rules: {
        username: [
          { required: true, message: "用户名不可为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "密码不可为空", trigger: "blur" }
        ],
        role: [
          { required: true, message: "请选择要注册的账号类型", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    handleRegister(formName) {
      // register({
      //   username:this.form.username,
      //   password:this.form.password,
      //   user_role:this.form.role
      // }).then(res=>{
      //   console.log(res);
      //
      // })
      // 为表单绑定验证功能
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
          register({
            username: this.form.username,
            password: this.form.password,
            userRole: this.form.role
          }).then(res => {
            console.log(res);
            if (res.code === 1) {
              ElMessage({ message: "注册成功，请登录", type: "success" });
            }
          });
        } else {
          this.dialogVisible = true;
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
#all {
  background: url("../assets/img_1.png");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
.register-box {
  background-color: white;
  border: 1px solid #dcdfe6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #dcdfe6;
}

.register-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>
