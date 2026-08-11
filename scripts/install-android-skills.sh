#!/usr/bin/env bash
# Copies vendored official Android skills to the Claude global skills folder.
# Skills already live in .agents/skills/android for this repo.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
SRC="$ROOT/.agents/skills/android"
DEST="${HOME}/.claude/skills/android"
if [[ ! -d "$SRC" ]]; then
  echo "Missing $SRC — clone https://github.com/android/skills first." >&2
  exit 1
fi
mkdir -p "$(dirname "$DEST")"
rm -rf "$DEST"
cp -a "$SRC" "$DEST"
rm -rf "$DEST/.git"
echo "Installed Android skills to $DEST"
