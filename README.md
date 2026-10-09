<div align="center">

# HRMS · 人力资源管理系统

**基于 ContiNew Admin 二次开发的招聘管理系统 —— 覆盖「招聘计划 → 候选人 → 面试」完整业务闭环**

Java 17 · Spring Boot 3 · MyBatis-Plus · Sa-Token · Vue 3 · TypeScript · Arco Design · MySQL 8 · Redis 7 · Docker

</div>

---

## 项目简介

HRMS 是一套前后端分离的人力资源管理系统。它在 [ContiNew Admin](https://github.com/continew-org/continew-admin) 通用后台框架（用户 / 角色 / 菜单 / 部门 / 权限 / 日志等基础能力）之上，扩展了完整的**招聘业务模块**：

| 模块 | 职责 | 后端入口 |
| --- | --- | --- |
| 招聘计划管理 | 招聘需求 / 岗位计划的新增、编辑、上下架与导出 | `RecruitmentController` → `/system/recruitment` |
| 候选人管理 | 候选人档案、投递记录、跟进状态 | `CandidateController` |
| 面试管理 | 面试安排、面试官与时间、面试结果 | `InterviewController` |

三个模块均基于 ContiNew Starter 的 CRUD 扩展（`@CrudRequestMapping`，自动生成分页 / 详情 / 新增 / 修改 / 删除 / 导出接口），并配套菜单 SQL 接入后台权限体系。

## 技术栈

| 层次 | 技术选型 |
| --- | --- |
| 后端语言 / 框架 | Java 17 · Spring Boot 3 · ContiNew Starter |
| 持久层 | MyBatis-Plus |
| 认证鉴权 | Sa-Token · RBAC 权限 · 数据权限 |
| 缓存 | Redis 7 · Redisson · JetCache |
| 数据库 | MySQL 8（同时提供 PostgreSQL 初始化脚本） |
| 数据库版本管理 | Liquibase changelog |
| 前端 | Vue 3 · TypeScript · Vite · Arco Design Vue · Pinia |
| 部署 | Docker Compose · Nginx |

## 目录结构

```text
HRMS/
├── continew-admin-system/              # 自定义业务模块源码
│   └── src/main/java/top/continew/admin/system/
│       ├── controller/                 # 招聘 / 候选人 / 面试 Controller
│       ├── service/ + service/impl/    # 业务逻辑
│       ├── mapper/                     # MyBatis-Plus Mapper
│       ├── model/{entity,query,req,resp}/
│       └── sql/                        # 三个模块的菜单 SQL
├── continew-admin-ui/                  # 前端工程（Vue3 + Vite）
├── backend/
│   ├── bin/continew-admin.jar          # 后端可执行包（thin jar）
│   ├── lib/                            # 依赖 jar（体积较大，未纳入版本库）
│   └── config/                         # 外部化配置（application*.yml / db changelog / logback / 模板）
├── deploy/
│   ├── docker-compose.yml              # mysql + redis + server + ui
│   ├── backend/Dockerfile              # 后端镜像（JDK 17 JRE）
│   ├── Dockerfile.ui                   # 前端镜像（Node 构建 + Nginx 托管）
│   ├── nginx/                          # Nginx 反向代理配置
│   └── start.sh / stop.sh / hrms.sh    # 一键启停与管理脚本
└── continew_admin.sql                  # 数据库全量初始化脚本
```

## 快速开始（Docker Compose）

```bash
cd deploy
cp .env.example .env        # 填写数据库密码等（.env 不会进入版本库）
./start.sh                  # 一键启动
./hrms.sh status            # 查看容器状态
./hrms.sh logs              # 查看日志
./hrms.sh stop              # 停止
```

启动后：

- 前端界面：`http://<服务器IP>:39000`
- 后端接口：`http://<服务器IP>:18000`（健康检查 `/actuator/health`）

> `MYSQL_DATABASE` 指定的库会由 MySQL 镜像自动创建；表结构与初始数据请导入 `continew_admin.sql`。

## 手动部署

1. **数据库**：创建 MySQL 8 库（默认 `continew_admin`），导入 `continew_admin.sql`；
2. **后端配置**：按 `backend/config/application-dev.yml` 修改数据库、Redis 连接；
3. **启动后端**：

   ```bash
   java -jar backend/bin/continew-admin.jar \
     --spring.config.additional-location=file:./backend/config/
   ```

4. **前端**：

   ```bash
   cd continew-admin-ui
   pnpm install
   pnpm build          # 产物 dist/ 交给 Nginx 托管
   ```

## 配置说明

所有敏感配置通过环境变量注入，模板见 [`deploy/.env.example`](deploy/.env.example)。**请勿把真实密码提交到仓库。**

| 变量 | 说明 |
| --- | --- |
| `MYSQL_ROOT_PASSWORD` | MySQL root 密码（必填） |
| `MYSQL_DATABASE` | 数据库名，默认 `continew_admin` |
| `MYSQL_USER` | 业务库账号，默认 `hrms` |
| `MYSQL_PASSWORD` | 业务库密码（必填） |
| `REDIS_PASSWORD` | Redis 密码（未设置可留空） |
| `PROJECT_URL` | 前端访问地址（跨域放行 + 第三方登录回调），如 `http://your-server:39000` |
| `HRMS_PROJECT_DIR` | 项目根目录，默认 `..`（即 `deploy/` 的上级目录） |

> 本地开发若需要邮箱验证码，另需在运行环境提供 `MAIL_USERNAME`（发件邮箱）与 `MAIL_PASSWORD`（SMTP 授权码）。

## 说明

- 本项目基于 [ContiNew Admin](https://github.com/continew-org/continew-admin)（Apache-2.0）二次开发，仅用于学习与个人部署。
- 后端以「thin jar + 外部 `lib/` + 外部化配置」形式运行；`backend/lib/` 依赖包体积较大，未纳入版本库，需在部署机本地准备。
- 部署到生产环境前，请务必修改默认管理员密码与数据库密码。

## License

本项目遵循 [Apache License 2.0](LICENSE)。
