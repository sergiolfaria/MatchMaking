import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GerenciadorJogadores {
    private ListaDuplamenteEncadeada listaJogadores;
    private String arquivoJogadores = "jogadores.txt";
    private GerenciadorPartidas gerenciadorPartidas;
    Jogador jogador = new Jogador(0,"","",0);

    public GerenciadorJogadores(Jogador jogador) {
        listaJogadores = new ListaDuplamenteEncadeada();
        
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

    public void listarPartidas() {
        gerenciadorPartidas.listarPartidasEmAndamento(); // chamar o método listarPartidas do Gerenciador de Partidas
    }


   public Jogador parearJogadores(String role) {
       int pontuacaoMinima = jogador.getPontuacaoHabilidade();
       No noAtual = jogador.getPrimeiro();
       Jogador jogadorEscolhido = null;
   
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null && jogador.getRole().equals(role) && jogador.getPontuacaoHabilidade() >= pontuacaoMinima) {
               if (jogadorEscolhido == null || jogador.getPontuacaoHabilidade() < jogadorEscolhido.getPontuacaoHabilidade()) {
                   jogadorEscolhido = jogador;
               }
           }
           noAtual = noAtual.getProximo();
       }
   
       jogador.removerJogador(jogadorEscolhido);
       return jogadorEscolhido;
   }

    public void removerJogadorPartida() {
        String nomePartida = Utils.lerTexto("Informe o nome da partida: ");
        String nomeJogador = Utils.lerTexto("Informe o nome do jogador: ");
        gerenciadorPartidas.removerJogadorPartida(nomePartida, nomeJogador); // chamar o método removerJogadorPartida do Gerenciador de Partidas
    }

    public void listarJogadoresPartida() {
        String nomePartida = Utils.lerTexto("Informe o nome da partida: ");
        gerenciadorPartidas.listarJogadoresPartida(nomePartida); // chamar o método listarJogadoresPartida do Gerenciador de Partidas
    }
}