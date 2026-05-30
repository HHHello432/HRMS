<template>
  <a-card class="card" :bordered="false">
    <a-row align="center" wrap :gutter="[{ xs: 0, sm: 14, md: 14, lg: 14, xl: 14, xxl: 14 }, 16]" class="content">
      <a-space size="medium">
        <Avatar :src="userStore.avatar" :name="userStore.nickname" :size="68" />
        <div class="welcome">
          <p class="hello">{{ goodTimeText() }}！{{ userStore.nickname }}</p>
          <p>人力资源管理系统</p>
        </div>
        <h3 class="current_time">当前时间为：{{ currentTime }}</h3>
      </a-space>
    </a-row>
  </a-card>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { useUserStore } from '@/stores'
import { goodTimeText } from '@/utils'

const userStore = useUserStore()

// 定义一个响应式变量来存储当前时间
const currentTime = ref(new Date().toLocaleTimeString())

// 定义一个定时器来更新当前时间
let timer: number

onMounted(() => {
  timer = setInterval(() => {
    currentTime.value = new Date().toLocaleString()
  }, 1000)
})

onBeforeUnmount(() => {
  clearInterval(timer)
})
</script>

<style scoped lang="scss">
:deep(.arco-statistic-title) {
  margin-bottom: 0;
}

.card {
  .content {
    padding: 8px 20px;

    .welcome {
      margin: 8px 0;
      color: $color-text-3;

      .hello {
        font-size: 1.25rem;
        color: $color-text-1;
        margin-bottom: 10px;
      }
    }
  }
}

.current_time {
  font-size: 1.190rem; // 增大字体大小
  color: $color-text-2; // 调整文字颜色
  margin-top: 10px; // 添加上边距
  font-weight: 500; // 增加字体粗细
  text-align: center; // 居中对齐
  margin-left: 50px;
  margin-bottom: 37px;
}

.a-space {
  display: flex;
  align-items: center; // 垂直居中对齐
  justify-content: space-between; // 水平分布
  width: 100%; // 占满整个宽度
}
</style>
