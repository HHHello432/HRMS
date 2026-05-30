<template>
  <a-modal
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    draggable
    :width="width >= 600 ? 600 : '100%'"
    @before-ok="save"
    @close="reset"
  >
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { getAttendance, addAttendance, updateAttendance } from '@/apis/system/attendance'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useForm } from '@/hooks'
import { useDict } from '@/hooks/app'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? '修改员工考勤' : '新增员工考勤'))
const formRef = ref<InstanceType<typeof GiForm>>()

const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const { form, resetForm } = useForm({
  // todo 待补充
})

const columns: Columns = reactive([
  {
    label: '员工id',
    field: 'userId',
    type: 'input',
    rules: [{ required: true, message: '请输入员工id' }]
  },
  {
    label: '日期',
    field: 'date',
    type: 'date-picker',
    rules: [{ required: true, message: '请输入日期' }]
  },
  {
    label: '考勤情况',
    field: 'status',
    type: 'input',
    rules: [{ required: true, message: '请输入1迟到、2早退、3旷工、4请假' }]
  },
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
      await updateAttendance(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addAttendance(form)
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
  const { data } = await getAttendance(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
