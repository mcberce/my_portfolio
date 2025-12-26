# ============================================================
# Script de Limpeza Segura para Windows
# Versao: 1.0
# Data: 24/12/2025
# ============================================================

# Tema "hacker": verde sobre preto (sem emotes)
$host.UI.RawUI.BackgroundColor = 'Black'
$host.UI.RawUI.ForegroundColor = 'Green'
Clear-Host

# Detecta privilegios
function Test-IsAdmin {
    try {
        $id = [Security.Principal.WindowsIdentity]::GetCurrent()
        $p = New-Object Security.Principal.WindowsPrincipal($id)
        return $p.IsInRole([Security.Principal.WindowsBuiltinRole]::Administrator)
    } catch { return $false }
}

$global:IsAdmin = Test-IsAdmin

# Auto-elevacao quando necessario
function Elevate-Self {
    try {
        Start-Process -FilePath "powershell.exe" -ArgumentList "-NoLogo -NoProfile -ExecutionPolicy Bypass -File `"$PSCommandPath`"" -Verb RunAs | Out-Null
        exit
    } catch {
        Write-Host "[ERRO] Elevacao cancelada"; return
    }
}

# Header ASCII estilo terminal
function Draw-Header {
    Clear-Host
    Write-Host "############################################################"
    Write-Host "# RSET // WINDOWS CLEANUP TOOL v1.0"
    if ($global:IsAdmin) { Write-Host "# PRIV: ADMIN | MODE: SAFE" } else { Write-Host "# PRIV: STD   | MODE: SAFE" }
    Write-Host "############################################################"
    Write-Host ""
}

# Menu principal (estilo hacker)
function Draw-Menu {
    Write-Host "[ MENU ]"
    if ($global:IsAdmin) {
        Write-Host "[1] quick.run    -> TEMP + Lixeira + WindowsTemp"
        Write-Host "[2] full.run     -> TEMP + Lixeira + Logs + Cache + Windows Update"
    } else {
        Write-Host "[1] quick.run    -> TEMP + Lixeira  (WindowsTemp se admin)"
        Write-Host "[2] full.run     -> Requer elevacao (UAC)"
    }
    Write-Host "[3] temp.run     -> Somente arquivos temporarios"
    Write-Host "[4] exit         -> Sair"
    Write-Host ""
}

# Funcao para calcular tamanho de pasta
function Get-FolderSize {
    param([string]$Path)
    
    if (Test-Path $Path) {
        try {
            $size = (Get-ChildItem -Path $Path -Recurse -Force -ErrorAction SilentlyContinue | 
                     Measure-Object -Property Length -Sum -ErrorAction SilentlyContinue).Sum
            return [math]::Round($size / 1MB, 2)
        }
        catch {
            return 0
        }
    }
    return 0
}

# Funcao para limpar pasta
function Clean-Folder {
    param(
        [string]$Path,
        [string]$Description
    )
    
    if (Test-Path $Path) {
        Write-Host "[>] Limpando: $Description..." -NoNewline
        
        $sizeBefore = Get-FolderSize -Path $Path
        
        try {
            Get-ChildItem -Path $Path -Recurse -Force -ErrorAction SilentlyContinue | 
                Remove-Item -Force -Recurse -ErrorAction SilentlyContinue
            
            $sizeAfter = Get-FolderSize -Path $Path
            $freed = $sizeBefore - $sizeAfter
            
            Write-Host " [OK] Liberado: $freed MB"
            return $freed
        }
        catch {
            Write-Host " [ERRO]"
            return 0
        }
    }
    else {
        Write-Host "[i] $Description nao encontrado, ignorando..."
        return 0
    }
}

# Funcao para limpar lixeira
function Clean-RecycleBin {
    Write-Host "[>] Limpando Lixeira..." -NoNewline
    
    try {
        $shell = New-Object -ComObject Shell.Application
        $recycleBin = $shell.Namespace(0xA)
        $sizeBefore = ($recycleBin.Items() | Measure-Object -Property Size -Sum).Sum / 1MB
        
        Clear-RecycleBin -Force -ErrorAction Stop
        
        Write-Host " [OK] Liberado: $([math]::Round($sizeBefore, 2)) MB"
        return $sizeBefore
    }
    catch {
        Write-Host " [ERRO]"
        return 0
    }
}

# Funcao para limpar cache do Windows Update
function Clean-WindowsUpdateCache {
    Write-Host "[>] Limpando cache do Windows Update..." -NoNewline
    
    try {
        Stop-Service -Name wuauserv -Force -ErrorAction SilentlyContinue
        
        $path = "C:\Windows\SoftwareDistribution\Download"
        $sizeBefore = Get-FolderSize -Path $path
        
        Get-ChildItem -Path $path -Recurse -Force -ErrorAction SilentlyContinue | 
            Remove-Item -Force -Recurse -ErrorAction SilentlyContinue
        
        Start-Service -Name wuauserv -ErrorAction SilentlyContinue
        
        $sizeAfter = Get-FolderSize -Path $path
        $freed = $sizeBefore - $sizeAfter
        
        Write-Host " [OK] Liberado: $freed MB"
        return $freed
    }
    catch {
        Start-Service -Name wuauserv -ErrorAction SilentlyContinue
        Write-Host " [ERRO]"
        return 0
    }
}

# Funcao para limpeza rapida
function Start-QuickCleanup {
    Write-Host ""
    Write-Host "==[ RUN ]===================================================="
    Write-Host "  LIMPEZA RAPIDA"
    Write-Host "============================================================"
    Write-Host ""
    
    $totalFreed = 0
    
    $totalFreed += Clean-Folder -Path $env:TEMP -Description "Arquivos temporarios do usuario"
    if ($global:IsAdmin) {
        $totalFreed += Clean-Folder -Path "C:\Windows\Temp" -Description "Arquivos temporarios do Windows"
    } else {
        Write-Host "[i] Windows Temp requer privilegios elevados, ignorando..."
    }
    $totalFreed += Clean-RecycleBin
    
    Write-Host ""
    Write-Host "==[ DONE ]==================================================="
    Write-Host "  TOTAL LIBERADO: $([math]::Round($totalFreed, 2)) MB"
    Write-Host "============================================================"
    Write-Host ""
}

# Funcao para limpeza completa
function Start-CompleteCleanup {
    Write-Host ""
    Write-Host "==[ RUN ]===================================================="
    Write-Host "  LIMPEZA COMPLETA"
    Write-Host "============================================================"
    Write-Host ""
    
    $totalFreed = 0
    
    if (-not $global:IsAdmin) {
        Write-Host "[!] Modo completo requer privilegios elevados"
        $ans = Read-Host "Elevar agora? (S/N)"
        if ($ans -match '^[sS]$') { Elevate-Self } else { Write-Host "[i] Operacao cancelada"; return }
    }

    $totalFreed += Clean-Folder -Path $env:TEMP -Description "Arquivos temporarios do usuario"
    $totalFreed += Clean-Folder -Path "C:\Windows\Temp" -Description "Arquivos temporarios do Windows"
    $totalFreed += Clean-RecycleBin
    $totalFreed += Clean-WindowsUpdateCache
    $totalFreed += Clean-Folder -Path "C:\Windows\Logs\CBS" -Description "Logs CBS do Windows"
    $totalFreed += Clean-Folder -Path "C:\Windows\Logs\DISM" -Description "Logs DISM"
    $totalFreed += Clean-Folder -Path "$env:LOCALAPPDATA\Microsoft\Windows\Explorer" -Description "Cache de miniaturas"
    $totalFreed += Clean-Folder -Path "$env:LOCALAPPDATA\Microsoft\Windows\Caches" -Description "Cache de fontes"
    
    Write-Host ""
    Write-Host "==[ DONE ]==================================================="
    Write-Host "  TOTAL LIBERADO: $([math]::Round($totalFreed, 2)) MB"
    Write-Host "============================================================"
    Write-Host ""
}

# Funcao para limpar apenas TEMP
function Start-TempCleanup {
    Write-Host ""
    Write-Host "==[ RUN ]===================================================="
    Write-Host "  LIMPEZA TEMP"
    Write-Host "============================================================"
    Write-Host ""
    
    $totalFreed = 0
    
    $totalFreed += Clean-Folder -Path $env:TEMP -Description "Arquivos temporarios do usuario"
    if ($global:IsAdmin) {
        $totalFreed += Clean-Folder -Path "C:\Windows\Temp" -Description "Arquivos temporarios do Windows"
    } else {
        Write-Host "[i] Windows Temp requer privilegios elevados, ignorando..."
    }
    
    Write-Host ""
    Write-Host "==[ DONE ]==================================================="
    Write-Host "  TOTAL LIBERADO: $([math]::Round($totalFreed, 2)) MB"
    Write-Host "============================================================"
    Write-Host ""
}

# Funcao para mostrar preview
function Show-CleanupPreview {
    param([string]$Type)
    
    Write-Host ""
    Write-Host "==[ SCAN ]==================================================="
    Write-Host "  ANALISE DO SISTEMA"
    Write-Host "============================================================"
    Write-Host ""
    
    $tempUser = Get-FolderSize -Path $env:TEMP
    $tempWin = Get-FolderSize -Path "C:\Windows\Temp"
    
    Write-Host "[*] Arquivos temporarios do usuario: $tempUser MB"
    Write-Host "[*] Arquivos temporarios do Windows: $tempWin MB"
    
    if ($Type -eq "completa") {
        if ($global:IsAdmin) {
            $logs = Get-FolderSize -Path "C:\Windows\Logs\CBS"
            $logs += Get-FolderSize -Path "C:\Windows\Logs\DISM"
            Write-Host "[*] Logs do sistema: $logs MB"
            Write-Host "[*] Cache de miniaturas e fontes"
            Write-Host "[*] Cache do Windows Update"
        } else {
            Write-Host "[*] Logs do sistema   [requer admin]"
            Write-Host "[*] Cache miniaturas  [requer admin]"
            Write-Host "[*] Cache WinUpdate   [requer admin]"
        }
    }
    
    Write-Host "[*] Lixeira"
    
    $total = $tempUser + $tempWin
    Write-Host ""
    Write-Host "  ESPACO ESTIMADO: ~$([math]::Round($total, 2)) MB"
    Write-Host ""
    Write-Host "============================================================"
}

# Loop principal
do {
    Draw-Header
    Draw-Menu
    $choice = Read-Host "Opcao >"
    
    switch ($choice) {
        "1" {
            Show-CleanupPreview -Type "rapida"
            $confirm = Read-Host "`nDeseja continuar? (S/N)"
            if ($confirm -eq "S" -or $confirm -eq "s") {
                Start-QuickCleanup
                Read-Host "`nPressione ENTER para voltar ao menu"
            }
        }
        "2" {
            Show-CleanupPreview -Type "completa"
            $confirm = Read-Host "`nDeseja continuar? (S/N)"
            if ($confirm -eq "S" -or $confirm -eq "s") {
                Start-CompleteCleanup
                Read-Host "`nPressione ENTER para voltar ao menu"
            }
        }
        "3" {
            Show-CleanupPreview -Type "temp"
            $confirm = Read-Host "`nDeseja continuar? (S/N)"
            if ($confirm -eq "S" -or $confirm -eq "s") {
                Start-TempCleanup
                Read-Host "`nPressione ENTER para voltar ao menu"
            }
        }
        "4" {
            Write-Host "`nEncerrando..." 
            Start-Sleep -Seconds 1
            exit
        }
        default {
            Write-Host "`n[ERRO] Opcao invalida! Tente novamente."
            Start-Sleep -Seconds 2
        }
    }
    
} while ($true)
