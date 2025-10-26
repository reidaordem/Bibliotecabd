package view;

import controller.EmprestimoController;
import controller.MultaController;
import controller.LivroController;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Emprestimo;
import model.Livro;
import model.Multa;
import model.Usuario;

public class MenuCliente extends JFrame {
    private Usuario usuario;
    private LivroController livrocontroller = new LivroController();
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
        JButton btnFazerEmprestimo = new JButton("Fazer Empréstimo");
        JButton btnPagarMulta = new JButton("Pagar Multa");
        JButton btnVerLivros = new JButton("📚 Ver Livros");
        JButton btnDevolver = new JButton("📦 Devolver Livro");





        JButton btnSair = new JButton("Sair");

        JPanel panel = new JPanel();
        
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.add(btnVerEmprestimos);
        panel.add(btnVerMultas);
        panel.add(btnFazerEmprestimo);
        panel.add(btnPagarMulta);
        panel.add(btnVerLivros);
        panel.add(btnDevolver);
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

        
        btnFazerEmprestimo.addActionListener(e -> {
        try {
            int idLivro = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do livro que deseja emprestar:"));
        LocalDate retirada = LocalDate.now();
        LocalDate devolucao = LocalDate.parse(JOptionPane.showInputDialog("Digite a data de devolução (AAAA-MM-DD):"));

        emprestimoController.cadastrarEmprestimoCliente(usuario.getId(), idLivro, retirada, devolucao);
         } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Erro ao fazer empréstimo: " + ex.getMessage());
            }
        });

        btnPagarMulta.addActionListener(e -> {
        try {
        int idMulta = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID da multa que deseja pagar:"));
        String tipoPagamento = JOptionPane.showInputDialog("Informe o tipo de pagamento (Ex: PIX, Cartão, Dinheiro):");

        multaController.pagarMulta(idMulta, tipoPagamento);
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Erro ao pagar multa: " + ex.getMessage());
        }
            });


           /*  btnVerMultas.addActionListener(e -> {
    List<Multa> multas = multaController.listarMultas();
    StringBuilder sb = new StringBuilder("Suas Multas:\n\n");

    for (Multa m : multas) {
        Emprestimo emp = emprestimoController.buscarPorId(m.getIdEmprestimo());
        if (emp != null && emp.getIdUsuario() == usuario.getId()) {
            sb.append("ID: ").append(m.getId())
              .append(" | Valor: R$").append(m.getValor())
              .append(" | Pago: ").append(m.isPago() ? "Sim" : "Não")
              .append(" | Tipo: ").append(m.getTipoPagamento() == null ? "-" : m.getTipoPagamento())
              .append("\n");
        }
    }

    String input = JOptionPane.showInputDialog(null, sb.toString() + "\nDigite o ID da multa que deseja pagar:");
    if (input != null && !input.isEmpty()) {
        try {
            int idMulta = Integer.parseInt(input);
            String tipoPagamento = JOptionPane.showInputDialog("Informe o tipo de pagamento (PIX, Dinheiro, Cartão):");

            if (tipoPagamento != null && !tipoPagamento.isEmpty()) {
                multaController.pagarMulta(idMulta, tipoPagamento);
                JOptionPane.showMessageDialog(null, "💰 Multa paga com sucesso via " + tipoPagamento + "!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }
});
*/

btnVerLivros.addActionListener(e -> {
    JFrame frame = new JFrame("Catálogo de Livros");
    frame.setSize(800, 400);
    frame.setLocationRelativeTo(null);
    frame.setLayout(new BorderLayout(10, 10));

    JPanel painelPesquisa = new JPanel(new FlowLayout());
    JComboBox<String> comboCampo = new JComboBox<>(new String[]{"titulo", "autor", "categoria"});
    JTextField txtPesquisa = new JTextField(15);
    JButton btnPesquisar = new JButton("Pesquisar");

    painelPesquisa.add(new JLabel("Pesquisar por:"));
    painelPesquisa.add(comboCampo);
    painelPesquisa.add(txtPesquisa);
    painelPesquisa.add(btnPesquisar);

    DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Título", "Autor", "Ano", "Categoria", "Disponível"}, 0
    );
    JTable tabela = new JTable(modelo);
    tabela.setEnabled(false); // 🔒 cliente não pode editar

    JScrollPane scroll = new JScrollPane(tabela);

    frame.add(painelPesquisa, BorderLayout.NORTH);
    frame.add(scroll, BorderLayout.CENTER);

    // 🔹 Função para listar todos os livros
    Runnable listarLivros = () -> {
        modelo.setRowCount(0);
        List<Livro> livros = livrocontroller.listarLivros();
        if (livros != null) {
            for (Livro l : livros) {
                modelo.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getAnoPublicacao(),
                    l.getCategoria(),
                    l.isDisponivel() ? "Sim" : "Não"
                });
            }
        }
    };

    listarLivros.run(); // listar ao abrir

    // 🔹 Pesquisa
    btnPesquisar.addActionListener(ev -> {
        String campo = comboCampo.getSelectedItem().toString();
        String valor = txtPesquisa.getText().trim();

        modelo.setRowCount(0);
        try {
            List<Livro> resultado = livrocontroller.pesquisar(campo, valor);
            for (Livro l : resultado) {
                modelo.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getAnoPublicacao(),
                    l.getCategoria(),
                    l.isDisponivel() ? "Sim" : "Não"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro na pesquisa: " + ex.getMessage());
        }
    });

    frame.setVisible(true);
});




btnDevolver.addActionListener(e -> {
    String idStr = JOptionPane.showInputDialog("Digite o ID do empréstimo que deseja devolver:");
    if (idStr == null || idStr.isEmpty()) return;

    try {
        int idEmp = Integer.parseInt(idStr);
        boolean devolvido = emprestimoController.devolverLivro(idEmp);

        if (!devolvido) {
            int opc = JOptionPane.showConfirmDialog(null, 
                "Livro está atrasado e gerou uma multa!\nDeseja pagar agora para devolver?",
                "Devolução atrasada",
                JOptionPane.YES_NO_OPTION);

            if (opc == JOptionPane.YES_OPTION) {
                String tipoPagamento = JOptionPane.showInputDialog("Informe o tipo de pagamento (PIX, CARTAO, DINHEIRO):");
                emprestimoController.devolverComPagamento(idEmp, tipoPagamento.toUpperCase());
                JOptionPane.showMessageDialog(null, "Multa paga e livro devolvido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Devolução cancelada até o pagamento da multa.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "ID inválido!");
    }
});

    }
    
}
