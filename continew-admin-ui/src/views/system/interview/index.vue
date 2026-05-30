<template>
  <div class="table-page">
    <GiTable
      title="面试记录管理"
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      :disabled-column-keys="['name']"
      @refresh="search"
    >
      <template #toolbar-left>
	    <a-input v-model="queryForm.candidateId" placeholder="请输入关联应聘者" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.interviewTime" placeholder="请输入面试时间" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.result" placeholder="请输入面试结果(1:通过 2:待定 3:不通过)" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:interview:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:interview:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:interview:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:interview:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link
            v-permission="['system:interview:delete']"
            status="danger"
            :disabled="record.disabled"
            :title="record.disabled ? '不可删除' : '删除'"
            @click="onDelete(record)"
          >
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>

    <InterviewAddModal ref="InterviewAddModalRef" @save-success="search" />
    <InterviewDetailDrawer ref="InterviewDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import InterviewAddModal from './InterviewAddModal.vue'
import InterviewDetailDrawer from './InterviewDetailDrawer.vue'
import { type InterviewResp, type InterviewQuery, deleteInterview, exportInterview, listInterview } from '@/apis/system/interview'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Interview' })


const queryForm = reactive<InterviewQuery>({
  candidateId: undefined,
  interviewTime: undefined,
  result: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listInterview({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '', dataIndex: 'id', slotName: 'id' },
  { title: '关联应聘者', dataIndex: 'candidateId', slotName: 'candidateId' },
  { title: '面试轮次', dataIndex: 'round', slotName: 'round' },
  { title: '面试官', dataIndex: 'interviewer', slotName: 'interviewer' },
  { title: '面试时间', dataIndex: 'interviewTime', slotName: 'interviewTime' },
  { title: '面试地点', dataIndex: 'address', slotName: 'address' },
  { title: '面试内容', dataIndex: 'content', slotName: 'content' },
  { title: '面试评价', dataIndex: 'evaluation', slotName: 'evaluation' },
  { title: '面试结果(1:通过 2:待定 3:不通过)', dataIndex: 'result', slotName: 'result' },
  { title: '下一轮面试时间', dataIndex: 'nextRoundTime', slotName: 'nextRoundTime' },
  { title: '', dataIndex: 'createTime', slotName: 'createTime' },
  { title: '', dataIndex: 'updateTime', slotName: 'updateTime' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:interview:detail', 'system:interview:update', 'system:interview:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.candidateId = undefined
  queryForm.interviewTime = undefined
  queryForm.result = undefined
  search()
}

// 删除
const onDelete = (record: InterviewResp) => {
  return handleDelete(() => deleteInterview(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportInterview(queryForm))
}

const InterviewAddModalRef = ref<InstanceType<typeof InterviewAddModal>>()
// 新增
const onAdd = () => {
  InterviewAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: InterviewResp) => {
  InterviewAddModalRef.value?.onUpdate(record.id)
}

const InterviewDetailDrawerRef = ref<InstanceType<typeof InterviewDetailDrawer>>()
// 详情
const onDetail = (record: InterviewResp) => {
  InterviewDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
