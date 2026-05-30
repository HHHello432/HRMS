<template>
  <div class="table-page">
    <GiTable title="员工考勤管理" row-key="id" :data="dataList" :columns="columns" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
      :disabled-column-keys="['name']" @refresh="search">
      <template #toolbar-left>
        <a-input v-model="queryForm.userId" placeholder="请输入员工ID" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-input v-model="queryForm.nickname" placeholder="请输入员工姓名" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-date-picker v-model="queryForm.date" placeholder="请选择日期" format="YYYY-MM-DD" style="height: 32px" />
        <a-input v-model="queryForm.status" placeholder="请输入考勤情况" allow-clear @change="search">
          <!-- 1迟到、2早退、3旷工、4请假 -->
          <template #prefix><icon-search /></template>
        </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:attendance:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:attendance:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:attendance:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:attendance:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link v-permission="['system:attendance:delete']" status="danger" :disabled="record.disabled"
            :title="record.disabled ? '不可删除' : '删除'" @click="onDelete(record)">
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>

    <AttendanceAddModal ref="AttendanceAddModalRef" @save-success="search" />
    <AttendanceDetailDrawer ref="AttendanceDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import AttendanceAddModal from './AttendanceAddModal.vue'
import AttendanceDetailDrawer from './AttendanceDetailDrawer.vue'
import { type AttendanceResp, type AttendanceQuery, deleteAttendance, exportAttendance, listAttendance } from '@/apis/system/attendance'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'


defineOptions({ name: 'Attendance' })

interface CustomTableInstanceColumns extends TableInstanceColumns {
  customRender?: (params: { text: any }) => string;
}

const queryForm = reactive<AttendanceQuery>({
  userId: undefined,
  nickname: undefined,
  date: undefined,
  status: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listAttendance({ ...queryForm, ...page }), { immediate: true })

const attendanceStatusMap = {
  1: '迟到',
  2: '早退',
  3: '旷工',
  4: '请假'
}

const columns: CustomTableInstanceColumns[] = [
  { title: '员工ID', dataIndex: 'userId', slotName: 'userId'},
  { title: '员工姓名', dataIndex: 'nickname', slotName: 'nickname' },
  { 
    title: '考勤情况', 
    dataIndex: 'status',
    customRender: ({ text }) => attendanceStatusMap[text] || '未知状态' ,
  },
  { title: '考勤日期', dataIndex: 'date', slotName: 'date' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:attendance:detail', 'system:attendance:update', 'system:attendance:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.userId = undefined
  queryForm.nickname = undefined
  queryForm.date = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: AttendanceResp) => {
  return handleDelete(() => deleteAttendance(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportAttendance(queryForm))
}

const AttendanceAddModalRef = ref<InstanceType<typeof AttendanceAddModal>>()
// 新增
const onAdd = () => {
  AttendanceAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: AttendanceResp) => {
  AttendanceAddModalRef.value?.onUpdate(record.id)
}

const AttendanceDetailDrawerRef = ref<InstanceType<typeof AttendanceDetailDrawer>>()
// 详情
const onDetail = (record: AttendanceResp) => {
  AttendanceDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
