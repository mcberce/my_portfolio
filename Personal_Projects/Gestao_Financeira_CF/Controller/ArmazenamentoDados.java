package Controller;

import Model.Transacao;
import Model.Conta;
import Model.Categoria;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ArmazenamentoDados {
    private static final String ARQUIVO_CONTAS = "contas.dat";
    private static final String ARQUIVO_CATEGORIAS = "categorias.dat";
    private static final String ARQUIVO_TRANSACOES = "transacoes.csv";

    // Salvar dados em arquivos
    public static void salvarDados(TransacaoService service) {
        salvarContas(service);
        salvarCategorias(service);
        salvarTransacoes(service);
        System.out.println("✓ Todos os dados foram salvos com sucesso!");
    }

    private static void salvarContas(TransacaoService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CONTAS))) {
            for (Conta c : service.listarContas()) {
                writer.println(c.getId() + "|" + c.getNomeConta() + "|" + c.getSaldoAtual() + "|" + c.getTipoConta());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    private static void salvarCategorias(TransacaoService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_CATEGORIAS))) {
            for (Categoria cat : service.listarCategorias()) {
                writer.println(cat.getId() + "|" + cat.getNomeCategoria() + "|" + cat.getTipoCategoria());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar categorias: " + e.getMessage());
        }
    }

    private static void salvarTransacoes(TransacaoService service) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_TRANSACOES))) {
            // Cabeçalho CSV
            writer.println("ID,VALOR,CATEGORIA,CONTA_ORIGEM,CONTA_DESTINO,DATA,DESCRICAO");

            for (Transacao t : service.listarTransacoes()) {
                String contaOrig = t.getContaOrigem() != null ? t.getContaOrigem().getNomeConta() : "";
                String contaDest = t.getContaDestino() != null ? t.getContaDestino().getNomeConta() : "";
                String descricao = t.getDescricao().replace(",", ";"); // Evita quebra de CSV

                writer.printf("%d,%.2f,%s,%s,%s,%s,%s%n",
                    t.getId(),
                    t.getValor(),
                    t.getCategoria().getNomeCategoria(),
                    contaOrig,
                    contaDest,
                    t.getData(),
                    descricao);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar transações: " + e.getMessage());
        }
    }

    // Carregar dados dos arquivos
    public static void carregarDados(TransacaoService service) {
        carregarContas(service);
        carregarCategorias(service);
        carregarTransacoes(service);
        System.out.println("✓ Dados carregados com sucesso!");
    }

    private static void carregarContas(TransacaoService service) {
        File arquivo = new File(ARQUIVO_CONTAS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split("\\|");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                double saldo = Double.parseDouble(partes[2]);
                Conta.TipoConta tipo = Conta.TipoConta.valueOf(partes[3]);
                service.adicionarConta(new Conta(id, nome, saldo, tipo));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contas: " + e.getMessage());
        }
    }

    private static void carregarCategorias(TransacaoService service) {
        File arquivo = new File(ARQUIVO_CATEGORIAS);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CATEGORIAS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split("\\|");
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                Categoria.TipoCategoria tipo = Categoria.TipoCategoria.valueOf(partes[2]);
                service.adicionarCategoria(new Categoria(id, nome, tipo));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar categorias: " + e.getMessage());
        }
    }

    private static void carregarTransacoes(TransacaoService service) {
        File arquivo = new File(ARQUIVO_TRANSACOES);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_TRANSACOES))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // Pula cabeçalho
                    continue;
                }

                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(",");
                if (partes.length < 7) continue;

                try {
                    int id = Integer.parseInt(partes[0]);
                    double valor = Double.parseDouble(partes[1]);
                    String categoriaNome = partes[2];
                    String contaOrigNome = partes[3].isEmpty() ? null : partes[3];
                    String contaDestNome = partes[4].isEmpty() ? null : partes[4];
                    LocalDate data = LocalDate.parse(partes[5]);
                    String descricao = partes[6].replace(";", ",");

                    Categoria categoria = service.obterCategoriaPorNome(categoriaNome);
                    Conta contaOrig = contaOrigNome != null ? service.obterContaPorNome(contaOrigNome) : null;
                    Conta contaDest = contaDestNome != null ? service.obterContaPorNome(contaDestNome) : null;

                    if (categoria != null) {
                        Transacao t = new Transacao(id, valor, categoria, contaOrig, contaDest, data, descricao);
                        service.adicionarTransacaoCarregada(t); // não recalcula saldo
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao carregar transação: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar transações: " + e.getMessage());
        }
    }
}
