import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    private static final String ARQUIVO = "historico.csv";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HistoricoTreino historico = new HistoricoTreino();
        historico.carregarArquivo(ARQUIVO);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean continuar = true;
        while(continuar) {
            System.out.print("Nome do exercício: ");
            Exercicio exercicio = new Exercicio(input.nextLine());
            System.out.print("Peso utilizado (kg): ");
            float peso = Float.valueOf(input.nextLine());
            System.out.print("Data (dd/MM/yyyy): ");
            LocalDate data;
            try {
                data = LocalDate.parse(input.nextLine(), formato);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida, usando hoje.");
                data = LocalDate.now();
            }
            historico.adicionarRegistro(new RegistroTreino(exercicio, peso, data));

            System.out.print("Adicionar outro registro? (s/n): ");
            continuar = input.nextLine().equalsIgnoreCase("s");
        }

        historico.salvarEmArquivo(ARQUIVO);

        System.out.println("\n+-- Histórico completo --+");
        for (RegistroTreino r : historico.getTodos()){
            System.out.println(r);
        }
        System.out.println("\nFiltrar por exercício (nome exato): ");
        String nomeFiltro = input.nextLine();
        Exercicio filtro = new Exercicio(nomeFiltro);

        System.out.println("\n+-- Evolução de " + nomeFiltro + " --+");
        for(RegistroTreino r: historico.historicoDoExercicio(filtro)){
            System.out.println(r.getData() + " -> " + r.getPeso() + "kg");
        }
        input.close();
    }
}