# 🧹 Limpeza do Sistema Windows

Ferramenta de limpeza segura para liberar espaço em disco no Windows.

## 📋 Funcionalidades

### 1. Limpeza Rápida (quick.run)
Remove arquivos temporários básicos de forma segura.

**O que limpa:**
- ✅ Arquivos temporários do usuário (`%TEMP%`)
- ✅ Lixeira
- ✅ Arquivos temporários do Windows (se executar como admin)

**Quando usar:**
- Manutenção preventiva regular
- Liberar espaço rapidamente
- Uso diário

### 2. Limpeza Completa (full.run)
Limpeza profunda incluindo cache e logs do sistema.

**O que limpa:**
- ✅ Tudo da limpeza rápida
- ✅ Cache do Windows Update
- ✅ Logs CBS do Windows
- ✅ Logs DISM
- ✅ Cache de miniaturas
- ✅ Cache de fontes

**Quando usar:**
- PC lento ou travando
- Problemas após atualizações
- Limpeza mensal/trimestral

**⚠️ Requer:** Privilégios de administrador

### 3. Limpeza TEMP (temp.run)
Remove apenas arquivos temporários.

**O que limpa:**
- ✅ `%TEMP%` do usuário
- ✅ `C:\Windows\Temp` (se admin)

**Quando usar:**
- Instalações falharam
- Espaço quase cheio
- Limpeza focada

## 🔐 Auto-Elevação

Este script possui **auto-elevação inteligente**:

### Modo Usuário Normal
- ✅ Roda sem admin
- ✅ Limpa o que é possível
- ⚠️ Pula itens que requerem admin
- 📢 Informa o que foi ignorado

### Modo Completo (full.run)
- 🔑 Solicita elevação automaticamente via UAC
- ✅ Não precisa clicar direito → "Executar como admin"
- ✅ Pergunta se quer elevar quando necessário

## 🚀 Como Usar

### Duplo-Clique Normal
```
Executar.bat
```
- Interface interativa
- Escolha o tipo de limpeza
- Preview do espaço a ser liberado
- Confirmação antes de limpar

### PowerShell Direto
```powershell
.\CleanupTool.ps1
```

## 🎨 Interface

Tema **hacker-style**:
- Fundo preto, texto verde
- ASCII art header
- Comandos estilo terminal
- Preview com scan do sistema

```
############################################################
# RSET // WINDOWS CLEANUP TOOL v1.0
# PRIV: STD   | MODE: SAFE
############################################################

[ MENU ]
[1] quick.run    -> TEMP + Lixeira  (WindowsTemp se admin)
[2] full.run     -> Requer elevacao (UAC)
[3] temp.run     -> Somente arquivos temporarios
[4] exit         -> Sair

Opcao >
```

## 📊 Preview de Limpeza

Antes de limpar, o script mostra:
- Tamanho dos arquivos temporários do usuário
- Tamanho dos arquivos temporários do Windows
- Logs do sistema (se aplicável)
- Estimativa total de espaço a liberar

```
==[ SCAN ]===================================================
  ANALISE DO SISTEMA
============================================================

[*] Arquivos temporarios do usuario: 234.56 MB
[*] Arquivos temporarios do Windows: 89.12 MB
[*] Lixeira

  ESPACO ESTIMADO: ~323.68 MB

============================================================
```

## ⚙️ Segurança

### Operações Seguras
- ✅ Apenas remove arquivos temporários
- ✅ Não toca em arquivos do sistema críticos
- ✅ Não remove programas ou configurações
- ✅ Confirmação antes de executar

### Erros Silenciosos
- Se arquivo estiver em uso → ignora
- Se pasta não existir → ignora
- Se permissão negada → ignora
- Continue limpando outros itens

## 🛠️ Troubleshooting

### "Alguns arquivos não foram removidos"
**Normal:** Arquivos em uso são protegidos pelo Windows.

**Solução:** Feche programas e tente novamente.

### "Windows Temp requer privilégios elevados"
**Normal:** Rodando como usuário padrão.

**Solução:** 
- Execute como admin, ou
- Escolha opção 2 (full.run) para auto-elevar

### Script não abre
**Problema:** Execution Policy do PowerShell.

**Solução:** Use o `Executar.bat` (já contorna isso automaticamente)

## 📁 Arquivos

```
01-Limpeza/
├── CleanupTool.ps1    # Script principal PowerShell
├── Executar.bat       # Executor com bypass de políticas
└── README.md          # Esta documentação
```

## 🔄 Frequência Recomendada

| Tipo | Frequência | Motivo |
|------|-----------|--------|
| Quick | Semanal | Manutenção preventiva |
| Full | Mensal | Limpeza profunda |
| Temp | Quando necessário | Problemas específicos |

## ⚠️ O Que NÃO Faz

- ❌ Não remove programas instalados
- ❌ Não altera configurações do Windows
- ❌ Não remove drivers
- ❌ Não formata nada
- ❌ Não mexe em arquivos pessoais (documentos, fotos, etc.)

## 📈 Espaço Liberado Típico

| Sistema | Quick | Full |
|---------|-------|------|
| PC novo | 50-200 MB | 100-500 MB |
| PC em uso (6 meses) | 500 MB - 2 GB | 1-5 GB |
| PC antigo (anos) | 1-5 GB | 5-20 GB |

*Valores variam muito conforme uso e histórico*

---

**Última atualização:** 24/12/2025
