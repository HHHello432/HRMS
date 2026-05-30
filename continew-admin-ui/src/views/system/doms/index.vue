<template>
  <div class="table-page">
    <GiTable title="宿舍人员管理" row-key="id" :data="dataList" :columns="columns" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
      :disabled-column-keys="['name']" @refresh="search">
      <template #toolbar-left>
        <a-input v-model="queryForm.domId" placeholder="请输入宿舍ID" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-select v-model="queryForm.status" :options="dom_status" placeholder="请选择住宿情况" allow-clear
          style="width: 150px" @change="search" />
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:dom:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:dom:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #status="{ record }">
        <span v-if="dom_status[record.status]">{{ dom_status[record.status].value }}</span>
        
        <span v-else>未知状态</span>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:dom:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:dom:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link v-permission="['system:dom:delete']" status="danger" :disabled="record.disabled"
            :title="record.disabled ? '不可删除' : '删除'" @click="onDelete(record)">
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>
    <DomAddModal ref="DomAddModalRef" @save-success="search" />
    <DomDetailDrawer ref="DomDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import DomAddModal from './DomsAddModal.vue'
import DomDetailDrawer from './DomsDetailDrawer.vue'
import { type DomResp, type DomQuery, deleteDom, exportDom, listDoms } from '@/apis/system/doms'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Dom' })

const { dom_status } = useDict('dom_status');


const queryForm = reactive<DomQuery>({
  userId: undefined,
  nickname: undefined,
  domId: undefined,
  date: undefined,
  phone: undefined,
  gender: undefined,
  status: undefined as number | undefined,
  sort: ['id,desc']
})

const genderMap = {
  0: '未知',
  1: '男',
  2: '女'
}

const getGenderLabel = (gender: number): string => {
  return genderMap[gender] || '未知';
}

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listDoms({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '员工ID', dataIndex: 'userId', slotName: 'userId' },
  { title: '姓名', dataIndex: 'nickname', slotName: 'nickname' },
  { title: '宿舍', dataIndex: 'domId', slotName: 'domId' },
  { title: '手机号', dataIndex: 'phone', slotName: 'phone' },
  { title: '性别', dataIndex: 'gender', slotName: 'gender' },
  { title: '入住日期', dataIndex: 'date', slotName: 'date' },
  { title: '住宿情况', dataIndex: 'status', slotName: 'status' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:dom:detail', 'system:dom:update', 'system:dom:delete'])
  }
]



// 重置
const reset = () => {
  queryForm.userId = undefined
  queryForm.domId = undefined
  queryForm.date = undefined
  queryForm.status = undefined
  search()
}



// 删除
const onDelete = (record: DomResp) => {
  return handleDelete(() => deleteDom(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportDom(queryForm))
}

const DomAddModalRef = ref<InstanceType<typeof DomAddModal>>()
// 新增
const onAdd = () => {
  DomAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: DomResp) => {
  console.log('222222222222222222222222',record.id)
  DomAddModalRef.value?.onUpdate(record.id)
}

const DomDetailDrawerRef = ref<InstanceType<typeof DomDetailDrawer>>()
// 详情
const onDetail = (record: DomResp) => {
  DomDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>