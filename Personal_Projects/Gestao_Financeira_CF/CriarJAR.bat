@echo off
title Criar Executavel JAR

echo ========================================
echo   CRIANDO EXECUTAVEL JAR...
echo ========================================
echo.

:: Compilar
echo [1/3] Compilando codigo...
javac -d bin MainGUI.java Model\*.java Controller\*.java View\*.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao!
    pause
    exit /b
)

:: Criar diretorio bin se nao existir
if not exist bin mkdir bin

:: Criar manifest
echo [2/3] Criando manifest...
echo Main-Class: MainGUI > bin\MANIFEST.MF
echo. >> bin\MANIFEST.MF

:: Criar JAR
echo [3/3] Criando arquivo JAR...
cd bin
jar cvfm ControleFinanceiro.jar MANIFEST.MF *.class Model\*.class Controller\*.class View\*.class
cd ..

if exist bin\ControleFinanceiro.jar (
    move bin\ControleFinanceiro.jar ControleFinanceiro.jar
    echo.
    echo ========================================
    echo   SUCESSO!
    echo ========================================
    echo.
    echo Arquivo criado: ControleFinanceiro.jar
    echo.
    echo Para executar, de duplo clique no arquivo JAR
    echo ou execute: java -jar ControleFinanceiro.jar
) else (
    echo [ERRO] Falha ao criar JAR!
)

echo.
pause
