# 🔥 Formatação do Windows

Ferramenta destrutiva para resetar ou zerar completamente o Windows e discos.

## ⚠️ AVISOS CRÍTICOS

- **OPERAÇÕES IRREVERSÍVEIS**
- **FAÇA BACKUP ANTES DE USAR**
- **REQUER PRIVILÉGIOS DE ADMINISTRADOR**
- Use por sua conta e risco

## 📋 Funcionalidades

### 1️⃣ Formatação LIMPA (Restaurar Windows)
Restaura o Windows ao estado de fábrica usando ferramentas nativas do sistema.

**Opções:**
- **Manter arquivos pessoais**: Reinstala Windows, mantém documentos/fotos
- **Remover tudo**: Formatação completa do Windows

**O que acontece:**
- ✅ Reinstala o Windows
- ✅ Remove aplicativos instalados
- ✅ Restaura configurações padrão
- ✅ Mantém ou remove arquivos (você escolhe)

**Comandos usados:**
```powershell
systemreset.exe -cleanpc      # Manter arquivos
systemreset.exe -factoryreset # Remover tudo
```

**Quando usar:**
- PC lento demais
- Problemas graves no Windows
- Vender/doar PC
- Iniciar do zero

### 2️⃣ Formatação PROFUNDA (Zerar Disco)
Remove COMPLETAMENTE todos os dados de um disco físico escolhido.

**⚠️ EXTREMAMENTE PERIGOSO:**
- Permite escolher qual disco zerar
- Remove TODAS as partições
- Apaga TODOS os dados (incluindo Windows se escolher disco C:)
- PC não vai iniciar se zerar o disco do sistema

**Métodos disponíveis:**

1. **Limpeza rápida**
   - Remove partições
   - Dados recuperáveis com software especializado
   - Rápido (segundos/minutos)

2. **Limpeza segura**
   - Sobrescreve disco com zeros
   - Dados IRRECUPERÁVEIS
   - MUITO LENTO (horas em discos grandes)

**O que acontece:**
- 🗑️ Remove todas as partições do disco
- 🗑️ Apaga todos os dados
- 🗑️ Inicializa disco vazio (GPT)
- 🗑️ Opcional: sobrescreve com zeros

**Quando usar:**
- Vender/doar HD externo
- Zerar pendrive/HD secundário
- Preparar disco para revenda
- **NUNCA** no disco do Windows (a menos que queira reinstalar depois)

## 🔐 Auto-Elevação

Este script possui **auto-elevação automática**:

### Via Executar.bat
```batch
Executar.bat
```
- ✅ Detecta se não é admin
- ✅ Solicita UAC automaticamente
- ✅ Relança com privilégios elevados
- ✅ Não precisa clicar direito manualmente

### PowerShell Direto
```powershell
.\FormatacaoWindows.ps1
```
- ⚠️ Verifica privilégios
- ❌ Para se não for admin
- 📢 Pede para executar como admin

## 🚀 Como Usar

### Método Recomendado
```
Duplo-clique: Executar.bat
```
- Auto-elevação integrada
- UAC aparece automaticamente
- Interface interativa

### Menu de Opções
```
========================================
   FORMATACAO DO WINDOWS
========================================

Selecione o tipo de formatacao:

  1 - Formatacao LIMPA (Restaurar Windows)
      Reinstala o Windows mantendo ou removendo arquivos

  2 - Formatacao PROFUNDA (Zerar Disco)
      Remove TUDO de um disco especifico (inclui SO)

  0 - Sair
```

## 📖 Exemplos de Uso

### Cenário 1: Vender/Doar PC
```
1. Execute Executar.bat
2. Escolha [1] Formatação LIMPA
3. Escolha [2] Remover TUDO
4. Digite: CONFIRMO
5. PC reinicia e formata
```

### Cenário 2: PC lento (mantendo arquivos)
```
1. Execute Executar.bat
2. Escolha [1] Formatação LIMPA
3. Escolha [1] Manter arquivos
4. PC reinicia e reinstala Windows
```

### Cenário 3: Zerar HD externo
```
1. Execute Executar.bat
2. Escolha [2] Formatação PROFUNDA
3. Identifique número do HD externo (ex: Disco 1)
4. Digite: 1
5. Escolha método (rápido ou seguro)
6. Digite: ZERAR DISCO 1
7. Aguarde conclusão
```

### ⛔ Cenário 4: Zerar disco do Windows
```
ATENÇÃO: Só faça isso se tiver pendrive bootável para reinstalar!

1. Execute Executar.bat
2. Escolha [2] Formatação PROFUNDA
3. Identifique disco do Windows (geralmente Disco 0)
4. Script AVISA: "DISCO DO SISTEMA - PC NÃO VAI INICIAR"
5. Se realmente quiser, confirme
6. Disco zerado
7. PC não inicia mais
8. Reinstale Windows via pendrive bootável
```

## 🛡️ Camadas de Segurança

### Formatação LIMPA:
1. ✅ Escolha entre manter/remover
2. ✅ Confirmação final (CONFIRMO)
3. ✅ Aviso de reinicialização

### Formatação PROFUNDA:
1. ✅ Lista todos os discos
2. ✅ Identifica disco do sistema
3. ✅ Aviso CRÍTICO se escolher disco do Windows
4. ✅ Exibe detalhes do disco selecionado
5. ✅ Confirmação específica: `ZERAR DISCO X`
6. ✅ Não aceita confirmação errada

## ⚙️ Requisitos

- Windows 10/11
- PowerShell 5.1+
- **Privilégios de Administrador** (solicitado automaticamente)
- Backup de dados importantes

## 🆘 Troubleshooting

### UAC não aparece
- Verifique configurações do UAC no Windows
- Execute manualmente: botão direito → "Executar como Administrador"

### "Erro: Este script requer privilégios de Administrador"
- Use o `Executar.bat` ao invés do `.ps1` direto
- Ou: botão direito no .ps1 → Executar como Administrador

### Disco em uso (formatação profunda)
- Feche todos os programas
- Não é possível zerar disco C: enquanto Windows está rodando
- Para zerar disco do sistema: use pendrive bootável

### systemreset.exe não encontrado
- Recurso disponível apenas no Windows 10/11
- Verifique se Windows está atualizado

## 📁 Arquivos

```
02-Formatacao/
├── FormatacaoWindows.ps1    # Script principal
├── Executar.bat             # Executor com auto-elevação
└── README.md                # Esta documentação
```

## ⚠️ O Que Fazer Se Zerar o Disco Errado

### Se zerou disco do Windows (C:):
1. **PC não vai ligar** - esperado
2. **Você precisará:**
   - Pendrive bootável (8GB+)
   - Windows Media Creation Tool (site Microsoft)
   - Reinstalar Windows do zero

3. **Dados perdidos:**
   - Sem backup = perda permanente
   - Limpeza rápida: possível recuperar com software
   - Limpeza segura (zeros): **impossível** recuperar

## 📝 Notas Importantes

1. **Formatação Limpa** usa `systemreset.exe` (nativo do Windows)
2. **Formatação Profunda** usa cmdlets PowerShell (`Clear-Disk`, `Format-Volume`)
3. Limpeza segura pode demorar **HORAS** em discos grandes (500GB+ = 4-8h)
4. Zerar disco C: enquanto Windows roda **VAI FALHAR** (proteção)
5. Para zerar disco do sistema: boot por pendrive Windows

## 🔒 Privacidade e Segurança

### Formatação Limpa
- Dados podem ser recuperáveis
- Não é seguro para venda/descarte

### Formatação Profunda - Limpeza Segura
- Sobrescreve com zeros
- Dados irrecuperáveis
- **Recomendado** para venda/descarte de discos

---

**Última atualização:** 24/12/2025  
**⚠️ Use por sua conta e risco. Faça backup!**
