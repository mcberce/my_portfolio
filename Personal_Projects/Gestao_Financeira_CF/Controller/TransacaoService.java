package Controller;

import Model.Transacao;
import Model.Conta;
import Model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class TransacaoService {
    private List<Transacao> transacoes;
    private List<Conta> contas;
    private List<Categoria> categorias;
    private int proxIdTransacao = 1;
    private int proxIdConta = 1;
    private int proxIdCategoria = 1;

    public TransacaoService() {
        this.transacoes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

    // Adicionar transação com validações
    public boolean adicionarTransacao(Transacao transacao) {
        // Validar se categoria existe
        if (transacao.getCategoria() == null) {
            System.out.println("Erro: Transação sem categoria");
            return false;
        }

        // Validar conforme tipo de transação
        Categoria.TipoCategoria tipo = transacao.getCategoria().getTipoCategoria();

        if (tipo == Categoria.TipoCategoria.RECEITA) {
            // Receita: só precisa de conta destino
            if (transacao.getContaDestino() == null) {
                System.out.println("Erro: Receita precisa de uma conta destino");
                return false;
            }
            // Aumenta saldo da conta destino
            transacao.getContaDestino().setSaldoAtual(
                transacao.getContaDestino().getSaldoAtual() + transacao.getValor()
            );
        } else if (tipo == Categoria.TipoCategoria.DESPESA) {
            // Despesa: só precisa de conta origem
            if (transacao.getContaOrigem() == null) {
                System.out.println("Erro: Despesa precisa de uma conta origem");
                return false;
            }
            // Valida se tem saldo
            if (transacao.getContaOrigem().getSaldoAtual() < transacao.getValor()) {
                System.out.println("Erro: Saldo insuficiente na conta");
                return false;
            }
            // Diminui saldo da conta origem
            transacao.getContaOrigem().setSaldoAtual(
                transacao.getContaOrigem().getSaldoAtual() - transacao.getValor()
            );
        } else if (tipo == Categoria.TipoCategoria.TRANSFERENCIA) {
            // Transferência: precisa de origem e destino
            if (transacao.getContaOrigem() == null || transacao.getContaDestino() == null) {
                System.out.println("Erro: Transferência precisa de conta origem e destino");
                return false;
            }
            // Valida saldo
            if (transacao.getContaOrigem().getSaldoAtual() < transacao.getValor()) {
                System.out.println("Erro: Saldo insuficiente na conta origem");
                return false;
            }
            // Diminui da origem, aumenta no destino
            transacao.getContaOrigem().setSaldoAtual(
                transacao.getContaOrigem().getSaldoAtual() - transacao.getValor()
            );
            transacao.getContaDestino().setSaldoAtual(
                transacao.getContaDestino().getSaldoAtual() + transacao.getValor()
            );
        }

        // Se passou todas as validações, adiciona
        transacoes.add(transacao);
        System.out.println("Transação adicionada com sucesso!");
        return true;
    }

    // Uso interno para carregar do disco sem reprocessar saldos
    public void adicionarTransacaoCarregada(Transacao transacao) {
        transacoes.add(transacao);
        if (transacao.getId() >= proxIdTransacao) {
            proxIdTransacao = transacao.getId() + 1;
        }
    }

    public List<Transacao> listarTransacoes() {
        return new ArrayList<>(transacoes);
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
        proxIdConta++;
    }

    public List<Conta> listarContas() {
        return new ArrayList<>(contas);
    }

    public void adicionarCategoria(Categoria categoria) {
        categorias.add(categoria);
        proxIdCategoria++;
    }

    public List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    // Métodos extras úteis
    public double obterSaldoConta(String nomeConta) {
        for (Conta c : contas) {
            if (c.getNomeConta().equalsIgnoreCase(nomeConta)) {
                return c.getSaldoAtual();
            }
        }
        System.out.println("Conta não encontrada!");
        return 0;
    }

    public List<Transacao> filtrarPorCategoria(String nomeCategoria) {
        List<Transacao> resultado = new ArrayList<>();
        for (Transacao t : transacoes) {
            if (t.getCategoria().getNomeCategoria().equalsIgnoreCase(nomeCategoria)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public double calcularTotalReceitas() {
        double total = 0;
        for (Transacao t : transacoes) {
            if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.RECEITA) {
                total += t.getValor();
            }
        }
        return total;
    }

    public double calcularTotalDespesas() {
        double total = 0;
        for (Transacao t : transacoes) {
            if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.DESPESA) {
                total += t.getValor();
            }
        }
        return total;
    }

    public Conta obterContaPorNome(String nome) {
        for (Conta c : contas) {
            if (c.getNomeConta().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    public Categoria obterCategoriaPorNome(String nome) {
        for (Categoria cat : categorias) {
            if (cat.getNomeCategoria().equalsIgnoreCase(nome)) {
                return cat;
            }
        }
        return null;
    }

    public int getProxIdTransacao() {
        return proxIdTransacao++;
    }

    public int getProxIdConta() {
        return proxIdConta++;
    }

    public int getProxIdCategoria() {
        return proxIdCategoria++;
    }

    public boolean deletarTransacao(int id) {
        for (Transacao t : transacoes) {
            if (t.getId() == id) {
                transacoes.remove(t);
                System.out.println("Transação deletada!");
                return true;
            }
        }
        System.out.println("Transação não encontrada!");
        return false;
    }

    public boolean verificarContas() {
        return !contas.isEmpty();
    }

    public boolean verificarCategorias() {
        return !categorias.isEmpty();
    }
}