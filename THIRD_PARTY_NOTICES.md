# Third-party notices

## Redistributed build tooling: Gradle 8.8 wrapper

Files: `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`.
Upstream: https://github.com/gradle/gradle/tree/v8.8.0
Copyright: the original script headers (Copyright 2015 and 2015-2021 the original author or authors) and upstream notices are retained.
License: Apache License 2.0, full text in `LICENSES/Apache-2.0.txt`.
Official wrapper SHA-256: `cb0da6751c2b753a16ac168bb354870ebb1e162e9083f116729cec9c781156b8`.
Wrapper build tooling is distributed with the source, not embedded in the mod JAR. The root BSD license does not replace its license.

## External dependencies — not redistributed in this mod JAR

- Minecraft 1.20.1 (Mojang/Microsoft), subject to its EULA: https://www.minecraft.net/eula
- Minecraft Forge 1.20.1-47.4.6 and ForgeGradle 6, their upstream licenses apply: https://github.com/MinecraftForge/MinecraftForge and https://github.com/MinecraftForge/ForgeGradle
- JEI 15.20.0.116, mezz and contributors, MIT: https://github.com/mezz/JustEnoughItems/blob/1.20.1/LICENSE.txt — API compile-only, no copied implementation/API classes.
- EMI 1.1.24+1.20.1+forge, upstream metadata author `Emi`, MIT: https://github.com/emilyploszaj/emi — compile-only public artifact; no copied implementation/API classes.
- TrashSlot 15.1.0, BlayTheNinth; All Rights Reserved: https://github.com/TwelveIterations/TrashSlot — optional public-method reflection only; no code, textures or JAR bundled.

These are dependency/reference disclosures, not permission to redistribute their software under this project's BSD license, and not an endorsement. This project uses vanilla menus and rendering primitives at runtime; it does not ship Minecraft assets or a third-party trash implementation.
