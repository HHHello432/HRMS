import http from '@/utils/http'

const BASE_URL = '/system/approval'

export interface ApprovalResp {
  id: string
  userId: string
  createUser: string
  typeNum: string
  remark1: string
  createTime: string
  status: string
  approvalUserId: string
  approvalTime: string
  remark2: string
  createUserString: string
  updateUserString: string
}
export const APPROVAL_TYPES = {
  1: { label: '职务升降', value: '1' },
  2: { label: '岗位调整', value: '2' },
  3: { label: '出差报销', value: '3' },
  4: { label: '其他', value: '4' }
}
export interface ApprovalDetailResp {
  id: string
  userId: string
  createUser: string
  typeNum: string
  remark1: string
  createTime: string
  status: string
  approvalUserId: string
  approvalTime: string
  remark2: string
  isDeleted: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
}
export interface ApprovalQuery {
  userId: string
  createUser: string
  typeNum: string
  remark1: string
  createTime: string
  status: string
  approvalUserId: string
  approvalTime: string
  remark2: string
  sort: Array<string>
}
export interface ApprovalPageQuery extends ApprovalQuery, PageQuery { }

/** @desc 查询事务申请列表 */
export function listApproval(query: ApprovalPageQuery) {
  return http.get<PageRes<ApprovalResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询事务申请详情 */
export function getApproval(id: string) {
  return http.get<ApprovalDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增事务申请 */
export function addApproval(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改事务申请 */
export function updateApproval(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除事务申请 */
export function deleteApproval(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出事务申请 */
export function exportApproval(query: ApprovalQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}

/** @desc 审批事务申请 */
export function PassApproval(data: any, id: string) {
  return http.post(`${BASE_URL}/authorize/${id}`, data),{
    'Content-Type': 'application/x-www-form-urlencoded',
  }
}
