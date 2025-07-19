#!/bin/sh

SECRET_ZIP="/run/secrets/wallet.zip"
UNZIP_DIR="/app/wallet"

if [ -f "$SECRET_ZIP" ]; then
  echo "Unzipping wallet.zip from secret files..."
  mkdir -p "$UNZIP_DIR"
  unzip -o "$SECRET_ZIP" -d "$UNZIP_DIR"
else
  echo "Secret wallet.zip not found, skipping unzip."
fi

exec java -jar app.jar
