@echo off
:: ============================================
:: Executor - Limpeza do Sistema
:: Com auto-elevacao de privilegios
:: ============================================

title Limpeza do Sistema - Windows Toolkit

:: Tentar executar o script PowerShell
PowerShell -NoProfile -ExecutionPolicy Bypass -File "%~dp0CleanupTool.ps1"

:: Se falhar, pausar para ver erro
if %errorLevel% neq 0 (
    echo.
    echo Erro ao executar o script!
    pause
)
