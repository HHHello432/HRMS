<template>
  <a-card :class="['gradient-card', darkClass]" title="统计数据" bordered>
    <div v-for="(stat, index) in statsList" :key="index" class="stat-item" :class="darkClass">
      <div class="info">
        <div class="info-top">
          <span class="label">{{ stat.label }}</span>
          <span class="value">{{ stat.value }}</span>
        </div>
        <div class="info-desc">
          {{ stat.description }}
        </div>
      </div>
    </div>
  </a-card>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { Message } from '@arco-design/web-vue';
import { useDark } from '@vueuse/core';
import { useAppStore } from '@/stores';
import { listUserSocial, socialAuth, unbindSocialAccount } from '@/apis';

const appStore = useAppStore();
const isDark = useDark({
  selector: 'body',
  attribute: 'arco-theme',
  valueDark: 'dark',
  valueLight: 'light',
  storageKey: 'arco-theme',
});

const darkClass = computed(() => ({
  dark: isDark.value || appStore.theme === 'dark',
}));

interface StatItem {
  label: string;
  value: string | number;
  description: string;
}

const statsList = ref<StatItem[]>([
  {
    label: '完成项目数',
    value: '5项',
    description: ''
  },
  {
    label: '参与会议次数',
    value: '10次',
    description: ''
  },
  {
    label: '提交文档数',
    value: '3份',
    description: ''
  },
  {
    label: '总工时',
    value: '150 时',
    description: ''
  },
  {
    label: '平均评分',
    value: '8.0/10',
    description: ''
  },
]);

// 初始化数据
const initData = () => {
  listUserSocial().then((res) => {
    const socialSources = res.data.map((el) => el.source);
    statsList.value = [
      {
        label: '完成项目数',
        value: '5项',
        description: ''
      },
      {
        label: '参与会议次数',
        value: '10次',
        description: ''
      },
      {
        label: '提交文档数',
        value: '3份',
        description: ''
      },
      {
        label: '总工时',
        value: '150 时',
        description: ''
      },
      {
        label: '平均评分',
        value: '8.0/10',
        description: ''
      },
    ];
  });
};

// 绑定
const onBinding = (type: string, status: boolean) => {
  if (!status) {
    socialAuth(type).then((res) => {
      window.open(res.data.authorizeUrl, '_self');
    });
  } else {
    unbindSocialAccount(type).then(() => {
      initData();
      Message.success('解绑成功');
    });
  }
};

const verifyModelRef = ref<InstanceType<typeof VerifyModel>>();

// 修改
const onUpdate = (type: string) => {
  verifyModelRef.value?.open(type);
};

onMounted(() => {
  initData();
});
</script>

<style scoped lang="scss">
.gradient-card {
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  background: linear-gradient(135deg, var(--input-bg-color), var(--input-bg-color));
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;

  &.dark {
    background: linear-gradient(135deg, #1d1d1d, #1a1a1a);
  }
}

.gradient-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.stat-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 8px;
  padding: 8px;
  border-radius: 4px;
  background-color: var(--input-bg-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.3s ease-in-out;

  &.dark {
    background-color: #333333;
  }
}

.stat-item:hover {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
}

.info {
  flex: 1;
}

.info-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 3px;
}

.label {
  font-size: 14px;
  font-weight: bold;
  color: var(--color-login-text);
  color: #4b93ff;
}

.value {
  font-size: 14px;
  color: #0066ff;
}

.info-desc {
  font-size: 10px;
  color: var(--input-text-color);
}
</style>