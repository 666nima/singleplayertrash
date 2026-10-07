# 单人垃圾桶 1.0.4

[English](README.md)

Minecraft **1.20.1** / **Forge 47.4.6–47.x** / **Java 17**；模组 ID：`singleplayertrash`。仅在客户端安装，用于**未开放 LAN 的本地单人世界**，不是专用服务器工具。远程服务器、LAN、旁观、死亡和不安全/陈旧菜单均拒绝。

## 使用与销毁语义

- 生存模式按 E：入口挂接在背包面板右下沿，外框 26×24 GUI 像素、内部 18×18 槽。跟随面板移动，避让可识别可见控件、活动槽位及 TrashSlot 可见矩形；越界或无安全空间则隐藏。
- 创造模式：**只在“生存物品栏”标签页替换原版销毁槽**，分类/搜索页不显示。点击改为开桶，并拦截原销毁槽普通点击删除光标、Shift 点击清背包的语义；不是额外加一个原版删除槽。
- 客户端和集成服务器均要求**光标为空才允许打开**。先放下手中物品；光标有物品时拒绝开桶并保留物品。
- 临时垃圾桶 **4×9，共36槽**。打开期间可以取回；**关闭时仍在桶里的全部物品永久销毁，无撤销、持久储存或确认框**。
- 关闭时光标物品独立走原版回包/必要时掉落规则，不与桶内销毁混淆。真正的库存操作在集成服务器菜单执行，通过原版槽位包同步，不是客户端假删。

## 可选适配与未测边界

JEI **15.20.0.116**、EMI **1.1.24+1.20.1+forge** 仅为可选 `compileOnly` 编译依赖，不内嵌其 API/JAR，不声明为必装。插件通过 JEI `getGuiExtraAreas`、EMI `addExclusionArea` 提供生存背包入口排除区，不表示完整创造界面适配。

TrashSlot **15.1.0**（`trashslot`）不内嵌、不关闭、不复制代码或素材；只反射读取公开可见矩形用于生存入口避让，失败则保守隐藏。两个入口销毁语义并不相同；所有非 widget 自绘侧栏无法保证识别。

本次公开副本验证只覆盖生产编译/reobf、纯 Java 布局与资格策略、JAR/UTF-8/许可检查。**没有重新实测最终1.0.4的 JEI/EMI 插件运行时发现、鼠标优先级、视觉位置、所有 GUI 缩放和任意整合包兼容**；作者个人自测满意不等于通用兼容。死亡/断线关闭路径本次未重新运行。

## 可移植构建

将 `JAVA_HOME` 指向 Java 17 JDK：

```sh
./gradlew clean build reobfJar --console plain
# Windows：gradlew.bat clean build reobfJar --console plain
python tools/test_pure_java.py
```

官方 Gradle 8.8 wrapper 固定分发包 SHA-256；Forge/JEI 使用公开 Maven，EMI 使用公开 Modrinth Maven 的固定版本 ID `Axuu9I9R`。不依赖本机 helper 工程、私有平铺仓库或绝对路径。产物在 `build/libs`。

纯测试需 Python 3，`javac/java` 在 PATH 或 `JAVA_HOME`；临时测试类写入系统临时目录，自动删除。`tests/` 不属于生产源码集、不打包。公开副本不包含世界、日志、缓存、游戏启动验收/probe 夹具，也不提供 `-Pharness` 注入生产源码。

## 许可

原创模组实现按 **BSD-3-Clause** 发布，Copyright (c) 2026 **666nima**，见 [LICENSE](LICENSE)。不覆盖 Minecraft、Forge、可选第三方模组或 Gradle 的原许可。官方 Gradle wrapper 保留版权头及 Apache-2.0 全文；见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。
