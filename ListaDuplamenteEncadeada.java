import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ListaDuplamenteEncadeada {
    private No primeiro;
    private No ultimo;
    private int tamanho = 0;

    public ListaDuplamenteEncadeada() {
        tamanho = 0;
        primeiro = new No(null);
        ultimo = new No(null);
      
        primeiro.setProximo(ultimo);
        ultimo.setAnterior(primeiro);
    }
    public No getPrimeiro() {
      return primeiro;
    }
    
    public void addFirst(Jogador jogador) {
        No novoNo = new No(jogador);
        novoNo.setJogador(jogador);
        novoNo.setAnterior(primeiro);
        novoNo.setProximo(primeiro.getProximo());
      
        primeiro.getProximo().setAnterior(novoNo);
        primeiro.setProximo(novoNo);
        tamanho++;
    }

    public boolean contem(Jogador jogador) {
        No noAtual = primeiro.getProximo();
        while (noAtual != ultimo) {
            Jogador jogadorAtual = noAtual.getJogador();
            if (jogadorAtual != null && jogadorAtual.getNome().equals(jogador.getNome()) && jogadorAtual.getId() == jogador.getId()) {
                return true;
            }
            noAtual = noAtual.getProximo();
        }
        return false;
    }

    public void removerPorNome(String nome) {
        No noAtual = primeiro.getProximo();
        while (noAtual != ultimo) {
            Jogador jogador = noAtual.getJogador();
            if (jogador.getNome().equals(nome)) {
                No anterior = noAtual.getAnterior();
                No proximo = noAtual.getProximo();
                anterior.setProximo(proximo);
                proximo.setAnterior(anterior);
                tamanho--;
                return;
            }
            noAtual = noAtual.getProximo();
        }
    }

    public void removerPorId(int id) {
        No noAtual = primeiro.getProximo();
        while (noAtual != ultimo) {
            Jogador jogador = noAtual.getJogador();
            if (jogador.getId() == id) {
                No anterior = noAtual.getAnterior();
                No proximo = noAtual.getProximo();
                anterior.setProximo(proximo);
                proximo.setAnterior(anterior);
                tamanho--;
                return;
            }
            noAtual = noAtual.getProximo();
        }
    }
}