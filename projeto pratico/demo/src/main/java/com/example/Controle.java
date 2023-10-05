package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Controle {
    private ArrayList<Produto> lista_produtos = new ArrayList<>();
    private ArrayList<Cliente> lista_clientes = new ArrayList<>();
    private ArrayList<Pedido> lista_pedidos = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void adicionar_produto(Produto produto){
        lista_produtos.add(produto);
        System.out.printf("Produto %s adicionado com sucesso!!\n", produto.getNome());
    }

    public void listar_produtos(){
        System.out.println("LISTA DE PRODUTOS:");
        for(Produto produto : lista_produtos){
            System.out.println("-----------------------");
            System.out.printf("\nNome: %s\nPreco: R$%.2f\n", produto.getNome(), produto.getPreco());
        }
        System.out.println("-----------------------");
    }

    public void criar_pedido(){
        System.out.println("Selecione o Cliente:");
        System.out.println("Clientes disponíveis:");
        for (int i = 0; i < lista_clientes.size(); i++) {
            System.out.println(i + ". " + lista_clientes.get(i).getNome());
        }
        int cliente_selecionado = scanner.nextInt();
        scanner.nextLine();

        if(cliente_selecionado >= 0 && cliente_selecionado < lista_clientes.size()){
            Cliente cliente_pedido = lista_clientes.get(cliente_selecionado);
            Pedido novo_pedido = new Pedido(lista_pedidos.size() + 1, cliente_pedido);
            boolean add_pedido = true;
            while(add_pedido){
                System.out.println("Selecione um Produto:");
                System.out.println("Produtos disponíveis:");
                for (int i = 0; i < lista_produtos.size(); i++) {
                    System.out.println(i + ". " + lista_produtos.get(i).getNome());
                }
                int produto_selecionado = scanner.nextInt();
                scanner.nextLine();

                if(produto_selecionado >= 0 && produto_selecionado < lista_produtos.size()){
                    Produto produto_pedido = lista_produtos.get(produto_selecionado);
                    System.out.println("Quantidade:");
                    int quantidade_pedido = scanner.nextInt();
                    scanner.nextLine();
                    novo_pedido.adicionar_item(produto_pedido, quantidade_pedido);
                    System.out.println("Produto adicionado ao pedido.");
                } else {
                    System.out.println("Produto selecionado não existe.");
                }

                System.out.println("Deseja adicionar mais produtos ao pedido? (S/N)");
                String continuar = scanner.nextLine().toLowerCase();

                if (!continuar.equals("s")) {
                    add_pedido = false;
                }
            }
            lista_pedidos.add(novo_pedido);
            System.out.println("Pedido criado com sucesso!");
        } else {
            System.out.println("Cliente selecionado não existe.");
        } 
    }

    public void listar_pedidos(){
        System.out.println("LISTA DE PEDIDOS:");
        for(Pedido pedido : lista_pedidos){
            System.out.println(pedido);
            for(Item_pedido item : pedido.getItens()){
                System.out.println(item);
            }
            System.out.println("Valor total do pedido: R$" + pedido.calcular_total());
            System.out.println("-----------------------");
        }
    }

    public void adicionar_cliente(Cliente cliente){
        lista_clientes.add(cliente);
        System.out.printf("\nCliente %s adicionado com sucesso.\n\n", cliente.getNome());
    }

    public void listar_clientes(){
        System.out.println("LISTA DE CLIENTES:");
        for(Cliente cliente : lista_clientes){
            System.out.println("-----------------------");
            System.out.printf("Nome: %s\n", cliente.getNome());
            System.out.printf("Endereco: %s\n", cliente.getEndereco());
            System.out.printf("Telefone: %s\n", cliente.getTelefone());
        }
        System.out.println("-----------------------");
    }

    public ArrayList<Produto> getLista_produtos() {
        return lista_produtos;
    }

    public ArrayList<Cliente> getLista_clientes() {
        return lista_clientes;
    }

    public ArrayList<Pedido> getLista_pedidos() {
        return lista_pedidos;
    }
}