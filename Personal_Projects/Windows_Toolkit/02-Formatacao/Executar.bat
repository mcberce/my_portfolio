@echo off
:: ============================================
:: Executor - Formatacao Windows
:: Com auto-elevacao de privilegios
:: ============================================

title Formatacao Windows - Windows Toolkit

:: Verificar se esta como administrador
net session >nul 2>&1
if %errorLevel% neq 0 (
    :: Nao e admin, solicitar elevacao
    echo Solicitando privilegios de Administrador...
    PowerShell -Command "Start-Process '%~f0' -Verb RunAs"
    exit /b
)

:: Ja e admin, executar script
PowerShell -NoProfile -ExecutionPolicy Bypass -File "%~dp0FormatacaoWindows.ps1"

:: Pausar se houver erro
if %errorLevel% neq 0 (
    echo.
    echo Erro ao executar o script!
    pause
)
