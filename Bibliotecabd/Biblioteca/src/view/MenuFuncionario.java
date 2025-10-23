package view;

import javax.swing.*;
import model.Usuario;

public class MenuFuncionario extends JFrame {
    private Usuario usuario;

    public MenuFuncionario(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Painel do Funcionário - " + usuario.getNome());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + usuario.getNome());
        lblBemVindo.setAlignmentX(CENTER_ALIGNMENT);
        lblBemVindo.setFont(lblBemVindo.getFont().deriveFont(18f));

        JButton btnUsuarios = new JButton("👥 Gerenciar Usuários");
        JButton btnLivros = new JButton("📚 Gerenciar Livros");
        JButton btnEmprestimos = new JButton("📦 Gerenciar Empréstimos");
        JButton btnMultas = new JButton("💰 Gerenciar Multas");
        JButton btnSair = new JButton("🚪 Sair");

        btnUsuarios.addActionListener(e -> new TelaUsuario().setVisible(true));
        btnLivros.addActionListener(e -> new TelaLivro().setVisible(true));
        btnEmprestimos.addActionListener(e -> new TelaEmprestimo().setVisible(true));
        btnMultas.addActionListener(e -> new TelaMulta().setVisible(true));
        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });

        panel.add(lblBemVindo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnUsuarios);
        panel.add(btnLivros);
        panel.add(btnEmprestimos);
        panel.add(btnMultas);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnSair);

        add(panel);
    }
}
