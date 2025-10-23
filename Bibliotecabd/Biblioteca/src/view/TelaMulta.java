package view;

import controller.MultaController;
import model.Multa;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaMulta extends JFrame {
    private JTextField txtIdEmprestimo, txtValor;
    private JComboBox<String> comboTipo;
    private JCheckBox chkPago;
    private MultaController controller = new MultaController();

    public TelaMulta() {
        setTitle("Gerenciar Multas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        JLabel lblEmprestimo = new JLabel("ID do Empréstimo:");
        JLabel lblValor = new JLabel("Valor (R$):");
        JLabel lblTipo = new JLabel("Tipo de Pagamento:");
        JLabel lblPago = new JLabel("Pago:");

        txtIdEmprestimo = new JTextField();
        txtValor = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"DINHEIRO", "CARTAO", "PIX"});
        chkPago = new JCheckBox("Sim");

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnListar = new JButton("Listar Todas");

        add(lblEmprestimo);
        add(txtIdEmprestimo);
        add(lblValor);
        add(txtValor);
        add(lblTipo);
        add(comboTipo);
        add(lblPago);
        add(chkPago);
        add(btnCadastrar);
        add(btnAtualizar);
        add(btnDeletar);
        add(btnListar);

        // 🔹 Ações
        btnCadastrar.addActionListener(e -> {
            controller.cadastrarMulta(
                    Integer.parseInt(txtIdEmprestimo.getText()),
                    Double.parseDouble(txtValor.getText()),
                    chkPago.isSelected(),
                    comboTipo.getSelectedItem().toString()
            );
        });

        btnAtualizar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("ID da Multa a atualizar:");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                controller.atualizarMulta(
                        id,
                        Integer.parseInt(txtIdEmprestimo.getText()),
                        Double.parseDouble(txtValor.getText()),
                        chkPago.isSelected(),
                        comboTipo.getSelectedItem().toString()
                );
            }
        });

        btnDeletar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("ID da Multa a deletar:");
            if (idStr != null) controller.deletarMulta(Integer.parseInt(idStr));
        });

        btnListar.addActionListener(e -> {
            List<Multa> multas = controller.listarMultas();
            StringBuilder sb = new StringBuilder("📋 Multas:\n\n");
            for (Multa m : multas) {
                sb.append("ID: ").append(m.getId())
                        .append(" | Empréstimo: ").append(m.getIdEmprestimo())
                        .append(" | Valor: R$").append(m.getValor())
                        .append(" | Pago: ").append(m.isPago() ? "Sim" : "Não")
                        .append(" | Tipo: ").append(m.getTipoPagamento())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });
    }
}
