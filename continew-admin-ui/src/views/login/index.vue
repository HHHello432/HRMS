<template>
  <div class="login" :class="[isDesktop ? 'pc' : 'h5']">
    <div class="login-container">
      <div class="login-logo">
        <img v-if="logo" :src="logo" alt="logo" />
        <img v-else src="/logo.svg" alt="logo" />
        <span style="color: var(--color-login-text);">{{ title }}</span>
      </div>
      <a-row align="stretch" class="login-box">
        <div class="login-right">
          <a-tabs class="login-right__form">
            <a-tab-pane key="1" title="账号登录">
              <AccountLogin />
            </a-tab-pane>
          </a-tabs>
        </div>
      </a-row>
      <div v-if="isDesktop" class="footer">
        <div class="beian">
          <div class="below text">
            {{ appStore.getCopyright() }}{{ appStore.getForRecord() ? ` ·
            ${appStore.getForRecord()}` : '' }}
          </div>
        </div>
      </div>
    </div>
    <GiThemeBtn class="theme-btn" @themeChanged="handleThemeChange" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import AccountLogin from './components/account/index.vue'
import { useAppStore } from '@/stores'
import { useDevice } from '@/hooks'


const appStore = useAppStore()
const { isDesktop } = useDevice()
const title = computed(() => appStore.getTitle())
const logo = computed(() => appStore.getLogo())

const handleThemeChange = (isDark: boolean) => {
  // GiThemeBtn 已通过 useDark + toggleTheme 处理 body 的 arco-theme 属性
}
</script>

<style scoped lang="scss">
.login {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: var(--color-login-text);

  .login-container input,
  .login-container textarea,
  .login-container select{
    background-color: var(--input-bg-color,#ffffff);
    color: var(--color-login-text);
  }

  &-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 35px;
    border-radius: 16px;
    box-shadow: 0 15px 50px rgba(0, 0, 0, 0.3);
    max-width: 400px; /* 可以根据需要调整最大宽度 */
    width: 100%;
    margin: 0 auto; /* 居中对齐 */
    background-color: var(--login-bg, #ffffff);
    color: var(--color-login-text, #333);
    transition: background-color 0.3s, color 0.3s;
    min-width: 300px;
  }

  &-logo {
    width: 100%;
    font-weight: 700;
    font-size: 22px;
    line-height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 24px;
    color: #333;

    img {
      width: 38px;
      height: 38px;
      margin-right: 10px;
      border-radius: 8px;
    }
  }

  &-box {
    width: 100%;
    max-width: 800px;
    display: flex;
    z-index: 999;
    box-shadow: 0 2px 4px 2px rgba(0, 0, 0, 0.08);
  }

  &-right {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 30px;
    box-sizing: border-box;

    &__form {
      // 样式调整
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      box-sizing: border-box;
    }
  }
}

.pc {
  // 桌面端特有样式
}

.h5 {

  // 移动端特有样式
  .login-logo {
    // 移动端 logo 样式调整
  }
}

.theme-btn {
  position: fixed;
  top: 20px;
  right: 30px;
  z-index: 9999;
}

.footer {
  align-items: center;
  box-sizing: border-box;
  position: absolute;
  bottom: 10px;
  z-index: 999;

  .beian {
    .text {
      font-size: 12px;
      font-weight: 400;
      letter-spacing: 0.2px;
      line-height: 20px;
      text-align: center;
      color: var(--color-login-text);
    }
  }
}
</style>
