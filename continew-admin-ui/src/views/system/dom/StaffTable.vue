<template>
  <div>
    <h3>员工信息</h3>
    <a-table :columns="columns" :data-source="staffList || []" :pagination="false" :bordered="true">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          {{ record.status === 0 ? '未入住' : record.status === 1 ? '已入住' : '已退宿' }}
        </template>
        <template v-else-if="column.key === 'gender'">
          {{ record.gender === 1 ? '男' : '女' }}
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { defineProps, ref, onMounted } from 'vue'
import { listDoms } from '@/apis/system/dom'

interface Staff {
  id: number | null;
  createUser: number | null;
  createTime: string | null;
  updateUser: number | null;
  updateTime: string | null;
  domId: number;
  userId: number;
  username: string;
  nickname: string;
  gender: number;
  phone: string;
  date: string;
  status: number;
}

interface PageRes<T> {
  code: string;
  msg: string;
  success: boolean;
  timestamp: number;
  data: {
    list: T[];
    total: number;
  };
}

const staffList = ref<Staff[]>([])

const fetchStaffList = async () => {
  try {
    const response = await listDoms({}) // 根据实际情况传递查询参数
    if (response.data && response.data.list) {
      staffList.value = response.data.list.map(item => ({
        id: item.id,
        createUser: null,
        createTime: null,
        updateUser: null,
        updateTime: null,
        domId: item.domId,
        userId: item.userId,
        username: item.username,
        nickname: item.nickname,
        gender: item.gender,
        phone: item.phone,
        date: item.date,
        status: item.status
      }))
    }
  } catch (error) {
    console.error('Error fetching staff list:', error)
  }
}

onMounted(() => {
  fetchStaffList()
})

const columns = [
  { title: '员工ID', dataIndex: 'userId', key: 'userId' },
  { title: '用户名', dataIndex: 'username', key: 'username' },
  { title: '昵称', dataIndex: 'nickname', key: 'nickname' },
  { title: '性别', dataIndex: 'gender', key: 'gender' },
  { title: '电话', dataIndex: 'phone', key: 'phone' },
  { title: '入住日期', dataIndex: 'date', key: 'date' },
  { title: '状态', dataIndex: 'status', key: 'status' }
]
</script>