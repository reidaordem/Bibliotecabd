package view;

import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnCadastrar;

    public TelaLogin() {
        setTitle("Login - Biblioteca");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblEmail = new JLabel("Email:");
        JLabel lblSenha = new JLabel("Senha:");

        txtEmail = new JTextField();
        txtSenha = new JPasswordField();

        btnEntrar = new JButton("Entrar");
        btnCadastrar = new JButton("Cadastrar-se");

        add(lblEmail);
        add(txtEmail);
        add(lblSenha);
        add(txtSenha);
        add(new JLabel());
        add(btnEntrar);
        add(new JLabel());
        add(btnCadastrar);

        UsuarioController usuarioController = new UsuarioController();

        // 🔹 Botão entrar
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());

                Usuario usuario = usuarioController.autenticar(email, senha);

                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo, " + usuario.getNome() + "!");
                    dispose(); // fecha a tela de login

                    if (usuario.getTipo().equalsIgnoreCase("FUNCIONARIO")) {
                        new MenuFuncionario(usuario).setVisible(true);
                        } else {
                        new MenuCliente(usuario).setVisible(true);
                        }   

                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha incorretos!");
                }
            }
        });

        // 🔹 Botão cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TelaCadastroUsuario().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}
