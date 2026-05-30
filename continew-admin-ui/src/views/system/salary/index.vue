<!-- 员工工资管理主页面：实现工资信息的增删改查、导出等功能 -->
<template>
  <div class="table-page">
    <!-- GiTable: 通用表格组件，用于展示工资数据列表 -->
    <GiTable
      title="员工工资管理"
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
      <!-- 表格左侧工具栏：搜索区域 -->
      <template #toolbar-left>
        <!-- 员工ID搜索输入框 -->
	    <a-input v-model="queryForm.userId" placeholder="请输入员工ID" allow-clear @change="search">
	      <template #prefix><icon-search /></template>
	    </a-input>
        <!-- 月份选择器：用于筛选指定月份的工资数据 -->
        <a-date-picker
          v-model="queryForm.month"
          placeholder="请选择月份"
          format="YYYY-MM-DD"
          style="height: 32px"
          @change="search"
        ><template #prefix><icon-search /></template></a-date-picker>
        <!-- 重置按钮：清空所有筛选条件 -->
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>重置</template>
        </a-button>
      </template>

      <!-- 表格右侧工具栏：功能按钮区域 -->
      <template #toolbar-right>
        <!-- 新增按钮：打开新增工资信息弹窗 -->
        <a-button v-permission="['system:salary:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>新增</template>
        </a-button>
        <!-- 导出按钮：导出当前筛选条件下的工资数据 -->
        <a-button v-permission="['system:salary:export']" @click="onExport">
          <template #icon><icon-download /></template>
          <template #default>导出</template>
        </a-button>
      </template>

      <!-- 表格操作列：每行数据的操作按钮 -->
      <template #action="{ record }">
        <a-space>
          <!-- 详情按钮：查看工资详细信息 -->
          <a-link v-permission="['system:salary:detail']" title="详情" @click="onDetail(record)">详情</a-link>
          <!-- 修改按钮：修改工资信息 -->
          <a-link v-permission="['system:salary:update']" title="修改" @click="onUpdate(record)">修改</a-link>
          <!-- 删除按钮：删除工资记录 -->
          <a-link
            v-permission="['system:salary:delete']"
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

    <!-- 子组件：工资信息的新增/编辑弹窗 -->
    <SalaryAddModal ref="SalaryAddModalRef" @save-success="search" />
    <!-- 子组件：工资详情查看抽屉 -->
    <SalaryDetailDrawer ref="SalaryDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
// 导入必要的组件和工具函数
import SalaryAddModal from './SalaryAddModal.vue'
import SalaryDetailDrawer from './SalaryDetailDrawer.vue'
import { type SalaryResp, type SalaryQuery, deleteSalary, exportSalary, listSalary } from '@/apis/system/salary'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

// 定义组件名称
defineOptions({ name: 'Salary' })

// 查询条件表单：定义查询参数的数据结构
const queryForm = reactive<SalaryQuery>({
  userId: undefined,           // 员工ID
  baseSalary: undefined,      // 基础工资
  subsidy: undefined,         // 补贴
  performance: undefined,     // 绩效工资
  overtimeSalary: undefined,  // 加班工资
  bonus: undefined,           // 奖金
  totalSalary: undefined,     // 总工资
  lateDeduct: undefined,      // 迟到扣款
  leaveDeduct: undefined,     // 请假扣款
  leaveEarlyDeduct: undefined,// 早退扣款
  absenteeismDeduct: undefined,// 旷工扣款
  month: undefined,           // 工资月份
  remark: undefined,          // 备注信息
  sort: ['id,desc']           // 默认按ID降序排序
})

// 使用表格Hook，统一处理表格数据加载、分页等逻辑
const {
  tableData: dataList,        // 表格数据列表
  loading,                    // 加载状态
  pagination,                 // 分页信息
  search,                     // 搜索方法
  handleDelete                // 删除处理方法
} = useTable((page) => listSalary({ ...queryForm, ...page }), { immediate: true })

// 定义表格列配置
const columns: TableInstanceColumns[] = [
  { title: '员工ID', dataIndex: 'userId', slotName: 'userId' },
  { title: '员工姓名', dataIndex: 'nickname', slotName: 'nickname' },
  { title: '基础工资', dataIndex: 'baseSalary', slotName: 'baseSalary' },
  { title: '补贴', dataIndex: 'subsidy', slotName: 'subsidy' },
  { title: '绩效', dataIndex: 'performance', slotName: 'performance' },
  { title: '奖金', dataIndex: 'bonus', slotName: 'bonus' },
  { title: '公积金缴纳', dataIndex: 'perHousePay', slotName: 'perHousePay' },
  { title: '社保缴纳', dataIndex: 'perSocialPay', slotName: 'perSocialPay' },
  { title: '总工资', dataIndex: 'totalSalary', slotName: 'totalSalary' },
  { title: '月份', dataIndex: 'month', slotName: 'month' },
  {
    title: '操作',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['system:salary:detail', 'system:salary:update', 'system:salary:delete'])
  }
]

// 重置查询条件
const reset = () => {
  // 重置所有查询字段为默认值
  queryForm.userId = undefined
  queryForm.baseSalary = undefined
  queryForm.subsidy = undefined
  queryForm.performance = undefined
  queryForm.overtimeSalary = undefined
  queryForm.bonus = undefined
  queryForm.totalSalary = undefined
  queryForm.lateDeduct = undefined
  queryForm.leaveDeduct = undefined
  queryForm.leaveEarlyDeduct = undefined
  queryForm.absenteeismDeduct = undefined
  queryForm.month = undefined
  queryForm.remark = undefined
  // 触发重新搜索
  search()
}

// 删除工资记录
const onDelete = (record: SalaryResp) => {
  return handleDelete(() => deleteSalary(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true
  })
}

// 导出工资数据
const onExport = () => {
  useDownload(() => exportSalary(queryForm))
}

// 新增/编辑弹窗组件引用
const SalaryAddModalRef = ref<InstanceType<typeof SalaryAddModal>>()
// 打开新增工资信息弹窗
const onAdd = () => {
  SalaryAddModalRef.value?.onAdd()
}

// 打开修改工资信息弹窗
const onUpdate = (record: SalaryResp) => {
  SalaryAddModalRef.value?.onUpdate(record.id)
}

// 详情抽屉组件引用
const SalaryDetailDrawerRef = ref<InstanceType<typeof SalaryDetailDrawer>>()
// 打开工资详情抽屉
const onDetail = (record: SalaryResp) => {
  SalaryDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>