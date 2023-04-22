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
       public boolean isEmpty() {
         return tamanho == 0;
       }
        public int getTamanho() {
        return tamanho;
    }

      public void setTamanho(int tamanho) {
         this.tamanho = tamanho;
    }
      
     
      public ListaDuplamenteEncadeada(Jogador jogador) {
          this();
       }
       public void addLast(Jogador jogador) {
       No novoNo = new No(jogador);
       if (isEmpty()) {
           primeiro = novoNo;
           ultimo = novoNo;
       } else {
           ultimo.setProximo(novoNo);
           novoNo.setAnterior(ultimo);
           ultimo = novoNo;
       }
       tamanho++;
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
            if (jogadorAtual != null && jogadorSeguinte != null && jogadorAtual.getPontuacaoHabilidade() < jogadorSeguinte.getPontuacaoHabilidade()) {
                noAtual.setJogador(jogadorSeguinte);
                noSeguinte.setJogador(jogadorAtual);
            }
            noSeguinte = noSeguinte.getProximo();
        }
        noAtual = noAtual.getProximo();
    }
   }
   public Jogador getRemovePrimeiro() {
    if (isEmpty()) {
        return null;
    }
    Jogador jogador = primeiro.getProximo().getJogador();
    primeiro.setProximo(primeiro.getProximo().getProximo());
    primeiro.getProximo().setAnterior(primeiro);
    tamanho--;
    return jogador;
}
  
}