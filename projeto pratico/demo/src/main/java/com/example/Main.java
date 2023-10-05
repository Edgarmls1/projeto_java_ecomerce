package com.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Controle controle = new Controle();

        while (true) {
            menu();

            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1:
                    System.out.println("Nome do Produto:");
                    String nome_produto = scanner.nextLine();
                    System.out.println("Preço do Produto:");
                    double preco_produto = scanner.nextDouble();
                    Produto produto = new Produto(nome_produto, preco_produto);
                    controle.adicionar_produto(produto);
                    break;
                case 2:
                    controle.listar_produtos();
                    break;
                case 3:
                    controle.criar_pedido();
                    break;
                case 4:
                    controle.listar_pedidos();
                    break;
                case 5:
                    System.out.println("Nome do cliente:");
                    String nome_cliente = scanner.nextLine();
                    System.out.println("Endereco do cliente:");
                    String endereco = scanner.nextLine();
                    System.out.println("Telefone do cliente:");
                    String telefone = scanner.nextLine();
                    Cliente cliente = new Cliente(nome_cliente, endereco, telefone);
                    controle.adicionar_cliente(cliente);
                    break;
                case 6:
                    controle.listar_clientes();
                    break;
                case 7:
                    System.out.println("Salvando dados em JSON...");
                    salvarDadosEmJSON("produtos.json", produtosToJsonArray(controle.getLista_produtos()));
                    salvarDadosEmJSON("clientes.json", clientesToJsonArray(controle.getLista_clientes()));
                    salvarDadosEmJSON("pedidos.json", pedidosToJsonArray(controle.getLista_pedidos()));
                    System.out.println("Saindo do programa.");
                    System.exit(0);
                default:
                    System.out.println("Escolha inválida. Tente novamente.");
            }
        }
    }

    public static void salvarDadosEmJSON(String arquivo, JSONArray dados) {
        try (FileWriter file = new FileWriter(arquivo)) {
            file.write(dados.toJSONString());
            System.out.println("Dados salvos com sucesso em " + arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + arquivo);
        }
    }

    public static JSONArray carregarDadosDeJSON(String arquivo) {
        JSONParser parser = new JSONParser();
        JSONArray dados = new JSONArray();

        try (FileReader reader = new FileReader(arquivo)) {
            Object obj = parser.parse(reader);
            if (obj instanceof JSONArray) {
                dados = (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao carregar dados de " + arquivo);
        }

        return dados;
    }

    private static JSONArray produtosToJsonArray(List<Produto> produtos) {
        JSONArray jsonArray = new JSONArray();
        for (Produto produto : produtos) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nome", produto.getNome());
            jsonObject.put("preco", produto.getPreco());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private static List<Produto> jsonArrayToProdutos(JSONArray jsonArray) {
        List<Produto> produtos = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String nome = (String) jsonObject.get("nome");
            double preco = (double) jsonObject.get("preco");
            produtos.add(new Produto(nome, preco));
        }
        return produtos;
    }

    private static JSONArray clientesToJsonArray(List<Cliente> clientes) {
        JSONArray jsonArray = new JSONArray();
        for (Cliente cliente : clientes) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nome", cliente.getNome());
            jsonObject.put("endereco", cliente.getEndereco());
            jsonObject.put("telefone", cliente.getTelefone());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private static List<Cliente> jsonArrayToClientes(JSONArray jsonArray) {
        List<Cliente> clientes = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String nome = (String) jsonObject.get("nome");
            String endereco = (String) jsonObject.get("endereco");
            String telefone = (String) jsonObject.get("telefone");
            clientes.add(new Cliente(nome, endereco, telefone));
        }
        return clientes;
    }

    private static JSONArray pedidosToJsonArray(List<Pedido> pedidos) {
        JSONArray jsonArray = new JSONArray();
        for (Pedido pedido : pedidos) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_pedido", pedido.getId_pedido());
            jsonObject.put("cliente", pedido.getCliente().getNome());

            JSONArray itensArray = new JSONArray();
            for (Item_pedido item : pedido.getItens()) {
                JSONObject itemObject = new JSONObject();
                itemObject.put("produto", item.getProduto().getNome());
                itemObject.put("quantidade", item.getQuantidade());
                itensArray.add(itemObject);
            }
            jsonObject.put("itens", itensArray);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    private static List<Pedido> jsonArrayToPedidos(JSONArray jsonArray, List<Cliente> clientes, List<Produto> produtos) {
        List<Pedido> pedidos = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            int numeroPedido = ((Long) jsonObject.get("numeroPedido")).intValue();
            String clienteNome = (String) jsonObject.get("cliente");

            Cliente cliente = null;
            for (Cliente c : clientes) {
                if (c.getNome().equals(clienteNome)) {
                    cliente = c;
                    break;
                }
            }

            Pedido pedido = new Pedido(numeroPedido, cliente);

            JSONArray itensArray = (JSONArray) jsonObject.get("itens");
            for (Object itemObj : itensArray) {
                JSONObject itemJson = (JSONObject) itemObj;
                String produtoNome = (String) itemJson.get("produto");
                int quantidade = ((Long) itemJson.get("quantidade")).intValue();

                Produto produto = null;
                for (Produto p : produtos) {
                    if (p.getNome().equals(produtoNome)) {
                        produto = p;
                        break;
                    }
                }

                if (produto != null) {
                    pedido.adicionar_item(produto, quantidade);
                }
            }

            pedidos.add(pedido);
        }
        return pedidos;
    }

    static void menu(){
        System.out.println("MENU:");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("3. Criar Pedido");
        System.out.println("4. Listar Pedidos");
        System.out.println("5. Adicionar Cliente");
        System.out.println("6. Listar Clientes");
        System.out.println("7. Sair");
    }
}


