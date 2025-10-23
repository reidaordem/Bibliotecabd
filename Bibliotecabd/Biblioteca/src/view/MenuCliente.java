package view;

import controller.EmprestimoController;
import controller.MultaController;
import model.Emprestimo;
import model.Multa;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuCliente extends JFrame {
    private Usuario usuario;
    private EmprestimoController emprestimoController = new EmprestimoController();
    private MultaController multaController = new MultaController();

    public MenuCliente(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Área do Cliente - " + usuario.getNome());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnVerEmprestimos = new JButton("📚 Ver Meus Empréstimos");
        JButton btnVerMultas = new JButton("💰 Ver Minhas Multas");
        JButton btnSair = new JButton("Sair");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.add(btnVerEmprestimos);
        panel.add(btnVerMultas);
        panel.add(btnSair);
        add(panel);

        // 🔹 Ver empréstimos
        btnVerEmprestimos.addActionListener(e -> {
            List<Emprestimo> emprestimos = emprestimoController.listarEmprestimos();
            StringBuilder sb = new StringBuilder("Seus Empréstimos:\n\n");
            for (Emprestimo emp : emprestimos) {
                if (emp.getIdUsuario() == usuario.getId()) {
                    sb.append("Livro ID: ").append(emp.getIdLivro())
                            .append(" | Retirada: ").append(emp.getRetirada())
                            .append(" | Devolução: ").append(emp.getDevolucao())
                            .append(" | Devolvido: ").append(emp.isDevolvido() ? "Sim" : "Não")
                            .append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // 🔹 Ver multas
        btnVerMultas.addActionListener(e -> {
            List<Multa> multas = multaController.listarMultas();
            StringBuilder sb = new StringBuilder("Suas Multas:\n\n");
            for (Multa m : multas) {
                Emprestimo emp = emprestimoController.buscarPorId(m.getIdEmprestimo());
                if (emp != null && emp.getIdUsuario() == usuario.getId()) {
                    sb.append("Valor: R$").append(m.getValor())
                            .append(" | Pago: ").append(m.isPago() ? "Sim" : "Não")
                            .append(" | Tipo: ").append(m.getTipoPagamento())
                            .append("\n");
                }
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnSair.addActionListener(e -> {
            dispose();
            new TelaLogin().setVisible(true);
        });
    }
}
