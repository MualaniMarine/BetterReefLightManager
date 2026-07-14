# ThaloPilot / 礁谱智控

ThaloPilot 是一款面向 K7 系列海水灯的本地控制与调试工具。项目以 Kotlin 和 Jetpack Compose 重建，重点解决设备网络连接、局域网发现、亮度控制和时间曲线配置等实际使用问题。

中文系统中的应用名称为“礁谱智控”，其他系统显示为 `ThaloPilot`。Android 包名为 `com.mualanimarine.thalopilot`。

## 核心能力

- AP 直连：扫描并连接 `K7_Pro` 设备热点，自动带入控制地址。
- 局域网控制：扫描同网段 TCP `8266` 设备，一键带入 IP 并进入控制页。
- 设备调试：读取设备配置、显示设备名称、切换自动或手动模式、同步时间和查看通信日志。
- 六路亮度：按通道颜色提供滑块与数值输入，支持发送当前手动亮度。
- 时间曲线：编辑 24 点六通道曲线，管理预置与自定义灯光方案，支持平滑和跳变检查。
- 快捷导入：支持从相册或相机读取配套工具生成的方案二维码。
- 配网试验：保留 Airkiss 与 Esptouch 入口；当前仅用于兼容性验证，尚不作为稳定配网流程。

## 技术栈

- Android 原生
- Kotlin
- Jetpack Compose + Material 3
- 单 Activity + Navigation Compose
- TCP Socket 本地通信
- SharedPreferences 本地持久化

## 快速开始

1. 使用 Android Studio 打开本目录。
2. 等待 Gradle 同步完成。
3. 选择真机或模拟器，运行 `app` 模块。
4. 真机连接设备热点或同一局域网后，从“灯具网络配置”进入对应模式。

设备联调优先使用 Android 真机。热点扫描、Wi-Fi 连接和局域网发现会受系统版本及位置/附近设备权限影响。

## 使用流程

### AP 直连

1. 进入“灯具网络配置”，选择 AP 模式。
2. 扫描并选择名称以 `K7_Pro` 开头的灯具热点。
3. 应用会以固定密码 `12345678` 请求连接，并带入热点网关地址。
4. 跳转“灯具控制调试”，连接设备并读取全部配置。

### 局域网控制

1. 让手机与已配网灯具连接同一个 Wi-Fi。
2. 在“灯具网络配置”选择局域网模式并扫描设备。
3. 选择发现的设备，带入其 IP 后进入控制页。
4. 首次读取成功后，应用会缓存该 IP 对应的设备名称，便于后续识别。

## 工程结构

- [`app/`](app/)：正式 Android 工程。
- [`app/src/main/java/com/mualanimarine/thalopilot/`](app/src/main/java/com/mualanimarine/thalopilot/)：应用源码。
- [`app/src/main/java/com/mualanimarine/thalopilot/MainActivity.kt`](app/src/main/java/com/mualanimarine/thalopilot/MainActivity.kt)：页面、导航与主要交互入口。
- [`app/src/main/java/com/mualanimarine/thalopilot/ConnectThread.kt`](app/src/main/java/com/mualanimarine/thalopilot/ConnectThread.kt)：TCP 连接与收发。
- [`app/src/main/java/com/mualanimarine/thalopilot/util/CommandUtil.kt`](app/src/main/java/com/mualanimarine/thalopilot/util/CommandUtil.kt)：设备命令封包。
- [`app/src/main/java/com/mualanimarine/thalopilot/device/LightingProfiles.kt`](app/src/main/java/com/mualanimarine/thalopilot/device/LightingProfiles.kt)：设备配置解析与预置曲线。
- [`docs/`](docs/)：协议、状态和接手文档。
- [`legacy/`](legacy/)：旧 APK 反编译参考材料，仅用于追溯历史逻辑，禁止作为正式源码修改。

## 设备协议

控制链路为本地 TCP，默认端口 `8266`。发送包通常使用 `AAA5 + 功能码 + 数据区 + BB` 格式；常用功能码包括：

- `1003`：同步时间
- `1004`：设置自动或手动模式
- `1005`：发送六路手动亮度
- `1007`：发送全量配置
- `1008`：读取全部配置
- `100A`：设置演示模式

完整字段约定与已知边界请见[设备通信协议](docs/PROTOCOL_SPEC.md)。

## 文档索引

- [当前重建状态](docs/REBUILD_STATUS.md)
- [设备通信协议](docs/PROTOCOL_SPEC.md)
- [项目接手梳理](docs/TAKEOVER_AUDIT.md)

## 开发约定

- 正式功能只修改 `app/`，不要直接改动 `legacy/`。
- 修改命令或配置解析前，先核对[设备通信协议](docs/PROTOCOL_SPEC.md)并保留实机报文日志。
- `SPS`、`LPS`、`SL` 是预置时间-亮度方案，不是设备运行模式；设备运行模式只有自动和手动。
- 读取设备配置后，应以设备返回的模式状态作为下一次全量配置发送的模式来源。
