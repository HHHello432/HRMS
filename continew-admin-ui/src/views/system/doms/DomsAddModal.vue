<template>
  <a-modal v-model:visible="visible" :title="title" :mask-closable="false" :esc-to-close="false" draggable
    :width="width >= 600 ? 600 : '100%'" @before-ok="save" @close="reset">
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { ref, computed, reactive } from 'vue'
import { useWindowSize } from '@vueuse/core'
import { getDom, addDom, updateDom } from '@/apis/system/doms'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useForm } from '@/hooks'
import { useDict } from '@/hooks/app'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()
const { dom_status } = useDict('dom_status')

const visible = ref(false)
const formRef = ref<InstanceType<typeof GiForm>>()
const dataId = ref('')
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? '修改宿舍管理' : '新增宿舍管理'))

const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

// 定义表单初始值和类型
const { form, resetForm } = useForm({
  userId: undefined,
  domId: undefined,
  date: undefined,
  status: undefined
})

const columns = reactive<Columns>([
  {
    label: '员工ID',
    field: 'userId',
    type: 'inputNumber', // 使用数字输入框
    rules: [{ required: true, message: '请输入员工ID' }]
  },
  {
    label: '宿舍ID',
    field: 'domId',
    type: 'inputNumber', // 使用数字输入框
    rules: [{ required: true, message: '请输入宿舍ID' }]
  },
  {
    label: '入住日期',
    field: 'date',
    type: 'datePicker',
    props: {
      format: 'YYYY-MM-DD',
      valueFormat: 'YYYY-MM-DD', // 确保日期格式正确
    },
    rules: [{ required: true, message: '请选择入住日期' }]
  },
  {
    label: '入住状态',
    field: 'status',
    type: 'select',
    options: [
      { label: '未入住', value: 0 },
      { label: '已入住', value: 1 },
      { label: '已退宿', value: 2 }
    ],
    rules: [{ required: true, message: '请选择入住状态' }]
  }
])

// 重置
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
}

// 保存前处理数据
const formatFormData = (formData: any) => {
  return {
    ...formData,
    userId: Number(formData.userId),
    domId: Number(formData.domId),
    status: Number(formData.status)
  }
}

// 保存
const save = async () => {
  try {
    const isInvalid = await formRef.value?.formRef?.validate()
    if (isInvalid) return false

    // 格式化数据
    const submitData = formatFormData(form)

    if (isUpdate.value) {
      await updateDom(submitData, dataId.value)
      Message.success('修改成功')
    } else {
      await addDom(submitData)
      Message.success('新增成功')
    }
    emit('save-success')
    return true
  } catch (error) {
    console.error('保存失败:', error)
    return false
  }
}

// 新增
const onAdd = () => {
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  try {
    const { data } = await getDom(id)
    Object.assign(form, data)
    visible.value = true
  } catch (error) {
    console.error('获取详情失败:', error)
    Message.error('获取详情失败')
  }
}

defineExpose({ onAdd, onUpdate })
</script>