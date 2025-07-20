#!/bin/sh

SECRET_B64_FILE="/etc/secrets/wallet.b64"
ZIP_PATH="/app/wallet.zip"
UNZIP_DIR="/app/wallet"

if [ -f "$SECRET_B64_FILE" ]; then
  echo "Decoding wallet.zip from secret file..."
  cat "$SECRET_B64_FILE" | base64 -d > "$ZIP_PATH"

  mkdir -p "$UNZIP_DIR"
  unzip "$ZIP_PATH" -d "$UNZIP_DIR"
  
  echo "checking unziped files..."
  ls -al "$UNZIP_DIR"
else
  echo "Secret file $SECRET_B64_FILE not found. Skipping unzip."
fi

exec java -jar app.jar
