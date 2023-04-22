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

        listaJogadores.addFirst(jogador);
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
                String nome = dados[0];
                int id = Integer.parseInt(dados[1]);
                String role = dados[2];
                int pontuacaoHabilidade = Integer.parseInt(dados[3]);
                Jogador jogador = new Jogador(id, nome, role, pontuacaoHabilidade);
                listaJogadores.addFirst(jogador);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar jogadores: " + e.getMessage());
        }
    }
   /*  public void iniciar() {
        listaJogadores.ordenarPorPontuacaoHabilidade();
        ListaDuplamenteEncadeada time1 = new ListaDuplamenteEncadeadaTime();
        ListaDuplamenteEncadeada time2 = new ListaDuplamenteEncadeadaTime();
        int pontuacaoTime1 = 0;
        int pontuacaoTime2 = 0;
        No noAtual = listaJogadores.getPrimeiro();
        for (int i = 0; i < 6 && noAtual != null; i++) {
            Jogador jogador = noAtual.getJogador();
            if (jogador != null) {
                if (i % 2 == 0) {
                    time1.addFirst(jogador);
                    pontuacaoTime1 += jogador.getPontuacaoHabilidade();
                } else {
                    time2.addFirst(jogador);
                    pontuacaoTime2 += jogador.getPontuacaoHabilidade();
                }
            }
            noAtual = noAtual.getProximo();
        }
        salvarPartida(time1, time2, pontuacaoTime1, pontuacaoTime2);
        System.out.println("Partida salva com sucesso!");
    }
   */
    public void salvarPartida(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, int pontuacaoTime1, int pontuacaoTime2) {
       try {
           // Cria um objeto BufferedWriter para escrever no arquivo "partida.txt"
           BufferedWriter writer = new BufferedWriter(new FileWriter("partida.txt", true));
   
           // Escreve a pontuação do time 1 e a lista de jogadores
           writer.write("Time 1 - Habilidade " + pontuacaoTime1 + ":");
           writer.newLine();
           No noAtualTime1 = time1.getPrimeiro();
           while (noAtualTime1 != null) {
               writer.write(noAtualTime1.getJogador().getNome());
               writer.newLine();
               noAtualTime1 = noAtualTime1.getProximo();
           }
   
           // Escreve a pontuação do time 2 e a lista de jogadores
           writer.write("Time 2 - Habilidade " + pontuacaoTime2 + ":");
           writer.newLine();
           No noAtualTime2 = time2.getPrimeiro();
           while (noAtualTime2 != null) {
               writer.write(noAtualTime2.getJogador().getNome());
               writer.newLine();
               noAtualTime2 = noAtualTime2.getProximo();
           }
   
           // Escreve uma linha separadora para indicar o final da partida
           writer.write("----------------------------------------");
           writer.newLine();
   
           // Fecha o objeto BufferedWriter
           writer.close();
   
           System.out.println("Partida salva com sucesso!");
   
       } catch (IOException e) {
           System.out.println("Erro ao salvar a partida.");
           e.printStackTrace();
       }
   }

   
}
