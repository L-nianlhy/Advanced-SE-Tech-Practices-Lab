import axios from "axios";
import { TASK_MODULE } from "@/api/_prefix";

/**
 *  获取任务参与人员名单
 * @returns
 * @param taskId
 **/
export const getUser = taskId => {
  console.log(taskId);
  return axios
    .get(`${TASK_MODULE}/getUser/${taskId}`, {
      params: { taskId: "${taskId}" }
    })
    .then(res => {
      return res.data;
    });
};
