import java.time.LocalDate;
public class RegistroTreino {
    private Exercicio exercicio;
    private float peso;
    private LocalDate data;

    public RegistroTreino(Exercicio ex, float pesoTreino, LocalDate dataTreino){
        exercicio = ex;
        peso = pesoTreino;
        data = dataTreino;
    }

    public Exercicio getExercicio(){
        return exercicio;
    }
    public float getPeso(){
        return peso;
    }
    public LocalDate getData(){
        return data;
    }

    @Override
    public String toString(){
        return "[" + exercicio + "] - Peso: " + peso + "kg - Data: " + data;
    }
}
