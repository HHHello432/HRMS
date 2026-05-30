import http from '@/utils/http'

const BASE_URL = '/system/Doms'
const BASE_URL2 = '/system/doms'
const BASE_URL3 = '/system/dom'

export interface DomResp {
  staffList: any
  id: string
  staffId: string
  domId: string
  date: string
  status: string
  createUserString: string
  updateUserString: string
}

export interface DomDetailResp {
  id: string
  description: string
  staffList: {
    userId: string
    username: string
    nickname: string
    gender: number
    phone: string
    date: string
    status: number
  }[]
  createUserString: string
  updateUserString: string
}

export interface DomQuery {
  staffId: string
  domId: string
  date: string
  status: string
  sort: Array<string>
}

export interface DomPageQuery extends DomQuery, PageQuery {}

/** @desc 查询宿舍管理列表 */
export function listDom(query: DomPageQuery) {
  return http.get<PageRes<DomResp[]>>(`${BASE_URL3}`, query)
    .catch(error => {
      console.error('Error fetching list of doms:', error)
      throw error
    })
}

/** @desc 查询宿舍员工管理列表 */
export function listDoms(query: DomPageQuery) {
  return http.get<PageRes<DomResp[]>>(`${BASE_URL3}`, query)
    .catch(error => {
      console.error('Error fetching list of doms:', error)
      throw error
    })
}

/** @desc 查询宿舍管理详情 */
export function getDom(id: string) {
  return http.get<DomDetailResp>(`${BASE_URL3}/${id}`)
    .catch(error => {
      console.error('Error fetching dom detail:', error)
      throw error
    })
}

/** @desc 查询宿舍员工管理详情 */
export function getDoms(id: string) {
  return http.get<DomDetailResp>(`${BASE_URL2}/detail/${id}`)
    .catch(error => {
      console.error('Error fetching dom detail:', error)
      throw error
    })
}

/** @desc 新增宿舍管理 */
export function addDom(data: any) {
  return http.post(`${BASE_URL3}`, data)
}

/** @desc 修改宿舍管理 */
export function updateDom(data: any, id: string) {
  return http.put(`${BASE_URL3}/${id}`, data)
}

/** @desc 删除宿舍管理 */
export function deleteDom(id: string) {
  return http.del(`${BASE_URL3}/${id}`)
}

/** @desc 导出宿舍管理 */
export function exportDom(query: DomQuery) {
  return http.download<any>(`${BASE_URL3}/export`, query)
}