import http from '@/utils/http'

const BASE_URL = '/system/interview'

export interface InterviewResp {
  id: string
  candidateId: string
  round: string
  interviewer: string
  interviewTime: string
  address: string
  content: string
  evaluation: string
  result: string
  nextRoundTime: string
  createTime: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface InterviewDetailResp {
  id: string
  candidateId: string
  round: string
  interviewer: string
  interviewTime: string
  address: string
  content: string
  evaluation: string
  result: string
  nextRoundTime: string
  createTime: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface InterviewQuery {
  candidateId: string
  interviewTime: string
  result: string
  sort: Array<string>
}
export interface InterviewPageQuery extends InterviewQuery, PageQuery {}

/** @desc 查询面试记录列表 */
export function listInterview(query: InterviewPageQuery) {
  return http.get<PageRes<InterviewResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询面试记录详情 */
export function getInterview(id: string) {
  return http.get<InterviewDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增面试记录 */
export function addInterview(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改面试记录 */
export function updateInterview(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除面试记录 */
export function deleteInterview(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出面试记录 */
export function exportInterview(query: InterviewQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
