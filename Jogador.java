public class Jogador {
    private int id;
    private String nome;
    private String role;
    private int pontuacaoHabilidade;

    

    public Jogador(int id, String nome, String role, int pontuacaoHabilidade ) {
        this.id = id;
        this.nome = nome;
        this.role = role;
        this.pontuacaoHabilidade = pontuacaoHabilidade;
   
       
       
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRole() {
        return role;
    }

    public int getPontuacaoHabilidade() {
        return pontuacaoHabilidade;
    }
    
    public void setId(int id){
      this.id = id;
    }
    public void setNome(String nome){
      this.nome = nome;
    }
    public void setRole(String role){
      this.role = role;
    }
    public void setPontuacaoHabilidade(int pontuacaoHabilidade){
      this.pontuacaoHabilidade = pontuacaoHabilidade;
    }

  
}
