<template>
  <div class="table-page">
    <GiTable title="宿舍管理" row-key="id" :data="flatDataList" :columns="columns" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
      :disabled-column-keys="['name']" @refresh="search">
      <template #toolbar-left>
        <a-input v-model="queryForm.domId" placeholder="请输入宿舍id" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-date-picker v-model="queryForm.date" placeholder="请选择入住日期" show-time format="YYYY-MM-DD HH:mm:ss"
          style="height: 32px" />
        <a-input v-model="queryForm.status" placeholder="请输入0未入住、1已入住、2已退宿" allow-clear @change="search">
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
import DomAddModal from './DomAddModal.vue'
import DomDetailDrawer from './DomDetailDrawer.vue'
import { type DomResp, type DomQuery, deleteDom, exportDom, listDom } from '@/apis/system/dom'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'
import { computed } from 'vue';

defineOptions({ name: 'Dom' })


const queryForm = reactive<DomQuery>({
  domId: undefined,
  date: undefined,
  status: undefined,
  sort: ['id,desc']
})

const flatDataList = computed(() => {
  return dataList.value.flatMap((dom) => {
    return dom.staffList.map((staff) => ({
      ...dom,
      ...staff
    }));
  });
});

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listDom({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '宿舍ID', dataIndex: 'id', slotName: 'id' },
  { title: '员工ID', dataIndex: 'userId', slotName: 'userId' },
  { title: '宿舍ID', dataIndex: 'domId', slotName: 'domId' },
  { title: '入住日期', dataIndex: 'date', slotName: 'date' },
  { title: '状态', dataIndex: 'status', slotName: 'status' },
  { title: '创建人', dataIndex: 'createUser', slotName: 'createUser' },
  { title: '创建时间', dataIndex: 'createTime', slotName: 'createTime' },
  { title: '更新人', dataIndex: 'updateUser', slotName: 'updateUser' },
  { title: '更新时间', dataIndex: 'updateTime', slotName: 'updateTime' },
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
  DomDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>