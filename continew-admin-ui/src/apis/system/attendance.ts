import http from '@/utils/http'

const BASE_URL = '/system/attendance'

export interface AttendanceResp {
  id: number;
  createUser: string;
  createTime: string;
  updateUser: string | null;
  updateTime: string | null;
  userId: number; // 修改为 string
  userName: string;
  nickname: string;
  phone: string;
  date: string;
  status: number;
}
export interface AttendanceDetailResp {
  id: string
  userId: string
  userName: string
  date: string
  status: string
  createUser: string
  createTime: string
  updateUser: string
  updateTime: string
  createUserString: string
  updateUserString: string
  phone: string
  nickname: string
}
export interface AttendanceQuery {
  userId: string
  userName: string
  date: string
  status: string
  sort: Array<string>
}
export interface AttendancePageQuery extends AttendanceQuery, PageQuery {}

/** @desc 查询员工考勤列表 */
export function listAttendance(query: AttendancePageQuery) {
  return http.get<PageRes<AttendanceResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询员工考勤详情 */
export function getAttendance(id: string) {
  return http.get<AttendanceDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增员工考勤 */
export function addAttendance(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改员工考勤 */
export function updateAttendance(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除员工考勤 */
export function deleteAttendance(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出员工考勤 */
export function exportAttendance(query: AttendanceQuery) {
  return http.download<any>(`${BASE_URL}/export`, query)
}
