<template>
  <a-modal v-model:visible="visible" :title="title" :mask-closable="false" :esc-to-close="false" draggable
    :width="width >= 600 ? 600 : '100%'" @before-ok="save" @close="reset">
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
    <!-- <h3>员工信息</h3>
    <a-table :columns="staffColumns" :data-source="form.staffList" :pagination="false" :bordered="true">
      <template #bodyCell="{ column, record, index }">
        <template v-if="column.key === 'action'">
          <a-button type="link" @click="removeStaff(index)">删除</a-button>
        </template>
</template>
</a-table>
<a-button type="dashed" block @click="addStaff">添加员工</a-button> -->
  </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { getDom, addDom, updateDom } from '@/apis/system/dom'
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
const title = computed(() => (isUpdate.value ? '修改宿舍管理' : '新增宿舍管理'))
const formRef = ref<InstanceType<typeof GiForm>>()

const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const { form, resetForm } = useForm({
  id: '',
  description: '',
  status: 0,
  staffList: []
})


const columns: Columns = reactive([
  {
    label: '宿舍ID',
    field: 'id',
    type: 'input',
    rules: [{ required: true, message: '请输入宿舍ID' }]
  },
  {
    label: '宿舍可住人数',
    field: 'residentCount',
    type: 'input',
    rules: [{ required: true, message: '请输入宿舍可住人数' }]
  },
  {
    label: '描述',
    field: 'description',
    type: 'input',
    rules: [{ required: true, message: '请输入宿舍描述' }]
  },
])

// const staffColumns = [
//   { title: '员工ID', dataIndex: 'userId', key: 'userId' },
//   { title: '用户名', dataIndex: 'username', key: 'username' },
//   { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
//   { title: '性别', dataIndex: 'gender', key: 'gender', customRender: ({ text }) => (text === 1 ? '男' : '女') },
//   { title: '电话', dataIndex: 'phone', key: 'phone' },
//   { title: '入住日期', dataIndex: 'date', key: 'date' },
//   { title: '状态', dataIndex: 'status', key: 'status', customRender: ({ text }) => (text === 0 ? '未入住' : text === 1 ? '已入住' : '已退宿') },
//   { title: '操作', key: 'action' }
// ]

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
      await updateDom(form, dataId.value)
      Message.success('修改成功')
    } else {
      await addDom(form)
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
  const { data } = await getDom(id)
  Object.assign(form, data)
  const staff = data.staffList
  // console.log('d2222222222222222222ata:', data)
  // console.log('data.staffList:',staff)
  // console.log('1111111111111111111111',staff)
  // 将 staffList 数据赋值给 form.staffList
  form.staffList = data.staffList.map(staff => ({
    userId: staff.userId,
    username: staff.username,
    nickname: staff.nickname,
    gender: staff.gender,
    phone: staff.phone,
    date: staff.date,
    status: staff.status
  }))
  // console.log('1111111111111form:', form.staffList)

  // 其他字段赋值
  Object.assign(form, {
    id: data.id,
    description: data.description,
    status: data.status
  })
  visible.value = true
}

//打开添加员工模态框
const openAddStaffModal = () => {
  addStaffModalRef.value?.open()
}

// 添加员工
const addStaff = () => {
  form.staffList.push({
    userId: undefined,
    username: '',
    nickname: '',
    gender: undefined,
    phone: '',
    date: '',
    status: 0
  })
}

// 删除员工
const removeStaff = (index: number) => {
  form.staffList.splice(index, 1)
}

defineExpose({ onAdd, onUpdate })
</script>