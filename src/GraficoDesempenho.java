import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GraficoDesempenho {
    public static JFreeChart criarGrafico(String nomeExercicio, List<RegistroTreino> registros){
        TimeSeries serie = new TimeSeries(nomeExercicio);

        for (RegistroTreino r : registros){
            LocalDate data = r.getData();
            Day dia = new Day(data.getDayOfMonth(), data.getMonthValue(), data.getYear());
            serie.add(dia, r.getPeso());
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(serie);

        return ChartFactory.createTimeSeriesChart(
                "Evolução de carga - " + nomeExercicio,
                "Data",
                "Peso(kg)",
                dataset,
                true,
                true,
                false
        );
    }

    public static ChartPanel criarPainel(String nomeExercicio, List<RegistroTreino> registros){
        return new ChartPanel(criarGrafico(nomeExercicio, registros));
    }

    public static ChartPanel criarPainelMes(HistoricoTreino historico, Exercicio exercicio, int mes, int ano){
        List<RegistroTreino> registrosDoMes = historico.historicoDoMes(exercicio, mes, ano);

        if (registrosDoMes.isEmpty()){
            return new ChartPanel(criarGraficoVazio(exercicio.getNome(), mes, ano));
        }
        String nomeMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        String titulo = exercicio.getNome() + " - " + nomeMes + "/" + ano;

        JFreeChart grafico = criarGrafico(exercicio.getNome(), registrosDoMes);
        grafico.setTitle(titulo);
        return new ChartPanel(grafico);
    }

    private static JFreeChart criarGraficoVazio(String nomeExercicio, int mes, int ano){
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(new TimeSeries(nomeExercicio));
        return ChartFactory.createTimeSeriesChart(
                "Sem registros para " + nomeExercicio + " em " + mes + "/" + ano,
                "Data",
                "Peso (kg)",
                dataset,
                true,
                true,
                false
        );
    }

    public static JFreeChart criarGraficoComparativo(Map<String, List<RegistroTreino>> registrosPorExercicio){
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for(Map.Entry<String, List<RegistroTreino>> entrada : registrosPorExercicio.entrySet()){
            String nomeExercicio = entrada.getKey();
            List<RegistroTreino> registros = entrada.getValue();

            TimeSeries serie = new TimeSeries(nomeExercicio);
            for (RegistroTreino r : registros){
                LocalDate data = r.getData();
                Day dia = new Day(data.getDayOfMonth(), data.getMonthValue(), data.getYear());
                try {
                    serie.add(dia, r.getPeso());
                }catch (org.jfree.data.general.SeriesException ignorada){
                    serie.update(dia, r.getPeso());
                }
            }
            dataset.addSeries(serie);
        }
        return ChartFactory.createTimeSeriesChart(
                "Comparativo de desempenho",
                "Data",
                "Peso (kg)",
                dataset,
                true,
                true,
                false
        );
    }

    public static ChartPanel criarPainelComparativo(Map<String, List<RegistroTreino>> registrosPorExercicio){
        return new ChartPanel(criarGraficoComparativo(registrosPorExercicio));
    }



}
