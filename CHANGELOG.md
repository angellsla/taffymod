# Taffy Popup Mod 更新日志

## v1.0.1

### 修复
- 修复服务端启动崩溃问题：将 Swing 弹窗逻辑从主模组类中拆分到客户端专用类 `TaffyClientHandler`，避免服务端加载 `javax.swing` / `java.awt` 导致无头环境报错。
- 主模组类 `TaffyMod` 现在只包含服务端命令逻辑，双端职责分离更清晰。

## v1.0.0

### 新增
- NeoForge 1.21.1 局域网联机整活弹窗模组。
- 新增 `/taffy <target> <message>` 命令，主机可向指定玩家或全体玩家发送弹窗。
- 客户端使用微软雅黑字体显示弹窗内容，彻底解决中文乱码。
