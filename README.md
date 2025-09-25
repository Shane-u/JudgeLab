# JudgeLab · 在线判题平台

<div align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MyBatis--Plus-3.5%2B-blue.svg" alt="MyBatis-Plus" />
  <img src="https://img.shields.io/badge/MySQL-8.x-blue.svg" alt="MySQL" />
  <img src="https://img.shields.io/badge/Redis-6%2B-red.svg" alt="Redis" />
  <img src="https://img.shields.io/badge/JWT-auth-yellow.svg" alt="JWT" />
  <img src="https://img.shields.io/badge/Maven-3.8%2B-orange.svg" alt="Maven" />
</div>

<div align="center">
  <p>⚖️ 高性能、可扩展的在线判题系统，支持题目管理、提交评测与结果可视化</p>
  <p>🧩 采用可插拔判题策略与沙箱代理，兼容多语言、多用例评测场景</p>
</div>

---

## 🚀 项目简介

JudgeLab 是一款面向在线评测与算法竞赛场景打造的判题平台后端。项目提供题目管理、代码提交、评测流程编排与结果查询等能力，结合判题沙箱实现安全隔离与资源控制，适用于 OJ 平台、课程作业、企业内部训练营等场景。

- 多语言代码评测（以 Java 为起点，易于扩展）
- 判题策略可配置（默认/语言特定策略）
- 判题沙箱代理 + 远程代码沙箱适配
- 题目、提交、用户等完整业务闭环
- 统一异常处理与标准化响应

---

## 🎯 核心亮点

- 🧠 分层清晰：标准分层（Controller / Service / Mapper / Strategy），职责明确
- 🧩 判题策略抽象：`JudgeStrategy` + `JudgeManager`，按语言或场景灵活切换
- 🔒 安全可控：`AuthInterceptor` + `@AuthCheck` 注解式鉴权，精细化接口访问
- 🧪 用例驱动：多测试用例、资源限制、评测结论与信息结构化存储
- 🧰 可观测性：统一错误码、全局异常处理、标准 `BaseResponse` 返回体
- 🧱 易于集成：`CodeSandBoxFactory/Proxy` 封装，支持远程沙箱与本地扩展

---

## 🧱 技术选型

| 模块         | 技术栈                                   | 说明 |
|--------------|------------------------------------------|------|
| 架构模式     | 单体应用 + 分层设计（控制器/服务/策略）  | 简洁、易维护 |
| 后端框架     | Spring Boot, MyBatis-Plus                | 快速开发、ORM 增强 |
| 数据库       | MySQL 8.x                                | 结构化数据存储 |
| 缓存         | Redis                                    | 会话/限流/热点缓存 |
| 安全认证     | 基于拦截器 `AuthInterceptor` + JWT        | 轻量鉴权方案 |
| 判题引擎     | 策略模式 + 代码沙箱（代理/工厂）          | 可插拔、可扩展 |
| 构建工具     | Maven                                    | 依赖与构建管理 |

---

## 🗃️ 核心数据模型（简要）

| 表/实体                  | 说明                               |
|--------------------------|------------------------------------|
| `user`                   | 用户信息、角色、状态               |
| `question`               | 题目信息、难度、判题配置           |
| `question_submit`        | 提交记录、语言、状态、评测结果     |

> 结合 `MyBatis-Plus` 映射到实体：`User`、`Question`、`QuestionSubmit`，并通过 `Mapper.xml` 编排查询。

---

## 🧩 模块与目录结构

```text
src/
  main/
    java/com/shane/judgeLab/
      annotation/          # 注解：@AuthCheck
      aop/                 # 拦截器：AuthInterceptor
      common/              # 通用返回体、分页、错误码
      config/              # 跨域、JSON、MyBatisPlus 配置
      constant/            # 公共常量、用户常量
      controller/          # 控制器：Question、User
      exception/           # 业务异常、全局异常处理
      judge/
        judgecodesandbox/  # 代码沙箱接口、工厂、代理 + 实现
        strategy/          # 判题策略：默认、Java、上下文
        JudgeManager.java  # 策略调度与编排
        JudgeService*.java # 评测服务接口与实现
      mapper/              # Mapper 接口 + XML（resources/mapper）
      model/               # DTO / Entity / Enum / VO
      service/             # 题目/提交/用户服务层
      utils/               # 工具
    resources/
      application.yml      # 应用配置
      mapper/              # MyBatis XML
```

---

## ⚙️ 快速开始

- **环境准备**：JDK 17+、MySQL 8.x、Redis 6+、Maven 3.8+
- **初始化数据库**：执行 `sql/create_table.sql`
- **配置应用**：编辑 `src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/judgelab?useSSL=false&serverTimezone=UTC
    username: root
    password: your_password
  redis:
    host: localhost
    port: 6379

# 判题相关自定义配置可按需扩展
```

- **构建与运行**：

```bash
# 构建
mvn -q -DskipTests clean package

# 运行（在 target 目录生成可执行 jar）
java -jar target/*.jar
```

---

## 🔌 判题流程（简述）

1. 用户提交代码与语言 → `QuestionSubmitService`
2. 进入评测编排 → `JudgeServiceImpl` 调用 `JudgeManager`
3. `JudgeManager` 依据上下文选择 `JudgeStrategy`
4. 通过 `CodeSandBoxProxy` 调用实际沙箱执行
5. 汇总用例结果、资源消耗，生成评测信息与结论

> 语言适配可通过新增 `JudgeStrategy` 与沙箱实现进行扩展。

---

## 🔐 鉴权与安全

- `@AuthCheck` 注解 + `AuthInterceptor` 拦截器：基于角色/登录态的接口访问控制
- 全局异常捕获与标准错误码：见 `exception` 与 `common/ErrorCode`
- 跨域/JSON 序列化等在 `config` 中集中管理

---

## 🛣️ 路线图（Roadmap）

- ✅ 判题策略解耦与多语言扩展点
- ✅ 判题沙箱代理与工厂，支持远程沙箱对接
- ✅ 题目/提交/用户全流程打通
- ⏳ 语言矩阵扩展（C/C++/Python/Go）
- ⏳ 评测资源限额与限流策略完善
- ⏳ Web 管理端（可视化题库与评测）
- ⏳ 评测结果可视化与统计报表

---

## 👨‍💻 维护者

| 姓名  | 角色       | 联系方式 |
|-------|------------|----------|
| Shane | 项目开发者 | 1554096735@qq.com        |

> 欢迎 Issue / PR 参与共建！

---

## 📄 License

本项目基于 MIT License 开源，您可以自由使用、修改和分发本项目。
