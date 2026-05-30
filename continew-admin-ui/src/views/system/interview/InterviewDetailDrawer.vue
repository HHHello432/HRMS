<template>
  <a-drawer v-model:visible="visible" title="面试记录详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="关联应聘者">{{ dataDetail?.candidateId }}</a-descriptions-item>
      <a-descriptions-item label="面试轮次">{{ dataDetail?.round }}</a-descriptions-item>
      <a-descriptions-item label="面试官">{{ dataDetail?.interviewer }}</a-descriptions-item>
      <a-descriptions-item label="面试时间">{{ dataDetail?.interviewTime }}</a-descriptions-item>
      <a-descriptions-item label="面试地点">{{ dataDetail?.address }}</a-descriptions-item>
      <a-descriptions-item label="面试内容">{{ dataDetail?.content }}</a-descriptions-item>
      <a-descriptions-item label="面试评价">{{ dataDetail?.evaluation }}</a-descriptions-item>
      <a-descriptions-item label="面试结果(1:通过 2:待定 3:不通过)">{{ dataDetail?.result }}</a-descriptions-item>
      <a-descriptions-item label="下一轮面试时间">{{ dataDetail?.nextRoundTime }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.updateTime }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type InterviewDetailResp, getInterview as getDetail } from '@/apis/system/interview'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<InterviewDetailResp>()
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
