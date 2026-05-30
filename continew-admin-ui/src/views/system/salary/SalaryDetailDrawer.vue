<template>
  <!-- 工资详情抽屉 -->
  <a-drawer v-model:visible="visible" title="员工工资详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <!-- 详情信息展示区 -->
    <a-descriptions :column="2" size="large" class="general-description">
      <!-- 基本信息 -->
      <a-descriptions-item label="员工ID">{{ dataDetail?.userId }}</a-descriptions-item>
      <a-descriptions-item label="基础工资">{{ dataDetail?.baseSalary }}</a-descriptions-item>
      
      <!-- 薪资组成部分 -->
      <a-descriptions-item label="补贴">{{ dataDetail?.subsidy }}</a-descriptions-item>
      <a-descriptions-item label="绩效">{{ dataDetail?.performance }}</a-descriptions-item>
      <a-descriptions-item label="加班费">{{ dataDetail?.overtimeSalary }}</a-descriptions-item>
      <a-descriptions-item label="奖金">{{ dataDetail?.bonus }}</a-descriptions-item>
      <a-descriptions-item label="总工资">{{ dataDetail?.totalSalary }}</a-descriptions-item>
      <a-descriptions-item label=""></a-descriptions-item>
      <a-descriptions-item label="迟退扣款">{{ dataDetail?.lateDeduct }}</a-descriptions-item>
      <a-descriptions-item label="请假扣款">{{ dataDetail?.leaveDeduct }}</a-descriptions-item>
      <a-descriptions-item label="早退扣款">{{ dataDetail?.leaveEarlyDeduct }}</a-descriptions-item>
      <a-descriptions-item label="旷工扣款">{{ dataDetail?.absenteeismDeduct }}</a-descriptions-item>
      <a-descriptions-item label="公积金缴纳">{{ dataDetail?.perHousePay }}</a-descriptions-item>
      <a-descriptions-item label="社保缴纳">{{ dataDetail?.perSocialPay }}</a-descriptions-item>
      <a-descriptions-item label="月份">{{ dataDetail?.month }}</a-descriptions-item>
     
      <a-descriptions-item label=""></a-descriptions-item>
      <a-descriptions-item label="创建用户">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="创建时间">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="更新用户">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="更新时间">{{ dataDetail?.updateTime }}</a-descriptions-item>
      <a-descriptions-item label="备注">{{ dataDetail?.remark }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type SalaryDetailResp, getSalary as getDetail } from '@/apis/system/salary'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<SalaryDetailResp>()
const visible = ref(false)

// 查询详情
const getDataDetail = async () => {
  const { data } = await getDetail(dataId.value)
  dataDetail.value = data
}

// 打开
const onOpen = async (id: string) => {
  dataId.value = id
  await getDataDetail()
  visible.value = true
}

defineExpose({ onOpen })
</script>

<style scoped lang="scss"></style>
