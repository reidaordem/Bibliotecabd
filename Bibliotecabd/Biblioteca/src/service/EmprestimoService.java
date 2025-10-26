package service;

import dao.ConnectionFactory;
import dao.EmprestimoDAO;
import dao.LivroDAO;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.JOptionPane;
import model.Emprestimo;
import model.Multa;

public class EmprestimoService {

    private EmprestimoDAO emprestimoDAO;
    
    private LivroService livroservice;
    private MultaService multaService;

    public EmprestimoService() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.livroservice = new LivroService();
        this.multaService = new MultaService();
    }

    // 🔸 CADASTRAR EMPRÉSTIMO
    public void cadastrarEmprestimo(Emprestimo emprestimo) {
        try {
            // Verifica se o livro já está emprestado
            if (livroJaEmprestado(emprestimo.getIdLivro())) {
                JOptionPane.showMessageDialog(null, "❌ Este livro já está emprestado e ainda não foi devolvido!");
                return;
            }

            emprestimo.setDevolvido(false); // por segurança, novo empréstimo sempre começa como não devolvido
            emprestimoDAO.inserir(emprestimo);
            JOptionPane.showMessageDialog(null, "✅ Empréstimo registrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar empréstimo: " + e.getMessage());
        }
    }

    // 🔸 LISTAR TODOS
    public List<Emprestimo> listarEmprestimos() {
        try {
            List<Emprestimo> lista = emprestimoDAO.listarTodos();

            // Atualiza multa dos empréstimos não devolvidos
            for (Emprestimo e : lista) {
                if (!e.isDevolvido()) {
                    e.setMulta(calcularMulta(e));
                } else {
                    e.setMulta(0.0);
                }
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar empréstimos: " + e.getMessage());
            return null;
        }
    }

    // 🔸 ATUALIZAR EMPRÉSTIMO
    public void atualizarEmprestimo(Emprestimo emprestimo) {
        try {
            // Atualiza a multa antes de salvar
            double novaMulta = calcularMulta(emprestimo);
            emprestimo.setMulta(novaMulta);

            emprestimoDAO.atualizar(emprestimo);
            JOptionPane.showMessageDialog(null, "✅ Empréstimo atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }

    // 🔸 MARCAR COMO DEVOLVIDO
  public void marcarComoDevolvido(int idEmprestimo) {
    try {
        Emprestimo e = emprestimoDAO.buscarPorId(idEmprestimo);
        if (e == null) {
            JOptionPane.showMessageDialog(null, "Empréstimo não encontrado!");
            return;
        }

        if (e.isDevolvido()) {
            JOptionPane.showMessageDialog(null, "Esse empréstimo já foi devolvido!");
            return;
        }

        e.setDevolvido(true);
        emprestimoDAO.atualizar(e);
        livroservice.atualizarDisponibilidade(e.getIdLivro(), true); // marca como disponível
        JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao marcar devolução: " + ex.getMessage());
    }
}


    // 🔸 DELETAR
    public void deletarEmprestimo(int id) {
        try {
            emprestimoDAO.deletar(id);
            JOptionPane.showMessageDialog(null, "Empréstimo excluído com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir empréstimo: " + e.getMessage());
        }
    }

    // 🔹 Verifica se o livro já está emprestado
    private boolean livroJaEmprestado(int idLivro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Emprestimo WHERE id_livro = ? AND devolvido = false";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLivro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // true = já emprestado
            }
            return false;
        }
    }

    // 🔹 Calcula multa (somente para empréstimos não devolvidos e atrasados)
    private double calcularMulta(Emprestimo e) {
        if (e.isDevolvido()) return 0.0; // devolvido = sem multa

        LocalDate hoje = LocalDate.now();
        if (hoje.isAfter(e.getDevolucao())) {
            long diasAtraso = ChronoUnit.DAYS.between(e.getDevolucao(), hoje);
            double valorPorDia = 2.0; // multa diária
            return diasAtraso * valorPorDia;
        }
        return 0.0;
    }

    public Emprestimo buscarPorId(int id) {
    try {
        return emprestimoDAO.buscarPorId(id);
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Erro ao buscar empréstimo: " + e.getMessage());
        return null;
    }
}


public void cadastrarEmprestimoCliente(int idUsuario, int idLivro, LocalDate retirada, LocalDate devolucao) {
    try {
        // ⚠️ Regra: máximo 3 dias para clientes
        long dias = java.time.temporal.ChronoUnit.DAYS.between(retirada, devolucao);
        if (dias > 3) {
            JOptionPane.showMessageDialog(null,
                    "Clientes só podem emprestar livros por no máximo 3 dias.\nPrazo informado: " + dias + " dias.");
            return;
        }

        // ⚠️ Verifica se o livro está disponível
        if (livroservice.livroJaEmprestado(idLivro)) {
            JOptionPane.showMessageDialog(null, "Este livro já está emprestado!");
            return;
        }

        // 🔹 Cria o empréstimo
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdUsuario(idUsuario);
        emprestimo.setIdLivro(idLivro);
        emprestimo.setRetirada(retirada);
        emprestimo.setDevolucao(devolucao);
        emprestimo.setDevolvido(false);
        emprestimo.setMulta(0.0);

        emprestimoDAO.inserir(emprestimo);

        // 🔹 Atualiza disponibilidade do livro
        livroservice.atualizarDisponibilidade(idLivro, false);

        JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso por 3 dias!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar empréstimo: " + e.getMessage());
    }
}

public boolean devolverLivro(int idEmprestimo) {
        try {
            Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);
            if (emp == null) {
                System.out.println("Empréstimo não encontrado!");
                return false;
            }

            // Já devolvido?
            if (emp.isDevolvido()) {
                System.out.println("Este livro já foi devolvido!");
                return false;
            }

            LocalDate hoje = LocalDate.now();
            boolean atrasado = hoje.isAfter(emp.getDevolucao());

            if (atrasado) {
                long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(emp.getDevolucao(), hoje);
                double valorMulta = diasAtraso * 2.0; // 💰 R$2,00 por dia

                Multa multa = new Multa();
                multa.setIdEmprestimo(emp.getId());
                multa.setValor(valorMulta);
                multa.setPago(false);
                multaService.cadastrarMulta(multa);

                System.out.println("Livro está atrasado! Multa de R$" + valorMulta + " gerada.");
                return false; // 🔒 bloqueia devolução até pagar
            }

            // ✅ Pode devolver normalmente
            emp.setDevolvido(true);
            emprestimoDAO.atualizar(emp);
            livroservice.atualizarDisponibilidade(emp.getIdLivro(), true);
            
            System.out.println("Livro devolvido com sucesso!");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean devolverComPagamento(int idEmprestimo, String tipoPagamento) {
    try {
        Emprestimo emp = emprestimoDAO.buscarPorId(idEmprestimo);
        if (emp == null) return false;

        // Pega a multa associada a esse empréstimo
        Multa multa = null;
        for (Multa m : multaService.listarMultas()) {
            if (m.getIdEmprestimo() == emp.getId()) {
                multa = m;
                break;
            }
        }

        if (multa != null && !multa.isPago()) {
            multaService.pagarMulta(multa.getId(), tipoPagamento);
        }

        // Marca como devolvido
        emp.setDevolvido(true);
        emprestimoDAO.atualizar(emp);
        livroservice.atualizarDisponibilidade(emp.getIdLivro(), true);

        System.out.println("Livro devolvido e multa paga via " + tipoPagamento + "!");
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
