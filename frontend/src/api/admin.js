import axios from "axios";
import { ADMIN_MODULE } from "@/api/_prefix";
/**
 *  获取众测工人任务偏好分布
 * @param
 * @returns
 * 按照功能测试，性能测试，其他排列
 **/
export const getAllEmployeeTaskPreference = () => {
  return axios.get(`${ADMIN_MODULE}/getAllEmployeeTaskPreference`).then(res => {
    return res.data;
  });
};
/**
 *  获取众测工人活跃度分布
 * @param
 * @returns
 * 按照高中低排列
 **/
export const getAllUserActivityLevel = () => {
  return axios.get(`${ADMIN_MODULE}/getAllUserActivityLevel`).then(res => {
    return res.data;
  });
};
/**
 *  获取众测任务测试设备分布
 * @param
 * @returns
 * 按照Android，linux，鸿蒙，mac，windows排列
 **/
export const getAllTaskDevice = () => {
  return axios.get(`${ADMIN_MODULE}/getAllTaskDevice`).then(res => {
    return res.data;
  });
};
/**
 *  获取众测任务测试类型分布
 * @param
 * @returns
 * 按照功能测试，性能测试，其他排列
 **/
export const getAllTaskType = () => {
  return axios.get(`${ADMIN_MODULE}/getAllTaskType`).then(res => {
    return res.data;
  });
};
