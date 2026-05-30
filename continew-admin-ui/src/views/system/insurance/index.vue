<template>
  <div class="table-page">
    <GiTable title="员工五险一金管理" row-key="id" :data="dataList" :columns="columns" :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }" :pagination="pagination" :disabled-tools="['size']"
      :disabled-column-keys="['name']" @refresh="search">

      <!-- <GiTable 
          title="员工五险一金管理"        // 表格标题
          row-key="id"                  // 行数据的唯一标识字段
          :data="dataList"             // 表格数据源
          :columns="columns"           // 表格列配置
          :loading="loading"           // 加载状态
          :scroll="{                   // 表格滚动配置
            x: '100%',                // 水平滚动宽度
            y: '100%',                // 垂直滚动高度
            minWidth: 1000            // 最小宽度
          }" 
          :pagination="pagination"     // 分页配置
          :disabled-tools="['size']"   // 禁用的工具栏项
          :disabled-column-keys="['name']"  // 禁用列设置的字段
          @refresh="search"            // 刷新事件处理函数
> -->
      <template #toolbar-left>
        <a-input v-model="queryForm.userId" placeholder="请输入员工ID" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-input v-model="queryForm.cityName" placeholder="请输入城市" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>
      <template #toolbar-right>
        <!-- v-permission 权限:当用户在登陆后会检测他有什么权限，如果他有system:insurance:add权限，那么他就可以点击新增按钮 -->
        <a-button v-permission="['system:insurance:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <a-button v-permission="['system:insurance:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>
      <template #action="{ record }">
        <!-- 操作 -->
        <a-space>
          <a-link v-permission="['system:insurance:detail']" title="详情" @click="onDetail(record.id)">详情</a-link>
          <a-link v-permission="['system:insurance:update']" title="修改" @click="onUpdate(record.id)">修改</a-link>
          <a-link v-permission="['system:insurance:delete']" status="danger" :disabled="record.disabled"
            :title="record.disabled ? '不可删除' : '删除'" @click="onDelete(record)">
            删除
          </a-link>
        </a-space>
      </template>
    </GiTable>
    <!-- 新增弹窗 -->
    <InsuranceAddModal ref="InsuranceAddModalRef" @save-success="search" />
    <!-- 详情弹窗 -->
    <InsuranceDetailDrawer ref="InsuranceDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
// 组件
import InsuranceAddModal from './InsuranceAddModal.vue'
import InsuranceDetailDrawer from './InsuranceDetailDrawer.vue'
// 接口
import { type InsuranceResp, type InsuranceQuery, deleteInsurance, exportInsurance, listInsurance, getInsurance } from '@/apis/system/insurance'
// 类型
import type { TableInstanceColumns } from '@/components/GiTable/type'
// 钩子
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Insurance' })

// 定义查询对象
const queryForm = reactive<InsuranceQuery>({
  id: undefined,
  userId: undefined,
  cityId: undefined,
  houseBase: undefined,
  houseRemark: undefined,
  socialBase: undefined,
  comInjuryRate: undefined,
  socialRemark: undefined,
  sort: ['id,desc']
})


const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete
} = useTable(async (page) => {
  // 获取表格数据
  const response = await listInsurance({ ...queryForm, ...page })
  return {
    data: response.data.records,
    total: response.data.total,
    size: response.data.size,
    current: response.data.current,
    pages: response.data.pages
  }
}, { immediate: true })

// 定义表格列
const columns: TableInstanceColumns[] = [
  { title: '员工ID', dataIndex: 'userId', slotName: 'userId', width: 80 },
  { title: '员工姓名', dataIndex: 'nickname', slotName: 'nickname', width: 130 },
  { title: '城市名称', dataIndex: 'cityName', slotName: 'cityName', width: 110 },
  { title: '公积金基数', dataIndex: 'houseBase', slotName: 'houseBase', width: 110 },
  { title: '社保基数', dataIndex: 'socialBase', slotName: 'socialBase', width: 110 },
  { title: '公积金个人缴纳费用', dataIndex: 'perHousePay', slotName: 'perHousePay', width: 160 },
  { title: '社保个人缴纳费用', dataIndex: 'perSocialPay', slotName: 'perSocialPay', width: 160 },
  { title: '公积金企业缴纳费用', dataIndex: 'comHousePay', slotName: 'comHousePay', width: 160 },
  { title: '社保企业缴纳费用', dataIndex: 'comSocialPay', slotName: 'comSocialPay', width: 160 },

  // { title: '社保备注', dataIndex: 'socialRemark', slotName: 'socialRemark', width: 110 },
  // { title: '公积金备注', dataIndex: 'houseRemark', slotName: 'houseRemark', width: 110 },
  // { title: '创建人ID', dataIndex: 'createUser', slotName: 'createUser' },
  // { title: '创建时间', dataIndex: 'createTime', slotName: 'createTime' },
  // { title: '更新人', dataIndex: 'updateUser', slotName: 'updateUser' },
  // { title: '更新时间', dataIndex: 'updateTime', slotName: 'updateTime' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:insurance:detail', 'system:insurance:update', 'system:insurance:delete'])
  }
]

// 定义新增弹窗
const InsuranceAddModalRef = ref<InstanceType<typeof InsuranceAddModal>>()
// 定义详情弹窗
const InsuranceDetailDrawerRef = ref<InstanceType<typeof InsuranceDetailDrawer>>()


// 重置
const reset = () => {
  queryForm.id = undefined
  queryForm.userId = undefined
  queryForm.cityName = undefined
  queryForm.houseBase = undefined
  queryForm.houseRemark = undefined
  queryForm.socialBase = undefined
  queryForm.comInjuryRate = undefined
  queryForm.socialRemark = undefined
  search()
}

// 删除
const onDelete = (record: InsuranceResp) => {
  return handleDelete(() => deleteInsurance(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportInsurance(queryForm))
}


// 新增
const onAdd = () => {
  InsuranceAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: InsuranceResp) => {
  console.log(record)
  // 调用新增弹窗的 onUpdate 方法，InsuranceAddModalRef.value如果不为空，则调用onUpdate方法
  InsuranceAddModalRef.value?.onUpdate(record)
}

// 详情
const onDetail = (record: InsuranceResp) => {
  console.log('onDetail - record:', record) // 打印 record 对象
  console.log('onDetail - record.id:', record.id) // 打印 record.id
  InsuranceDetailDrawerRef.value?.onOpen(record)
}
</script>

<style scoped lang="scss"></style>