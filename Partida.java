public class Partida {
   private int id;
   private ListaDuplamenteEncadeada time1;
   private ListaDuplamenteEncadeada time2;
   private int pontuacaoTime1;
   private int pontuacaoTime2;

   public Partida(int id, ListaDuplamenteEncadeada time1, ListaDuplamenteEncadeada time2, int pontuacaoTime1, int pontuacaoTime2) {
      this.id = id;
      this.time1 = time1;
      this.time2 = time2;
      this.pontuacaoTime1 = pontuacaoTime1;
      this.pontuacaoTime2 = pontuacaoTime2;
   }

   public int getId() {
      return id;
   }

   public ListaDuplamenteEncadeada getTime1() {
      return time1;
   }

   public ListaDuplamenteEncadeada getTime2() {
      return time2;
   }

   public int getPontuacaoTime1() {
      return pontuacaoTime1;
   }

   public int getPontuacaoTime2() {
      return pontuacaoTime2;
   }
}
