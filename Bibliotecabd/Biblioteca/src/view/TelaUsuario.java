package view;

import controller.UsuarioController;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Usuario;

public class TelaUsuario extends JFrame {
    private JTextField txtNome, txtMatricula, txtEmail, txtSenha;
    private JComboBox<String> comboTipo;
    private UsuarioController usuarioController = new UsuarioController();

    public TelaUsuario() {
        setTitle("Gerenciar Usuários");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblMatricula = new JLabel("Matrícula:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblSenha = new JLabel("Senha:");
        JLabel lblTipo = new JLabel("Tipo de Usuário:");

        txtNome = new JTextField();
        txtMatricula = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"CLIENTE", "FUNCIONARIO"});

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnListar = new JButton("Listar Todos");

        add(lblNome); add(txtNome);
        add(lblMatricula); add(txtMatricula);
        add(lblEmail); add(txtEmail);
        add(lblSenha); add(txtSenha);
        add(lblTipo); add(comboTipo);
        add(btnCadastrar); add(btnAtualizar);
        add(btnDeletar); add(btnListar);

        // 🔹 Botão cadastrar
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String matricula = txtMatricula.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            String tipo = comboTipo.getSelectedItem().toString();

            usuarioController.cadastrarUsuario(nome, matricula, email, senha, tipo);
        });

        // 🔹 Atualizar usuário
        btnAtualizar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("ID do usuário a atualizar:");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                usuarioController.atualizarUsuario(
                        id,
                        txtNome.getText(),
                        txtMatricula.getText(),
                        txtEmail.getText(),
                        txtSenha.getText(),
                        comboTipo.getSelectedItem().toString()
                );
            }
        });

        // 🔹 Deletar usuário
        btnDeletar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("ID do usuário a deletar:");
            if (idStr != null) {
                usuarioController.deletarUsuario(Integer.parseInt(idStr));
            }
        });

        // 🔹 Listar todos
        btnListar.addActionListener(e -> {
            List<Usuario> usuarios = usuarioController.listarUsuarios();
            StringBuilder sb = new StringBuilder("📋 Usuários:\n\n");
            for (Usuario u : usuarios) {
                sb.append("ID: ").append(u.getId())
                        .append(" | Nome: ").append(u.getNome())
                        .append(" | Matrícula: ").append(u.getMatricula())
                        .append(" | Email: ").append(u.getEmail())
                        .append(" | Tipo: ").append(u.getTipo())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });
    }
}
