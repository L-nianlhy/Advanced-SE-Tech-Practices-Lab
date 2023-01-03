<template>
  <div style="width: 1400px;margin: 0 auto">
    <div v-show="this.role === 'employee'">
      <div style="height: 120px;width: 1100px;margin: 0 auto">
        <img
          src="../assets/recommend-tasks.png"
          style="height: 120px;width: 300px;float: left"
        />
      </div>
      <div style="margin-left:1%;margin-right:1%">
        <el-row>
          <el-col
            :span="4"
            v-for="(item, i) in tasks"
            :key="item.id"
            :offset="1"
          >
            <el-card class="card" shadow="always">
              <div style="{font-size: 40px; margin: 10px 10px 25px 5px; }">
                <el-icon class="icon-task" v-if="item.taskType === '功能测试'">
                  <Setting />
                </el-icon>
                <el-icon class="icon-task" v-if="item.taskType === '性能测试'">
                  <Bicycle />
                </el-icon>
                <el-icon class="icon-task" v-if="item.taskType === '其他'">
                  <PieChart />
                </el-icon>
                <strong>{{ item.taskName }}</strong>
              </div>
              <div
                class="bottom clearfix"
                style="margin: 10px 10px 40px 5px; width: 200px; height: 100px"
              >
                <p class="task-info" align="left">
                  任务类型：
                  <span class="span-1" style="color: #909399">{{
                    item.taskType
                  }}</span>
                </p>
                <p class="task-info" align="left">
                  任务简介：
                  <span class="span-1" style="color: #909399">{{
                    item.introduction
                  }}</span>
                </p>
                <p class="task-info" align="left">
                  截止时间：
                  <span class="span-1" style="color: #909399">{{
                    item.endTime
                  }}</span>
                </p>
                <!--<span class="time" style="text-align:left">简介：{{item.introduction}}</span><br><br><br>
                  <span class="time">类型：{{item.taskType}}</span><br><br><br>-->
              </div>
              <el-divider
                style="margin-top: 0;margin-bottom: 0;position: absolute;bottom: 60px;width: 210px"
              ></el-divider>
              <el-button
                type="primary"
                class="button"
                @click="handleDetails(i, item)"
                style="position: absolute; bottom: 5%; left:33%;"
                >查看详情</el-button
              >
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>

    <div style="height: 120px;width: 1100px;margin: 0 auto">
      <img
        src="../assets/allProjects.png"
        style="height: 120px;width: 300px;float: left"
      />
    </div>
    <div style="margin-left:1%;margin-right:1%">
      <el-row>
        <el-col
          :span="4"
          v-for="(item, i) in projects"
          :key="item.id"
          :offset="1"
        >
          <el-card class="project-card" shadow="always">
            <div style="{font-size: 40px; margin: 10px 10px 25px 5px; }">
              <el-icon class="icon-task">
                <Setting />
              </el-icon>
              <strong>{{ item.projectName }}</strong>
            </div>
            <div
              class="bottom clearfix"
              style="margin: 10px 10px 40px 5px; width: 200px; height: 100px"
            >
              <p class="task-info" align="left">
                项目简介：
                <span class="span-1" style="color: #909399">{{
                  item.introduction
                }}</span>
              </p>
              <!--<span class="time" style="text-align:left">简介：{{item.introduction}}</span><br><br><br>
                <span class="time">类型：{{item.taskType}}</span><br><br><br>-->
            </div>
            <el-divider
              style="margin-top: 0;margin-bottom: 0;position: absolute;bottom: 60px;width: 210px"
            ></el-divider>
            <el-button
              type="primary"
              class="button"
              @click="handleDetailsProject(item.id)"
              style="position: absolute; bottom: 5%; left:33%;"
              >查看详情</el-button
            >
          </el-card>
        </el-col>
      </el-row>
    </div>

    <el-dialog v-model="dialogVisible" title="任务详情" width="60%">
      <!--    <span>This is a message</span>-->
      <el-descriptions class="margin-top" title="" :column="3" border>
        <template #extra>
          <el-button type="primary" v-show="buttonVisible" @click="signUp"
            >接收任务</el-button
          >
        </template>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <user />
              </el-icon>
              任务名称
            </div>
          </template>
          {{ allTasks[flag].taskName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <iphone />
              </el-icon>
              任务类型
            </div>
          </template>
          {{ allTasks[flag].taskType }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              人数需求
            </div>
          </template>
          {{ allTasks[flag].needWorkers }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              报名人数
            </div>
          </template>
          {{ allTasks[flag].signedNum }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              测试设备
            </div>
          </template>
          {{ allTasks[flag].testDevice }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              开始时间
            </div>
          </template>
          <!--      <el-tag size="small">School</el-tag>-->
          {{ allTasks[flag].startTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              结束时间
            </div>
          </template>
          <!--      <el-tag size="small">School</el-tag>-->
          {{ allTasks[flag].endTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <office-building />
              </el-icon>
              任务简介
            </div>
          </template>
          {{ allDescriptions[flag] }}
        </el-descriptions-item>
      </el-descriptions>
      <a :href="getFileLink(allTasks[flag].id)">下载文件</a>
    </el-dialog>

    <el-dialog v-model="dialogVisibleNotOrder" title="任务详情" width="60%">
      <!--    <span>This is a message</span>-->
      <el-descriptions class="margin-top" title="" :column="3" border>
        <template #extra>
          <el-button
            type="primary"
            v-show="buttonVisible"
            @click="signUpNotOrder"
            >接收任务</el-button
          >
        </template>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <user />
              </el-icon>
              任务名称
            </div>
          </template>
          {{ notOrderTasks[flag].taskName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <iphone />
              </el-icon>
              任务类型
            </div>
          </template>
          {{ notOrderTasks[flag].taskType }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              人数需求
            </div>
          </template>
          {{ notOrderTasks[flag].needWorkers }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              报名人数
            </div>
          </template>
          {{ notOrderTasks[flag].signedNum }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              测试设备
            </div>
          </template>
          {{ notOrderTasks[flag].testDevice }}
        </el-descriptions-item>

        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              开始时间
            </div>
          </template>
          <!--      <el-tag size="small">School</el-tag>-->
          {{ notOrderTasks[flag].startTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              结束时间
            </div>
          </template>
          <!--      <el-tag size="small">School</el-tag>-->
          {{ notOrderTasks[flag].endTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <office-building />
              </el-icon>
              任务简介
            </div>
          </template>
          {{ allDescriptionsNotOrder[flag] }}
        </el-descriptions-item>
      </el-descriptions>
      <a :href="getFileLink(notOrderTasks[flag].id)">下载文件</a>
    </el-dialog>
  </div>
</template>

<script>
import {
  User,
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  Edit,
  Setting,
  Bicycle,
  PieChart
} from "@element-plus/icons";
import { ref } from "vue";
import axios from "axios";
import { ElMessage } from "element-plus";
import { TARGET } from "@/api/_prefix";
import { getAllProjects } from "@/api/project";

const size = ref("");
export default {
  name: "DynamicSquare",
  components: {
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
  data() {
    return {
      colorList: ["#26A69A", "#00B0FF", "#5C6BC0", "#FFB300", "#E57373"],
      taskId: 0,
      flag: 0,
      dialogVisible: false,
      dialogVisibleNotOrder: false,
      buttonVisible: false,
      tasks: [],
      allTasks: [],
      userId: window.localStorage.getItem("userId"),
      role: window.localStorage.getItem("role"),
      allDescriptions: [],
      allDescriptionsNotOrder: [],
      adminId: 1,
      notOrderTasks: [],
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
  created: function() {
    getAllProjects(this.userId).then(res => {
      this.projects = res;
    });
    axios.post(`${TARGET}/task/getAllTasks?userId=${this.userId}`).then(res => {
      console.log(res.data);
      this.allTasks = res.data;
      for (var i = 0; i < this.allTasks.length; i++) {
        this.allDescriptions.push(this.allTasks[i].introduction);
        if (this.allTasks[i].introduction.length >= 20) {
          this.allTasks[i].introduction =
            this.allTasks[i].introduction.substring(0, 20) + "...";
        }
        if (i < 8) {
          this.tasks.push(this.allTasks[i]);
        }
      }
      if (window.localStorage.getItem("role") === "employee") {
        this.buttonVisible = true;
      }
    });
    axios
      .post(`${TARGET}/task/getAllTasks?userId=${this.adminId}`)
      .then(res => {
        console.log(res.data);
        this.notOrderTasks = res.data;
        for (var i = 0; i < this.notOrderTasks.length; i++) {
          this.allDescriptionsNotOrder.push(this.notOrderTasks[i].introduction);
          if (this.notOrderTasks[i].introduction.length >= 20) {
            this.notOrderTasks[i].introduction =
              this.notOrderTasks[i].introduction.substring(0, 20) + "...";
          }
        }
      });
  },
  methods: {
    getFileLink(val) {
      console.log(this.taskId);
      return `${TARGET}/download?taskId=` + val;
    },
    handleDetails(i, item) {
      this.flag = i;
      this.taskId = item.id;
      this.dialogVisible = true;
      window.localStorage.setItem("taskId", item.id);
      console.log(this.flag);
    },
    handleDetailsProject(id) {
      this.$router.push({
        path: "/employer/taskRelease",
        query: { projectId: id }
      });
    },
    signUp() {
      let employeeId = parseInt(window.localStorage.getItem("userId"));
      axios
        .post(
          `${TARGET}/user/signUp?taskId=${
            this.allTasks[this.flag].id
          }&employeeId=${employeeId}`
        )
        .then(res => {
          console.log(res);
          if (res.data.code === 1) {
            ElMessage({ message: "接收任务成功", type: "success" });
          } else {
            ElMessage.error(res.data.msg);
          }
        });
    },
    signUpNotOrder() {
      let employeeId = parseInt(window.localStorage.getItem("userId"));
      axios
        .post(
          `${TARGET}/user/signUp?taskId=${
            this.notOrderTasks[this.flag].id
          }&employeeId=${employeeId}`
        )
        .then(res => {
          console.log(res);
          if (res.data.code === 1) {
            ElMessage({ message: "接收任务成功", type: "success" });
          } else {
            ElMessage.error(res.data.msg);
          }
        });
    }
  }
};
</script>

<style scoped>
.card {
  width: 250px;
  height: 300px;
  margin: 25px 50px 25px 80px;
  position: relative;
}
.project-card {
  width: 250px;
  height: 200px;
  margin: 25px 50px 25px 80px;
  position: relative;
}

.task-info {
  margin-top: 8px;
  margin-bottom: 8px;
}

.icon-task {
  font-size: 40px;
  position: absolute;
  right: 2%;
  bottom: 2%;
  color: #9fe9ff;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 600px;
  height: 100px;
}
.el-descriptions {
  margin-top: 20px;
}
.cell-item {
  display: flex;
  align-items: center;
}
.margin-top {
  margin-top: 20px;
}
.card {
  background-color: #fafcfd;
}
</style>
