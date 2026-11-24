#!/bin/sh
set -eu
cd "$(dirname "$0")/.."
npm --prefix frontend ci
npm --prefix frontend run build
mkdir -p backend/src/main/resources/static
cp -R frontend/dist/. backend/src/main/resources/static/
cd backend
./mvnw test-compile exec:java -Dexec.mainClass=com.eatinghalal.LocalDemoLauncher -Dexec.classpathScope=test
