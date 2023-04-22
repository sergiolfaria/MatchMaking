import java.util.Scanner;
class Main{
   
    public static void main(String[] args) {
        Jogador jogador = new Jogador(0,"","",0);
        GerenciadorJogadores gerenciador = new GerenciadorJogadores(jogador);
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.println("---- GERENCIADOR DE JOGADORES ----");
            System.out.println("1. Adicionar jogador");
            System.out.println("2. carregar Jogadores");
            System.out.println("3. Listar jogadores");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    gerenciador.adicionarJogador();
                    break;
                case 2:
                    gerenciador.carregarJogadores();
                    break;
                case 3:
                    gerenciador.listarJogadores();
                    break;
                 case 4:
                    gerenciador.iniciar();
                    
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

            System.out.println(); // Linha em branco para separar as iterações
        } while (opcao != 0);
    }
}