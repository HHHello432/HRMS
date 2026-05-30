<template>
  <a-drawer v-model:visible="visible" title="事务申请详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="用户名">{{ dataDetail?.nickname }}</a-descriptions-item>
      <a-descriptions-item label="事务类型">{{ statusMap[dataDetail?.typeNum ?? ''] }}</a-descriptions-item>
      <a-descriptions-item label="事务内容">{{ dataDetail?.remark1 }}</a-descriptions-item>
      <a-descriptions-item label="提交事务时间">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="审批用户">{{ dataDetail?.approvalnickname }}</a-descriptions-item>
      <a-descriptions-item label="审批时间">{{ dataDetail?.approvalTime }}</a-descriptions-item>
      <a-descriptions-item label="审核状态">{{ dataDetail?.status }}</a-descriptions-item>
      <a-descriptions-item label="审批内容">{{ dataDetail?.remark2 }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type ApprovalDetailResp, getApproval as getDetail } from '@/apis/system/approval'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<ApprovalDetailResp>()
const visible = ref(false)

const statusMap = {
  '1': '职务升降',
  '2': '人员变动',
  '3': '出差报销',
  '4': '其他'
}

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
