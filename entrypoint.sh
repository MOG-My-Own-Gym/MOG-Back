#!/bin/sh


SECRET_ZIP="/etc/secrets/wallet.zip"
UNZIP_DIR="/app/wallet"

if [ -f "$SECRET_ZIP" ]; then
  echo "Unzipping wallet.zip from secret files..."
  unzip /etc/secrets/wallet.zip -d /app/wallet
else
  echo "Secret wallet.zip not found, skipping unzip."
fi

exec java -jar app.jar
