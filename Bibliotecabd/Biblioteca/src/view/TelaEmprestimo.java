package view;

import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import model.Emprestimo;
import model.Livro;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaEmprestimo extends JFrame {

    private JTextField txtId, txtRetirada, txtDevolucao, txtMulta;
    private JComboBox<Usuario> comboUsuario;
    private JComboBox<Livro> comboLivro;
    private JTable tabela;
    private DefaultTableModel modelo;
    private EmprestimoController controller;
    private LivroController livroController;
    private UsuarioController usuarioController;

    public TelaEmprestimo() {
        super("Gerenciar Empréstimos");

        controller = new EmprestimoController();
        livroController = new LivroController();
        usuarioController = new UsuarioController();

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 🔹 Painel do formulário
        JPanel painelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Cadastro / Atualização de Empréstimo"));

        txtId = new JTextField();
        txtId.setEditable(false);
        comboUsuario = new JComboBox<>();
        comboLivro = new JComboBox<>();
        txtRetirada = new JTextField(LocalDate.now().toString()); // data atual padrão
        txtDevolucao = new JTextField();
        txtMulta = new JTextField("0.00");

        painelForm.add(new JLabel("ID:"));
        painelForm.add(txtId);
        painelForm.add(new JLabel("Usuário:"));
        painelForm.add(comboUsuario);
        painelForm.add(new JLabel("Livro:"));
        painelForm.add(comboLivro);
        painelForm.add(new JLabel("Data de Retirada (AAAA-MM-DD):"));
        painelForm.add(txtRetirada);
        painelForm.add(new JLabel("Data de Devolução (AAAA-MM-DD):"));
        painelForm.add(txtDevolucao);
        painelForm.add(new JLabel("Multa (R$):"));
        painelForm.add(txtMulta);

        add(painelForm, BorderLayout.NORTH);

        // 🔹 Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnListar = new JButton("Listar Todos");
        JButton btnDevolver = new JButton("Marcar como Devolvido");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnDevolver);
        painelBotoes.add(btnListar);

        add(painelBotoes, BorderLayout.SOUTH);

        // 🔹 Tabela
        modelo = new DefaultTableModel(new Object[]{"ID", "Usuário", "Livro", "Retirada", "Devolução", "Multa", "Devolvido"}, 0);
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // 🔹 Carregar combos e tabela
        carregarUsuarios();
        carregarLivros();
        listarEmprestimos();

        // 🔹 Eventos
        btnCadastrar.addActionListener(e -> cadastrarEmprestimo());
        btnAtualizar.addActionListener(e -> atualizarEmprestimo());
        btnExcluir.addActionListener(e -> excluirEmprestimo());
        btnListar.addActionListener(e -> listarEmprestimos());
        btnDevolver.addActionListener(e -> marcarDevolvido());
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCampos());
    }

    private void carregarUsuarios() {
        comboUsuario.removeAllItems();
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        if (usuarios != null) {
            for (Usuario u : usuarios) comboUsuario.addItem(u);
        }
    }

    private void carregarLivros() {
        comboLivro.removeAllItems();
        List<Livro> livros = livroController.listarLivros();
        if (livros != null) {
            for (Livro l : livros) comboLivro.addItem(l);
        }
    }

    private void cadastrarEmprestimo() {
        Usuario usuario = (Usuario) comboUsuario.getSelectedItem();
        Livro livro = (Livro) comboLivro.getSelectedItem();

        if (usuario == null || livro == null) {
            JOptionPane.showMessageDialog(null, "Selecione um usuário e um livro!");
            return;
        }

        LocalDate retirada = LocalDate.parse(txtRetirada.getText());
        LocalDate devolucao = LocalDate.parse(txtDevolucao.getText());
        double multa = Double.parseDouble(txtMulta.getText());

        controller.cadastrarEmprestimo(usuario.getId(), livro.getId(), retirada, devolucao, multa);
        listarEmprestimos();
        limparCampos();
    }

    private void listarEmprestimos() {
        modelo.setRowCount(0);
        List<Emprestimo> emprestimos = controller.listarEmprestimos();
        if (emprestimos != null) {
            for (Emprestimo e : emprestimos) {
                modelo.addRow(new Object[]{
                        e.getId(),
                        e.getIdUsuario(),
                        e.getIdLivro(),
                        e.getRetirada(),
                        e.getDevolucao(),
                        e.getMulta(),
                        e.isDevolvido() ? "Sim" : "Não"
                });
            }
        }
    }

    private void preencherCampos() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(tabela.getValueAt(linha, 0).toString());
            txtRetirada.setText(tabela.getValueAt(linha, 3).toString());
            txtDevolucao.setText(tabela.getValueAt(linha, 4).toString());
            txtMulta.setText(tabela.getValueAt(linha, 5).toString());
        }
    }

    private void atualizarEmprestimo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um empréstimo para atualizar!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        Usuario usuario = (Usuario) comboUsuario.getSelectedItem();
        Livro livro = (Livro) comboLivro.getSelectedItem();
        LocalDate retirada = LocalDate.parse(txtRetirada.getText());
        LocalDate devolucao = LocalDate.parse(txtDevolucao.getText());
        double multa = Double.parseDouble(txtMulta.getText());

        controller.atualizarEmprestimo(id, usuario.getId(), livro.getId(), retirada, devolucao, multa);
        listarEmprestimos();
        limparCampos();
    }

    private void excluirEmprestimo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um empréstimo para excluir!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        controller.deletarEmprestimo(id);
        listarEmprestimos();
        limparCampos();
    }

    private void marcarDevolvido() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione um empréstimo para marcar como devolvido!");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        controller.marcarComoDevolvido(id);
        listarEmprestimos();
    }

    private void limparCampos() {
        txtId.setText("");
        txtRetirada.setText(LocalDate.now().toString());
        txtDevolucao.setText("");
        txtMulta.setText("0.00");
    }
}
