<template>
  <div class="table-page">
    <GiTable title="参保城市管理" row-key="id" :data="dataList" :columns="columns" :loading="loading"
             :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
             :disabled-column-keys="['name']" @refresh="search">
      <template #toolbar-left>
        <a-input v-model="queryForm.id" placeholder="请输入参保城市ID" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-input v-model="queryForm.cityName" placeholder="请输入参保城市" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:city:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:city:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:city:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <a-link v-permission="['system:city:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <a-link v-permission="['system:city:delete']" status="danger" :disabled="record.disabled"
                  :title="record.disabled ? '不可删除' : '删除'" @click="onDelete(record)">
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>

    <CityAddModal ref="CityAddModalRef" @save-success="search" />
    <CityDetailDrawer ref="CityDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import CityAddModal from './CityAddModal.vue'
import CityDetailDrawer from './CityDetailDrawer.vue'
import { type CityResp, type CityQuery, deleteCity, exportCity, listCity } from '@/apis/system/city'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'City' })


const queryForm = reactive<CityQuery>({
  id: undefined,
  cityName: undefined,
  socUpperLimit: undefined,
  socLowerLimit: undefined,
  houUpperLimit: undefined,
  houLowerLimit: undefined,
  perPensionRate: undefined,
  comPensionRate: undefined,
  perMedicalRate: undefined,
  comMedicalRate: undefined,
  perUnemploymentRate: undefined,
  comUnemploymentRate: undefined,
  comMaternityRate: undefined,
  remark: undefined,
  sort: ['id,desc']
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable((page) => listCity({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  { title: '参保城市ID', dataIndex: 'id', slotName: 'id', width: 110 },
  { title: '参保城市', dataIndex: 'cityName', slotName: 'cityName', width: 88 },
  // { title: '社保缴纳基数上限', dataIndex: 'socUpperLimit', slotName: 'socUpperLimit', width: 150 },
  // { title: '社保缴纳基数下限', dataIndex: 'socLowerLimit', slotName: 'socLowerLimit', width: 150 },
  // { title: '公积金缴纳基数上限', dataIndex: 'houUpperLimit', slotName: 'houUpperLimit', width: 160 },
  // { title: '公积金缴纳基数下限', dataIndex: 'houLowerLimit', slotName: 'houLowerLimit', width: 160 },
  { title: '个人养老保险缴费比例', dataIndex: 'perPensionRate', slotName: 'perPensionRate', width: 175 },
  { title: '企业养老保险缴费比例', dataIndex: 'comPensionRate', slotName: 'comPensionRate', width: 175 },
  { title: '个人医疗保险缴费比例', dataIndex: 'perMedicalRate', slotName: 'perMedicalRate', width: 175 },
  { title: '企业医疗保险缴费比例', dataIndex: 'comMedicalRate', slotName: 'comMedicalRate', width: 175 },
  { title: '个人失业保险缴费比例', dataIndex: 'perUnemploymentRate', slotName: 'perUnemploymentRate', width: 175 },
  { title: '企业失业保险缴费比例', dataIndex: 'comUnemploymentRate', slotName: 'comUnemploymentRate', width: 175 },
  { title: '企业生育保险缴费比例', dataIndex: 'comMaternityRate', slotName: 'comMaternityRate', width: 175 },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:city:detail', 'system:city:update', 'system:city:delete'])
  }
]

// 重置
const reset = () => {
  queryForm.id = undefined
  queryForm.cityName = undefined
  queryForm.socUpperLimit = undefined
  queryForm.socLowerLimit = undefined
  queryForm.houUpperLimit = undefined
  queryForm.houLowerLimit = undefined
  queryForm.perPensionRate = undefined
  queryForm.comPensionRate = undefined
  queryForm.perMedicalRate = undefined
  queryForm.comMedicalRate = undefined
  queryForm.perUnemploymentRate = undefined
  queryForm.comUnemploymentRate = undefined
  queryForm.comMaternityRate = undefined
  queryForm.remark = undefined
  search()
}

// 删除
const onDelete = (record: CityResp) => {
  return handleDelete(() => deleteCity(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportCity(queryForm))
}

const CityAddModalRef = ref<InstanceType<typeof CityAddModal>>()
// 新增
const onAdd = () => {
  CityAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: CityResp) => {
  CityAddModalRef.value?.onUpdate(record.id)
}

const CityDetailDrawerRef = ref<InstanceType<typeof CityDetailDrawer>>()
// 详情
const onDetail = (record: CityResp) => {
  CityDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
