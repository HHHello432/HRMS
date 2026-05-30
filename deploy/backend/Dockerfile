# HRMS 后端 - 基于 Eclipse Temurin JDK 17 (Alpine, 最小化)
FROM eclipse-temurin:17-jre-alpine

# 安装时区工具并设置上海时区
RUN apk add --no-cache tzdata curl \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata

# 创建非 root 运行用户
RUN addgroup -S hrms && adduser -S hrms -G hrms

WORKDIR /app

# 复制编译好的二进制和配置（context = /root/projects/HRMS）
COPY --chown=hrms:hrms backend/bin/ ./bin/
COPY --chown=hrms:hrms backend/lib/ ./lib/
COPY --chown=hrms:hrms backend/config/ ./config/

# 日志目录（日志配置写 ../logs/ 即 /logs）+ lib引用修复
RUN mkdir -p /logs /app/logs && chown -R hrms /logs /app && ln -sf /app/lib /lib

USER hrms

# 🚀 JVM 内存优化关键参数
# -Xmx256m: 最大堆 256MB（默认可能吃 1GB+，此处压缩 75%+）
# -Xms128m: 初始堆 128MB，避免启动时动态扩缩
# -XX:+UseG1GC: G1 垃圾收集器，平衡吞吐和延迟
# -XX:MaxGCPauseMillis=200: 最大 GC 暂停 200ms
# -XX:+UseStringDeduplication: 自动去重重复字符串
# -XX:+DisableExplicitGC: 禁止 System.gc()
# -Dspring.profiles.active=prod: 激活生产配置
# -Dlogging.level.root=WARN: 减少日志 IO 和内存开销
# -Dlogging.level.top.continew=WARN: 降低业务日志级别
# -Dserver.undertow.threads.worker=32: 原配置 256 -> 32，大幅减少线程栈内存
# 添加懒加载：只在高阶函数被调用时才初始化 Bean，减少启动时间
ENV JAVA_OPTS="-Xmx384m -Xms192m \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UseStringDeduplication \
  -XX:+DisableExplicitGC \
  -Dspring.profiles.active=prod \
  -Dlogging.level.root=WARN \
  -Dlogging.level.top.continew=WARN \
  -Dserver.undertow.threads.worker=32 \
  -Dloader.path=../lib/ \
  -Dspring.liquibase.enabled=false \
  -Dspring.main.lazy-initialization=true \
  -Dcontinew-starter.log.is-print=false \
  -Dsnail-job.enabled=false \
"


EXPOSE 18000

# 健康检查
HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
  CMD curl -sf http://localhost:18000/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar bin/continew-admin.jar"]
