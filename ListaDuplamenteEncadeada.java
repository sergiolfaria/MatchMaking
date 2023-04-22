import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ListaDuplamenteEncadeada {
    private No primeiro;
    private No ultimo;
    private int tamanho = 0;

    public ListaDuplamenteEncadeada(Jogador jogador){
      tamanho = 0;
      primeiro = new No(null);
      ultimo = new No(null);
      
      primeiro.setProximo(ultimo);
      ultimo.setAnterior(primeiro);
    }
    
    public void addFirst(Jogador jogador){
      No novoNo = new No(jogador);
      novoNo.setJogador(jogador);
      novoNo.setAnterior(primeiro);
      novoNo.setProximo(primeiro.getProximo());
      
      primeiro.getProximo().setAnterior(novoNo);
     primeiro.setProximo(novoNo);
      tamanho++;
   }

    public boolean contem(Jogador jogador) {
    No noAtual = primeiro;
    while (noAtual != null) {
        Jogador jogadorAtual = noAtual.getJogador();
        if (jogadorAtual != null && jogadorAtual.getNome().equals(jogador.getNome()) && jogadorAtual.getId() == jogador.getId()) {
            return true;
        }
        noAtual = noAtual.getProximo();
    }
    return false;
}


    public void removerPorNome(String nome) {
        No noAtual = primeiro;
        while (noAtual != null) {
            Jogador jogador = noAtual.getJogador();
            if (jogador.getNome().equals(nome)) {
                No anterior = noAtual.getAnterior();
                No proximo = noAtual.getProximo();
                if (anterior == null) {
                    primeiro = proximo;
                } else {
                    anterior.setProximo(proximo);
                }
                if (proximo == null) {
                    ultimo = anterior;
                } else {
                    proximo.setAnterior(anterior);
                }
                tamanho--;
                return;
            }
            noAtual = noAtual.getProximo();
        }
    }

    public void removerPorId(int id) {
        No noAtual = primeiro;
        while (noAtual != null) {
            Jogador jogador = noAtual.getJogador();
            if (jogador.getId() == id) {
                No anterior = noAtual.getAnterior();
                No proximo = noAtual.getProximo();
                if (anterior == null) {
                    primeiro = proximo;
                } else {
                    anterior.setProximo(proximo);
                }
                if (proximo == null) {
                    ultimo = anterior;
                } else {
                    proximo.setAnterior(anterior);
                }
                tamanho--;
                return;
            }
            noAtual = noAtual.getProximo();
        }
    }
    public No getPrimeiro() {
    return primeiro;
   }
    public void ordenarPorPontuacaoHabilidade() {
        No noAtual = getPrimeiro();
        while (noAtual != null) {
            No noSeguinte = noAtual.getProximo();
            while (noSeguinte != null) {
                Jogador jogadorAtual = noAtual.getJogador();
                Jogador jogadorSeguinte = noSeguinte.getJogador();
                if (jogadorAtual.getPontuacaoHabilidade() < jogadorSeguinte.getPontuacaoHabilidade()) {
                    noAtual.setJogador(jogadorSeguinte);
                    noSeguinte.setJogador(jogadorAtual);
                }
                noSeguinte = noSeguinte.getProximo();
            }
            noAtual = noAtual.getProximo();
        }
    }
    
   
}