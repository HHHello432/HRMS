<template>
  <a-drawer v-model:visible="visible" title="参保城市详情" :width="width >= 600 ? 600 : '100%'" :footer="false" style="font-size: 100px;">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="参保城市ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="参保城市">{{ dataDetail?.cityName }}</a-descriptions-item>
      <a-descriptions-item label="职工社保缴纳基数上限">{{ dataDetail?.socUpperLimit }}</a-descriptions-item>
      <a-descriptions-item label="公积金缴纳基数上限">{{ dataDetail?.houUpperLimit }}</a-descriptions-item>
      <a-descriptions-item label="职工社保缴纳基数下限">{{ dataDetail?.socLowerLimit }}</a-descriptions-item>
      <a-descriptions-item label="公积金缴纳基数下限">{{ dataDetail?.houLowerLimit }}</a-descriptions-item>
      <a-descriptions-item label="个人养老保险缴费比例">{{ dataDetail?.perPensionRate }}</a-descriptions-item>
      <a-descriptions-item label="企业养老保险缴费比例">{{ dataDetail?.comPensionRate }}</a-descriptions-item>
      <a-descriptions-item label="个人医疗保险缴费比例">{{ dataDetail?.perMedicalRate }}</a-descriptions-item>
      <a-descriptions-item label="企业医疗保险缴费比例">{{ dataDetail?.comMedicalRate }}</a-descriptions-item>
      <a-descriptions-item label="个人失业保险缴费比例">{{ dataDetail?.perUnemploymentRate }}</a-descriptions-item>
      <a-descriptions-item label="企业失业保险缴费比例">{{ dataDetail?.comUnemploymentRate }}</a-descriptions-item>
      <a-descriptions-item label="企业生育保险缴费比例">{{ dataDetail?.comMaternityRate }}</a-descriptions-item>
      <a-descriptions-item label=""></a-descriptions-item>
      <a-descriptions-item label="创建时间">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="更新时间">{{ dataDetail?.updateTime }}</a-descriptions-item>
      <a-descriptions-item label="创建用户">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="更新用户">{{ dataDetail?.updateUser }}</a-descriptions-item>
      <a-descriptions-item label="修改用户">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="备注">{{ dataDetail?.remark }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type CityDetailResp, getCity as getDetail } from '@/apis/system/city'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<CityDetailResp>()
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

<style scoped lang="scss">
</style>
