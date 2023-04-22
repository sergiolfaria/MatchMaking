import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GerenciadorJogadores {
    private ListaDuplamenteEncadeada listaJogadores;
    private String arquivoJogadores = "jogadores.txt";

    public GerenciadorJogadores(Jogador jogador) {
        listaJogadores = new ListaDuplamenteEncadeada(jogador);
    }

    public void adicionarJogador() {
        String nome = Utils.lerTexto("Informe o nome do jogador: ");
        int id = Utils.lerInt("Informe o ID do jogador: ");
        String role = Utils.lerTexto("Informe a role do jogador: ");
        int pontuacaoHabilidade = Utils.lerInt("Informe a pontuação de habilidade do jogador: ");

        Jogador jogador = new Jogador(id, nome, role, pontuacaoHabilidade);

        listaJogadores.addLast(jogador);
        salvarJogadores();
        System.out.println("Jogador adicionado com sucesso!");
    }

    public void removerJogador() {
        String nome = Utils.lerTexto("Informe o nome do jogador que deseja remover: ");

        listaJogadores.removerPorNome(nome);
        salvarJogadores();
        System.out.println("Jogador removido com sucesso!");
    }

    public void listarJogadores() {
        carregarJogadores();
        System.out.println("Lista de jogadores: ");
        No noAtual = listaJogadores.getPrimeiro();
        while (noAtual != null) {
            Jogador jogador = noAtual.getJogador();
            if (jogador != null) {
                System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
            }
            noAtual = noAtual.getProximo();
        }
    }

    public void salvarJogadores() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoJogadores));
            No noAtual = listaJogadores.getPrimeiro();
            while (noAtual != null) {
                Jogador jogador = noAtual.getJogador();
                if (jogador != null) {
                    writer.write(jogador.getNome() + ";" + jogador.getId() + ";" + jogador.getRole() + ";" + jogador.getPontuacaoHabilidade());
                    writer.newLine();
                }
                noAtual = noAtual.getProximo();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar jogadores.");
            e.printStackTrace();
        }
    }

      public void carregarJogadores() {
       try {
           BufferedReader reader = new BufferedReader(new FileReader(arquivoJogadores));
           String linha = null;
           while ((linha = reader.readLine()) != null) {
               String[] dados = linha.split(";");
               if (dados.length != 4) {
                   continue; // Ignora a linha se não tiver 4 valores
               }
               String nome = dados[0];
               int id = Integer.parseInt(dados[1]);
               String role = dados[2];
               int pontuacaoHabilidade = Integer.parseInt(dados[3]);
               Jogador jogador = new Jogador(id, nome, role, pontuacaoHabilidade);
               listaJogadores.addLast(jogador);
           }
           reader.close();
       } catch (IOException e) {
           System.out.println("Erro ao carregar jogadores: " + e.getMessage());
       }
   }
   public void iniciar() {
       listaJogadores.ordenarPorPontuacaoHabilidade();
       ListaDuplamenteEncadeada time1 = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada time2 = new ListaDuplamenteEncadeada();
       int pontuacaoTime1 = 0;
       int pontuacaoTime2 = 0;
       No noAtual = listaJogadores.getPrimeiro();
       for (int i = 0; i < 6 && noAtual != null; i++) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               if (i % 2 == 0) {
                   time1.addLast(jogador);
                   pontuacaoTime1 += jogador.getPontuacaoHabilidade();
               } else {
                   time2.addLast(jogador);
                   pontuacaoTime2 += jogador.getPontuacaoHabilidade();
               }
           }
           noAtual = noAtual.getProximo();
       }
       System.out.println("Time 1 - Habilidade " + pontuacaoTime1 + ":");
       No noAtualTime1 = time1.getPrimeiro();
       while (noAtualTime1 != null) {
           Jogador jogador = noAtualTime1.getJogador();
           if (jogador != null) {
               System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
           }
           noAtualTime1 = noAtualTime1.getProximo();
       }
       System.out.println("Time 2 - Habilidade " + pontuacaoTime2 + ":");
       No noAtualTime2 = time2.getPrimeiro();
       while (noAtualTime2 != null) {
           Jogador jogador = noAtualTime2.getJogador();
           if (jogador != null) {
               System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
           }
           noAtualTime2 = noAtualTime2.getProximo();
       }
       salvarPartida(time1, time2, pontuacaoTime1, pontuacaoTime2);
       System.out.println("Partida salva com sucesso!");
   }





   
     public void salvarPartida(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, int pontuacaoTime1, int pontuacaoTime2) {
       try {
           // Cria um objeto BufferedWriter para escrever no arquivo "partida.txt"
           BufferedWriter writer = new BufferedWriter(new FileWriter("partida.txt", true));
   
           // Escreve a pontuação do time 1 e a lista de jogadores
           writer.write("Time 1 - Habilidade " + pontuacaoTime1 + ":");
           writer.newLine();
           No noAtualTime1 = time1.getPrimeiro();
           while (noAtualTime1 != null) {
               Jogador jogador = noAtualTime1.getJogador();
               if (jogador != null) {
                   writer.write(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
                   writer.newLine();
               }
               noAtualTime1 = noAtualTime1.getProximo();
           }
   
           // Escreve a pontuação do time 2 e a lista de jogadores
           writer.write("Time 2 - Habilidade " + pontuacaoTime2 + ":");
           writer.newLine();
           No noAtualTime2 = time2.getPrimeiro();
           while (noAtualTime2 != null) {
               Jogador jogador = noAtualTime2.getJogador();
               if (jogador != null) {
                   writer.write(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
                   writer.newLine();
               }
               noAtualTime2 = noAtualTime2.getProximo();
           }
   
           // Escreve a quebra de linha no final para separar as partidas
           writer.newLine();
   
           writer.close();
       } catch (IOException e) {
           System.out.println("Erro ao salvar partida: " + e.getMessage());
       }
   }   
}
