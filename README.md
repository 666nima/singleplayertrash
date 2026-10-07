# Singleplayer Trash 1.0.4

[中文说明](README.zh-CN.md)

A client-installed mod for Minecraft **1.20.1**, **Forge 47.4.6–47.x**, **Java 17**. Mod ID: `singleplayertrash`.

## Scope and use

Only a local singleplayer world **not opened to LAN** is supported. Remote servers, LAN sessions, spectators, dead players and stale/unsafe menus are rejected. Do not install it as a dedicated-server utility.

- In the survival inventory (E), click the trash entry attached to the panel's lower-right edge. Its outer frame is 26×24 GUI pixels with an 18×18 inner slot. It follows the panel and hides if detected widgets, active slots, a visible TrashSlot rectangle or screen boundaries leave no safe space.
- In creative mode, the entry **replaces the vanilla destroy slot only on the Survival Inventory tab**. Category/search tabs have no entry. Clicking it opens this mod's menu instead of deleting the cursor stack; vanilla destroy-slot click and Shift-click clearing semantics are intercepted. It is not an extra creative destroy slot.
- Opening requires an **empty cursor** on both client and integrated-server checks. Put down a held stack first; an occupied cursor is preserved and opening is rejected.
- The menu is a temporary **36-slot (4×9) trash container**. Items can be taken back while it remains open. **Closing permanently destroys everything still inside. There is no undo, persistent storage or confirmation dialog.**
- Cursor items at menu close follow vanilla return-to-inventory / drop-if-needed handling, separately from clearing the trash container. Inventory changes run through the integrated server and vanilla menu synchronization, not client-only deletion.

## Optional integrations and limits

JEI **15.20.0.116** and EMI **1.1.24+1.20.1+forge** are optional, compile-only dependencies. This JAR contains this mod's plugin implementations, **not their APIs or their mod JARs**, and declares neither as mandatory. Plugins request survival-inventory exclusion rectangles through JEI `getGuiExtraAreas` and EMI `addExclusionArea`; this is not a claim of full creative-viewer integration.

TrashSlot **15.1.0** (`trashslot`) is not bundled. A reflective, read-only lookup of its public visible rectangle is used for survival-entry avoidance; lookup failures hide the survival entry conservatively. This does not disable the other trash mod or unify the two deletion semantics. Arbitrary non-widget overlays are not guaranteed to be detected.

**Verification boundary:** production compilation/reobfuscation, pure-Java placement and eligibility tests, archive/resource/license checks do not establish runtime viewer compatibility. The final 1.0.4 JEI/EMI plugin discovery, viewer mouse priority, visual placement, all GUI scales and arbitrary modpacks are **not runtime-tested in this public staging verification**. The author's personal acceptance does not imply universal compatibility. Death/disconnection closure paths are not newly exercised here.

## Build and pure tests

Set `JAVA_HOME` to a Java 17 JDK. No local helper project, private JAR repository or machine-specific path is required.

```sh
./gradlew clean build reobfJar --console plain
# Windows: gradlew.bat clean build reobfJar --console plain
python tools/test_pure_java.py
```

The official Gradle 8.8 wrapper pins the distribution SHA-256. Forge/JEI resolve from public repositories; EMI resolves from public Modrinth Maven using the immutable version ID `Axuu9I9R` (1.1.24+1.20.1+forge). Build outputs are under `build/libs`. The Python test runner needs Python 3 and JDK `javac`/`java` on PATH or `JAVA_HOME`, and writes classes only into a temporary directory.

`tests/` contains standalone pure-Java tests, outside the main source set. Private game worlds, logs, caches and game-launch acceptance/probe fixtures are intentionally omitted; no `-Pharness` production injection exists.

## License

Original mod implementation: **BSD-3-Clause**, copyright (c) 2026 **666nima**. See [LICENSE](LICENSE). This does not relicense Minecraft, Forge, optional mods, or Gradle. The official Gradle wrapper retains its original copyright headers and Apache-2.0 license; see [third-party notices](THIRD_PARTY_NOTICES.md).
