import { createApp } from 'vue'
import ArcoVue, { Card, Drawer, Modal } from '@arco-design/web-vue'
import '@/styles/arco-ui/index.less'
// import '@arco-themes/vue-gi-demo/index.less'
// import '@arco-design/web-vue/dist/arco.css'

// 额外引入 Arco Design Icon图标库
import ArcoVueIcon from '@arco-design/web-vue/es/icon'
import * as Icons from '@ant-design/icons-vue'
import App from './App.vue'
import store from './stores'
import router from './router'

import '@/router/permission'

// 使用动画库
import 'animate.css/animate.min.css'

// 自定义过渡动画
import '@/styles/css/transition.css'

// 导入全局scss主文件
import '@/styles/index.scss'

// 支持SVG
import 'virtual:svg-icons-register'

// 自定义指令
import directives from './directives'
import pinia from '@/stores'

// 对特定组件进行默认配置
Card.props.bordered = false

const app = createApp(App)
Modal._context = app._context
Drawer._context = app._context
app.use(store).use(router).use(ArcoVue)

// 全局使用图标，遍历引入
const icons: any = Icons
for (const i in icons) {
  app.component(i, icons[i])
}

app.use(router)
app.use(pinia)
app.use(ArcoVue)
app.use(ArcoVueIcon)
app.use(directives)

app.mount('#app')
