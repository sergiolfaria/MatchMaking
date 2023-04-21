public class GerenciadorPartidas {
    private ListaDuplamenteEncadeada jogadoresDisponiveis;
    private ListaDuplamenteEncadeada jogadoresEmPartida;
    private int idPartida = 1;

    public GerenciadorPartidas(ListaDuplamenteEncadeada jogadoresDisponiveis) {
        this.jogadoresDisponiveis = jogadoresDisponiveis;
        jogadoresEmPartida = new ListaDuplamenteEncadeada();
    }

    public void listarPartidasEmAndamento() {
        System.out.println("Partidas em andamento: ");
        No noAtual = jogadoresEmPartida.getPrimeiro();
        while (noAtual != null) {
            Partida partida = (Partida) noAtual.getElemento();
            if (partida != null) {
                System.out.println("Partida " + partida.getId() + ": " + partida.getJogador1().getNome() + " x " + partida.getJogador2().getNome() + " x " + partida.getJogador3().getNome());
            }
            noAtual = noAtual.getProximo();
        }
    }

    public void iniciarPartida() {
        if (jogadoresDisponiveis.size() >= 3) {
            No noJogador1 = jogadoresDisponiveis.getPrimeiro();
            Jogador jogador1 = (Jogador) noJogador1.getElemento();
            jogadoresDisponiveis.removerPorId(jogador1.getId());

            No noJogador2 = jogadoresDisponiveis.getPrimeiro();
            Jogador jogador2 = (Jogador) noJogador2.getElemento();
            jogadoresDisponiveis.removerPorId(jogador2.getId());

            No noJogador3 = jogadoresDisponiveis.getPrimeiro();
            Jogador jogador3 = (Jogador) noJogador3.getElemento();
            jogadoresDisponiveis.removerPorId(jogador3.getId());

            Partida partida = new Partida(idPartida, jogador1, jogador2, jogador3);
            jogadoresEmPartida.adicionar(partida);

            System.out.println("Partida #" + idPartida + " iniciada com os jogadores " + jogador1.getNome() + ", " + jogador2.getNome() + " e " + jogador3.getNome());
            idPartida++;
        } else {
            System.out.println("Não há jogadores suficientes para iniciar uma partida.");
        }
    }
}



