package com.example;

import java.util.ArrayList;

public class Pedido {
    private int id_pedido;
    private Cliente cliente;
    private ArrayList<Item_pedido> itens = new ArrayList<>();

    public Pedido(int id_pedido, Cliente cliente) {
        this.id_pedido = id_pedido;
        this.cliente = cliente;
    }

    public void adicionar_item(Produto produto, int quantidade) {
        Item_pedido item = new Item_pedido(produto, quantidade);
        itens.add(item);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Item_pedido> getItens() {
        return itens;
    }

    public double calcular_total() {
        double total = 0;
        for (Item_pedido item : itens) {
            total += item.calcular_subtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Pedido #" + id_pedido + " - Cliente: " + cliente.getNome();
    }
}