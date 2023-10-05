package com.example;

public class Item_pedido {
    private Produto produto;
    private int quantidade;

    public Item_pedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double calcular_subtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return produto.getNome() + " - Quantidade: " + quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
