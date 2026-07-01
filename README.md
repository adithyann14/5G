# BypassTile

A one-purpose Quick Settings tile that flips charge-bypass mode by writing to:

```
/sys/class/power_supply/battery/input_suspend
```

`1` = bypass active (device runs off the charger directly, battery isn't
charging). `0` = bypass off (normal charging).

## There is no app, just a tile

This APK declares **zero activities**. There is no launcher icon, no app
drawer entry, no UI of any kind. The only component is a single
`TileService` (`BypassTileService.kt`). After installing it:

1. Open the notification shade → Quick Settings → **Edit** (pencil icon).
2. Find **"Bypass Charging"** in the list of available tiles and drag it
   into your active tiles.
3. Tap it.

## Root behavior

Root is requested **only on tap** — never just from opening the Quick
Settings panel. The first tap calls `su -c id` the same way RomFlasher does:

- **KernelSU / APatch** — succeeds silently if this app is already
  allow-listed in the manager app; otherwise the call simply fails until you
  allow-list it manually there.
- **Magisk** — the first `su` call triggers Magisk's native grant prompt
  automatically, the classic flow.

Once granted, the tile silently re-checks the live sysfs value every time
the Quick Settings panel opens (no further prompts — root is already
granted at that point).

## Why it doesn't try to remember state across reboots

`input_suspend` resets on its own on every reboot — that's kernel/hardware
behavior, not something this app controls. So the app doesn't try to track
or restore it either: every status update is a fresh `cat` of the live node.
If the on-screen state and the real hardware state ever look like they
disagree, the live node is always the answer — there's no app-side cache to
get out of sync.

## Building

CI (`.github/workflows/build.yml`) builds debug + an **unsigned** release
APK automatically via `gradle/actions/setup-gradle` (no committed
`gradle-wrapper.jar` needed). A signed-release job is included but
commented out — instructions for wiring up a keystore are inline in the
workflow file.

Locally:
```
gradle :app:assembleDebug
```

**Versions:** AGP 8.7.0, Gradle 8.9, Kotlin 2.0.21, compileSdk/targetSdk 36,
minSdk 29 (matches RomFlasher for consistency).

## Honesty note

`input_suspend` is not a standard/universal sysfs node — it exists on some
devices' battery power_supply driver and not others. If it's missing on
yours, the tile will show **"Not supported"** rather than silently failing.
