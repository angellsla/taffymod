# Taffy Popup Mod

一个 NeoForge 1.21.1 局域网联机整活模组。

## 功能

- 服务端（局域网主机）执行 `/taffy` 命令
- 指定目标玩家（支持 `@a`、`@p` 或具体玩家名）
- 目标玩家客户端弹出一个 Windows 风格弹窗（Swing JOptionPane），显示自定义文本
- 弹窗内容强制使用微软雅黑字体，彻底解决中文乱码问题

## 环境

- Minecraft: 1.21.1
- NeoForge: 21.1.82
- Java: 21

## 使用方式

1. 将模组 jar 同时放入主机和所有玩家的 `mods` 文件夹
2. 主机进入局域网世界
3. 在聊天栏输入命令：

```mcfunction
/taffy @a 关注塔菲喵
/taffy Steve 你是一个一个一个一个...
```

## 命令格式

```mcfunction
/taffy <target> <message>
```

- `target`: 目标玩家，支持 `@a`、`@p`、玩家名
- `message`: 弹窗显示文本，支持空格（使用 greedy string）

## 编译

本地编译：

```bash
./gradlew build
```

产物位于 `build/libs/taffymod-1.0.0.jar`。

## 许可证

MIT
