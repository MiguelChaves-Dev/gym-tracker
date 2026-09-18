public class ListaExercicio {
    public ListaExercicio(int qntExercicios){
        exercicios = new Exercicio[qntExercicios];
    }

    private Exercicio[] exercicios;
    private int qnt = 0;

    public void adicionarExercicio(Exercicio e){
        if (qnt == exercicios.length){
            dobrarLista();
        }
        exercicios[qnt] = e;
        qnt++;
    }
    public void dobrarLista(){
        Exercicio[] listaDobrada = new Exercicio[exercicios.length*2];
        for (int i = 0; i < qnt; i++){
            listaDobrada[i] = exercicios[i];
        }
        exercicios = listaDobrada;
    }
    public int getQnt(){
        return qnt;
    }

}
