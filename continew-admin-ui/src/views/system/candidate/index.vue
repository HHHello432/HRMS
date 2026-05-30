<template>
  <div class="table-page">
    <GiTable
      title="应聘者管理"
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
	    <a-input v-model="queryForm.recruitmentId" placeholder="请输入关联招聘计划" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.name" placeholder="请输入姓名" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.phone" placeholder="请输入手机号" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.email" placeholder="请输入邮箱" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.education" placeholder="请输入学历" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.status" placeholder="请输入状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:candidate:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:candidate:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:candidate:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:candidate:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link
            v-permission="['system:candidate:delete']"
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

    <CandidateAddModal ref="CandidateAddModalRef" @save-success="search" />
    <CandidateDetailDrawer ref="CandidateDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import CandidateAddModal from './CandidateAddModal.vue'
import CandidateDetailDrawer from './CandidateDetailDrawer.vue'
import { type CandidateResp, type CandidateQuery, deleteCandidate, exportCandidate, listCandidate } from '@/apis/system/candidate'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Candidate' })


const queryForm = reactive<CandidateQuery>({
  recruitmentId: undefined,
  name: undefined,
  phone: undefined,
  email: undefined,
  education: undefined,
  status: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listCandidate({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '', dataIndex: 'id', slotName: 'id' },
  { title: '关联招聘计划', dataIndex: 'recruitmentId', slotName: 'recruitmentId' },
  { title: '姓名', dataIndex: 'name', slotName: 'name' },
  { title: '性别(0:未知 1:男 2:女)', dataIndex: 'gender', slotName: 'gender' },
  { title: '手机号', dataIndex: 'phone', slotName: 'phone' },
  { title: '邮箱', dataIndex: 'email', slotName: 'email' },
  { title: '学历', dataIndex: 'education', slotName: 'education' },
  { title: '毕业院校', dataIndex: 'school', slotName: 'school' },
  { title: '专业', dataIndex: 'major', slotName: 'major' },
  { title: '工作经历', dataIndex: 'experience', slotName: 'experience' },
  { title: '简历附件', dataIndex: 'resumeUrl', slotName: 'resumeUrl' },
  { title: '状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)', dataIndex: 'status', slotName: 'status' },
  { title: '', dataIndex: 'createTime', slotName: 'createTime' },
  { title: '', dataIndex: 'updateTime', slotName: 'updateTime' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:candidate:detail', 'system:candidate:update', 'system:candidate:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.recruitmentId = undefined
  queryForm.name = undefined
  queryForm.phone = undefined
  queryForm.email = undefined
  queryForm.education = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: CandidateResp) => {
  return handleDelete(() => deleteCandidate(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportCandidate(queryForm))
}

const CandidateAddModalRef = ref<InstanceType<typeof CandidateAddModal>>()
// 新增
const onAdd = () => {
  CandidateAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: CandidateResp) => {
  CandidateAddModalRef.value?.onUpdate(record.id)
}

const CandidateDetailDrawerRef = ref<InstanceType<typeof CandidateDetailDrawer>>()
// 详情
const onDetail = (record: CandidateResp) => {
  CandidateDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
