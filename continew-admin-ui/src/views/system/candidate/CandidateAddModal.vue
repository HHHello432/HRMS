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
import { getCandidate, addCandidate, updateCandidate } from '@/apis/system/candidate'
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
const title = computed(() => (isUpdate.value ? '修改应聘者' : '新增应聘者'))
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
    label: '关联招聘计划',
    field: 'recruitmentId',
    type: 'input',
    rules: [{ required: true, message: '请输入关联招聘计划' }]
  },
  {
    label: '姓名',
    field: 'name',
    type: 'input',
    rules: [{ required: true, message: '请输入姓名' }]
  },
  {
    label: '性别(0:未知 1:男 2:女)',
    field: 'gender',
    type: 'input',
  },
  {
    label: '手机号',
    field: 'phone',
    type: 'input',
  },
  {
    label: '邮箱',
    field: 'email',
    type: 'input',
  },
  {
    label: '学历',
    field: 'education',
    type: 'input',
  },
  {
    label: '毕业院校',
    field: 'school',
    type: 'input',
  },
  {
    label: '专业',
    field: 'major',
    type: 'input',
  },
  {
    label: '工作经历',
    field: 'experience',
    type: 'input',
  },
  {
    label: '简历附件',
    field: 'resumeUrl',
    type: 'input',
  },
  {
    label: '状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)',
    field: 'status',
    type: 'input',
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
      await updateCandidate(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addCandidate(form)
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
  const { data } = await getCandidate(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
