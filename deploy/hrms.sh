#!/bin/bash
# HRMS 人力资源管理系统 — 一键管理脚本
# 用法: ./hrms.sh {start|stop|restart|status|logs|rebuild}
#
# 系统架构:
#   hrms-server    — Spring Boot 3 后端 (256MB 内存, 0.5核)
#   hrms-redis     — Redis 7 缓存 (64MB 内存, 0.25核)
#   hrms-ui        — Nginx 前端 (32MB 内存, 0.25核)
#
# 端口: 39000 (前端UI)
# 数据库: 复用 docker-project_app-net 网络下的 mysql8 容器
# 数据卷: hrms-redis-data, hrms-logs

set -e

# ======================== 配置 ========================
COMPOSE_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT="hrms"

# ======================== 颜色 ========================
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

info()  { echo -e "${BLUE}[INFO]${NC} $1"; }
ok()    { echo -e "${GREEN}[OK]${NC}   $1"; }
warn()  { echo -e "${YELLOW}[WARN]${NC} $1"; }
err()   { echo -e "${RED}[ERR]${NC}  $1"; }

# ======================== 函数 ========================

cmd_status() {
    echo ""
    info "═══════════════════════════════════════"
    info "  HRMS 系统状态"
    info "═══════════════════════════════════════"
    cd "$COMPOSE_DIR"
    docker compose -p "$PROJECT" ps
    echo ""
    info "内存占用:"
    docker stats --no-stream --format "table {{.Name}}\t{{.MemUsage}}\t{{.MemPerc}}\t{{.CPUPerc}}" \
      hrms-server hrms-redis hrms-ui 2>/dev/null || echo "  (某些容器未运行)"
}

cmd_start() {
    info "启动 HRMS 系统..."
    cd "$COMPOSE_DIR"
    docker compose -p "$PROJECT" up -d 2>&1
    ok "HRMS 启动命令已发送"
    info "等待后端就绪..."
    for i in $(seq 1 30); do
        HEALTH=$(docker inspect --format='{{.State.Health.Status}}' hrms-server 2>/dev/null)
        if [ "$HEALTH" = "healthy" ]; then
            ok "后端启动完成！"
            break
        fi
        sleep 2
    done
    if [ "$HEALTH" != "healthy" ]; then
        warn "后端尚未就绪，请稍后用 status 命令检查"
    fi
    info "前端访问: http://localhost:39000"
}

cmd_stop() {
    info "停止 HRMS 系统..."
    cd "$COMPOSE_DIR"
    docker compose -p "$PROJECT" stop
    ok "HRMS 已停止"
}

cmd_restart() {
    info "重启 HRMS 系统..."
    cmd_stop
    sleep 2
    cmd_start
}

cmd_logs() {
    local service="${1:-}"
    cd "$COMPOSE_DIR"
    if [ -n "$service" ]; then
        docker compose -p "$PROJECT" logs -f "$service"
    else
        docker compose -p "$PROJECT" logs -f
    fi
}

cmd_rebuild() {
    info "重新构建后端镜像..."
    cd "$COMPOSE_DIR"
    docker compose -p "$PROJECT" build server 2>&1 | tail -5
    info "重新创建容器..."
    docker compose -p "$PROJECT" up -d --force-recreate server 2>&1
    ok "重建完成"
}

# ======================== Main ========================

case "${1:-status}" in
    start)
        cmd_start
        ;;
    stop)
        cmd_stop
        ;;
    restart)
        cmd_restart
        ;;
    status)
        cmd_status
        ;;
    logs)
        cmd_logs "$2"
        ;;
    rebuild)
        cmd_rebuild
        ;;
    *)
        echo "用法: $0 {start|stop|restart|status|logs [service]|rebuild}"
        echo ""
        echo "  服务列表: server, redis, ui"
        exit 1
        ;;
esac
