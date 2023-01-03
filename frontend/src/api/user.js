import axios from "axios";
import { TARGET } from "@/api/_prefix";

/**
 *  用户登录 POST /user/login
 * @param {*} payload
 * @returns
 */
export const login = payload => {
  console.log(payload);
  const { username, password } = payload;
  return axios
    .post(`${TARGET}/user/login`, { username, password })
    .then(res => {
      return res.data;
    });
};

/**
 * 用户注册 POST /user/register
 * @param {*} payload
 * @returns
 */
export const register = payload => {
  console.log(payload);
  const { username, password, userRole } = payload;
  return axios
    .post(`${TARGET}/user/register`, {
      username,
      password,
      userRole
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 用户注册 POST /user/register
 * @param {*} payload
 * @returns
 */
export const release = payload => {
  console.log(payload);
  const {
    projectId,
    employerId,
    taskName,
    needWorkers,
    introduction,
    startTime,
    endTime,
    taskType,
    testDevice,
    filePath
  } = payload;
  return axios
    .post(`${TARGET}/task/newTask`, {
      projectId,
      employerId,
      taskName,
      needWorkers,
      introduction,
      startTime,
      endTime,
      taskType,
      testDevice,
      filePath
    })
    .then(res => {
      return res.data;
    });
};

export const fillReport = payload => {
  console.log(payload);
  const {
    rId,
    reportName,
    defectDescription,
    recurrenceSteps,
    deviceType
  } = payload;
  return axios
    .post(`${TARGET}/report/completeReport`, {
      rId,
      reportName,
      defectDescription,
      recurrenceSteps,
      deviceType
    })
    .then(res => {
      return res.data;
    });
};

/**
 * 封禁用戶 POST /user/forbidden/{userId}
 * @returns
 * @param userId
 */
export const forbidden = userId => {
  console.log(userId);
  return axios
    .post(`${TARGET}/user/forbidden?userId=${userId}`)
    .then(res => {
      return res.data;
    });
};
/**
 * 封禁用戶 POST /user/findAll
 * @returns UserList 所有用户消息
 **/
export const findAll = () => {
  return axios.get(`${TARGET}/user/findAll`).then(res => {
    return res.data;
  });
};

/**
 * 更新用户签名 POST /user/updateUserSignature
 * @param {*} payload
 * @returns
 **/
/*
export const updateUserSignature = (userId, signature) => {

  return axios
    .post(`${TARGET}/user/updateUserSignature?userId=${userId}&userSignature=${signature}`)
    .then(res => {
      return res.data;
    });
};
*/

/**
 * 判断是否有即将结束未完成的项目 POST /user/hasSoonFinishTaskOrNot
 * @returns
 * @param userId
 **/
export const hasSoonFinishTaskOrNot = userId => {
    console.log(typeof userId)
  return axios
    .post(`${TARGET}/user/hasSoonFinishTaskOrNot?userId=${userId}`)
    .then(res => {
      return res.data;
    });
};

/**
 * 接受任务 POST /user/signUp
 * @returns
 * @param payload
 **/
export const signUp = payload => {
  const { taskId, employeeId } = payload;
  return axios
    .post(`${TARGET}/user/signUp?taskId=${taskId}&employeeId=${employeeId}`)
    .then(res => {
      return res;
    });
};
