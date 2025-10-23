package view;

import controller.UsuarioController;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroUsuario extends JFrame {
    private JTextField txtNome, txtMatricula, txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar, btnVoltar;
    private UsuarioController usuarioController = new UsuarioController();

    public TelaCadastroUsuario() {
        setTitle("Cadastro de Novo Usuário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome:");
        JLabel lblMatricula = new JLabel("Matrícula:");
        JLabel lblEmail = new JLabel("Email:");
        JLabel lblSenha = new JLabel("Senha:");

        txtNome = new JTextField();
        txtMatricula = new JTextField();
        txtEmail = new JTextField();
        txtSenha = new JPasswordField();

        btnCadastrar = new JButton("Cadastrar");
        btnVoltar = new JButton("Voltar");

        add(lblNome); add(txtNome);
        add(lblMatricula); add(txtMatricula);
        add(lblEmail); add(txtEmail);
        add(lblSenha); add(txtSenha);
        add(btnCadastrar); add(btnVoltar);

        // 🔹 Botão cadastrar
        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String matricula = txtMatricula.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            if (nome.isEmpty() || matricula.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            try {
                usuarioController.cadastrarUsuario(nome, matricula, email, senha, "CLIENTE");
                JOptionPane.showMessageDialog(this, "✅ Cadastro realizado com sucesso!");
                dispose(); // fecha a tela de cadastro
                new TelaLogin().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
            }
        });

        // 🔹 Botão voltar
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }
}
