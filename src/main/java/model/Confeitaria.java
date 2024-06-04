package model;

/**
 * Esta classe representa um produto de confeitaria.
 */

public class Confeitaria {
    private int codigo;
    private String marca;
    private String nomeProduto;
    private Double preco;
    private int quantidade;
    private String dataVal;
    private int notaFiscal;

    /**
     * Construtor da classe Confeitaria.
     * @param codigo O código do produto.
     * @param marca A marca do produto.
     * @param nomeProduto O nome do produto.
     * @param preco O preço do produto.
     * @param quantidade A quantidade do produto.
     * @param dataVal A data de validade do produto.
     * @param notaFiscal A nota fiscal do produto.
     */

    public Confeitaria(int codigo, String marca, String nomeProduto, Double preco, int quantidade, String dataVal, int notaFiscal) {
        this.codigo = codigo;
        this.marca = marca;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.dataVal = dataVal;
        this.notaFiscal = notaFiscal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDataVal() {
        return dataVal;
    }

    public void setDataVal(String dataVal) {
        this.dataVal = dataVal;
    }

    public int getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(int notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
}