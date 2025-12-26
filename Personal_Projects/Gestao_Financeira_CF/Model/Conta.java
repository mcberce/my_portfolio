package Model;

public class Conta {
    private int id;
    private String nomeConta;
    private double saldoAtual;
    private TipoConta tipoConta;

    public Conta(int id, String nomeConta, double saldoAtual, TipoConta tipoConta) {
        this.id = id;
        this.nomeConta = nomeConta;
        this.saldoAtual = saldoAtual;
        this.tipoConta = tipoConta;
    }

    //GETTER E SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoconta) {
        this.tipoConta = tipoconta;
    }

    //TIPO DA CONTA
    public enum TipoConta {
        CORRENTE,
        POUPANCA,
        INVESTIMENTO,
        DIGITAL
    }
}