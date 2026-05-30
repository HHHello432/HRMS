import http from '@/utils/http'

const BASE_URL = '/system/recruitment'

export interface RecruitmentResp {
  id: string
  title: string
  department: string
  positionType: string
  headcount: string
  description: string
  requirement: string
  salaryMin: string
  salaryMax: string
  address: string
  status: string
  deadline: string
  sort: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface RecruitmentDetailResp {
  id: string
  title: string
  department: string
  positionType: string
  headcount: string
  description: string
  requirement: string
  salaryMin: string
  salaryMax: string
  address: string
  status: string
  deadline: string
  sort: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface RecruitmentQuery {
  title: string
  department: string
  positionType: string
  status: string
  sort: Array<string>
}
export interface RecruitmentPageQuery extends RecruitmentQuery, PageQuery {}

/** @desc 查询招聘计划列表 */
export function listRecruitment(query: RecruitmentPageQuery) {
  return http.get<PageRes<RecruitmentResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询招聘计划详情 */
export function getRecruitment(id: string) {
  return http.get<RecruitmentDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增招聘计划 */
export function addRecruitment(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改招聘计划 */
export function updateRecruitment(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除招聘计划 */
export function deleteRecruitment(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出招聘计划 */
export function exportRecruitment(query: RecruitmentQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
