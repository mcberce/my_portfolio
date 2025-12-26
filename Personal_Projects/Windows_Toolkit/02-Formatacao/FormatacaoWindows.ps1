# ============================================
# Script de Formatação do Windows
# ============================================
# ATENÇÃO: Este script realiza operações DESTRUTIVAS
# Use com EXTREMO CUIDADO!
# ============================================

# Requer privilégios de administrador
if (-NOT ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) {
    Write-Host "ERRO: Este script requer privilegios de Administrador!" -ForegroundColor Red
    Write-Host "Clique com botao direito e selecione 'Executar como Administrador'" -ForegroundColor Yellow
    pause
    exit
}

function Show-Banner {
    Clear-Host
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host "   FORMATACAO DO WINDOWS" -ForegroundColor Cyan
    Write-Host "========================================" -ForegroundColor Cyan
    Write-Host ""
}

function Reset-WindowsClean {
    Show-Banner
    Write-Host "FORMATACAO LIMPA - Restaurar Windows" -ForegroundColor Yellow
    Write-Host "========================================" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Esta opcao vai:" -ForegroundColor White
    Write-Host "  [+] Reinstalar o Windows" -ForegroundColor Green
    Write-Host "  [+] Manter ou remover seus arquivos pessoais (voce escolhe)" -ForegroundColor Green
    Write-Host "  [+] Remover aplicativos instalados" -ForegroundColor Green
    Write-Host "  [+] Restaurar configuracoes padrao" -ForegroundColor Green
    Write-Host ""
    Write-Host "Escolha uma opcao:" -ForegroundColor Cyan
    Write-Host "  1 - Manter meus arquivos pessoais" -ForegroundColor White
    Write-Host "  2 - Remover TUDO (formatacao completa)" -ForegroundColor White
    Write-Host "  0 - Voltar ao menu principal" -ForegroundColor Gray
    Write-Host ""
    
    $escolha = Read-Host "Digite sua opcao"
    
    switch ($escolha) {
        "1" {
            Write-Host ""
            Write-Host "Iniciando Reset do Windows (mantendo arquivos)..." -ForegroundColor Green
            Write-Host "AVISO: O computador vai reiniciar!" -ForegroundColor Red
            Start-Sleep -Seconds 3
            
            # systemreset -cleanpc (manter arquivos)
            & systemreset.exe -cleanpc
        }
        "2" {
            Write-Host ""
            Write-Host "ULTIMA CONFIRMACAO!" -ForegroundColor Red
            Write-Host "Isso vai REMOVER TUDO do Windows!" -ForegroundColor Red
            $confirmacao = Read-Host "Digite 'CONFIRMO' para continuar"
            
            if ($confirmacao -eq "CONFIRMO") {
                Write-Host "Iniciando Reset completo do Windows..." -ForegroundColor Green
                Write-Host "AVISO: O computador vai reiniciar!" -ForegroundColor Red
                Start-Sleep -Seconds 3
                
                # systemreset -factoryreset (remover tudo)
                & systemreset.exe -factoryreset
            } else {
                Write-Host "Operacao cancelada." -ForegroundColor Yellow
                Start-Sleep -Seconds 2
            }
        }
        "0" {
            return
        }
        default {
            Write-Host "Opcao invalida!" -ForegroundColor Red
            Start-Sleep -Seconds 2
            Reset-WindowsClean
        }
    }
}

function Format-DeepDiskWipe {
    Show-Banner
    Write-Host "FORMATACAO PROFUNDA - Limpar Disco Completo" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "PERIGO: Esta opcao ZERA completamente um disco!" -ForegroundColor Red
    Write-Host "Todos os dados serao PERMANENTEMENTE perdidos!" -ForegroundColor Red
    Write-Host ""
    
    # Listar discos físicos
    Write-Host "Discos disponiveis no sistema:" -ForegroundColor Cyan
    Write-Host ""
    
    $discos = Get-Disk | Select-Object Number, FriendlyName, @{Name="Tamanho";Expression={[math]::Round($_.Size/1GB,2)}}, PartitionStyle
    
    $discos | Format-Table -AutoSize | Out-String | Write-Host
    
    # Identificar disco do sistema
    $sistemaDisk = Get-Partition | Where-Object {$_.DriveLetter -eq 'C'} | Get-Disk
    Write-Host "ATENCAO: Disco $($sistemaDisk.Number) contem o Windows (C:)!" -ForegroundColor Yellow
    Write-Host ""
    
    Write-Host "Digite o numero do disco que deseja ZERAR: " -ForegroundColor White -NoNewline
    $diskNumber = Read-Host
    
    # Validar entrada
    if ($diskNumber -notmatch '^\d+$') {
        Write-Host "Numero invalido!" -ForegroundColor Red
        Start-Sleep -Seconds 2
        return
    }
    
    $diskNumber = [int]$diskNumber
    
    # Verificar se disco existe
    $discoSelecionado = Get-Disk -Number $diskNumber -ErrorAction SilentlyContinue
    if (-not $discoSelecionado) {
        Write-Host "Disco nao encontrado!" -ForegroundColor Red
        Start-Sleep -Seconds 2
        return
    }
    
    # Confirmações múltiplas
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "DISCO SELECIONADO:" -ForegroundColor Red
    Write-Host "  Numero: $($discoSelecionado.Number)" -ForegroundColor White
    Write-Host "  Nome: $($discoSelecionado.FriendlyName)" -ForegroundColor White
    Write-Host "  Tamanho: $([math]::Round($discoSelecionado.Size/1GB,2)) GB" -ForegroundColor White
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    
    if ($diskNumber -eq $sistemaDisk.Number) {
        Write-Host "AVISO CRITICO: Este e o disco do sistema Windows!" -ForegroundColor Red
        Write-Host "Se continuar, o Windows sera REMOVIDO e o PC nao vai iniciar!" -ForegroundColor Red
        Write-Host ""
    }
    
    # Detectar tipo de disco (USB = pendrive, outros = HD/SSD)
    $isUSB = $discoSelecionado.BusType -eq 'USB'
    
    Write-Host "Escolha o metodo de limpeza:" -ForegroundColor Cyan
    Write-Host "  1 - Limpeza rapida (apenas remove particoes)" -ForegroundColor White
    Write-Host "  2 - Limpeza segura (sobrescreve com zeros - LENTO)" -ForegroundColor White
    Write-Host "  0 - Cancelar" -ForegroundColor Gray
    Write-Host ""
    
    $metodo = Read-Host "Digite sua opcao"
    
    if ($metodo -eq "0") {
        Write-Host "Operacao cancelada." -ForegroundColor Yellow
        Start-Sleep -Seconds 2
        return
    }
    
    # Se for pendrive (USB), perguntar sistema de arquivos
    $fileSystem = "NTFS"  # Padrão para HD/SSD
    
    if ($isUSB) {
        Write-Host ""
        Write-Host "Sistema de arquivos:" -ForegroundColor Cyan
        Write-Host "  1 - FAT32 (compativel: Windows/Mac/Linux/TV/Console)" -ForegroundColor White
        Write-Host "      Limite: arquivos ate 4GB" -ForegroundColor Gray
        Write-Host "  2 - NTFS  (apenas Windows, sem limite de tamanho)" -ForegroundColor White
        Write-Host ""
        
        $fsChoice = Read-Host "Digite sua opcao"
        
        if ($fsChoice -eq "1") {
            $fileSystem = "FAT32"
        } else {
            $fileSystem = "NTFS"
        }
    }
    
    # Confirmação final
    Write-Host ""
    Write-Host "CONFIRMACAO FINAL!" -ForegroundColor Red -BackgroundColor White
    Write-Host "Isso vai DESTRUIR TODOS OS DADOS do Disco $diskNumber!" -ForegroundColor Red
    Write-Host "Digite 'ZERAR DISCO $diskNumber' para confirmar: " -ForegroundColor Yellow -NoNewline
    $confirmacao = Read-Host
    
    if ($confirmacao -ne "ZERAR DISCO $diskNumber") {
        Write-Host "Confirmacao incorreta. Operacao cancelada." -ForegroundColor Yellow
        Start-Sleep -Seconds 2
        return
    }
    
    # Executar formatação
    try {
        Write-Host ""
        Write-Host "Iniciando limpeza do disco $diskNumber..." -ForegroundColor Green
        Write-Host ""
        
        # Remover todas as partições
        Write-Host "[1/3] Removendo todas as particoes..." -ForegroundColor Cyan
        Get-Disk -Number $diskNumber | Clear-Disk -RemoveData -RemoveOEM -Confirm:$false -ErrorAction Stop
        
        Write-Host "[2/3] Inicializando disco..." -ForegroundColor Cyan
        
        # Verificar se disco precisa ser inicializado
        $disk = Get-Disk -Number $diskNumber
        if ($disk.PartitionStyle -eq 'RAW') {
            Initialize-Disk -Number $diskNumber -PartitionStyle GPT -ErrorAction Stop
        } else {
            Write-Host "      Disco ja inicializado, pulando..." -ForegroundColor Gray
        }
        
        Write-Host "[3/3] Criando particao e formatando..." -ForegroundColor Cyan
        
        # Criar partição
        $partition = New-Partition -DiskNumber $diskNumber -UseMaximumSize -AssignDriveLetter -ErrorAction Stop
        
        # Determinar label baseado no tipo de disco
        if ($isUSB) {
            $label = "Pendrive"
        } else {
            $label = "Disco"
        }
        
        Write-Host "      Formatando como $fileSystem..." -ForegroundColor Gray
        
        if ($metodo -eq "2") {
            # Limpeza segura (formatação completa com zeros)
            Write-Host "      Modo SEGURO: Sobrescrevendo com zeros (PODE DEMORAR HORAS)..." -ForegroundColor Yellow
            Format-Volume -Partition $partition -FileSystem $fileSystem -NewFileSystemLabel $label -Full -Confirm:$false -ErrorAction Stop
        } else {
            # Limpeza rápida
            Format-Volume -Partition $partition -FileSystem $fileSystem -NewFileSystemLabel $label -Confirm:$false -ErrorAction Stop
        }
        
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "DISCO $diskNumber ZERADO COM SUCESSO!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "O disco agora esta completamente vazio e pronto para uso." -ForegroundColor White
        
    } catch {
        Write-Host ""
        Write-Host "ERRO ao limpar disco: $($_.Exception.Message)" -ForegroundColor Red
    }
    
    Write-Host ""
    Read-Host "Pressione Enter para continuar"
}

# Menu Principal
function Show-Menu {
    do {
        Show-Banner
        Write-Host "Selecione o tipo de formatacao:" -ForegroundColor White
        Write-Host ""
        Write-Host "  1 - Formatacao LIMPA (Restaurar Windows)" -ForegroundColor Green
        Write-Host "      Reinstala o Windows mantendo ou removendo arquivos" -ForegroundColor Gray
        Write-Host ""
        Write-Host "  2 - Formatacao PROFUNDA (Zerar Disco)" -ForegroundColor Red
        Write-Host "      Remove TUDO de um disco especifico (inclui SO)" -ForegroundColor Gray
        Write-Host ""
        Write-Host "  0 - Sair" -ForegroundColor Yellow
        Write-Host ""
        
        $opcao = Read-Host "Digite sua opcao"
        
        switch ($opcao) {
            "1" {
                Reset-WindowsClean
            }
            "2" {
                Format-DeepDiskWipe
            }
            "0" {
                Write-Host "Saindo..." -ForegroundColor Green
                exit
            }
            default {
                Write-Host "Opcao invalida!" -ForegroundColor Red
                Start-Sleep -Seconds 2
            }
        }
    } while ($true)
}

# Iniciar o script
Show-Menu
