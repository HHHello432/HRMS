<template>
  <!-- 抽屉组件：用于显示员工五险一金详情 -->
  <a-drawer v-model:visible="visible" title="员工五险一金详情" :width="width >= 600 ? 600 : '40%'" :footer="false">
    <!-- 描述列表：展示五险一金的详细信息 -->
    <a-descriptions :column="2" size="large" class="general-description">
      <!-- 基本信息展示区域 -->
      <a-descriptions-item label="员工ID">{{ dataDetail?.userId }}</a-descriptions-item>
      <a-descriptions-item label="城市名称">{{ dataDetail?.cityName }}</a-descriptions-item>
      
      <!-- 公积金相关信息 -->
      <a-descriptions-item label="公积金基数">{{ dataDetail?.houseBase }}</a-descriptions-item>
      <a-descriptions-item label="公积金个人缴纳比例">{{ dataDetail?.perHouseRate }}</a-descriptions-item>
      <a-descriptions-item label="公积金个人缴纳费用">{{ dataDetail?.perHousePay }}</a-descriptions-item>
      <a-descriptions-item label="公积金企业缴纳比例">{{ dataDetail?.comHouseRate }}</a-descriptions-item>
      <a-descriptions-item label="公积金企业缴纳费用">{{ dataDetail?.comHousePay }}</a-descriptions-item>
      <a-descriptions-item label=""></a-descriptions-item>
      
      <!-- 社保相关信息 -->
      <a-descriptions-item label="社保基数">{{ dataDetail?.socialBase }}</a-descriptions-item>
      <a-descriptions-item label="社保企业缴纳费用">{{ dataDetail?.comSocialPay }}</a-descriptions-item>
      <a-descriptions-item label="社保个人缴纳费用">{{ dataDetail?.perSocialPay }}</a-descriptions-item>
      <a-descriptions-item label="工伤保险企业缴纳比例">{{ dataDetail?.comInjuryRate }}</a-descriptions-item>
      
      <!-- 系统信息 -->
      <a-descriptions-item label="创建用户">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="创建时间">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="更新用户">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="更新时间">{{ dataDetail?.updateTime }}</a-descriptions-item>
      
      <!-- 备注信息 -->
      <a-descriptions-item label="公积金备注">{{ dataDetail?.houseRemark }}</a-descriptions-item>
      <a-descriptions-item label="社保备注">{{ dataDetail?.socialRemark }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
// 导入所需的组件和接口
import { ref, reactive } from 'vue'
import { type InsuranceDetailResp, updateInsurance, getInsurance as getDetail } from '@/apis/system/insurance'

// 定义响应式数据
// 当前查看的数据ID
const dataId = ref('')
// 详情数据对象
const dataDetail = ref<InsuranceDetailResp>()
// 控制抽屉显示状态
const visible = ref(false)
// 表单数据对象，用于数据修改
const formData = reactive<Partial<InsuranceDetailResp>>({})

// 打开详情抽屉
const onOpen = async (id: string) => {
  // 设置当前数据ID
  dataId.value = id
  // 获取详情数据
  await getDataDetail()
  // 显示抽屉
  visible.value = true
}

// 获取详情数据
const getDataDetail = async () => {
  try {
    // 调用接口获取数据
    const { data } = await getDetail(dataId.value)
    if (data) {
      // 更新详情数据
      dataDetail.value = data
      // 同步表单数据
      Object.assign(formData, data)
    }
  } catch (error) {
    console.error('获取详情失败:', error)
  }
}

// 保存数据
const onSave = async () => {
  try {
    // 调用更新接口
    await updateInsurance(formData, formData.id as string)
    // 触发保存成功事件
    emit('save-success')
    // 关闭抽屉
    visible.value = false
  } catch (error) {
    console.error('保存失败:', error)
  }
}

// 关闭抽屉
const onCancel = () => {
  visible.value = false
}

// 定义组件事件
const emit = defineEmits(['save-success'])

// 暴露组件方法
defineExpose({ onOpen })
</script>

<style scoped lang="scss"></style>