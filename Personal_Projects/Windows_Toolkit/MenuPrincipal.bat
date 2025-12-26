@echo off
:: ============================================
:: Windows Toolkit - Menu Principal
:: ============================================
:: Auto-elevacao de privilegios incluida
:: ============================================

title Windows Toolkit - Menu Principal

:check_permissions
    net session >nul 2>&1
    if %errorLevel% == 0 (
        goto :menu
    ) else (
        goto :elevate
    )

:elevate
    echo Solicitando privilegios de Administrador...
    powershell -Command "Start-Process '%~f0' -Verb RunAs"
    exit /b

:menu
    cls
    echo ============================================
    echo    WINDOWS TOOLKIT - Menu Principal
    echo ============================================
    echo.
    echo  Selecione a ferramenta desejada:
    echo.
    echo  [1] Limpeza do Sistema
    echo      Remove arquivos temporarios e cache
    echo.
    echo  [2] Formatacao
    echo      Restaurar ou formatar Windows
    echo.
    echo  [0] Sair
    echo.
    echo ============================================
    echo.
    
    set /p opcao="Digite sua opcao: "
    
    if "%opcao%"=="1" goto limpeza
    if "%opcao%"=="2" goto formatacao
    if "%opcao%"=="0" goto sair
    
    echo.
    echo Opcao invalida!
    timeout /t 2 >nul
    goto menu

:limpeza
    echo.
    echo Iniciando Limpeza do Sistema...
    timeout /t 1 >nul
    cd /d "%~dp0\01-Limpeza"
    call Executar.bat
    goto menu

:formatacao
    echo.
    echo Iniciando Formatacao...
    timeout /t 1 >nul
    cd /d "%~dp0\02-Formatacao"
    call Executar.bat
    goto menu

:sair
    echo.
    echo Encerrando...
    timeout /t 1 >nul
    exit /b 0
