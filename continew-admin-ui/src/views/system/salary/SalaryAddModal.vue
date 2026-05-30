<template>
  <!-- 工资新增/修改弹窗 -->
  <a-modal
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    draggable
    :width="width >= 700 ? 700 : '100%'"
    @before-ok="save"
    @close="reset"
  >
    <!-- 表单组件 -->
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
// 导入所需的组件和接口
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { getSalary, addSalary, updateSalary } from '@/apis/system/salary'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useForm } from '@/hooks'
import { useDict } from '@/hooks/app'

// 定义组件事件
const emit = defineEmits<{
  (e: 'save-success'): void
}>()

// 获取窗口大小
const { width } = useWindowSize()

// 定义响应式数据
const dataId = ref('') // 当前编辑的数据ID
const visible = ref(false) // 控制弹窗显示状态
const isUpdate = computed(() => !!dataId.value) // 是否为修改模式
const title = computed(() => (isUpdate.value ? '修改员工工资' : '新增员工工资')) // 弹窗标题
const formRef = ref<InstanceType<typeof GiForm>>() // 表单引用

// 定义表单配置项
const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

// 初始化表单数据
const { form, resetForm } = useForm({
  // todo 待补充
})

// 定义表单字段配置
const columns: Columns = reactive([
  // 员工ID字段
  {
    label: '员工id',
    field: 'userId',
    type: 'input',
    rules: [{ required: true, message: '请输入员工id' }]
  },
  // 基础工资字段
  {
    label: '基础工资',
    field: 'baseSalary',
    type: 'input',
    rules: [{ required: true, message: '请输入基础工资' }]
  },
  // 其他薪资相关字段...
  {
    label: '补贴',
    field: 'subsidy',
    type: 'input',
  },
  {
    label: '绩效',
    field: 'performance',
    type: 'input',
  },
  {
    label: '加班费',
    field: 'overtimeSalary',
    type: 'input',
  },
  {
    label: '奖金',
    field: 'bonus',
    type: 'input',
  },
  // 月份选择器
  {
    label: '月份',
    field: 'month',
    type: 'date-picker',
    rules: [{ required: true, message: '请输入月份' }]
  },
  // 备注字段
  {
    label: '备注',
    field: 'remark',
    type: 'input',
  },
])

// 重置表单数据
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
}

// 保存表单数据
const save = async () => {
  try {
    // 表单验证
    const isInvalid = await formRef.value?.formRef?.validate()
    if (isInvalid) return false
    // 根据模式选择保存方式
    if (isUpdate.value) {
      await updateSalary(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addSalary(form)
      Message.success('新增成功')
    }
    emit('save-success')
    return true
  } catch (error) {
    return false
  }
}

// 新增操作
const onAdd = async () => {
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改操作
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getSalary(id)
  Object.assign(form, data)
  visible.value = true
}

// 暴露组件方法
defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
