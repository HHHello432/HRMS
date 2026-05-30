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
import { getInterview, addInterview, updateInterview } from '@/apis/system/interview'
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
const title = computed(() => (isUpdate.value ? '修改面试记录' : '新增面试记录'))
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
    label: '关联应聘者',
    field: 'candidateId',
    type: 'input',
    rules: [{ required: true, message: '请输入关联应聘者' }]
  },
  {
    label: '面试轮次',
    field: 'round',
    type: 'input',
  },
  {
    label: '面试官',
    field: 'interviewer',
    type: 'input',
  },
  {
    label: '面试时间',
    field: 'interviewTime',
    type: 'input',
  },
  {
    label: '面试地点',
    field: 'address',
    type: 'input',
  },
  {
    label: '面试内容',
    field: 'content',
    type: 'input',
  },
  {
    label: '面试评价',
    field: 'evaluation',
    type: 'input',
  },
  {
    label: '面试结果(1:通过 2:待定 3:不通过)',
    field: 'result',
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
      await updateInterview(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addInterview(form)
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
  const { data } = await getInterview(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
