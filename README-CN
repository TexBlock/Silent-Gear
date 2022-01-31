# Silent-Gear

用于 Minecraft 的模块化工具/装甲模块。制作是使用蓝图处理的，这消除了所有配方冲突。材料和零件可以通过数据包与 JSON 文件一起添加。装备制作配方（所需材料、所需零件的数量等）也可以通过数据包进行更改。

这是基于并完全取代了 Silent's Gems 的工具/装甲系统，但有各种变化和改进。

附加模组可以添加新的零件类型、齿轮类型和特性类型，以及数据包可以做的任何事情。

## 链接和下载

- [CurseForge](https://minecraft.curseforge.com/projects/silent-gear) （下载和更多信息）
- [Wiki](https://github.com/SilentChaos512/Silent-Gear/wiki) （高级信息）
- [GitHub repository](https://github.com/SilentChaos512/Silent-Gear) （源代码）
- [Issue Tracker on GitHub](https://github.com/SilentChaos512/Silent-Gear/issues) （错误报告和功能请求）
- [Discord Server](https://discord.gg/Adyk9zHnUn) （获得快速问题解答的最简单方法，不要用于报告错误）

### 下载注意事项

**我只将构建上传到 Minecraft CurseForge。** 如果您从 Curse/CurseForge 或 Twitch 启动器（或在某些情况下作为整合包的一部分）以外的其他地方下载了 mod，我无法对文件或其内容做出任何保证，因为它是在未经我许可的情况下上传的。

-----------------------------------

## 制作附属模组

要在项目中使用 Silent Gear，您需要添加 Silent Gear、Silent Lib 和silent-utils 的依赖项。将以下内容添加到您的`build.gradle`。

您还需要生成一个 GitHub 令牌并将其与您的 GitHub 用户名一起添加到您的个人 `gradle.properties` 文件中的 `C:\Users\YOUR_USERNAME\.gradle` 或 `~/.gradle/gradle.properties` 中。 该文件可能不存在，您必须自己创建。

GitHub 令牌可以在 [这里](https://github.com/settings/tokens) 生成。 单击 _生成新令牌_ 并单击 _read:packages_ 的复选标记

`C:\Users\YOUR_USERNAME\.gradle` 或 `~/.gradle/gradle.properties` 中的 `gradle.properties` 文件示例

```gradle
//你的 GitHub 用户名
gpr.username=SilentChaos512

// 您的 GitHub 生成的具有读取权限的令牌（一组十六进制数字）
gpr.token=paste_your_token_here
```

-----------------------------------

添加到`build.gradle`的代码。 _注意“silentlib”没有连字符。 在创建 repo 时，我采用了不同的命名方式。

我更喜欢把我的认证细节分配给一个变量，以减少重复，使构建文件看起来更干净。

```gradle
// GitHub 包的身份验证详细信息
// 这也可以放在 `repositories` 块中，或者如果您愿意，也可以将其内联
def gpr_creds = {
    username = property('gpr.username')
    password = property('gpr.token')
}
```

添加所有必要的存储库...

```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/silentchaos512/silent-gear")
        credentials gpr_creds
    }
    maven {
        url = uri("https://maven.pkg.github.com/silentchaos512/silentlib")
        credentials gpr_creds
    }
    maven {
        url = uri("https://maven.pkg.github.com/silentchaos512/silent-utils")
        credentials gpr_creds
    }
}
```

最后，添加Silent Gear和Silent Lib的依赖关系（这将为你包括silent-utils）。

```gradle
dependencies {
    // 将VERSION替换为你需要的版本，形式为“MC_VERSION-MOD_VERSION”
    // 例如: compile fg.deobf("net.silentchaos512:silent-gear:1.16.3-2.+")
    // 可用的构建可以在这里找到：https://github.com/SilentChaos512/silent-gear/packages
    // 在某些情况下，“排除模块”行将防止导入错误
    compile fg.deobf("net.silentchaos512:silent-gear:VERSION") {
        exclude module: 'forge'
        exclude module: 'jei-1.16.3'
        exclude module: 'silent-lib-1.16.3'
        exclude module: 'curios-forge'
    }

    // 和以前一样，VERSION的形式是 "MC_VERSION-MOD_VERSION"（例如，1.16.3-4.+）。
    // https://github.com/SilentChaos512/silentlib/packages
    compile fg.deobf("net.silentchaos512:silent-lib:VERSION") {
        exclude module: "forge"
    }
}
```
