<template>
  <a-spin :loading="loading" style="width: 100%">
    <a-card
      class="general-card"
      :style="{
        background: isDark
          ? 'linear-gradient(180deg, #312565 0%, #201936 100%)'
          : 'linear-gradient(180deg, #F7F7FF 0%, #ECECFF 100%)',
      }"
    >
      <div class="content-wrap">
        <div class="content">
          <a-statistic
            title="员工人数统计"
            :value="totalCount"
            :value-from="0"
            animation
            show-group-separator
          />
          <div class="desc">
            <a-typography-text type="secondary" class="label">总人数</a-typography-text>
            <a-typography-text type="success">
              {{ totalCount }}
              <icon-arrow-rise />
            </a-typography-text>
          </div>
        </div>
        <div class="chart">
          <Chart v-if="!loading" :option="chartOption" />
        </div>
      </div>
    </a-card>
  </a-spin>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, defineAsyncComponent } from 'vue'
import { useChart } from '@/hooks'
import { useAppStore } from '@/stores'
import { listUser } from '@/apis/system/user' // 引入用户列表接口

const Chart = defineAsyncComponent(() => import('@/components/Chart/index.vue'))
const appStore = useAppStore()
const isDark = computed(() => appStore.theme === 'dark')

const loading = ref(false)
const chartData = ref<{ name: string; value: number; itemStyle: { color: string } }[]>([])
const totalCount = computed(() => chartData.value.reduce((acc, curr) => acc + curr.value, 0))

// 部门ID配置
const DEPT_IDS = {
  GD: '547887852587843590', // 广东总部ID
  FJ: '657956641349619748'  // 福建总部ID - 请替换为实际ID
}

const colors = ['#8D4EDA', '#00B2FF', '#86DF6C']

const { chartOption } = useChart(() => {
  return {
    grid: {
      left: 0,
      right: 0,
      top: 0,
      bottom: 0,
    },
    legend: {
      show: true,
      top: 'center',
      right: '20%',
      orient: 'vertical',
      icon: 'circle',
      itemWidth: 6,
      itemHeight: 6,
      textStyle: {
        color: '#4E5969',
      },
    },
    tooltip: {
      show: true,
      formatter: '{b}: {c}人 ({d}%)'
    },
    series: [
      {
        name: '部门人数',
        type: 'pie',
        radius: ['50%', '70%'],
        center: ['30%', '50%'],
        label: {
          show: false,
        },
        data: chartData.value,
      },
    ],
  }
})

// 查询部门人数
const getDeptUserCount = async (deptId: string) => {
  try {
    const response = await listUser({
      deptId,
      page: 1,
      size: 1
    })
    return response.data.total || 0
  } catch (error) {
    console.error(`获取部门 ${deptId} 人数失败:`, error)
    return 0
  }
}

// 获取图表数据
const getChartData = async () => {
  try {
    loading.value = true
    
    // 并行获取两个部门的人数
    const [gdCount, fjCount] = await Promise.all([
      getDeptUserCount(DEPT_IDS.GD),
      getDeptUserCount(DEPT_IDS.FJ)
    ])

    // 更新图表数据
    chartData.value = [
      {
        name: '广东总部',
        value: gdCount,
        itemStyle: { color: colors[0] }
      },
      {
        name: '福建总部',
        value: fjCount,
        itemStyle: { color: colors[1] }
      }
    ]
  } catch (error) {
    console.error('获取数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getChartData()
})
</script>

<style scoped lang="less">
:deep(.arco-card) {
  border-radius: 4px;
}
:deep(.arco-card-body) {
  width: 100%;
  height: 134px;
  padding: 0;
}
.content-wrap {
  width: 100%;
  padding: 16px;
  white-space: nowrap;
}
:deep(.content) {
  float: left;
  width: 108px;
  height: 102px;
}
:deep(.arco-statistic) {
  .arco-statistic-title {
    font-size: 16px;
    font-weight: bold;
    white-space: nowrap;
  }
  .arco-statistic-content {
    margin-top: 10px;
  }
}

.chart {
  float: right;
  width: calc(100% - 108px);
  height: 90px;
  vertical-align: bottom;
}

.label {
  padding-right: 8px;
  font-size: 12px;
}
</style>