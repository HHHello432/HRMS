<template>
  <a-drawer v-model:visible="visible" title="员工考勤详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="员工ID">{{ dataDetail?.userId }}</a-descriptions-item>
      <a-descriptions-item label="员工姓名">{{ dataDetail?.nickname }}</a-descriptions-item>
      <a-descriptions-item label="日期">{{ dataDetail?.date }}</a-descriptions-item>
      <a-descriptions-item label="状态">{{ statusMap[dataDetail?.status ?? ''] }}</a-descriptions-item>
      <a-descriptions-item label="电话">{{ dataDetail?.phone }}</a-descriptions-item>
      <a-descriptions-item label=""></a-descriptions-item>
      <a-descriptions-item label="创建用户">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="创建时间">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="更新用户">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="更新时间">{{ dataDetail?.updateTime }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type AttendanceDetailResp, getAttendance as getDetail } from '@/apis/system/attendance'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<AttendanceDetailResp>()
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

const statusMap = {
  '1': '迟到',
  '2': '早退',
  '3': '旷工',
  '4': '请假'
}

defineExpose({ onOpen })
</script>

<style scoped lang="scss"></style>
