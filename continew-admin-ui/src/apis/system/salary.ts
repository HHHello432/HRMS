import http from '@/utils/http'

const BASE_URL = '/system/salary'
// 定义员工工资响应对象
export interface SalaryResp {
  id: string
  userId: string
  baseSalary: string
  subsidy: string
  performance: string
  overtimeSalary: string
  bonus: string
  totalSalary: string
  lateDeduct: string
  leaveDeduct: string
  leaveEarlyDeduct: string
  absenteeismDeduct: string
  month: string
  remark: string
  createUserString: string
  updateUserString: string
}
// 定义员工工资详情响应对象
export interface SalaryDetailResp {
  id: string
  userId: string
  baseSalary: string
  subsidy: string
  performance: string
  overtimeSalary: string
  bonus: string
  totalSalary: string
  lateDeduct: string
  leaveDeduct: string
  leaveEarlyDeduct: string
  absenteeismDeduct: string
  perHousePay: string
  perSocialPay: string
  month: string
  remark: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
// 定义员工工资查询对象
export interface SalaryQuery {
  userId: string
  baseSalary: string
  subsidy: string
  performance: string
  overtimeSalary: string
  bonus: string
  totalSalary: string
  lateDeduct: string
  leaveDeduct: string
  leaveEarlyDeduct: string
  absenteeismDeduct: string
  month: string
  remark: string
  sort: Array<string>
}
// 定义员工工资分页查询对象
export interface SalaryPageQuery extends SalaryQuery, PageQuery {}

/** @desc 查询员工工资列表 */
export function listSalary(query: SalaryPageQuery) {
  return http.get<PageRes<SalaryResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询员工工资详情 */
export function getSalary(id: string) {
  return http.get<SalaryDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增员工工资 */
export function addSalary(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改员工工资 */
export function updateSalary(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除员工工资 */
export function deleteSalary(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出员工工资 */
export function exportSalary(query: SalaryQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
