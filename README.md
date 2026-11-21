# Chess Study Overlay (Android)

An offline Android floating chess study panel with a tap-to-move board, FEN/PGN loading, and local Stockfish analysis.

## Build the APK

1. Create a GitHub repository and upload the contents of this folder (the `.github` folder must be included).
2. Open **Actions** → **Build Android APK** → **Run workflow**.
3. When the run finishes, open it and download the `ChessStudyOverlay-debug-apk` artifact.
4. Extract the artifact ZIP; it contains `app-debug.apk`.
5. Install the APK on your Android device. Open it, tap **Enable Floating Panel**, and allow **Display over other apps**.

You can also open the project in Android Studio and build `app` → `assembleDebug`. The workflow fetches the pinned JavaScript chess rules library and Stockfish.js Lite single-thread engine, then packages both into the APK. Once built, analysis works offline.

## Features

- Movable Android floating panel (drag the header)
- Interactive chessboard, flip, undo, reset
- Local Stockfish Lite engine analysis (depth 13)
- FEN import and copy
- PGN import for post-game review
- No account, API key, or runtime network connection

## Important scope

This is a study and post-game analysis tool. It does not inspect Chess.com or other live games, automate moves, or attempt to bypass fair-play systems. Use analysis outside active rated games and follow each platform's rules.

## Licenses

Stockfish is GPL-3.0; its license text is packaged in the app assets by the build workflow. The `chess.js` library is BSD-2-Clause. See their upstream package metadata for full notices.
