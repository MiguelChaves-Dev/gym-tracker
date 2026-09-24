import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.Set;
public class MainFrame extends JFrame {
    private static final String ARQUIVO = "historico.csv";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final HistoricoTreino historico;
    private JTextField campoNome;
    private JTextField campoPeso;
    private JTextField campoData;
    private DefaultTableModel tabelaModel;
    private JComboBox<String> comboExercicios;
    private JPanel painelGrafico;

    public MainFrame(){
        super("Desempenho na Academia");
        historico = new HistoricoTreino();
        historico.carregarArquivo(ARQUIVO);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setSize(900,700);
        setLocationRelativeTo(null);

        add(criarPainelFormulario(), BorderLayout.NORTH);
        add(criarPainelTabela(), BorderLayout.CENTER);
        add(criarPainelGraficoContainer(), BorderLayout.SOUTH);

        atualizarTabela();
        atualizarComboExercicios();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e){
                historico.salvarEmArquivo(ARQUIVO);
                dispose();
                System.exit(0);
            }
        });
    }

    private JPanel criarPainelFormulario(){
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        campoNome = new JTextField(12);
        campoPeso = new JTextField(6);
        campoData = new JTextField(10);
        campoData.setText(LocalDate.now().format(FORMATO_DATA));
        JButton botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.addActionListener(e -> adicionarRegistro());

        painel.add(new JLabel("Exercício: "));
        painel.add(campoNome);
        painel.add(new JLabel("Peso (kg): "));
        painel.add(campoPeso);
        painel.add(new JLabel("Data: "));
        painel.add(campoData);
        painel.add(botaoAdicionar);

        return painel;
    }

    private JScrollPane criarPainelTabela(){
        tabelaModel = new DefaultTableModel(new Object[]{"Exercício", "Peso (kg)", "Data"}, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(tabelaModel);
        return new JScrollPane(tabela);
    }

    private JPanel criarPainelGraficoContainer(){
        JPanel container = new JPanel(new BorderLayout(5 ,5));
        container.setPreferredSize(new Dimension(900, 350));

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboExercicios = new JComboBox<>();
        JButton botaoGerar = new JButton("Gerar Gráfico");
        botaoGerar.addActionListener(e -> gerarGrafico());

        controles.add(new JLabel("Exercício:"));
        controles.add(comboExercicios);
        controles.add(botaoGerar);

        painelGrafico = new JPanel(new BorderLayout());
        painelGrafico.add(new JLabel("Selecione um exercício e clique em Gerar Gráfico", SwingConstants.CENTER), BorderLayout.CENTER);

        container.add(controles, BorderLayout.NORTH);
        container.add(painelGrafico, BorderLayout.CENTER);

        return container;
    }

    private void adicionarRegistro(){
        String nome = campoNome.getText().trim();
        String pesoTexto = campoPeso.getText().trim();
        String dataTexto = campoData.getText().trim();

        if (nome.isEmpty() || pesoTexto.isEmpty() || dataTexto.isEmpty()){
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float peso;
        try {
            peso = Float.valueOf(pesoTexto.replace(",", "."));
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Peso inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataTexto, FORMATO_DATA);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,"Data inválida. Use dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        historico.adicionarRegistro(new RegistroTreino(new Exercicio(nome), peso, data));
        historico.salvarEmArquivo(ARQUIVO);

        campoNome.setText("");
        campoPeso.setText("");
        atualizarTabela();
        atualizarComboExercicios();
    }

    private void atualizarTabela(){
        tabelaModel.setRowCount(0);
        for (RegistroTreino r : historico.getTodos()){
            tabelaModel.addRow(new Object[]{r.getExercicio().getNome(), r.getPeso(), r.getData().format(FORMATO_DATA)});
        }
    }

    private void atualizarComboExercicios(){
        String selecionadoAtual = (String) comboExercicios.getSelectedItem();
        comboExercicios.removeAllItems();

        Set<String> nomes = new LinkedHashSet<>();
        for (RegistroTreino r : historico.getTodos()){
            nomes.add(r.getExercicio().getNome());
        }
        for (String nome : nomes){
            comboExercicios.addItem(nome);
        }

        if (selecionadoAtual != null){
            comboExercicios.setSelectedItem(selecionadoAtual);
        }
    }

    private void gerarGrafico(){
        String nomeSelecionado = (String) comboExercicios.getSelectedItem();
        if(nomeSelecionado == null){
            JOptionPane.showMessageDialog(this, "Nenhum exercício cadastrado ainda.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Exercicio exercicio = new Exercicio(nomeSelecionado);
        var registros = historico.historicoDoExercicio(exercicio);

        painelGrafico.removeAll();
        painelGrafico.add(GraficoDesempenho.criarPainel(nomeSelecionado, registros), BorderLayout.CENTER);
        painelGrafico.revalidate();
        painelGrafico.repaint();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
