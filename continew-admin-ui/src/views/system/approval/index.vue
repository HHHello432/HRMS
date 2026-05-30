<template>
  <div class="table-page">
    <GiTable title="事务申请管理" row-key="id" :data="dataList" :columns="columns" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
      :disabled-column-keys="['name']" @refresh="search">
      <template #toolbar-left>
        <a-input v-model="queryForm.nickname" placeholder="请输入用户名" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-select v-model="queryForm.typeNum" placeholder="请选择事务类型" allow-clear @change="search">
          <a-option v-for="type in Object.values(APPROVAL_TYPES)" :key="type.value" :value="type.value">
            {{ type.label }}
          </a-option>
        </a-select>
        <a-select v-model="queryForm.status" placeholder="请选择审核状态" allow-clear @change="search">
          <a-option v-for="(label, value) in statusMap" :key="value" :value="value">{{ label }}</a-option>
        </a-select>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:approval:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:approval:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:approval:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:approval:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link v-permission="['system:approval:Authorize']" title="审批" @click="onApprove(record)">审批</a-link>
          <a-link v-permission="['system:approval:delete']" status="danger" :disabled="record.disabled"
            :title="record.disabled ? '不可删除' : '删除'" @click="onDelete(record)">
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>

<ApprovalAddModal ref="ApprovalAddModalRef" @save-success="search" />
<PassApprovalModal ref="PassApprovalModalRef" @save-success="search" />
<ApprovalDetailDrawer ref="ApprovalDetailDrawerRef" />
</div>
</template>

<script setup lang="ts">
import { APPROVAL_TYPES } from '@/apis/system/approval'
import ApprovalAddModal from './ApprovalAddModal.vue'
import ApprovalDetailDrawer from './ApprovalDetailDrawer.vue'
import PassApprovalModal from './PassApprovalModal.vue'
import { type ApprovalResp, type ApprovalQuery, deleteApproval, exportApproval, listApproval } from '@/apis/system/approval'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'
import dayjs from 'dayjs'

defineOptions({ name: 'Approval' })

// 格式化日期时间
const formatDateTime = (dateTime: string | undefined) => {
  if (!dateTime) return ''
  return dayjs(dateTime).format('YYYY-MM-DD')
}

// 定义审核状态映射
const statusMap = {
  0: '未审核',
  1: '审核通过',
  2: '驳回',
  3: '撤销',
  4: '待审批'
}

const queryForm = reactive<ApprovalQuery>({
  userId: undefined,
  nickname: undefined,
  typeNum: undefined,
  remark1: undefined,
  createTime: undefined,
  status: undefined,
  approvalnickname: undefined,
  approvalTime: undefined,
  remark2: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listApproval({ ...queryForm, ...page }), { immediate: true })

const columns: TableInstanceColumns[] = [
  { title: '用户名', dataIndex: 'nickname' },
  {
    title: '事务类型',
    dataIndex: 'typeNum',
    customRender: ({ text }) => {
      const type = APPROVAL_TYPES[text] || { label: '未知类型', value: text }
      return type.label
    }
  },
  { title: '事务内容', dataIndex: 'remark1' },
  {
    title: '审核状态',
    dataIndex: 'status',
    customRender: ({ text }) => statusMap[text] || '未知状态'
  },
  { title: '审批用户', dataIndex: 'approvalnickname' },
  {
    title: '申请时间',
    dataIndex: 'applyTime',
    customRender: ({ text }) => formatDateTime(text),
    width: 180
  },
  {
    title: '审批时间',
    dataIndex: 'approvalTime',
    customRender: ({ text }) => formatDateTime(text),
    width: 180
  },
  { title: '审批内容', dataIndex: 'remark2' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 200,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:approval:detail', 'system:approval:update', 'system:approval:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.userId = undefined
  queryForm.nickname = undefined
  queryForm.typeNum = undefined
  queryForm.remark1 = undefined
  queryForm.createTime = undefined
  queryForm.status = undefined
  queryForm.approvalnickname = undefined
  queryForm.approvalTime = undefined
  queryForm.remark2 = undefined
  search()
}

// 删除
const onDelete = (record: ApprovalResp) => {
  return handleDelete(() => deleteApproval(record.id), {
    content: '是否确定删除该条数据？',
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportApproval(queryForm))
}

const ApprovalAddModalRef = ref<InstanceType<typeof ApprovalAddModal>>()
const PassApprovalModalRef = ref<InstanceType<typeof PassApprovalModal>>()
const ApprovalDetailDrawerRef = ref<InstanceType<typeof ApprovalDetailDrawer>>()

// 新增
const onAdd = () => {
  ApprovalAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: ApprovalResp) => {
  ApprovalAddModalRef.value?.onUpdate(record.id)
}

// 详情
const onDetail = (record: ApprovalResp) => {
  ApprovalDetailDrawerRef.value?.onOpen(record.id)
}

// 审批
const onApprove = (record: ApprovalResp) => {
  PassApprovalModalRef.value?.onApprove(record.id)
}
</script>

<style scoped lang="scss"></style>