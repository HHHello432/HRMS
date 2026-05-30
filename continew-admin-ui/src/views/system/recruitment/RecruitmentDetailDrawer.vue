<template>
  <a-drawer v-model:visible="visible" title="招聘计划详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="职位名称">{{ dataDetail?.title }}</a-descriptions-item>
      <a-descriptions-item label="招聘部门">{{ dataDetail?.department }}</a-descriptions-item>
      <a-descriptions-item label="职位类别(全职/兼职/实习)">{{ dataDetail?.positionType }}</a-descriptions-item>
      <a-descriptions-item label="招聘人数">{{ dataDetail?.headcount }}</a-descriptions-item>
      <a-descriptions-item label="职位描述">{{ dataDetail?.description }}</a-descriptions-item>
      <a-descriptions-item label="任职要求">{{ dataDetail?.requirement }}</a-descriptions-item>
      <a-descriptions-item label="最低薪资">{{ dataDetail?.salaryMin }}</a-descriptions-item>
      <a-descriptions-item label="最高薪资">{{ dataDetail?.salaryMax }}</a-descriptions-item>
      <a-descriptions-item label="工作地点">{{ dataDetail?.address }}</a-descriptions-item>
      <a-descriptions-item label="状态(0:关闭 1:发布中)">{{ dataDetail?.status }}</a-descriptions-item>
      <a-descriptions-item label="截止日期">{{ dataDetail?.deadline }}</a-descriptions-item>
      <a-descriptions-item label="排序">{{ dataDetail?.sort }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.createUser }}</a-descriptions-item>
      <a-descriptions-item label="创建人">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.updateUser }}</a-descriptions-item>
      <a-descriptions-item label="修改人">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="">{{ dataDetail?.updateTime }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type RecruitmentDetailResp, getRecruitment as getDetail } from '@/apis/system/recruitment'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<RecruitmentDetailResp>()
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
