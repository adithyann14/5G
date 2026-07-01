# Keep the tile service explicitly (also auto-kept since it's referenced
# from the manifest, but explicit for clarity).
-keep class com.example.testingmenu.TestingMenuTileService { *; }

-dontwarn org.jetbrains.annotations.**
