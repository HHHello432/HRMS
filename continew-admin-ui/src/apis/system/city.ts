import http from '@/utils/http'

const BASE_URL = '/system/city'

export interface CityResp {
  id: string
  cityName: string
  socUpperLimit: string
  socLowerLimit: string
  houUpperLimit: string
  houLowerLimit: string
  perPensionRate: string
  comPensionRate: string
  perMedicalRate: string
  comMedicalRate: string
  perUnemploymentRate: string
  comUnemploymentRate: string
  comMaternityRate: string
  remark: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface CityDetailResp {
  id: string
  cityName: string
  socUpperLimit: string
  socLowerLimit: string
  houUpperLimit: string
  houLowerLimit: string
  perPensionRate: string
  comPensionRate: string
  perMedicalRate: string
  comMedicalRate: string
  perUnemploymentRate: string
  comUnemploymentRate: string
  comMaternityRate: string
  remark: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface CityQuery {
  id: string
  cityName: string
  socUpperLimit: string
  socLowerLimit: string
  houUpperLimit: string
  houLowerLimit: string
  perPensionRate: string
  comPensionRate: string
  perMedicalRate: string
  comMedicalRate: string
  perUnemploymentRate: string
  comUnemploymentRate: string
  comMaternityRate: string
  remark: string
  sort: Array<string>
}
export interface CityPageQuery extends CityQuery, PageQuery {}

/** @desc 查询参保城市列表 */
export function listCity(query: CityPageQuery) {
  return http.get<PageRes<CityResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询参保城市详情 */
export function getCity(id: string) {
  return http.get<CityDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增参保城市 */
export function addCity(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改参保城市 */
export function updateCity(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除参保城市 */
export function deleteCity(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出参保城市 */
export function exportCity(query: CityQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
