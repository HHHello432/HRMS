#!/bin/bash
# ============================================
# HRMS - 一键启动脚本
# 人力资源管理系统 Docker Compose 部署
# ============================================

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
COMPOSE_FILE="${SCRIPT_DIR}/docker-compose.yml"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"
BACKEND_DIR="${PROJECT_DIR}/backend"
UI_DIR="${PROJECT_DIR}/continew-admin-ui"
DOCKER_NET="docker-project_app-net"

# 颜色
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m'

echo -e "${CYAN}========================================${NC}"
echo -e "${CYAN}  HRMS 人力资源管理系统 - 一键启动${NC}"
echo -e "${CYAN}========================================${NC}"

# 检查 Docker
if ! command -v docker &> /dev/null; then
    echo -e "${RED}[ERROR] Docker 未安装！${NC}"
    exit 1
fi

# 检查 docker-compose
if ! docker compose version &> /dev/null; then
    echo -e "${RED}[ERROR] docker compose 插件未安装！${NC}"
    exit 1
fi

# 确保外部网络存在
echo -e "${YELLOW}[1/5] 检查网络...${NC}"
if ! docker network inspect "$DOCKER_NET" &> /dev/null; then
    echo -e "${YELLOW}  创建网络 ${DOCKER_NET}...${NC}"
    docker network create "$DOCKER_NET"
fi
echo -e "${GREEN}  ✓ 网络就绪${NC}"

# 检查已有 mysql8 容器
echo -e "${YELLOW}[2/5] 检查数据库...${NC}"
if ! docker ps --format '{{.Names}}' | grep -q '^mysql8$'; then
    echo -e "${YELLOW}  ⚠️ mysql8 容器未运行，请先启动 docker-project:${NC}"
    echo -e "     cd /root/docker-project && docker compose up -d mysql8"
    exit 1
fi

# 初始化数据库（仅首次）
echo -e "${YELLOW}[3/5] 检查数据库初始化...${NC}"
DB_EXISTS=$(docker exec mysql8 mysql -uroot -pgj1234 -N -e "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name='continew_admin'" 2>/dev/null || echo "0")
if [ "$DB_EXISTS" = "0" ]; then
    echo -e "  首次部署：导入数据库..."
    docker exec mysql8 mysql -uroot -pgj1234 -e "CREATE DATABASE IF NOT EXISTS continew_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
    docker cp "${UI_DIR}/continew_admin.sql" mysql8:/tmp/
    docker exec mysql8 mysql -uroot -pgj1234 continew_admin < /tmp/continew_admin.sql
    echo -e "${GREEN}  ✓ 数据库初始化完成${NC}"
else
    echo -e "${GREEN}  ✓ 数据库已就绪${NC}"
fi

# 构建并启动服务
echo -e "${YELLOW}[4/5] 构建并启动服务...${NC}"
cd "$SCRIPT_DIR"
docker compose -p hrms build --no-cache 2>&1 | tail -5
docker compose -p hrms up -d

echo -e "${GREEN}  ✓ 服务已启动${NC}"

# 等待就绪
echo -e "${YELLOW}[5/5] 等待服务就绪...${NC}"
MAX_RETRIES=30
RETRY=0
while [ $RETRY -lt $MAX_RETRIES ]; do
    STATUS=$(docker inspect --format='{{.State.Health.Status}}' hrms-server 2>/dev/null || echo "starting")
    if [ "$STATUS" = "healthy" ]; then
        break
    fi
    RETRY=$((RETRY + 1))
    sleep 2
done

if [ "$STATUS" = "healthy" ]; then
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}  ✅ HRMS 部署完成！${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo -e "  🌐 前端地址: ${CYAN}http://localhost:39000${NC}"
    echo -e ""
    echo -e "  📊 容器状态:"
    docker compose -p hrms ps
    echo -e ""
    echo -e "  📈 内存占用:"
    docker stats --no-stream --format "table {{.Name}}\t{{.MemUsage}}" $(docker compose -p hrms ps -q)
else
    echo -e "${RED}  ⚠️ 服务启动超时，请检查日志:${NC}"
    echo -e "     docker compose -p hrms logs server"
fi
