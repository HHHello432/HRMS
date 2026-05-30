#!/bin/bash
# ============================================
# HRMS - 一键关闭脚本
# 停止并清理 HRMS 相关容器
# ============================================

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
COMPOSE_FILE="${SCRIPT_DIR}/docker-compose.yml"

# 颜色
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${YELLOW}========================================${NC}"
echo -e "${YELLOW}  HRMS 人力资源管理系统 - 关闭${NC}"
echo -e "${YELLOW}========================================${NC}"

# 检查是否在运行
if ! docker compose -p hrms ps --services --filter "status=running" 2>/dev/null | grep -q .; then
    echo -e "${YELLOW}  HRMS 服务未在运行${NC}"
    exit 0
fi

# 停止服务
echo -e "  正在停止 HRMS 服务..."
docker compose -p hrms stop

echo -e "${GREEN}  ✅ 服务已停止${NC}"
echo ""

# 询问是否清理数据
read -p "  是否同时清理数据卷（Redis 缓存 + 日志）？(y/N): " CLEAN_VOLUMES
if [ "$CLEAN_VOLUMES" = "y" ] || [ "$CLEAN_VOLUMES" = "Y" ]; then
    echo -e "  正在清理数据卷..."
    docker compose -p hrms down -v
    echo -e "${GREEN}  ✅ 数据卷已清理${NC}"
else
    docker compose -p hrms down
    echo -e "  数据卷已保留（下次启动可复用 Redis 缓存）"
fi

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}  HRMS 已完全关闭${NC}"
echo -e "${GREEN}========================================${NC}"
