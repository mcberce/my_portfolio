package Controller;

import Model.Transacao;
import Model.Conta;
import Model.Categoria;
import java.time.LocalDate;
import java.util.*;

public class RelatorioService {

    public static void gerarRelatorioPorPeriodo(TransacaoService service, LocalDate dataInicio, LocalDate dataFim) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RELATÓRIO DO PERÍODO: " + dataInicio + " até " + dataFim);
        System.out.println("=".repeat(70));

        double receitas = 0, despesas = 0;
        Map<String, Double> despesasPorCategoria = new HashMap<>();

        for (Transacao t : service.listarTransacoes()) {
            if (t.getData().isAfter(dataInicio.minusDays(1)) && t.getData().isBefore(dataFim.plusDays(1))) {
                if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.RECEITA) {
                    receitas += t.getValor();
                } else if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.DESPESA) {
                    despesas += t.getValor();
                    String catNome = t.getCategoria().getNomeCategoria();
                    despesasPorCategoria.put(catNome, despesasPorCategoria.getOrDefault(catNome, 0.0) + t.getValor());
                }
            }
        }

        System.out.printf("Receitas Totais: R$ %.2f%n", receitas);
        System.out.printf("Despesas Totais: R$ %.2f%n", despesas);
        System.out.printf("Saldo: R$ %.2f%n%n", receitas - despesas);

        if (!despesasPorCategoria.isEmpty()) {
            System.out.println("Despesas por Categoria:");
            for (String categoria : despesasPorCategoria.keySet()) {
                System.out.printf("  %s: R$ %.2f%n", categoria, despesasPorCategoria.get(categoria));
            }
        }
        System.out.println("=".repeat(70));
    }

    public static double obterSaldoTotalContas(TransacaoService service) {
        double total = 0;
        for (Conta c : service.listarContas()) {
            total += c.getSaldoAtual();
        }
        return total;
    }

    public static void gerarRelatorioCompleto(TransacaoService service) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("RELATÓRIO COMPLETO DO SISTEMA");
        System.out.println("=".repeat(70));

        System.out.println("\n📊 CONTAS:");
        for (Conta c : service.listarContas()) {
            System.out.printf("  %s (%s): R$ %.2f%n", c.getNomeConta(), c.getTipoConta(), c.getSaldoAtual());
        }

        System.out.println("\n💰 SALDO TOTAL: R$ " + String.format("%.2f", obterSaldoTotalContas(service)));

        System.out.println("\n📋 TRANSAÇÕES:");
        int count = 0;
        for (Transacao t : service.listarTransacoes()) {
            System.out.printf("  [%d] %s - R$ %.2f (%s) - %s%n",
                t.getId(), t.getData(), t.getValor(), t.getCategoria().getNomeCategoria(), t.getDescricao());
            count++;
        }
        if (count == 0) System.out.println("  Nenhuma transação registrada.");

        System.out.println("\n📈 RESUMO FINANCEIRO:");
        System.out.printf("  Receitas: R$ %.2f%n", service.calcularTotalReceitas());
        System.out.printf("  Despesas: R$ %.2f%n", service.calcularTotalDespesas());
        System.out.printf("  Resultado: R$ %.2f%n", service.calcularTotalReceitas() - service.calcularTotalDespesas());

        System.out.println("=".repeat(70));
    }
}
