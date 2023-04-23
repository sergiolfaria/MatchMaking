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
    
 public void iniciar(ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2) {
    ListaDuplamenteEncadeada listaEspera = new ListaDuplamenteEncadeada();
    ListaDuplamenteEncadeada jogadoresParaRemover = new ListaDuplamenteEncadeada();
    No noAtual = listaJogadores.getPrimeiro();
    while (noAtual != null) {
        Jogador jogador = noAtual.getJogador();
        if (jogador != null) {
            String role = jogador.getRole();
            if (time1.getTamanho() < 3 && !time1.containsRole(role)) {
                time1.addLast(jogador);
                pontuacaoTime1 += jogador.getPontuacaoHabilidade();
                jogadoresParaRemover.addLast(jogador); // Adiciona jogador à lista de remoção
            } else if (time2.getTamanho() < 3 && !time2.containsRole(role)) {
                time2.addLast(jogador);
                this.pontuacaoTime2 += jogador.getPontuacaoHabilidade();              
                jogadoresParaRemover.addLast(jogador); // Adiciona jogador à lista de remoção
            } else {
                listaEspera.addLast(jogador);
            }
        }
        noAtual = noAtual.getProximo();
    }

    // Remove os jogadores da listaJogadores
    No noRemover = jogadoresParaRemover.getPrimeiro();
    while (noRemover != null) {
        Jogador jogador = noRemover.getJogador();
        if (jogador != null) {
            listaJogadores.removerPorNome(jogador.getNome());
        }
        noRemover = noRemover.getProximo();
    }
    // Exibir informações do Time 1
    System.out.println("Time 1 - Habilidade " + pontuacaoTime1 + ":");
    No noAtualTime1 = time1.getPrimeiro();
    while (noAtualTime1 != null) {
        Jogador jogador = noAtualTime1.getJogador();
        System.out.println(jogador.getNome() + " - " + jogador.getRole() + " - Habilidade: " + jogador.getPontuacaoHabilidade());
        noAtualTime1 = noAtualTime1.getProximo();
    }

    // Exibir informações do Time 2
    System.out.println("Time 2 - Habilidade " + this.pontuacaoTime2 + ":");
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
      System.out.println("Pontuação Time 1: " + pontuacaoTime1);
      System.out.println("Pontuação Time 2: " + pontuacaoTime2);
      salvarPartida(time1, time2);
       System.out.println("Partida salva com sucesso!");
       
       limparTimes();
       limparJogadoresComTime();
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
   
       pontuacaoMedia /= contadorJogadores; // Calcula a pontuação média dos jogadores selecionados
   
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
         if (jogadorSelecionado != null) {
         System.out.println("Jogador selecionado: " + jogadorSelecionado.getNome() + " - " + jogadorSelecionado.getRole() + " - Habilidade: " + jogadorSelecionado.getPontuacaoHabilidade());
      }
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
       iniciar(time1, time2);
   }
  

}