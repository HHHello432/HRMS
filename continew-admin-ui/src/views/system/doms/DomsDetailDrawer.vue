<template>
  <a-drawer v-model:visible="visible" title="宿舍人员管理详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="员工ID">{{ dataDetail?.userId }}</a-descriptions-item>
      <a-descriptions-item label="员工姓名">{{ dataDetail?.nickname }}</a-descriptions-item>
      <a-descriptions-item label="性别">{{ dataDetail?.gender }}</a-descriptions-item>
      <a-descriptions-item label="宿舍ID">{{ dataDetail?.domId }}</a-descriptions-item>
      <a-descriptions-item label="手机号码">{{ dataDetail?.phone }}</a-descriptions-item>
      <a-descriptions-item label="入住日期">{{ dataDetail?.date }}</a-descriptions-item>
      <a-descriptions-item label="住宿情况">{{ getStatusLabel(dataDetail?.status) }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type DomDetailResp, getDom as getDetail } from '@/apis/system/doms'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<DomDetailResp>()
const visible = ref(false)

const statusMap = {
  0: '未入住',
  1: '已入住',
  2: '已退宿'
}

// 获取住宿情况标签
const getStatusLabel = (status: number | undefined): string => {
  return status !== undefined ? statusMap[status] ?? '未知状态' : '未知状态'
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