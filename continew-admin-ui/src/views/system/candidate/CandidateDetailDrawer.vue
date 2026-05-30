<template>
  <a-drawer v-model:visible="visible" title="应聘者详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="关联招聘计划">{{ dataDetail?.recruitmentId }}</a-descriptions-item>
      <a-descriptions-item label="姓名">{{ dataDetail?.name }}</a-descriptions-item>
      <a-descriptions-item label="性别(0:未知 1:男 2:女)">{{ dataDetail?.gender }}</a-descriptions-item>
      <a-descriptions-item label="手机号">{{ dataDetail?.phone }}</a-descriptions-item>
      <a-descriptions-item label="邮箱">{{ dataDetail?.email }}</a-descriptions-item>
      <a-descriptions-item label="学历">{{ dataDetail?.education }}</a-descriptions-item>
      <a-descriptions-item label="毕业院校">{{ dataDetail?.school }}</a-descriptions-item>
      <a-descriptions-item label="专业">{{ dataDetail?.major }}</a-descriptions-item>
      <a-descriptions-item label="工作经历">{{ dataDetail?.experience }}</a-descriptions-item>
      <a-descriptions-item label="简历附件">{{ dataDetail?.resumeUrl }}</a-descriptions-item>
      <a-descriptions-item label="状态(1:待筛选 2:初筛通过 3:面试中 4:已录用 5:不通过 6:已放弃)">{{ dataDetail?.status }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.updateTime }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type CandidateDetailResp, getCandidate as getDetail } from '@/apis/system/candidate'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<CandidateDetailResp>()
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
