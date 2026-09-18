import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoTreino {
    private List<RegistroTreino> registros;

    public HistoricoTreino(){
        registros = new ArrayList<>();
    }
    public void adicionarRegistro(RegistroTreino r){
        registros.add(r);
    }

    public List<RegistroTreino> historicoDoExercicio(Exercicio ex) {
        return registros.stream()
                .filter(r -> r.getExercicio().equals(ex))
                .sorted((a, b) -> a.getData().compareTo(b.getData()))
                .collect(Collectors.toList());
    }

    public List<RegistroTreino> historicoDoMes(Exercicio ex, int mes, int ano){
        return historicoDoExercicio(ex).stream()
                .filter(r -> r.getData().getMonthValue() == mes && r.getData().getYear() == ano)
                .collect(Collectors.toList());
    }

    public List<RegistroTreino> getTodos(){
        return registros;
    }

}
