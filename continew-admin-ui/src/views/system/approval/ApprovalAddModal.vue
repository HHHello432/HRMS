<template>
  <a-modal v-model:visible="visible" :title="title" :mask-closable="false" :esc-to-close="false" draggable
    :width="width >= 600 ? 600 : '100%'" @before-ok="save" @close="reset">
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { getApproval, addApproval, updateApproval } from '@/apis/system/approval'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useForm } from '@/hooks'
import { APPROVAL_TYPES } from '@/apis/system/approval'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const visible = ref(false)
const formRef = ref<InstanceType<typeof GiForm>>()
const dataId = ref('')
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? '修改事务申请' : '新增事务申请'))

const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const { form, resetForm } = useForm({
  typeNum: undefined,
  remark1: undefined
})

// 删除重复定义的 columns，只保留这一个
const columns = reactive<Columns>([
  {
    label: '事务类型',
    field: 'typeNum',
    type: 'select',
    options: Object.values(APPROVAL_TYPES),
    rules: [{ required: true, message: '请选择事务类型' }]
  },
  {
    label: '事务内容',
    field: 'remark1',
    type: 'input',
    rules: [{ required: true, message: '请输入事务内容' }]
  }
])

// 重置
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
}

// 保存
const save = async () => {
  try {
    const isInvalid = await formRef.value?.formRef?.validate()
    if (isInvalid) return false
    if (isUpdate.value) {
      await updateApproval(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addApproval(form)
      Message.success('新增成功')
    }
    emit('save-success')
    return true
  } catch (error) {
    return false
  }
}

// 新增
const onAdd = async () => {
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getApproval(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>