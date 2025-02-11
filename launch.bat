@echo off
docker info >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo Docker n'est pas en cours d'exécution ou n'est pas installé.
    exit /b 1
)
./gradlew.bat run