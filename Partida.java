public class Partida {
    private int id;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogador3;

    public Partida(int id, Jogador jogador1, Jogador jogador2, Jogador jogador3) {
        this.id = id;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogador3 = jogador3;
    }

    public int getId() {
        return id;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public Jogador getJogador3() {
        return jogador3;
    }
}
