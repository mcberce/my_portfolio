package Model;

import java.time.LocalDate;

public class Transacao {
    private int id;
    private double valor;
    private Categoria categoria;
    private Conta contaOrigem;    // pode ser null para receitas
    private Conta contaDestino;   // pode ser null para despesas
    private LocalDate data;
    private String descricao;

    public Transacao(int id, double valor, Categoria categoria, Conta contaOrigem, Conta contaDestino,
                     LocalDate data, String descricao) {
        this.id = id;
        this.valor = valor;
        this.categoria = categoria;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.data = data;
        this.descricao = descricao;
    }

    //GETTER E SETTER  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}