<template>
  <a-modal v-model:visible="visible" :title="title" :mask-closable="false" :esc-to-close="false" draggable
    :width="width >= 600 ? 600 : '100%'" @before-ok="save" @close="reset">
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import { ref, computed, reactive } from 'vue';
import { Message } from '@arco-design/web-vue';
import { useWindowSize } from '@vueuse/core';
import { getInsurance, addInsurance, updateInsurance } from '@/apis/system/insurance';
import { type Columns, GiForm, type Options } from '@/components/GiForm';
import { useForm } from '@/hooks';
import { useDict } from '@/hooks/app';

// 定义弹窗的属性
const emit = defineEmits<{
  // 定义 save-success 事件
  (e: 'save-success'): void;
}>();

// 获取窗口大小
const { width } = useWindowSize();
const dataId = ref('');
const visible = ref(false);
// 定义是否是修改
const isUpdate = computed(() => !!dataId.value);
// 定义弹窗标题
const title = computed(() => (isUpdate.value ? '修改员工五险一金' : '新增员工五险一金'));
// 定义表单引用
const formRef = ref<InstanceType<typeof GiForm>>();
// 定义表单选项
const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
};
// 定义表单
const { form, resetForm } = useForm({
});
// 定义表格列
const columns: Columns = reactive([
  {
    label: '员工ID',
    field: 'userId',
    type: 'input',
    rules: [{ required: true, message: '请输入员工ID' }],
  },
  {
    label: '参保城市ID',
    field: 'cityId',
    type: 'input',
    rules: [{ required: true, message: '请输入参保城市ID' }],
  },
  {
    label: '公积金基数',
    field: 'houseBase',
    type: 'input',
    rules: [{ required: true, message: '请输入公积金基数' }],
  },
  {
    label: '社保基数',
    field: 'socialBase',
    type: 'input',
    rules: [{ required: true, message: '请输入社保基数' }],
  },
  {
    label: '公积金个人缴纳比例',
    field: 'perHouseRate',
    type: 'input',
    rules: [{ required: true, message: '请输入公积金个人缴纳比例' }],
  },
  {
    label: '公积金企业缴纳比例',
    field: 'comHouseRate',
    type: 'input',
    rules: [{ required: true, message: '请输入公积金企业缴纳比例' }],
  },
  {
    label: '工伤保险企业缴纳比例',
    field: 'comInjuryRate',
    type: 'input',
    rules: [{ required: true, message: '请输入工伤保险企业缴纳比例' }],
  },
  {
    label: '公积金备注',
    field: 'houseRemark',
    type: 'input',
  },
  {
    label: '社保备注',
    field: 'socialRemark',
    type: 'input',
  },
]);

// 重置
const reset = () => {
  // 介绍：formRef.value?.formRef?.resetFields() 是 GiForm 组件中的一个方法，用于重置表单的值。
  formRef.value?.formRef?.resetFields();
  resetForm();
};

// 打开详情
const onOpen = async (id: string) => {
  reset();
  dataId.value = id.toString();
  try {
    const { data } = await getInsurance(id);
    // 将获取到的数据赋值给表单
    Object.assign(form, data);
    visible.value = true;
  } catch (error) {
    Message.error('获取详情失败');
  }
};

// 保存
const save = async () => {
  try {
    // 验证表单
    const isInvalid = await formRef.value?.formRef?.validate();
    if (isInvalid) return false;
    // 判断是否是修改
    if (isUpdate.value) {
      await updateInsurance(form, dataId.value);
      Message.success('修改成功');
    } else {
      await addInsurance(form);
      Message.success('新增成功');
    }
    emit('save-success');
    return true;
  } catch (error) {
    return false;
  }
};

// 新增
const onAdd = async () => {
  reset();
  dataId.value = '';
  visible.value = true;
};

// 修改
const onUpdate = async (id: string) => {
  reset();
  dataId.value = parent.String(id);
  const { data } = await getInsurance(id);
  // 将获取到的数据赋值给表单
  Object.assign(form, data);
  visible.value = true;
};

defineExpose({ onAdd, onUpdate });
</script>

<style scoped lang="scss">

</style>