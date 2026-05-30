<template>
  <div class="table-page">
    <GiTable
      title="宿舍管理"
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
        <a-input v-model="queryForm.domId" placeholder="请输入宿舍ID" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
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
      <template #residentCount="{ record }">
        {{ record.staffList.length }}
      </template>
      <template #staffInfo="{ record }">
        <div v-for="(staff, index) in record.staffList" :key="index">
          <span>{{ staff.nickname }} ({{ staff.status === 0 ? '未入住' : staff.status === 1 ? '已入住' : '已退宿' }})</span>
        </div>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:dom:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:dom:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link
            v-permission="['system:dom:delete']"
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

    <DomAddModal ref="DomAddModalRef" @save-success="search" />
    <DomDetailDrawer ref="DomDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import DomAddModal from './DomAddModal.vue'
import DomDetailDrawer from './DomDetailDrawer.vue'
import { type DomResp, type DomQuery, deleteDom, exportDom, listDom } from '@/apis/system/dom'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Dom' })

const queryForm = reactive<DomQuery>({
  userId: undefined,
  domId: undefined,
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
} = useTable((page) => listDom({ ...queryForm, ...page }), { immediate: true })

const columns: TableInstanceColumns[] = [
  { title: '宿舍ID', dataIndex: 'id', slotName: 'id' },
  { title: '描述', dataIndex: 'description', slotName: 'description' },
  { title: '入住人数', dataIndex: 'residentCount', slotName: 'residentCount' },
  { title: '员工信息', dataIndex: 'staffInfo', slotName: 'staffInfo' }, // 新增列
  { title: '操作', dataIndex: 'action', slotName: 'action', width: 160, align: 'center', fixed: !isMobile() ? 'right' : undefined, show: has.hasPermOr(['system:dom:detail', 'system:dom:update', 'system:dom:delete']) }
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
  DomAddModalRef.value?.onUpdate(record.id)
}

const DomDetailDrawerRef = ref<InstanceType<typeof DomDetailDrawer>>()
// 详情
const onDetail = (record: DomResp) => {
  DomDetailDrawerRef.value?.onOpen(record)
  console.log('record:', record) // 添加调试信息
}
</script>