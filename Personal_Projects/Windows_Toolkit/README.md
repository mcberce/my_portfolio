# 🛠️ Windows Toolkit - Kit de Ferramentas do Windows

Kit completo de ferramentas para limpeza e formatação do Windows.

## 📂 Estrutura do Projeto

```
WindowsToolkit/
├── README.md                    # Este arquivo
├── MenuPrincipal.bat           # Launcher principal
│
├── 01-Limpeza/                 # Ferramentas de limpeza
│   ├── CleanupTool.ps1         # Script de limpeza
│   ├── Executar.bat            # Executor com auto-elevação
│   └── README.md               # Documentação da limpeza
│
└── 02-Formatacao/              # Ferramentas de formatação
    ├── FormatacaoWindows.ps1   # Script de formatação
    ├── Executar.bat            # Executor com auto-elevação
    └── README.md               # Documentação da formatação
```

## 🚀 Início Rápido

### Opção 1: Menu Principal (Recomendado)
```
Duplo-clique em: MenuPrincipal.bat
```
- Interface interativa
- Selecione a ferramenta desejada
- Auto-elevação de privilégios quando necessário

### Opção 2: Executar Diretamente
```
01-Limpeza/Executar.bat         → Limpeza do sistema
02-Formatacao/Executar.bat      → Formatação
```

## 🔧 Ferramentas Disponíveis

### 01 - Limpeza do Sistema
Limpa arquivos temporários, cache e libera espaço em disco.

**Funcionalidades:**
- 🗑️ Arquivos temporários (Temp, Prefetch)
- 🗑️ Cache de navegadores (Chrome, Edge, Firefox)
- 🗑️ Lixeira
- 🗑️ Logs do Windows
- 🗑️ Windows Update Cache
- 🗑️ Thumbnails e cache de ícones

**Quando usar:**
- PC lento ou travando
- Pouco espaço em disco
- Manutenção preventiva

### 02 - Formatação
Restaura ou formata completamente o Windows.

**Funcionalidades:**
- 🔄 Formatação Limpa (resetar Windows)
  - Manter ou remover arquivos
- 💥 Formatação Profunda (zerar disco)
  - Limpeza rápida ou segura
  - Escolha qual disco zerar

**Quando usar:**
- Vender/doar PC
- Problemas graves no Windows
- Iniciar do zero

## 🔐 Privilégios e Segurança

### Auto-Elevação
Todos os scripts possuem **auto-elevação automática**:
- ✅ Pode dar duplo-clique normalmente
- ✅ Script solicita admin automaticamente
- ✅ UAC aparece apenas quando necessário

### Avisos de Segurança
- ⚠️ Limpeza: operações geralmente seguras
- 🔥 Formatação: **DESTRUTIVO** - múltiplas confirmações

## 📋 Requisitos

- Windows 10/11
- PowerShell 5.1+
- Privilégios de Administrador (solicitado automaticamente)

## 📖 Uso Detalhado

### Menu Principal
```batch
MenuPrincipal.bat
```
Interface interativa com todas as ferramentas.

### Executar Individualmente
```batch
cd 01-Limpeza
Executar.bat
```
ou
```batch
cd 02-Formatacao
Executar.bat
```

### PowerShell Direto (Avançado)
```powershell
# Limpeza
.\01-Limpeza\CleanupTool.ps1

# Formatação
.\02-Formatacao\FormatacaoWindows.ps1
```

## 🆘 Troubleshooting

### "Script desabilitado"
Os arquivos .bat já contornam isso automaticamente.

### UAC não aparece
- Verifique configurações do UAC
- Execute manualmente como admin (botão direito)

### Erros de permissão
- Feche programas que usam os arquivos
- Reinicie e tente novamente

## ⚙️ Customização

Cada ferramenta possui:
- Script PowerShell principal (`.ps1`)
- Executor batch (`.bat`)
- Documentação específica (`README.md`)

Edite os arquivos `.ps1` para customizar funcionalidades.

## 📝 Changelog

**v1.0** (24/12/2025)
- ✅ Estrutura organizada por pastas
- ✅ Menu principal unificado
- ✅ Auto-elevação de privilégios
- ✅ Documentação completa

---

**Desenvolvido em:** 24/12/2025  
**Licença:** Use por sua conta e risco
