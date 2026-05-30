<template>
    <a-modal v-model:visible="visible" title="事务审批" :mask-closable="false" :esc-to-close="false" draggable
        :width="width >= 600 ? 600 : '100%'" @before-ok="save" @close="reset">
        <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
    </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { getApproval, updateApproval, PassApproval } from '@/apis/system/approval'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useForm } from '@/hooks'
import { useDict } from '@/hooks/app'

const emit = defineEmits<{
    (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const visible = ref(false)
const formRef = ref<InstanceType<typeof GiForm>>()
const dataId = ref('')
const isUpdate = computed(() => !!dataId.value)


const options: Options = {
    form: { size: 'large' },
    btns: { hide: true },
}

const { form, resetForm } = useForm({
    // todo 待补充
})


const columns: Columns = reactive([
    {
        label: '审核状态',
        field: 'status',
        type: 'select',
        options: [
            { label: '审核通过', value: '1' },
            { label: '驳回', value: '2' },
            { label: '撤销', value: '3' },
            { label: '未审核', value: '0' }
        ],
        rules: [{ required: true, message: '请选择审核状态' }]
    },
    {
        label: '审核内容',
        field: 'remark2',
        type: 'input',
        rules: [{ required: true, message: '请输入事务内容' }]
    },
    {
        label: '事务类型',
        field: 'typeNum',
        type: 'select',
        options: [
            { label: '职务升降', value: '1' },
            { label: '岗位调整', value: '2' },
            { label: '出差报销', value: '3' },
            { label: '其他', value: '4' }
        ],
        rules: [{ required: true, message: '请选择事务类型' }],
        disabled: true
    },
    {
        label: '事务内容',
        field: 'remark1',
        type: 'input',
        rules: [{ required: true, message: '请输入事务内容' }],
        disabled: true
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
            await PassApproval(form, dataId.value)
            Message.success('审批成功')
            const { data } = await getApproval(dataId.value)
            Object.assign(form, data)
            visible.value = false
            emit('save-success')
        } else {
            return false
        }
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

// 打开审批弹窗
const onApprove = async (id: string) => {
    reset()
    dataId.value = id
    const { data } = await getApproval(id)
    Object.assign(form, data)
    visible.value = true
}

defineExpose({ onAdd, onApprove })
</script>

<style scoped lang="scss"></style>
