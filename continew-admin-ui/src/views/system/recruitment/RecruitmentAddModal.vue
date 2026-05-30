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
import { getRecruitment, addRecruitment, updateRecruitment } from '@/apis/system/recruitment'
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
const title = computed(() => (isUpdate.value ? '修改招聘计划' : '新增招聘计划'))
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
    label: '职位名称',
    field: 'title',
    type: 'input',
    rules: [{ required: true, message: '请输入职位名称' }]
  },
  {
    label: '招聘部门',
    field: 'department',
    type: 'input',
  },
  {
    label: '职位类别(全职/兼职/实习)',
    field: 'positionType',
    type: 'input',
  },
  {
    label: '招聘人数',
    field: 'headcount',
    type: 'input',
  },
  {
    label: '职位描述',
    field: 'description',
    type: 'input',
  },
  {
    label: '任职要求',
    field: 'requirement',
    type: 'input',
  },
  {
    label: '最低薪资',
    field: 'salaryMin',
    type: 'input',
  },
  {
    label: '最高薪资',
    field: 'salaryMax',
    type: 'input',
  },
  {
    label: '工作地点',
    field: 'address',
    type: 'input',
  },
  {
    label: '状态(0:关闭 1:发布中)',
    field: 'status',
    type: 'input',
  },
  {
    label: '截止日期',
    field: 'deadline',
    type: 'input',
  },
  {
    label: '排序',
    field: 'sort',
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
      await updateRecruitment(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addRecruitment(form)
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
  const { data } = await getRecruitment(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
