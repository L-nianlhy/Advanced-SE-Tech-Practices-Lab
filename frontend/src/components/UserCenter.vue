<template>
  <div>
    <div class="grid-content bg-purple">
      <el-card class="box-card">
        <h2>个人信息</h2>
        <div>
          <span style="float: left;font-size: 20px">用户名：</span>
          <span style="float:left;font-size: 20px">{{
            dataForm.username
          }}</span>
          <img
            v-show="isempolyee"
            src="../assets/employee_avatar.png"
            alt=""
            style="width:80px;height:80px;position: absolute;left: 70%;"
          />
          <img
            v-show="isemployer"
            src="../assets/employer_avatar.png"
            alt=""
            style="width:80px;height:80px;position: absolute;left: 70%;"
          />
          <img
            v-show="isadmin"
            src="../assets/admin_avatar.png"
            alt=""
            style="width:80px;height:80px;position: absolute;left: 70%;"
          />
        </div>
        <br /><br />
        <div>
          <span style="float: left;font-size: 20px">角色：</span>
          <span style="float:left;font-size: 20px">{{
            this.changeRole(dataForm.role)
          }}</span>
        </div>
        <br /><br />
        <div>
          <span style="float: right">
            <el-button el-button type="primary" @click="dialogSignature = true"
              >修改</el-button
            >
          </span>
          <span style="float: left;font-size: 20px">个人签名：</span>
          <span style="float:left;font-size: 20px">{{
            dataForm.signature
          }}</span>
        </div>
        <br />
        <el-divider />
        <div v-show="isempolyee">
          <h2>个人特性</h2>
          <div>
            <span style="float: left;font-size: 20px">专业能力：</span>
            <span
              v-for="item in this.employeeInfo.professionalAbility"
              style="float:left;font-size: 20px; margin-right: 10px"
              >{{ item }}</span
            >
          </div>
          <br /><br />
          <div>
            <span style="float: left;;font-size: 20px">任务偏好：</span>
            <span style="float:left;font-size: 20px;">{{
              this.employeeInfo.taskPreference
            }}</span>
          </div>
          <br /><br />
          <div>
            <span style="float: left;;font-size: 20px">测试设备：</span>
            <span
              v-for="item in this.employeeInfo.testDevice"
              style="float:left;font-size: 20px; margin-right: 10px"
              >{{ item }}</span
            >
          </div>
          <br />

          <el-divider />
        </div>
        <div v-show="isempolyee">
          <h2>综合测评</h2>
          <div style="margin-bottom: 20px">
            <span v-show="isempolyee">综合评分：</span>
            <span
              v-show="isempolyee"
              style="font-size: 50px;font-weight: bolder;"
              >{{ Math.round(this.employeeInfo.totalScore) }}/100</span
            >
          </div>
          <div>
            <span style="float: left;width: 100px">报告平均分：</span>
            <el-progress
              text-inside="true"
              :color="statusChoose(Number(this.employeeInfo.avgScore) * 20)"
              style="margin-bottom: 30px"
              :percentage="(Number(this.employeeInfo.avgScore) * 20).toFixed(1)"
              stroke-width="24"
            ></el-progress>
            <span style="float: left;width: 100px">活跃度：</span>
            <el-progress
              text-inside="true"
              :color="statusChoose(this.employeeInfo.activityLevel)"
              style="margin-bottom: 30px"
              :percentage="this.employeeInfo.activityLevel"
              stroke-width="24"
            ></el-progress>
            <span style="float: left;width: 100px">协作能力：</span>
            <el-progress
              text-inside="true"
              :color="statusChoose(Number(this.employeeInfo.suppNum) * 5)"
              style="margin-bottom: 30px"
              :percentage="Number(this.employeeInfo.suppNum) * 5"
              stroke-width="24"
            ></el-progress>
            <span style="float: left;width: 100px">审查能力：</span>
            <el-progress
              text-inside="true"
              :color="statusChoose(this.employeeInfo.reviewAbility)"
              style="margin-bottom: 30px"
              :percentage="this.employeeInfo.reviewAbility"
              stroke-width="24"
            ></el-progress>

            <el-progress
              type="dashboard"
              :color="statusChoose(Number(this.employeeInfo.finishRate) * 100)"
              :percentage="Number(this.employeeInfo.finishRate) * 100"
              stroke-width="24"
              width="250"
            >
              <span class="percentage-label">完成率</span>
              <span class="percentage-num"
                >{{
                  (Number(this.employeeInfo.finishRate) * 100).toFixed(1)
                }}%</span
              >
            </el-progress>
            <el-progress
              style="margin-top: 20px;margin-left: 100px"
              :color="
                statusChoose2(Number(this.employeeInfo.reportSimilarity) * 100)
              "
              type="dashboard"
              :percentage="Number(this.employeeInfo.reportSimilarity) * 100"
              stroke-width="24"
              width="250"
            >
              <span class="percentage-label">报告重复度</span>
              <span class="percentage-num"
                >{{
                  (Number(this.employeeInfo.reportSimilarity) * 100).toFixed(1)
                }}%</span
              >
            </el-progress>
          </div>
          <el-divider />
        </div>

        <div v-show="isadmin">
          <div>
            <span style="float:left; font-size: 20px">当前推荐策略：</span>
            <span style="float:left; font-size: 20px">{{
              Current_recommendation_strategy
            }}</span>
          </div>
          <el-divider />
        </div>

        <el-button
          type="primary"
          @click="dialogFormVisible = true"
          v-show="isempolyee"
          >个人设置</el-button
        >
        <el-button
          type="primary"
          @click="strategyFormVisible = true"
          v-show="isadmin"
          >设置推荐策略</el-button
        >
        <el-button type="primary" @click="exit">退出登录</el-button>
      </el-card>
    </div>

    <el-dialog v-model="dialogFormVisible" title="个人设置">
      <el-form id="personified" ref="formRef">
        <el-form-item label="专业能力">
          <el-checkbox-group v-model="Professional_Ability1">
            <el-checkbox label="团队测试" name="type" />
            <el-checkbox label="业务知识" name="type" />
            <el-checkbox label="项目管理" name="type" />
            <el-checkbox label="测试能力" name="type" />
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="任务偏好">
          <el-radio-group v-model="Task_Preference1">
            <el-radio label="功能测试" />
            <el-radio label="性能测试" />
            <el-radio label="其他" />
          </el-radio-group>
        </el-form-item>

        <el-form-item label="测试设备">
          <el-checkbox-group v-model="Test_Device1">
            <el-checkbox label="Android设备" name="type" />
            <el-checkbox label="Linux设备" name="type" />
            <el-checkbox label="鸿蒙设备" name="type" />
            <el-checkbox label="Windows设备" name="type" />
            <el-checkbox label="Mac设备" name="type" />
          </el-checkbox-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="formReset">取消</el-button>
          <el-button type="primary" @click="edit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="strategyFormVisible" title="设置任务推荐策略">
      <el-form>
        <el-radio-group v-model="Current_recommendation_strategy">
          <el-radio label="测试偏好" />
          <el-radio label="测试设备" />
          <el-radio label="测试内容" />
          <el-radio label="历史测试" />
        </el-radio-group>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="strategyformReset">取消</el-button>
          <el-button type="primary" @click="editstrategy">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="dialogSignature" title="设置个人签名">
      <el-input
        type="text"
        placeholder="请输入个人签名"
        v-model="signature"
        maxlength="20"
        show-word-limit
      >
      </el-input>
      <br /><br />
      <span style="float:right ">
        <el-button el-button type="primary" @click="updateSignature"
          >确认</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import { TARGET } from "@/api/_prefix";
import { ElMessage } from "element-plus";

export default {
  name: "UserCenter",
  data() {
    return {
      userId: window.localStorage.getItem("userId"),
      employeeInfo: {
        totalScore: 0,
        activityLevel: 0,
        avgScore: 0,
        finishRate: 0,
        suppNum: 0,
        reviewAbility: 0,
        reportSimilarity: 0
      },
      employeeId: parseInt(window.localStorage.getItem("userId")),
      dataForm: {
        role: window.localStorage.getItem("role"),
        username: window.localStorage.getItem("username"),
        signature: ""
      },

      Professional_Ability: [],
      Task_Preference: "",
      Test_Device: [],
      Active: 0,

      Professional_Ability1: [],
      Task_Preference1: "",
      Test_Device1: [],

      dialogFormVisible: false,
      signature: "",

      strategyFormVisible: false,
      dialogSignature: false,
      isempolyee: false,
      isadmin: false,
      isemployer: false,
      Current_recommendation_strategy: "策略一"
    };
  },
  created() {
    if (window.localStorage.getItem("role") === "employee") {
      this.isempolyee = true;
    }

    if (window.localStorage.getItem("role") === "admin") {
      this.isadmin = true;
    }
    if (window.localStorage.getItem("role") === "employer") {
      this.isemployer = true;
    }
    if (this.isadmin === true) {
      axios
        .post(`${TARGET}/admin/getInfoByAdminId?adminId=${this.employeeId}`)
        .then(res => {
          console.log(res);

          this.Current_recommendation_strategy = res.data.rankRule;
          if (res.data.rankRule === "default")
            this.Current_recommendation_strategy = "默认";
        });
      /*
      axios.post(`http://localhost:8081/admin/getInfoByAdminId?adminId=${this.employeeId}`,).then(res=>{
        console.log(res);

        //this.Current_recommendation_strategy=res.data.rankRule;

      })
      */
    } else if (this.isempolyee === true) {
      axios
        .post(
          `${TARGET}/employee/getInfoByEmployeeId?employeeId=${this.employeeId}`
        )
        .then(res => {
          console.log(res);
          this.employeeInfo = res.data;
          this.Professional_Ability = res.data.professionalAbility;
          this.Task_Preference = res.data.taskPreference;
          this.Test_Device = res.data.testDevice;
        });

      this.reFreshInfo();
    }
  },
  methods: {
    changeRole(role) {
      if (role === "admin") return "管理员";
      else if (role === "employee") return "众包工人";
      else if (role === "employer") return "发包方";
    },
    statusChoose(num) {
      if (num >= 90) return "#67C23A";
      else if (num >= 80 && num < 90) return "#409EFF";
      else if (num >= 60 && num < 80) return "#E6A23C";
      else return "#F56C6C";
    },
    statusChoose2(num) {
      if (num >= 90) return "#F56C6C";
      else if (num >= 80 && num < 90) return "#E6A23C";
      else if (num >= 60 && num < 80) return "#409EFF";
      else return "#67C23A";
    },
    edit() {
      this.dialogFormVisible = false;
      console.log(this.Professional_Ability);

      axios
        .post(`${TARGET}/employee/setEmployeeInfo`, {
          employeeId: this.employeeId,
          professionalAbility: this.Professional_Ability1,
          taskPreference: this.Task_Preference1,
          testDevice: this.Test_Device1
        })
        .then(res => {
          console.log(res);
          ElMessage.success("修改成功");
        });

      this.Professional_Ability = this.Professional_Ability1;
      this.Task_Preference = this.Task_Preference1;
      this.Test_Device = this.Test_Device1;
    },

    exit() {
      this.$router.push("/");
    },
    formReset() {
      this.dialogFormVisible = false;
      this.Professional_Ability1 = [];
      this.Task_Preference1 = "";
      this.Test_Device1 = [];
    },
    strategyformReset() {
      this.strategyFormVisible = false;
    },
    editstrategy() {
      this.strategyFormVisible = false;

      axios
        .post(
          `${TARGET}/admin/setRecommendInfo?rankRule=${this.Current_recommendation_strategy}`
        )
        .then(res => {
          console.log(res);
          ElMessage.success("修改成功");
        });
    },
    updateSignature() {
      axios
        .post(
          `${TARGET}/user/updateUserSignature?userId=${this.userId}&userSignature=${this.signature}`
        )
        .then(res => {
          console.log(res);
          this.reFreshInfo();
          ElMessage.success("修改成功");
        });
      this.dialogSignature = false;
      this.signature = "";
    },
    reFreshInfo() {
      axios
        .post(`${TARGET}/user/getInfoByUserId?userId=${this.userId}`)
        .then(res => {
          console.log(res);
          this.dataForm.signature = res.data.userSignature;
        });
    }
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

.box-card {
  /*width: 70%;*/
  /*justify-content: center;*/
  position: relative;
  border: 1px solid #dcdfe6;
  width: 700px;
  margin: 50px auto;
  padding: 0px 35px 35px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.name-role {
  padding: 5px;
  text-align: center;
  margin-top: 50px;
  font-size: 40px;
}
.sender {
  text-align: center;
}
.register-info {
  text-align: center;
  padding-top: 10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0px 5px 15px;
  margin-right: 1px;
  width: 100%;
}

.relation-item {
  padding: 12px;
}
.dialog-footer {
  padding-top: 10px;
  padding-left: 10%;
}

.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
/*.bg-purple {*/
/*  background: #d3dce6;*/
/*}*/
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
  left: 50%;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.percentage-label {
  display: block;
  margin-top: 10px;
  font-size: 18px;
}
</style>
