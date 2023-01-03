<template>
  <div id="all">
    <el-form
      style="background-color: white"
      ref="loginForm"
      :model="form"
      :rules="rules"
      label-width="80px"
      class="login-box"
    >
      <img
        src="../assets/collectLogo.png"
        alt=""
        style="width: 250px;height:50px; margin-top:10px; margin-left: 20px; margin-bottom: 30px;"
      />
      <h3 class="login-title">欢迎登录</h3>
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
      <el-form-item>
        <el-button type="primary" @click="handleLogin('loginForm')"
          >登录</el-button
        >
        <el-button
          type="primary"
          @click="
            () => {
              this.$router.push('/register');
            }
          "
          >去注册</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { login } from "@/api/user";
import { ElMessage } from "element-plus";

export default {
  name: "Login",
  data() {
    return {
      form: {
        username: "",
        password: "",
        msg: ""
      },
      // 表单验证，需要在 el-form-item 元素中增加 prop 属性
      rules: {
        username: [
          { required: true, message: "用户名不可为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不可为空", trigger: "blur" }]
      },

      // 对话框显示和隐藏
      dialogVisible: true
    };
  },
  //   beforeCreate () {
  //   document.querySelector('body').setAttribute('style', 'background-im:#FFB300');
  // },
  methods: {
    handleLogin(formName) {
      // 为表单绑定验证功能
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
          // this.$router.push("/employer");
          login({
            username: this.form.username,
            password: this.form.password
          }).then(res => {
            console.log(res);
            console.log(res.code);
            if (res.code === 1) {
              window.localStorage.setItem("username", res.data.username);
              window.localStorage.setItem("role", res.data.userRole);
              window.localStorage.setItem("userId", res.data.id);
              this.username = res.data.username;
              ElMessage({ message: "登录成功，欢迎", type: "success" });
              if (res.data.userRole === "employer") {
                this.$router.push("/employer/employerSquare");
              } else if (res.data.userRole === "employee") {
                this.$router.push("/employee/employeeSquare");
              } else if (res.data.userRole === "admin") {
                this.$router.push("/admin/adminSquare");
              }
            } else if (res.code === 0) {
              this.msg = res.msg;
              ElMessage.warning({
                message: this.msg + "请联系管理员",
                type: "warning"
              })
            }
            else {
              this.msg = res.msg;
              ElMessage.error(res.msg);
            }
            // this.$router.push('/home');
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

<style>
#all {
  background: url("../assets/img_1.png");
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100% 100%;
}
html,
body {
  margin: 0;
  padding: 0;
}
.login-box {
  border: 1px solid #dcdfe6;
  width: 350px;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #dcdfe6;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>
