import java.time.LocalDate;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public void salvarEmArquivo(String caminho){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (RegistroTreino r: registros){
                writer.write(r.getExercicio().getNome() + ";" + r.getPeso() + ";" + r.getData());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public void carregarArquivo(String caminho){
        File arquivo = new File(caminho);
        if (!arquivo.exists()){
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))){
            String linha;
            while ((linha = reader.readLine()) != null){
                String[] partes = linha.split(";");
                if (partes.length != 3){
                    continue;
                }
                String nome = partes[0];
                float peso = Float.valueOf(partes[1]);
                LocalDate data = LocalDate.parse(partes[2]);
                Exercicio exercicio = new Exercicio(nome);
                registros.add(new RegistroTreino(exercicio, peso, data));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    public Map<String, List<RegistroTreino>> historicoDeVarios(List<Exercicio> exercicios){
        Map<String, List<RegistroTreino>> resultado = new LinkedHashMap<>();
        for (Exercicio ex: exercicios){
            resultado.put(ex.getNome(), historicoDoExercicio(ex));
        }
        return resultado;
    }


}
