import http from '@/utils/http'

const BASE_URL = '/system/insurance'
// 定义员工五险一金响应对象
export interface InsuranceResp {
  id: string
  userId: string
  cityId: string
  houseBase: string
  perHouseRate: string
  perHousePay: string
  comHouseRate: string
  comHousePay: string
  houseRemark: string
  socialBase: string
  comSocialPay: string
  perSocialPay: string
  comInjuryRate: string
  socialRemark: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
// 定义员工五险一金详情响应对象
export interface InsuranceDetailResp {
  id: string
  userId: string
  cityId: string
  houseBase: string
  perHouseRate: string
  perHousePay: string
  comHouseRate: string
  comHousePay: string
  houseRemark: string
  socialBase: string
  comSocialPay: string
  perSocialPay: string
  comInjuryRate: string
  socialRemark: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface InsuranceQuery {
  id: string
  userId: string
  cityId: string
  houseBase: string
  houseRemark: string
  socialBase: string
  comInjuryRate: string
  socialRemark: string
  sort: Array<string>
}
// 定义员工五险一金分页查询对象
export interface InsurancePageQuery extends InsuranceQuery, PageQuery {}

/** @desc 查询员工五险一金列表 */
export function listInsurance(query: InsurancePageQuery) {
  return http.get<PageRes<InsuranceResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询员工五险一金详情 */
export function getInsurance(id: string) {
  return http.get<InsuranceDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增员工五险一金 */
export function addInsurance(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改员工五险一金 */
export function updateInsurance(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除员工五险一金 */
export function deleteInsurance(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出员工五险一金 */
export function exportInsurance(query: InsuranceQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
