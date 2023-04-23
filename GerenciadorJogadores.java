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
           }
           reader.close();
       } catch (IOException e) {
           System.out.println("Erro ao carregar jogadores: " + e.getMessage());
       }
   }
    public void limparJogadoresComTime() {
        No atual = listaJogadores.getPrimeiro();

        while (atual != null) {
            Jogador jogadorAtual = (Jogador) atual.getElemento();
            No proximo = atual.getProximo();

            if (time1.buscar(jogadorAtual) != null || time2.buscar(jogadorAtual) != null) {
                listaJogadores.removerPorNome(jogadorAtual.getNome());
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
       No noAtual = listaJogadores.getPrimeiro();
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               String role = jogador.getRole();
               if (time1.getTamanho() < 3 && !time1.containsRole(role)) {
                   time1.addLast(jogador);
                   pontuacaoTime1 += jogador.getPontuacaoHabilidade();
                   jogadoresParaRemover.addLast(jogador);
               } else if (time2.getTamanho() < 3 && !time2.contains(role) && !time1.buscar(jogador)) {
                   time2.addLast(jogador);
                   pontuacaoTime2 += jogador.getPontuacaoHabilidade();
                   jogadoresParaRemover.addLast(jogador);
               } else {
                   listaEspera.addLast(jogador);
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
               listaJogadores.removerPorNome(jogador.getNome());
           }
           noRemover = noRemover.getProximo();
       }
   }
   public void iniciar(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2) {
       ListaDuplamenteEncadeada listaEspera = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada jogadoresParaRemover = new ListaDuplamenteEncadeada();
   
       distribuirJogadores(time1, time2, listaEspera, jogadoresParaRemover);
       removerJogadoresSelecionados(jogadoresParaRemover);
       exibirInformacoes(time1, time2, listaEspera);
   
       // Garantir que os times tenham 3 jogadores cada
       if (time1.getTamanho() < 3 || time2.getTamanho() < 3) {
           System.out.println("Não foi possível criar times com 3 jogadores cada.");
           return;
       }
   
       salvarPartida(time1, time2);
       System.out.println("Partida salva com sucesso!");
   
       limparTimes();
       limparJogadoresComTime();
   }
      public void exibirInformacoes(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, ListaDuplamenteEncadeada listaEspera) {
          // Exibir informações do Time 1
          System.out.println("Time 1 - Habilidade " + pontuacaoTime1 + ":");
          No noAtualTime1 = time1.getPrimeiro();
          while (noAtualTime1 != null) {
              Jogador jogador = noAtualTime1.getJogador();
              System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
              noAtualTime1 = noAtualTime1.getProximo();
          }
      
          // Exibir informações do Time 2
          System.out.println("Time 2 - Habilidade " + pontuacaoTime2 + ":");
          No noAtualTime2 = time2.getPrimeiro();
          while (noAtualTime2 != null) {
              Jogador jogador = noAtualTime2.getJogador();
              if (jogador != null) {
                  System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
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
       ListaDuplamenteEncadeada carregadores = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada tankers = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada suportes = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada magos = new ListaDuplamenteEncadeada();
   
       No noAtual = listaJogadores.getPrimeiro();
   
       // Agrupar jogadores por role
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               String role = jogador.getRole();
               switch (role) {
                   case "carregador":
                       carregadores.addLast(jogador);
                       break;
                   case "tanker":
                       tankers.addLast(jogador);
                       break;
                   case "suporte":
                       suportes.addLast(jogador);
                       break;
                   case "mago":
                       magos.addLast(jogador);
                       break;
               }
           }
           noAtual = noAtual.getProximo();
       }
   
       ListaDuplamenteEncadeada time1 = new ListaDuplamenteEncadeada();
       ListaDuplamenteEncadeada time2 = new ListaDuplamenteEncadeada();
   
       // Atribuir um jogador de cada role para cada time
       if (carregadores.getTamanho() >= 2) {
           time1.addLast(carregadores.getRemovePrimeiro());
           time2.addLast(carregadores.getRemovePrimeiro());
       } else {
           System.out.println("Não há jogadores suficientes com o role carregador para formar dois times.");
           return;
       }
   
       if (tankers.getTamanho() >= 2) {
           time1.addLast(tankers.getRemovePrimeiro());
           time2.addLast(tankers.getRemovePrimeiro());
       } else {
           System.out.println("Não há jogadores suficientes com o role tanker para formar dois times.");
           return;
       }
   
       if (suportes.getTamanho() >= 2) {
           time1.addLast(suportes.getRemovePrimeiro());
           time2.addLast(suportes.getRemovePrimeiro());
       } else {
           System.out.println("Não há jogadores suficientes com o role suporte para formar dois times.");
           return;
       }
   
       if (magos.getTamanho() >= 2) {
           time1.addLast(magos.getRemovePrimeiro());
           time2.addLast(magos.getRemovePrimeiro());
       } else {
           System.out.println("Não há jogadores suficientes com o role mago para formar dois times.");
           return;
       }
   
       // Chamar o método iniciar com os times apropriados
       iniciar(time1, time2);
   }

}
