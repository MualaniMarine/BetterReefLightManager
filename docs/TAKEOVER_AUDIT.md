# ThaloPilot 项目接手梳理

## 1. 接手结论

当前项目已经不是“只有反编译材料”的状态了。

现在目录里同时存在：

- 一套新的可维护 Android 工程
- 一份旧 APK 的反编译参考材料

接手时应明确区分：

- 新工程负责继续开发、调试、发版准备
- 旧材料只负责查历史逻辑、查协议、查文案

## 2. 项目来源

最初拿到的不是原始源码仓库，而是反编译产物。它能帮助还原：

- 包名
- 页面结构
- 设备协议
- 配网入口
- 预设数据

但它不适合作为长期维护基础，因为存在：

- 构建脚本缺失
- 方法反编译不完整
- 依赖版本缺失
- 可读性差

## 3. 现在的新工程情况

### 技术栈

- Android 原生
- Kotlin
- Jetpack Compose

### 主要入口

- [MainActivity.kt](../app/src/main/java/com/mualanimarine/thalopilot/MainActivity.kt)

### 核心能力

- `SharedPreferencesUtil`
- `ConnectThread`
- `CommandUtil`
- Compose 网络页
- Compose 控制页
- Airkiss / Esptouch 页面

## 4. 旧项目里确认过的关键事实

### 设备通信

- 核心控制走本地 TCP
- 端口 `8266`
- 协议包头尾为 `AAA5 ... BB`

### 配网

旧项目里确实存在：

- `Esptouch`
- `Airkiss`

同时还存在设备热点直连控制逻辑。

### 业务模型

- 设备类型：`K7`、`K7mini`
- 预设曲线：`SPS`、`LPS`、`SL`
- 自动 / 手动模式
- 演示模式

## 5. 现阶段最重要的业务判断

- `SPS / LPS / SL` 是三组预设时间-亮度数据
- 不是运行模式
- 控制链路的核心仍是本地 Socket，而不是云端接口
- `AP 模式` 和 `局域网模式` 现在都已经在新工程里有对应页面逻辑
- `Esptouch` 已确认在抓包层面属于正确方向，但设备兼容仍未完全跑通

## 6. 现在应该怎么看这个项目

### 日常开发看这里

- [README.md](../README.md)
- [REBUILD_STATUS.md](REBUILD_STATUS.md)
- [PROTOCOL_SPEC.md](PROTOCOL_SPEC.md)
- `app/`

### 历史对照看这里

- `legacy/decompiled/sources/`
- `legacy/decompiled/resources/`

## 7. 当前接手优先级

1. 稳定 `Esptouch`
2. 完善局域网设备发现和识别
3. 继续压实控制调试页的配置读写
4. 最后再考虑扫码、网页、历史页面回接

## 8. 当前不建议做的事

- 不要在 `legacy/decompiled/` 里继续叠开发
- 不要把反编译代码直接当成正式代码库
- 不要把“热点直连控制”和“SmartConfig 配网”混成同一件事

## 9. 备份说明

历史备份文件不随当前仓库保留。需要制作交付备份时，应通过 Git 标签或归档当前提交生成，避免文档引用本机绝对路径。
