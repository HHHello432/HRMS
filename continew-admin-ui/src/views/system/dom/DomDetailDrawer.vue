<template>
  <a-drawer v-model:visible="visible" title="宿舍管理详情" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="宿舍ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="描述">{{ dataDetail?.description }}</a-descriptions-item>
      <a-descriptions-item label="入住人数">{{ dataDetail?.staffList.length }}</a-descriptions-item>
      <a-descriptions-item label="创建用户">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="创建时间">{{ dataDetail?.createTime }}</a-descriptions-item>
    </a-descriptions>
    <!-- <StaffTable :staffList="dataDetail?.staffList || []" /> -->
    <!-- 添加调试信息 -->
    <!-- <pre>{{ JSON.stringify(dataDetail, null, 2) }}</pre> -->
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { ref } from 'vue'
// import { type DomDetailResp } from '@/apis/system/dom'
import StaffTable from './StaffTable.vue'

const { width } = useWindowSize()

const dataDetail = ref<DomDetailResp | null>(null)
const visible = ref(false)

interface DomDetailResp {
  id: number;
  description: string;
  staffList: Staff[];
}

interface Staff {
  id: number | null;
  createUserString: string | null;
  createTime: string | null;
  userId: number;
  username: string;
  nickname: string;
  gender: number;
  phone: string;
  domId: number;
  date: string;
  status: number;
}

// 打开
const onOpen = (detail: DomDetailResp) => {
  dataDetail.value = detail
  visible.value = true
  console.log('dataDetail:', dataDetail.value) // 添加调试信息
  console.log('staffList:', dataDetail.value?.staffList) // 添加调试信息
}

defineExpose({ onOpen })
</script>