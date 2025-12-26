@echo off
title Sistema de Controle Financeiro
cls

echo ========================================
echo   COMPILANDO O SISTEMA...
echo ========================================
echo.

javac -d . MainGUI.java Model\*.java Controller\*.java View\*.java 2>&1

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    echo Verifique se o Java JDK esta instalado.
    echo.
    pause
    exit /b
)

echo.
echo ========================================
echo   INICIANDO O SISTEMA...
echo ========================================
echo.

java MainGUI

echo.
echo.
echo ========================================
echo   Sistema encerrado.
echo ========================================
pause
