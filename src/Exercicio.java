public class Exercicio {
    public Exercicio(String nomeExercicio){
        nome = nomeExercicio;
    }
    private String nome;

    public String getNome(){
        return nome;
    }
    public void setNome(String novoNome){
        nome = novoNome;
    }

    @Override
    public String toString(){
        return nome;
    }
    public boolean equals(Exercicio e){
        if(nome.equals(e.getNome())){
            return true;
        }else{
            return false;
        }
    }
}
