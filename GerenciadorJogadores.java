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
    int pontuacaoTime1;
    int pontuacaoTime2;
    int indice;
    ListaDuplamenteEncadeada time1 = new ListaDuplamenteEncadeada();
    ListaDuplamenteEncadeada time2 = new ListaDuplamenteEncadeada();
    ListaDuplamenteEncadeada listaEspera = new ListaDuplamenteEncadeada();
    ListaDuplamenteEncadeada jogadoresParaRemover = new ListaDuplamenteEncadeada();

    public GerenciadorJogadores(Jogador jogador) {
        listaJogadores = new ListaDuplamenteEncadeada(jogador);
    }

    public void adicionarJogadorNaEspera(Jogador jogador) {
      listaEspera.addLast(jogador);
    }

    public void adicionarJogador() {
       String nome = Utils.lerTexto("Informe o nome do jogador: ");
       int id = Utils.lerInt("Informe o ID do jogador: ");
       String role = Utils.lerTexto("Informe a role do jogador: ");
       int pontuacaoHabilidade = Utils.lerInt("Informe a pontuação de habilidade do jogador: ");
   
       Jogador jogador = new Jogador(id, nome, role, pontuacaoHabilidade);
   
       listaJogadores.addLast(jogador);
       adicionarJogadorNaEspera(jogador); // Adiciona jogador à listaEspera
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
        No noAtual = listaEspera.getPrimeiro();
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
                  adicionarJogadorNaEspera(jogador); // Adiciona jogador à listaEspera
              }
              reader.close();
          } catch (IOException e) {
              System.out.println("Erro ao carregar jogadores: " + e.getMessage());
          }
      }
   public void limparJogadoresComTime() {
       No atual = listaEspera.getPrimeiro();
   
       while (atual != null) {
           Jogador jogadorAtual = (Jogador) atual.getElemento();
           No proximo = atual.getProximo();
   
           if (time1.buscar(jogadorAtual) != null || time2.buscar(jogadorAtual) != null) {
               // jogador adicionado em algum dos times, remover da lista de espera
               listaEspera.remover(jogadorAtual);
           }
   
           atual = proximo;
       }
   }

   
    public void limparTimes() {
        // Limpa os times
        time1.limpar();
        time2.limpar();
      
    }
    
  public void distribuirJogadores(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, ListaDuplamenteEncadeada listaEspera, ListaDuplamenteEncadeada jogadoresParaRemover) {
    No noAtual = listaEspera.getPrimeiro();
    while (noAtual != null) {
        Jogador jogador = noAtual.getJogador();
        if (jogador != null) {
            String role = jogador.getRole();
            if (time1.getTamanho() < 3 && !time1.containsRole(role)) {
                time1.addLast(jogador);
                pontuacaoTime1 += jogador.getPontuacaoHabilidade();
                jogadoresParaRemover.addLast(jogador);
            } else if (time2.getTamanho() < 3 && !time2.containsRole(role)) {
                time2.addLast(jogador);
                pontuacaoTime2 += jogador.getPontuacaoHabilidade();
                jogadoresParaRemover.addLast(jogador);
            }
        }
        noAtual = noAtual.getProximo();
    }
}

   
   public void removerJogadoresSelecionados(ListaDuplamenteEncadeada jogadoresParaRemover) {
       No noRemover = jogadoresParaRemover.getPrimeiro();
       while (noRemover != null) {
           Jogador jogador = noRemover.getJogador();
           if (jogador != null) {
               listaEspera.removerPorNome(jogador.getNome());
           }
           noRemover = noRemover.getProximo();
       }
}

   public void iniciar(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2) {
       distribuirJogadores(time1, time2, listaEspera, jogadoresParaRemover);
       removerJogadoresNaoAlocados();
       exibirInformacoes(time1, time2, listaEspera);
   
       // Garantir que os times tenham 3 jogadores cada
       if (time1.getTamanho() < 3 || time2.getTamanho() < 3) {
           System.out.println("Não foi possível criar times com 3 jogadores cada.");
           return;
       }
   
       salvarPartida(time1, time2);
       System.out.println("Partida salva com sucesso!");
   
       limparJogadoresComTime();
       limparTimes();
   }

      public void exibirInformacoes(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, ListaDuplamenteEncadeada listaEspera) {
          // Exibir informações do Time 1
          System.out.println("Time 1 - Habilidade " + pontuacaoTime1 + ":");
          No noAtualTime1 = time1.getPrimeiro();
          while (noAtualTime1 != null) {
              Jogador jogador = noAtualTime1.getJogador();
              if (jogador != null) {
                  System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
              } else {
                     }
              noAtualTime1 = noAtualTime1.getProximo();
          }
      
          // Exibir informações do Time 2
          System.out.println("Time 2 - Habilidade " + pontuacaoTime2 + ":");
          No noAtualTime2 = time2.getPrimeiro();
          while (noAtualTime2 != null) {
              Jogador jogador = noAtualTime2.getJogador();
              if (jogador != null) {
                  System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
              } else {
                  
              }
              noAtualTime2 = noAtualTime2.getProximo();
          }
      
          // Exibir informações da Lista de Espera
          System.out.println("Lista de espera:");
          No noAtualListaEspera = listaEspera.getPrimeiro();
          while (noAtualListaEspera != null) {
              Jogador jogador = noAtualListaEspera.getJogador();
              if (jogador != null) {
                  System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
              } else {
                  
              }
              noAtualListaEspera = noAtualListaEspera.getProximo();
          }
      }

   
         
 
     public void salvarPartida(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2) {
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
           writer.write("Time 2 - Habilidade " + this.pontuacaoTime2 + ":");
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
    No noAtual = listaEspera.getPrimeiro();

    while (noAtual != null) {
        Jogador jogador = noAtual.getJogador();
        if (jogador != null) {
            String role = jogador.getRole();
            if (time1.getTamanho() < 3 && !time1.containsRole(role)) {
                time1.addLast(jogador);
            } else if (time2.getTamanho() < 3 && !time2.containsRole(role)) {
                time2.addLast(jogador);
            }
        }
        noAtual = noAtual.getProximo();
    }

    // Verificar se os times têm roles diferentes e pontuações próximas (até 15% de diferença)
    if (timesSatisfazemRequisitos(time1, time2)) {      
        iniciar(time1, time2);
         // Adicione esta linha
    } else {
        System.out.println("Não foi possível criar times que satisfaçam os requisitos de roles e pontuação.");
    }
}
   private boolean timesSatisfazemRequisitos(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2) {
       if (time1.getTamanho() != 3 || time2.getTamanho() != 3) {
           return false;
       }
   
       int pontuacaoTotalTime1 = 0;
       int pontuacaoTotalTime2 = 0;
       No noAtual = time1.getPrimeiro().getProximo();
       while (noAtual != null && noAtual.getJogador() != null) {
           Jogador jogador = noAtual.getJogador();
           pontuacaoTotalTime1 += jogador.getPontuacaoHabilidade();
           noAtual = noAtual.getProximo();
       }
       noAtual = time2.getPrimeiro().getProximo();
       while (noAtual != null && noAtual.getJogador() != null) {
           Jogador jogador = noAtual.getJogador();
           pontuacaoTotalTime2 += jogador.getPontuacaoHabilidade();
           noAtual = noAtual.getProximo();
       }
   
       double diferencaPercentual = Math.abs((double) (pontuacaoTotalTime1 - pontuacaoTotalTime2) / ((pontuacaoTotalTime1 + pontuacaoTotalTime2) / 2)) * 100;
   
       return diferencaPercentual <= 15;
   }

   public void removerJogadoresNaoAlocados() {
       No noAtual = listaEspera.getPrimeiro();
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null && listaEspera.buscar(jogador) == null && time1.buscar(jogador) == null && time2.buscar(jogador) == null) {
               listaEspera.removerPorNome(jogador.getNome());
           }
           noAtual = noAtual.getProximo();
       }
   }

}