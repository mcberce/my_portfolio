package Model;

public class Categoria {
    private int id;
    private String nomeCategoria;
    private TipoCategoria tipoCategoria;

    public Categoria(int id, String nomeCategoria, TipoCategoria tipoCategoria) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.tipoCategoria = tipoCategoria;
    }

    //GETTER E SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public TipoCategoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(TipoCategoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    //TIPOS DE CATEGORIA
    public enum TipoCategoria {
        RECEITA,
        DESPESA,
        TRANSFERENCIA
    }
}