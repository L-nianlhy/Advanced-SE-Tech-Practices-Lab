import axios from "axios";
import { REPORT_MODULE } from "@/api/_prefix";

/**
 *  根据任务和工人获取报告
 * @returns
 * 若无报告，则result.code = 0 ,result.msg = "未提交报告"
 **/
export const findReportByTaskIdAndEmployeeId = payload => {
  return axios
    .get(`${REPORT_MODULE}/findReportsByTaskIdAndEmployeeId`, {
      params: payload
    })
    .then(res => {
      return res.data;
    });
};

/**
 *  获得报告质量评分
 * @returns
 *@param reportId
 **/
export const getImageAndTextSimilarity = reportId => {
  return axios
    .get(
      `${REPORT_MODULE}/findReportsByTaskIdAndEmployeeId?reportId=${reportId}`
    )
    .then(res => {
      let i = res.data;
      i = i.toFixed(2);
      return i;
    });
};
