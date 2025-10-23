package service;

import dao.ConnectionFactory;
import dao.EmprestimoDAO;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.JOptionPane;
import model.Emprestimo;

public class EmprestimoService {

    private EmprestimoDAO emprestimoDAO;

    public EmprestimoService() {
        this.emprestimoDAO = new EmprestimoDAO();
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
   public void marcarComoDevolvido(int id) {
    try {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(id); // 🔹 Buscar do banco primeiro

        if (emprestimo == null) {
            JOptionPane.showMessageDialog(null, "Empréstimo não encontrado!");
            return;
        }

        if (emprestimo.isDevolvido()) {
            JOptionPane.showMessageDialog(null, "Esse empréstimo já foi devolvido!");
            return;
        }

        emprestimo.setDevolvido(true);
        emprestimoDAO.atualizar(emprestimo);

        JOptionPane.showMessageDialog(null, "📚 Livro marcado como devolvido!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao marcar devolução: " + e.getMessage());
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

}
