import Controller.TransacaoService;
import Controller.ArmazenamentoDados;
import Model.Transacao;
import Model.Conta;
import Model.Categoria;
import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    static TransacaoService service;
    static Scanner sc;

    public static void main(String[] args) {
        service = new TransacaoService();
        sc = new Scanner(System.in);

        // Carregar dados ao iniciar
        ArmazenamentoDados.carregarDados(service);

        boolean rodando = true;

        while (rodando) {
            System.out.println("\n" + "=".repeat(45));
            System.out.println("   SISTEMA DE CONTROLE FINANCEIRO");
            System.out.println("=".repeat(45));
            System.out.println("1.  Adicionar Conta");
            System.out.println("2.  Adicionar Categoria");
            System.out.println("3.  Adicionar Transação");
            System.out.println("4.  Listar Transações");
            System.out.println("5.  Listar Contas e Saldos");
            System.out.println("6.  Ver Resumo Financeiro");
            System.out.println("7.  Filtrar Transações por Categoria");
            System.out.println("8.  Deletar Transação");
            System.out.println("0.  Sair (salvar dados)");
            System.out.println("=".repeat(45));
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        adicionarConta();
                        break;
                    case 2:
                        adicionarCategoria();
                        break;
                    case 3:
                        adicionarTransacao();
                        break;
                    case 4:
                        listarTransacoes();
                        break;
                    case 5:
                        listarContas();
                        break;
                    case 6:
                        verResumo();
                        break;
                    case 7:
                        filtrarPorCategoria();
                        break;
                    case 8:
                        deletarTransacao();
                        break;
                    case 0:
                        ArmazenamentoDados.salvarDados(service);
                        rodando = false;
                        System.out.println("\n✓ Sistema finalizado com sucesso!");
                        break;
                    default:
                        System.out.println("❌ Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("❌ Erro: entrada inválida!");
                sc.nextLine();
            }
        }
        sc.close();
    }

    private static void adicionarConta() {
        System.out.println("\n--- ADICIONAR CONTA ---");
        System.out.print("Nome da conta: ");
        String nome = sc.nextLine();

        System.out.print("Saldo inicial (R$): ");
        try {
            double saldo = sc.nextDouble();
            sc.nextLine();

            System.out.println("Tipo de conta:");
            System.out.println("1. CORRENTE");
            System.out.println("2. POUPANÇA");
            System.out.println("3. INVESTIMENTO");
            System.out.println("4. DIGITAL");
            System.out.print("Escolha: ");
            int tipoOpcao = sc.nextInt();
            sc.nextLine();

            Conta.TipoConta tipo;
            switch (tipoOpcao) {
                case 1:
                    tipo = Conta.TipoConta.CORRENTE;
                    break;
                case 2:
                    tipo = Conta.TipoConta.POUPANCA;
                    break;
                case 3:
                    tipo = Conta.TipoConta.INVESTIMENTO;
                    break;
                case 4:
                    tipo = Conta.TipoConta.DIGITAL;
                    break;
                default:
                    tipo = Conta.TipoConta.DIGITAL;
            }

            Conta conta = new Conta(service.getProxIdConta(), nome, saldo, tipo);
            service.adicionarConta(conta);
            System.out.println("✓ Conta adicionada com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar conta!");
            sc.nextLine();
        }
    }

    private static void adicionarCategoria() {
        System.out.println("\n--- ADICIONAR CATEGORIA ---");
        System.out.print("Nome da categoria: ");
        String nome = sc.nextLine();

        System.out.println("Tipo:");
        System.out.println("1. RECEITA");
        System.out.println("2. DESPESA");
        System.out.println("3. TRANSFERÊNCIA");
        System.out.print("Escolha: ");
        int tipoOpcao = sc.nextInt();
        sc.nextLine();

        Categoria.TipoCategoria tipo;
        switch (tipoOpcao) {
            case 1:
                tipo = Categoria.TipoCategoria.RECEITA;
                break;
            case 2:
                tipo = Categoria.TipoCategoria.DESPESA;
                break;
            case 3:
                tipo = Categoria.TipoCategoria.TRANSFERENCIA;
                break;
            default:
                tipo = Categoria.TipoCategoria.DESPESA;
        }

        Categoria categoria = new Categoria(service.getProxIdCategoria(), nome, tipo);
        service.adicionarCategoria(categoria);
        System.out.println("✓ Categoria adicionada com sucesso!");
    }

    private static void adicionarTransacao() {
        if (!service.verificarContas() || !service.verificarCategorias()) {
            System.out.println("❌ Adicione contas e categorias antes!");
            return;
        }

        System.out.println("\n--- ADICIONAR TRANSAÇÃO ---");
        System.out.print("Valor (R$): ");
        double valor = sc.nextDouble();
        sc.nextLine();

        System.out.println("\nCategorias:");
        java.util.List<Categoria> cats = service.listarCategorias();
        for (int i = 0; i < cats.size(); i++) {
            System.out.println((i + 1) + ". " + cats.get(i).getNomeCategoria() + " (" + cats.get(i).getTipoCategoria() + ")");
        }
        System.out.print("Escolha: ");
        int catIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (catIndex < 0 || catIndex >= cats.size()) {
            System.out.println("❌ Categoria inválida!");
            return;
        }

        Categoria categoria = cats.get(catIndex);

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        Conta contaOrigem = null;
        Conta contaDestino = null;

        java.util.List<Conta> contas = service.listarContas();

        if (categoria.getTipoCategoria() != Categoria.TipoCategoria.RECEITA) {
            System.out.println("\nContas disponíveis:");
            for (int i = 0; i < contas.size(); i++) {
                System.out.println((i + 1) + ". " + contas.get(i).getNomeConta() + " (Saldo: R$ " + contas.get(i).getSaldoAtual() + ")");
            }
            System.out.print("Conta origem: ");
            int origIndex = sc.nextInt() - 1;
            sc.nextLine();
            if (origIndex >= 0 && origIndex < contas.size()) {
                contaOrigem = contas.get(origIndex);
            }
        }

        if (categoria.getTipoCategoria() != Categoria.TipoCategoria.DESPESA) {
            System.out.println("\nContas disponíveis:");
            for (int i = 0; i < contas.size(); i++) {
                System.out.println((i + 1) + ". " + contas.get(i).getNomeConta() + " (Saldo: R$ " + contas.get(i).getSaldoAtual() + ")");
            }
            System.out.print("Conta destino: ");
            int destIndex = sc.nextInt() - 1;
            sc.nextLine();
            if (destIndex >= 0 && destIndex < contas.size()) {
                contaDestino = contas.get(destIndex);
            }
        }

        Transacao transacao = new Transacao(service.getProxIdTransacao(), valor, categoria, contaOrigem, contaDestino, LocalDate.now(), descricao);
        service.adicionarTransacao(transacao);
    }

    private static void listarTransacoes() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TODAS AS TRANSAÇÕES");
        System.out.println("=".repeat(80));

        java.util.List<Transacao> transacoes = service.listarTransacoes();
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
            return;
        }

        for (Transacao t : transacoes) {
            System.out.printf("ID: %d | Valor: R$ %.2f | Categoria: %s | Data: %s | %s%n",
                t.getId(), t.getValor(), t.getCategoria().getNomeCategoria(), t.getData(), t.getDescricao());
        }
        System.out.println("=".repeat(80));
    }

    private static void listarContas() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CONTAS E SALDOS");
        System.out.println("=".repeat(60));

        java.util.List<Conta> contas = service.listarContas();
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta registrada.");
            return;
        }

        for (Conta c : contas) {
            System.out.printf("%s (%s) - Saldo: R$ %.2f%n", c.getNomeConta(), c.getTipoConta(), c.getSaldoAtual());
        }
        System.out.println("=".repeat(60));
    }

    private static void verResumo() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO FINANCEIRO");
        System.out.println("=".repeat(50));
        System.out.printf("Total de Receitas: R$ %.2f%n", service.calcularTotalReceitas());
        System.out.printf("Total de Despesas: R$ %.2f%n", service.calcularTotalDespesas());
        System.out.printf("Saldo Líquido: R$ %.2f%n", service.calcularTotalReceitas() - service.calcularTotalDespesas());
        System.out.println("=".repeat(50));
    }

    private static void filtrarPorCategoria() {
        System.out.println("\n--- FILTRAR POR CATEGORIA ---");
        java.util.List<Categoria> cats = service.listarCategorias();

        if (cats.isEmpty()) {
            System.out.println("Nenhuma categoria registrada.");
            return;
        }

        for (int i = 0; i < cats.size(); i++) {
            System.out.println((i + 1) + ". " + cats.get(i).getNomeCategoria());
        }
        System.out.print("Escolha: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        if (index < 0 || index >= cats.size()) {
            System.out.println("❌ Categoria inválida!");
            return;
        }

        String nomeCat = cats.get(index).getNomeCategoria();
        System.out.println("\n" + "=".repeat(70));
        System.out.println("TRANSAÇÕES - " + nomeCat);
        System.out.println("=".repeat(70));

        java.util.List<Transacao> filtradas = service.filtrarPorCategoria(nomeCat);
        if (filtradas.isEmpty()) {
            System.out.println("Nenhuma transação nesta categoria.");
            return;
        }

        for (Transacao t : filtradas) {
            System.out.printf("ID: %d | Valor: R$ %.2f | Data: %s | %s%n",
                t.getId(), t.getValor(), t.getData(), t.getDescricao());
        }
        System.out.println("=".repeat(70));
    }

    private static void deletarTransacao() {
        System.out.println("\n--- DELETAR TRANSAÇÃO ---");
        System.out.print("ID da transação: ");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            service.deletarTransacao(id);
        } catch (Exception e) {
            System.out.println("❌ ID inválido!");
            sc.nextLine();
        }
    }
}