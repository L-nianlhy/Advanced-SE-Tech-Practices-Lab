import { createRouter, createWebHistory } from "vue-router";
import Register from "@/views/Register";
import Employer from "../views/employer/Employer";
import TaskRelease from "../views/employer/TaskRelease";
import Task from "../views/employer/Task";
import DynamicSquare from "../components/DynamicSquare";
import Login from "../views/Login";
import UserCenter from "../components/UserCenter";
import TaskReleased from "../views/employer/TaskReleased";

import Employee from "../views/employee/Employee";
import TaskReceived from "../views/employee/TaskReceived";
import FillReport from "../views/employee/FillReport";
import TaskFinished from "../views/employee/TaskFinished";

import Admin from "../views/admin/Admin";
import Report from "../views/employee/Report";``
import EmployerReport from "../views/employer/EmployerReport";
import SimilarReport from "../components/SimilarReport";
import EmployeeInfo from "../components/EmployeeInfo";
import Rank from "../components/Rank";
import Statistics from "@/components/Statistics";
import UserManage from "@/views/admin/UserManage";
import ProjectRelease from "@/views/employer/ProjectRelease";
import ProjectReleased from "@/views/employer/ProjectReleased";
const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/login"
  },
  {
    path: "/login",
    name: "login",
    component: Login
  },
  {
    path: "/register",
    name: "register",
    component: Register
  },
  {
    path: "/admin",
    name: "admin",
    component: Admin,
    children: [
      {
        path: "adminSquare",
        name: "adminSquare",
        component: DynamicSquare
      },
      {
        path: "adminStatistics",
        name: "adminStatistics",
        component: Statistics
      },
      {
        path: "adminCenter",
        name: "adminCenter",
        component: UserCenter
      },
      {
        path: "adminRank",
        name: "adminRank",
        component: Rank
      },
      {
        path: "adminUserManage",
        name: "adminUserManage",
        component: UserManage
      }
    ]
  },
  {
    path: "/employer",
    name: "employer",
    component: Employer,
    children: [
      {
        path: "projectRelease",
        name: "projectRelease",
        component: ProjectRelease
      },
      {
        path: "taskRelease",
        name: "taskRelease",
        component: TaskRelease
      },
      {
        path: "employerStatistics",
        name: "employerStatistics",
        component: Statistics
      },
      {
        path: "employerSquare",
        name: "employerSquare",
        component: DynamicSquare
      },
      {
        path: "employerCenter",
        name: "employerCenter",
        component: UserCenter
      },
      {
        path: "taskReleased",
        name: "taskReleased",
        component: TaskReleased
      },
      {
        path: "employerReport",
        name: "employerReport",
        component: EmployerReport
      },
      {
        path: "task",
        name: "task",
        component: Task
      },
      {
        path: "employerSR",
        name: "employerSR",
        component: SimilarReport
      },
      {
        path: "employeeInfo",
        name: "employeeInfo",
        component: EmployeeInfo
      },
      {
        path: "employerRank",
        name: "employerRank",
        component: Rank
      },
      {
        path: "projectReleased",
        name: "projectReleased",
        component: ProjectReleased
      }
    ]
  },
  // {
  //   path:'/employer/taskRelease',
  //   name:'taskRelease',
  //   component: TaskRelease
  // },
  {
    path: "/employee",
    name: "employee",
    component: Employee,
    children: [
      {
        path: "employeeSquare",
        name: "employeeSquare",
        component: DynamicSquare
      },
      {
        path: "taskReceived",
        name: "taskReceived",
        component: TaskReceived
      },
      {
        path: "employeeStatistics",
        name: "employeeStatistics",
        component: Statistics
      },
      {
        path: "employeeCenter",
        name: "employeeCenter",
        component: UserCenter
      },
      {
        path: "fillReport",
        name: "fillReport",
        component: FillReport
      },
      {
        path: "taskFinished",
        name: "taskFinished",
        component: TaskFinished
      },
      {
        path: "report",
        name: "report",
        component: Report
      },
      {
        path: "employeeSR",
        name: "employeeSR",
        component: SimilarReport
      },
      {
        path: "employeeRank",
        name: "employeeRank",
        component: Rank
      }
    ]
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
