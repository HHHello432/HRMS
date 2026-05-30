<template>
  <div class="table-page">
    <GiTable
      title="招聘计划管理"
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
	    <a-input v-model="queryForm.title" placeholder="请输入职位名称" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.department" placeholder="请输入招聘部门" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.positionType" placeholder="请输入职位类别(全职/兼职/实习)" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
	    <a-input v-model="queryForm.status" placeholder="请输入状态(0:关闭 1:发布中)" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:recruitment:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:recruitment:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:recruitment:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:recruitment:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link
            v-permission="['system:recruitment:delete']"
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

    <RecruitmentAddModal ref="RecruitmentAddModalRef" @save-success="search" />
    <RecruitmentDetailDrawer ref="RecruitmentDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import RecruitmentAddModal from './RecruitmentAddModal.vue'
import RecruitmentDetailDrawer from './RecruitmentDetailDrawer.vue'
import { type RecruitmentResp, type RecruitmentQuery, deleteRecruitment, exportRecruitment, listRecruitment } from '@/apis/system/recruitment'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Recruitment' })


const queryForm = reactive<RecruitmentQuery>({
  title: undefined,
  department: undefined,
  positionType: undefined,
  status: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listRecruitment({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '', dataIndex: 'id', slotName: 'id' },
  { title: '职位名称', dataIndex: 'title', slotName: 'title' },
  { title: '招聘部门', dataIndex: 'department', slotName: 'department' },
  { title: '职位类别(全职/兼职/实习)', dataIndex: 'positionType', slotName: 'positionType' },
  { title: '招聘人数', dataIndex: 'headcount', slotName: 'headcount' },
  { title: '职位描述', dataIndex: 'description', slotName: 'description' },
  { title: '任职要求', dataIndex: 'requirement', slotName: 'requirement' },
  { title: '最低薪资', dataIndex: 'salaryMin', slotName: 'salaryMin' },
  { title: '最高薪资', dataIndex: 'salaryMax', slotName: 'salaryMax' },
  { title: '工作地点', dataIndex: 'address', slotName: 'address' },
  { title: '状态(0:关闭 1:发布中)', dataIndex: 'status', slotName: 'status' },
  { title: '截止日期', dataIndex: 'deadline', slotName: 'deadline' },
  { title: '排序', dataIndex: 'sort', slotName: 'sort' },
  { title: '', dataIndex: 'createUser', slotName: 'createUser' },
  { title: '', dataIndex: 'createTime', slotName: 'createTime' },
  { title: '', dataIndex: 'updateUser', slotName: 'updateUser' },
  { title: '', dataIndex: 'updateTime', slotName: 'updateTime' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:recruitment:detail', 'system:recruitment:update', 'system:recruitment:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.title = undefined
  queryForm.department = undefined
  queryForm.positionType = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: RecruitmentResp) => {
  return handleDelete(() => deleteRecruitment(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportRecruitment(queryForm))
}

const RecruitmentAddModalRef = ref<InstanceType<typeof RecruitmentAddModal>>()
// 新增
const onAdd = () => {
  RecruitmentAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: RecruitmentResp) => {
  RecruitmentAddModalRef.value?.onUpdate(record.id)
}

const RecruitmentDetailDrawerRef = ref<InstanceType<typeof RecruitmentDetailDrawer>>()
// 详情
const onDetail = (record: RecruitmentResp) => {
  RecruitmentDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
