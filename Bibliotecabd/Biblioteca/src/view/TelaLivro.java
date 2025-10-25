package view;

import controller.LivroController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Livro;

public class TelaLivro extends JFrame {

    private JTextField txtId, txtTitulo, txtAutor, txtAno, txtCategoria;
    private JTable tabela;
    private DefaultTableModel modelo;
    private LivroController controller;
    JComboBox<String> comboCampo = new JComboBox<>(new String[]{"titulo", "autor", "categoria"});
    public TelaLivro() {
        super("Gerenciar Livros");
        controller = new LivroController();

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 🔹 Painel do formulário
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Cadastro / Atualização de Livro"));

        txtId = new JTextField();
        txtId.setEditable(false);
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAno = new JTextField();
        txtCategoria = new JTextField();

        painelForm.add(new JLabel("ID:"));
        painelForm.add(txtId);
        painelForm.add(new JLabel("Título:"));
        painelForm.add(txtTitulo);
        painelForm.add(new JLabel("Autor:"));
        painelForm.add(txtAutor);
        painelForm.add(new JLabel("Ano de Publicação:"));
        painelForm.add(txtAno);
        painelForm.add(new JLabel("Categoria:"));
        painelForm.add(txtCategoria);

        add(painelForm, BorderLayout.NORTH);

        // 🔹 Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnListar = new JButton("Listar Livros");
        JTextField txtPesquisa = new JTextField(15);
        JButton btnPesquisar = new JButton("Pesquisar");
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnListar);
        painelBotoes.add(new JLabel("Pesquisar por:"));
        painelBotoes.add(comboCampo);
        painelBotoes.add(txtPesquisa);
        painelBotoes.add(btnPesquisar);

        add(painelBotoes, BorderLayout.SOUTH);

        // 🔹 Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Título", "Autor", "Ano", "Categoria"}, 0);
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // 🔹 Eventos
        btnCadastrar.addActionListener(e -> cadastrarLivro());
        btnAtualizar.addActionListener(e -> atualizarLivro());
        btnExcluir.addActionListener(e -> excluirLivro());
        btnListar.addActionListener(e -> listarLivros());
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());
        btnPesquisar.addActionListener(e -> {
    String campo = comboCampo.getSelectedItem().toString();
    String valor = txtPesquisa.getText();

    List<Livro> resultado = controller.pesquisar(campo, valor);

    // Limpa a tabela antes de mostrar os resultados
    modelo.setRowCount(0);

    if (resultado.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum livro encontrado para o filtro informado!");
        return;
    }

    // Preenche a tabela com os resultados da pesquisa
    for (Livro l : resultado) {
        modelo.addRow(new Object[]{
            l.getId(),
            l.getTitulo(),
            l.getAutor(),
            l.getAnoPublicacao(),
            l.getCategoria() + " (" + (l.isDisponivel() ? "Disponível" : "Emprestado") + ")"
        });
    }
});


    }

    private void cadastrarLivro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String categoria = txtCategoria.getText();

        if (txtAno.getText().isEmpty() || titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Todos os campos são obrigatórios!");
            return;
        }

        int ano;
        try {
            ano = Integer.parseInt(txtAno.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O campo 'Ano' deve conter apenas números!");
            return;
        }

        controller.cadastrarLivro(titulo, autor, ano, categoria);
        listarLivros();
        limparCampos();
    }

    private void listarLivros() {
        modelo.setRowCount(0);
        List<Livro> livros = controller.listarLivros();
        if (livros != null) {
            for (Livro l : livros) {
                modelo.addRow(new Object[]{
                        l.getId(),
                        l.getTitulo(),
                        l.getAutor(),
                        l.getAnoPublicacao(),
                        l.getCategoria()
                });
            }
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(tabela.getValueAt(linha, 0).toString());
            txtTitulo.setText(tabela.getValueAt(linha, 1).toString());
            txtAutor.setText(tabela.getValueAt(linha, 2).toString());
            txtAno.setText(tabela.getValueAt(linha, 3).toString());
            txtCategoria.setText(tabela.getValueAt(linha, 4).toString());
        }
    }

    private void atualizarLivro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um livro na tabela para atualizar!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String categoria = txtCategoria.getText();

        if (txtAno.getText().isEmpty() || titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Todos os campos são obrigatórios!");
            return;
        }

        int ano;
        try {
            ano = Integer.parseInt(txtAno.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O campo 'Ano' deve conter apenas números!");
            return;
        }

        controller.atualizarLivro(id, titulo, autor, ano, categoria);
        listarLivros();
        limparCampos();
    }

    private void excluirLivro() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um livro na tabela para excluir!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        controller.deletarLivro(id);
        listarLivros();
        limparCampos();
    }

    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAno.setText("");
        txtCategoria.setText("");
    }
}
