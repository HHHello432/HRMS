<template>
  <a-button size="mini" class="gi_hover_btn" @click="handleToggleTheme">
    <template #icon>
      <icon-moon-fill v-if="appStore.theme === 'light'" :size="18" />
      <icon-sun-fill v-else :size="18" />
    </template>
  </a-button>
</template>

<script setup lang="ts">
import { useDark, useToggle } from '@vueuse/core'
import {ref, defineEmits} from 'vue';
import { useAppStore } from '@/stores'

defineOptions({ name: 'GiThemeBtn' })

const emit = defineEmits(['themeChanged'])
const appStore = useAppStore()
const isDarkTheme = ref(false);

const isDark = useDark({
  selector: 'body',
  attribute: 'arco-theme',
  valueDark: 'dark',
  valueLight: 'light',
  storageKey: 'arco-theme',
  onChanged(dark: boolean) {
    appStore.toggleTheme(dark)
  },
})

const toggleTheme = useToggle(isDark)

const handleToggleTheme = () => {
  toggleTheme()
  // eslint-disable-next-line vue/custom-event-name-casing
  emit('themeChanged', isDark.value)
}
</script>

<style lang="scss" scoped></style>

<style lang="scss">
:root {
  --color-login-text: #121314;
  --input-bg-color: #ffffff;
  --input-text-color: #000000;
  --remember-me-color: #000000;
  --login-bg: #ffffff;
}

[arco-theme="dark"] {
  --color-login-text: #ffffff;
  --input-bg-color: #1e1e1e;
  --input-text-color: #ffffff;
  --remember-me-color: #ffffff;
  --login-bg: #1e1e1e;
}
</style>