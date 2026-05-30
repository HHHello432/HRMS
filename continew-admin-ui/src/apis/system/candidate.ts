import http from '@/utils/http'

const BASE_URL = '/system/candidate'

export interface CandidateResp {
  id: string
  recruitmentId: string
  name: string
  gender: string
  phone: string
  email: string
  education: string
  school: string
  major: string
  experience: string
  resumeUrl: string
  status: string
  createTime: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface CandidateDetailResp {
  id: string
  recruitmentId: string
  name: string
  gender: string
  phone: string
  email: string
  education: string
  school: string
  major: string
  experience: string
  resumeUrl: string
  status: string
  createTime: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface CandidateQuery {
  recruitmentId: string
  name: string
  phone: string
  email: string
  education: string
  status: string
  sort: Array<string>
}
export interface CandidatePageQuery extends CandidateQuery, PageQuery {}

/** @desc 查询应聘者列表 */
export function listCandidate(query: CandidatePageQuery) {
  return http.get<PageRes<CandidateResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询应聘者详情 */
export function getCandidate(id: string) {
  return http.get<CandidateDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增应聘者 */
export function addCandidate(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改应聘者 */
export function updateCandidate(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除应聘者 */
export function deleteCandidate(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出应聘者 */
export function exportCandidate(query: CandidateQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
