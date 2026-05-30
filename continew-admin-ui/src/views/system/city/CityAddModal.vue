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
import { getCity, addCity, updateCity } from '@/apis/system/city'
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
const title = computed(() => (isUpdate.value ? '修改参保城市' : '新增参保城市'))
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
    label: '参保城市',
    field: 'cityName',
    type: 'input',
    rules: [{ required: true, message: '请输入参保城市' }]
  },
  {
    label: '职工社保缴纳基数上限',
    field: 'socUpperLimit',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入职工社保缴纳基数上限' }]
  },
  {
    label: '职工社保缴纳基数下限',
    field: 'socLowerLimit',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入职工社保缴纳基数下限' }]
  },
  {
    label: '公积金缴纳基数上限',
    field: 'houUpperLimit',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入公积金缴纳基数上限' }]
  },
  {
    label: '公积金缴纳基数下限',
    field: 'houLowerLimit',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入公积金缴纳基数下限' }]
  },
  {
    label: '个人养老保险缴费比例',
    field: 'perPensionRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入个人养老保险缴费比例' }]
  },
  {
    label: '企业养老保险缴费比例',
    field: 'comPensionRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入企业养老保险缴费比例' }]
  },
  {
    label: '个人医疗保险缴费比例',
    field: 'perMedicalRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入个人医疗保险缴费比例' }]
  },
  {
    label: '企业医疗保险缴费比例',
    field: 'comMedicalRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入企业医疗保险缴费比例' }]
  },
  {
    label: '个人失业保险缴费比例',
    field: 'perUnemploymentRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入个人失业保险缴费比例' }]
  },
  {
    label: '企业失业保险缴费比例',
    field: 'comUnemploymentRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入企业失业保险缴费比例' }]
  },
  {
    label: '企业生育保险缴费比例',
    field: 'comMaternityRate',
    type: 'input-number', 
    rules: [{ required: true, message: '请输入企业生育保险缴费比例' }]
  },
  {
    label: '备注',
    field: 'remark',
    type: 'input',
    rules: [{ required: true, message: '请输入备注' }]
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
      await updateCity(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addCity(form)
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
  const { data } = await getCity(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
