import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ControleEstoque {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Produt> produtos = new ArrayList<>();

    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n===== CONTROLE DE ESTOQUE =====");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Listar Produtos");
            System.out.println("3 - Filtrar por Categoria");
            System.out.println("4 - Ordenar por Categoria");
            System.out.println("5 - Remover Produto");
            System.out.println("6 - Atualizar Preço");
            System.out.println("7 - Listagem com Subtotal");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = input.nextInt();
            input.nextLine();

            switch(opcao) {

                case 1:
                    cadastrar();
                    break;

                case 2:
                    listar();
                    break;

                case 3:
                    filtrarCategoria();
                    break;

                case 4:
                    ordenarCategoria();
                    break;

                case 5:
                    removerProduto();
                    break;

                case 6:
                    atualizarPreco();
                    break;

                case 7:
                    subtotalPorCategoria();
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while(opcao != 0);

        input.close();
    }

    public static void cadastrar() {

        Produt produto = new Produt();

        System.out.println("\n--- Cadastro ---");

        System.out.print("Título: ");
        produto.titulo = input.nextLine();

        System.out.print("Descrição: ");
        produto.descricao = input.nextLine();

        System.out.print("Preço Unitário (ex: 8000,20): ");
        produto.precoUnitario = input.nextDouble();

        System.out.print("Quantidade em Estoque: ");
        produto.qtdEstoque = input.nextInt();

        input.nextLine();

        System.out.print("Categoria: ");
        produto.categoria = input.nextLine();

        System.out.print("Quantidade Mínima: ");
        produto.qtdMinima = input.nextInt();

        input.nextLine();

        produtos.add(produto);

        System.out.println("Produto cadastrado com sucesso!");
    }

    public static void listar() {

        if(produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for(Produt produto : produtos) {
            imprimir(produto);
        }
    }

    public static void imprimir(Produt produto) {

        System.out.println("\n--------------------------");
        System.out.println("Título: " + produto.titulo);
        System.out.println("Descrição: " + produto.descricao);
        System.out.printf("Preço: R$ %.2f%n", produto.precoUnitario);
        System.out.println("Estoque: " + produto.qtdEstoque);
        System.out.println("Categoria: " + produto.categoria);
        System.out.println("Qtd. Mínima: " + produto.qtdMinima);
    }

    public static void filtrarCategoria() {

        System.out.print("Digite a categoria: ");
        String categoria = input.nextLine();

        boolean encontrou = false;

        for(Produt produto : produtos) {

            if(produto.categoria.equalsIgnoreCase(categoria)) {
                imprimir(produto);
                encontrou = true;
            }
        }

        if(!encontrou) {
            System.out.println("Nenhum produto encontrado.");
        }
    }

    public static void ordenarCategoria() {

        produtos.sort(
            Comparator.comparing(
                p -> p.categoria.toLowerCase()
            )
        );

        System.out.println("Produtos ordenados por categoria.");
    }

    public static void removerProduto() {

        System.out.print("Digite o título do produto: ");
        String titulo = input.nextLine();

        boolean removido = produtos.removeIf(
            p -> p.titulo.equalsIgnoreCase(titulo)
        );

        if(removido) {
            System.out.println("Produto removido.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public static void atualizarPreco() {

        System.out.print("Digite o título do produto: ");
        String titulo = input.nextLine();

        for(Produt produto : produtos) {

            if(produto.titulo.equalsIgnoreCase(titulo)) {

                System.out.print("Novo preço: ");
                produto.precoUnitario = input.nextDouble();

                input.nextLine();

                System.out.println("Preço atualizado.");
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }

    public static void subtotalPorCategoria() {

        if(produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        ordenarCategoria();

        String categoriaAtual = "";
        double subtotal = 0;
        double totalGeral = 0;

        for(Produt produto : produtos) {

            double valor =
                produto.precoUnitario * produto.qtdEstoque;

            if(!produto.categoria.equalsIgnoreCase(categoriaAtual)) {

                if(!categoriaAtual.isEmpty()) {
                    System.out.printf(
                        "Subtotal: R$ %.2f%n%n",
                        subtotal
                    );
                }

                categoriaAtual = produto.categoria;
                subtotal = 0;

                System.out.println("\n" + categoriaAtual);
            }

            System.out.printf(
                "%s - %d - R$ %.2f%n",
                produto.titulo,
                produto.qtdEstoque,
                valor
            );

            subtotal += valor;
            totalGeral += valor;
        }

        System.out.printf(
            "Subtotal: R$ %.2f%n",
            subtotal
        );

        System.out.printf(
            "%nTOTAL GERAL: R$ %.2f%n",
            totalGeral
        );
    }
}