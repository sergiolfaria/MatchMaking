import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashSet;

public class GerenciadorJogadores {
    private ListaDuplamenteEncadeada listaJogadores;
    private String arquivoJogadores = "jogadores.txt";
    private ListaDuplamenteEncadeada jogadoresEmPartida = new ListaDuplamenteEncadeada();
    
    int pontuacaoTime1 = 0;
    int pontuacaoTime2 = 0;
    int indice = 0;
    ListaDuplamenteEncadeada time1 = new ListaDuplamenteEncadeada();
    ListaDuplamenteEncadeada time2 = new ListaDuplamenteEncadeada();

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

    public void listarFilaJogadores() {
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
                 System.out.println("Jogador carregado: " + nome + " - " + role + " - Habilidade: " + pontuacaoHabilidade); // Adiciona mensagem de confirmação
             }
             reader.close();
         } catch (IOException e) {
             System.out.println("Erro ao carregar jogadores: " + e.getMessage());
         }
      }
   public void iniciar() {
    ListaDuplamenteEncadeada listaEspera = new ListaDuplamenteEncadeada();
    No noAtual = listaJogadores.getPrimeiro();
    HashSet<String> rolesTime1 = new HashSet<>();
    HashSet<String> rolesTime2 = new HashSet<>();
    ListaDuplamenteEncadeada jogadoresEmPartida = new ListaDuplamenteEncadeada();
    No noAtualEspera;
    No noAtualJogadoresEmPartida;

    while (noAtual != null) {
        Jogador jogador = noAtual.getJogador();
        if (jogador != null) {
            String role = jogador.getRole();

            if (time1.size() < 3 && !rolesTime1.contains(role) && !jogadoresEmPartida.contem(jogador)) {
                time1.addLast(jogador);
                pontuacaoTime1 += jogador.getPontuacaoHabilidade();
                rolesTime1.add(role);
                jogadoresEmPartida.addLast(jogador);
            } else if (time2.size() < 3 && !rolesTime2.contains(role) && !jogadoresEmPartida.contem(jogador)) {
                time2.addLast(jogador);
                pontuacaoTime2 += jogador.getPontuacaoHabilidade();
                rolesTime2.add(role);
                jogadoresEmPartida.addLast(jogador);
            } else {
                listaEspera.addLast(jogador);
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

       System.out.println("Jogadores em lista de espera:");
    noAtualEspera = listaEspera.getPrimeiro();
    while (noAtualEspera != null) {
        Jogador jogador = noAtualEspera.getJogador();
        if (jogador != null) {
            System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
        }
        noAtualEspera = noAtualEspera.getProximo();
    }

    // Removendo jogadores em partida da lista de espera
    noAtualJogadoresEmPartida = jogadoresEmPartida.getPrimeiro();
    while (noAtualJogadoresEmPartida != null) {
        Jogador jogadorEmPartida = noAtualJogadoresEmPartida.getJogador();
        if (jogadorEmPartida != null) {
            listaEspera.removerPorNome(jogadorEmPartida.getNome());
        }
        noAtualJogadoresEmPartida = noAtualJogadoresEmPartida.getProximo();
    }

    // Exibindo a lista de espera atualizada
    System.out.println("Jogadores em lista de espera atualizada:");
    noAtualEspera = listaEspera.getPrimeiro();
    while (noAtualEspera != null) {
        Jogador jogador = noAtualEspera.getJogador();
        if (jogador != null) {
            System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
        }
        noAtualEspera = noAtualEspera.getProximo();
    }
    salvarPartida(time1, time2, pontuacaoTime1, pontuacaoTime2); // Adicione esta linha
    
    
    jogadoresEmPartida = new ListaDuplamenteEncadeada();
    time1 = new ListaDuplamenteEncadeada();
    time2 = new ListaDuplamenteEncadeada();
    pontuacaoTime1 = 0;
    pontuacaoTime2 = 0;
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
      public void selecionarJogadoresRolePontos() {
       ListaDuplamenteEncadeada jogadoresSelecionados = new ListaDuplamenteEncadeada();
       No noAtual = listaJogadores.getPrimeiro();
   
       int pontuacaoMedia = 0;
       int contadorJogadores = 0;
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               if (jogador.getRole().equals("carregador") || jogador.getRole().equals("tanker") || jogador.getRole().equals("suporte") || jogador.getRole().equals("mago")) {
                   pontuacaoMedia += jogador.getPontuacaoHabilidade();
                   contadorJogadores++;
               }
           }
           noAtual = noAtual.getProximo();
       }
   
      if (contadorJogadores > 0) {                     // Calcula a pontuação média dos jogadores selecionados
    pontuacaoMedia /= contadorJogadores;
      } 
   
       noAtual = listaJogadores.getPrimeiro();
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               if (jogador.getRole().equals("carregador") || jogador.getRole().equals("tanker") || jogador.getRole().equals("suporte") || jogador.getRole().equals("mago")) {
                   double variacao = Math.abs((double) jogador.getPontuacaoHabilidade() / pontuacaoMedia - 1);
                   if (variacao <= 0.15) {
                       jogadoresSelecionados.addLast(jogador);
                   }
               }
           }
           noAtual = noAtual.getProximo();
       }
       for (int i = 0; i < 6 && jogadoresSelecionados.getTamanho() > 0; i++) {
           Jogador jogadorSelecionado = jogadoresSelecionados.getRemovePrimeiro();
           System.out.println("Jogador selecionado: " + jogadorSelecionado.getNome() + " - " + jogadorSelecionado.getRole() + " - Habilidade: " + jogadorSelecionado.getPontuacaoHabilidade());
       }
   
       // Adiciona jogadores restantes às equipes
     Jogador jogador;
      noAtual = jogadoresSelecionados.getPrimeiro().getProximo(); // Começa no primeiro elemento da lista
      while (noAtual != null) { // Continua até o final da lista
          jogador = noAtual.getJogador();
          if (jogador != null) {
              if (time1.getTamanho() < 3) {
                  time1.addLast(jogador);
              } else {
                  time2.addLast(jogador);
              }
          }
          noAtual = noAtual.getProximo(); // Avança para o próximo elemento
      }
       
       // call the iniciar method with the appropriate arguments
       iniciar();
   }
}