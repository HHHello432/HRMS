// doms.ts
import http from '@/utils/http'

const BASE_URL = '/system/doms'

export interface DomResp {
  id: number
  createUserString: string
  createTime: string
  userId: number
  username: string
  nickname: string
  gender: number
  phone: string
  domId: number
  date: string
  status: number
}

export interface DomDetailResp {
  id: string
  staffId: string
  domId: string
  date: string
  status: string
  createUserString: string
  updateUserString: string
}

export interface DomQuery {
  staffId?: string
  domId?: string
  date?: string
  status?: string
  sort?: Array<string>
}

export interface DomPageQuery extends DomQuery, PageQuery {}

interface PageRes<T> {
  code: string
  msg: string
  success: boolean
  timestamp: number
  data: {
    list: T
    total: number
  }
}


/** @desc 查询宿舍员工列表 */
export function listDoms(query: DomPageQuery) {
  return http.get<PageRes<DomResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询宿舍管理详情 */
export function getDom(id: string) {
  return http.get<DomDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增宿舍管理 */
export function addDom(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改宿舍管理 */
export function updateDom(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除宿舍管理 */
export function deleteDom(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出宿舍管理 */
export function exportDom(query: DomQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}