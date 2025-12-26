# Sistema de Controle Financeiro

## Descrição
Sistema de controle financeiro pessoal desenvolvido em Java. Permite gerenciar contas, categorias e transações (receitas, despesas e transferências) com persistência de dados em arquivo.

## Funcionalidades

### ✅ Implementadas
- **Gerenciar Contas**: Adicionar contas com tipos (corrente, poupança, investimento, digital)
- **Gerenciar Categorias**: Criar categorias de receita, despesa e transferência
- **Registrar Transações**: Adicionar receitas, despesas e transferências com validação de saldo
- **Listar Transações**: Visualizar todas as transações registradas
- **Listar Contas**: Ver saldo de todas as contas
- **Resumo Financeiro**: Visualizar total de receitas, despesas e saldo líquido
- **Filtrar por Categoria**: Buscar transações por categoria específica
- **Deletar Transações**: Remover transações do sistema
- **Persistência de Dados**: Salva e carrega dados em arquivos (contas, categorias)
- **Relatórios**: Gerar relatórios por período

## Estrutura do Projeto

```
CF/
├── Model/
│   ├── Transacao.java      # Modelo de transação
│   ├── Conta.java          # Modelo de conta
│   └── Categoria.java      # Modelo de categoria
├── Controller/
│   ├── TransacaoService.java    # Lógica de negócio
│   ├── ArmazenamentoDados.java  # Persistência
│   └── RelatorioService.java    # Relatórios
├── Main.java               # Interface CLI
└── README.md              # Este arquivo
```

## Como Usar

### 1. Compilar
```bash
javac -d . Main.java Model/*.java Controller/*.java
```

### 2. Executar
```bash
java Main
```

### 3. Menu Principal
O sistema oferece um menu interativo com as seguintes opções:
1. Adicionar Conta
2. Adicionar Categoria
3. Adicionar Transação
4. Listar Transações
5. Listar Contas
6. Resumo Financeiro
7. Filtrar por Categoria
8. Deletar Transação
0. Sair (salva dados automaticamente)

## Tipos de Dados

### Conta
- ID único
- Nome da conta
- Saldo atual
- Tipo (CORRENTE, POUPANÇA, INVESTIMENTO, DIGITAL)

### Categoria
- ID único
- Nome
- Tipo (RECEITA, DESPESA, TRANSFERÊNCIA)

### Transação
- ID único
- Valor
- Categoria
- Conta de origem (opcional)
- Conta de destino (opcional)
- Data
- Descrição

## Validações

- ✓ Saldo insuficiente para despesa/transferência
- ✓ Transação sem categoria
- ✓ Receita sem conta destino
- ✓ Despesa sem conta origem
- ✓ Transferência sem origem ou destino

## Arquivos de Persistência

- `contas.dat` - Armazena dados de contas
- `categorias.dat` - Armazena dados de categorias
- `transacoes.dat` - Armazena dados de transações

## Tecnologias Utilizadas

- Java 8+
- Collections Framework
- LocalDate para datas
- File I/O para persistência

## Próximas Melhorias

- [ ] Banco de dados (SQL)
- [ ] Interface gráfica (JavaFX)
- [ ] Exportação para PDF/Excel
- [ ] Gráficos e visualizações
- [ ] Sistema de autenticação
- [ ] Backup automático

## Autor

Sistema desenvolvido como projeto educacional de controle financeiro.
