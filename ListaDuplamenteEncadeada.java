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
            primeiro.setProximo(novoNo);
            novoNo.setAnterior(primeiro);
            novoNo.setProximo(ultimo);
            ultimo.setAnterior(novoNo);
        } else {
            ultimo.getAnterior().setProximo(novoNo);
            novoNo.setAnterior(ultimo.getAnterior());
            novoNo.setProximo(ultimo);
            ultimo.setAnterior(novoNo);
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
    public void removerJogador(Jogador jogador) {
    No noAtual = primeiro;
    while (noAtual != null) {
        Jogador j = noAtual.getJogador();
        if (j != null && j.equals(jogador)) {
            if (noAtual == primeiro) {
                primeiro = noAtual.getProximo();
                if (primeiro != null) {
                    primeiro.setAnterior(null);
                }
            } else if (noAtual == ultimo) {
                ultimo = noAtual.getAnterior();
                if (ultimo != null) {
                    ultimo.setProximo(null);
                }
            } else {
                noAtual.getAnterior().setProximo(noAtual.getProximo());
                noAtual.getProximo().setAnterior(noAtual.getAnterior());
            }
            tamanho--;
            break;
        }
        noAtual = noAtual.getProximo();
    }
}

    
    
    public Jogador removerPorNome(String nome) {
       if (primeiro == null) {
           return null;
       }
   
       No noAtual = primeiro;
   
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
   
           if (jogador.getNome().equalsIgnoreCase(nome)) {
               if (noAtual == primeiro) {
                   primeiro = noAtual.getProximo();
                   if (primeiro != null) {
                       primeiro.setAnterior(null);
                   }
               } else if (noAtual == ultimo) {
                   ultimo = noAtual.getAnterior();
                   if (ultimo != null) {
                       ultimo.setProximo(null);
                   }
               } else {
                   noAtual.getAnterior().setProximo(noAtual.getProximo());
                   noAtual.getProximo().setAnterior(noAtual.getAnterior());
               }
               tamanho--;
               return jogador;
           }
   
           noAtual = noAtual.getProximo();
       }
   
       return null;
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
       No proximoNo = primeiro.getProximo();
       if (proximoNo == null) {
           return null;
       }
       Jogador jogador = proximoNo.getJogador();
       No proximoDoProximo = proximoNo.getProximo();
       if (proximoDoProximo != null) {
           proximoDoProximo.setAnterior(primeiro);
       }
       primeiro.setProximo(proximoDoProximo);
       tamanho--;
       return jogador;
   }
     public void limpar() {
       primeiro.setProximo(ultimo);
       ultimo.setAnterior(primeiro);
       tamanho = 0;
   }

    public No get(int indice) {
       if (indice < 0 || indice >= tamanho) {
           throw new IndexOutOfBoundsException();
       }
       
       No noAtual = primeiro.getProximo();
       for (int i = 0; i < indice; i++) {
           noAtual = noAtual.getProximo();
       }
       
       return noAtual;
   }
   public boolean containsRole(String role) {
       No noAtual = getPrimeiro();
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null && jogador.getRole().equals(role)) {
               return true;
           }
           noAtual = noAtual.getProximo();
       }
       return false;
   }
   private No proximoNoNaoNulo(No noAtual) {
    No proximoNo = noAtual.getProximo();
    while (proximoNo != null && proximoNo.getJogador() == null) {
        proximoNo = proximoNo.getProximo();
    }
    return proximoNo;
}

   public No buscar(Jogador jogador) {
       if (jogador == null) {
           return null;
       }
   
       No noAtual = proximoNoNaoNulo(primeiro);
       while (noAtual != null) {
           Jogador jogadorAtual = noAtual.getJogador();
           if (jogadorAtual.getId() == jogador.getId()) {
               return noAtual;
           }
           noAtual = proximoNoNaoNulo(noAtual);
       }
       return null;
   }
  public void remover(Jogador jogador) {
        No noAtual = primeiro;
        while (noAtual != null) {
            Jogador jogadorAtual = noAtual.getJogador();
            if (jogadorAtual != null && jogadorAtual.getId() == jogador.getId()) {
                No anterior = noAtual.getAnterior();
                No proximo = noAtual.getProximo();

                if (anterior != null) {
                    anterior.setProximo(proximo);
                }
                if (proximo != null) {
                    proximo.setAnterior(anterior);
                }

                tamanho--;
                return;
            }
            noAtual = noAtual.getProximo();
        }
    }
    public int calcularPontuacaoTotalHabilidade() {
       int totalHabilidade = 0;
       No noAtual = getPrimeiro();
       while (noAtual != null) {
           Jogador jogador = noAtual.getJogador();
           if (jogador != null) {
               totalHabilidade += jogador.getPontuacaoHabilidade();
           }
           noAtual = noAtual.getProximo();
       }
       return totalHabilidade;
   }
   public boolean contains(Jogador jogador) {
    No noAtual = primeiro;
    while (noAtual != null) {
        if (noAtual.getJogador().equals(jogador)) {
            return true;
        }
        noAtual = noAtual.getProximo();
    }
    return false;
}
public Jogador getJogadorPorNome(String nome) {
    if (nome == null || nome.isEmpty()) {
        return null;
    }

    No noAtual = getPrimeiro();
    while (noAtual != null) {
        Jogador jogador = noAtual.getJogador();
        if (jogador != null && jogador.getNome().equalsIgnoreCase(nome)) {
            return jogador;
        }
        noAtual = noAtual.getProximo();
    }

    return null;
}  


  
   
}