import axios from "axios";
import { PROJECT_MODULE } from "@/api/_prefix";

/**
 * 新建项目
 * @returns
 * @param {*}payload
 */
export const newProject = payload => {
  //console.log(payload);
  const { employerId, projectName, introduction } = payload;
  return axios
    .post(`${PROJECT_MODULE}/newProject`, {
      employerId,
      projectName,
      introduction
    })
    .then(res => {
      console.log(res);
      return res;
    });
};

/**
 * 获取所有的项目
 * @returns
 * @param userId
 */
export const getAllProjects = userId => {
  return axios
    .post(`${PROJECT_MODULE}/getAllProjects?userId=${userId}`)
    .then(res => {
      console.log(res);
      return res.data;
    });
};

/**
 * 通过发包方Id获取其发布的项目
 * @param
 * @returns
 */
export const getProjectsByEmployerID = payload => {
  console.log(payload);
  return axios
    .post(`${PROJECT_MODULE}/getProjectsByEmployerID`, {
      params: payload
    })
    .then(res => {
      return res.data;
    });
};
/**
 * 通过projectId获取子任务列表
 * @param {*} payload
 * @returns
 */
export const getTasksByProjectID = payload => {
  return axios
    .post(`${PROJECT_MODULE}/getTasksByProjectID`, {
      id: payload
    })
    .then(res => {
      return res;
    });
};
/**
 * 通过projectId获取project信息
 * @param {*} payload
 * @returns
 */
export const getProjectByID = payload => {
  return axios
    .post(`${PROJECT_MODULE}/getProjectByID`, { id: payload })
    .then(res => {
      return res;
    });
};
