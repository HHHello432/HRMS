# HRMS 前端构建
FROM node:22-alpine AS builder

WORKDIR /app

# 复制依赖文件
COPY package.json package-lock.json pnpm-lock.yaml ./

# 安装依赖（使用 pnpm）
RUN npm install -g pnpm && \
    pnpm config set script-shell /bin/sh && \
    pnpm approve-builds @vue-office/docx @vue-office/excel @vue-office/pdf es5-ext esbuild vue-demi vue-echarts 2>/dev/null; \
    pnpm install --frozen-lockfile --prefer-offline 2>&1 | grep -v "WARN\|ERR_PNPM_IGNORED_BUILDS\|approve-builds"

# 复制源代码
COPY . .

# 构建生产版本（跳过 frozen 校验，approve-builds 已在 layer1 处理）
RUN pnpm approve-builds @vue-office/docx @vue-office/excel @vue-office/pdf es5-ext esbuild vue-demi vue-echarts 2>/dev/null; \
    pnpm build 2>&1

# ========== 运行阶段 ==========
FROM nginx:1.25-alpine

# 设置时区
RUN apk add --no-cache tzdata \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

# 移除默认配置
RUN rm -f /etc/nginx/conf.d/default.conf

# 复制构建产物
COPY --from=builder /app/dist /usr/share/nginx/html

# 复制 Nginx 配置（API 代理到后端）
COPY deploy/nginx/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget -qO- http://localhost:80/ || exit 1

CMD ["nginx", "-g", "daemon off;"]
