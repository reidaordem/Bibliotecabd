package dao;

import java.sql.*;
import java.util.*;
import model.Emprestimo;

public class EmprestimoDAO {

    // 🔹 Inserir
    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO Emprestimo (id_usuario, id_livro, retirada, devolucao, multa, devolvido, id_multa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getIdUsuario());
            stmt.setInt(2, emprestimo.getIdLivro());
            stmt.setDate(3, java.sql.Date.valueOf(emprestimo.getRetirada()));
            stmt.setDate(4, java.sql.Date.valueOf(emprestimo.getDevolucao()));
            stmt.setDouble(5, emprestimo.getMulta());
            stmt.setBoolean(6, emprestimo.isDevolvido());
            
            if (emprestimo.getIdMulta() != null) {
                stmt.setInt(7, emprestimo.getIdMulta());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }

    // 🔹 Atualizar
    public void atualizar(Emprestimo emprestimo) throws SQLException {
        String sql = "UPDATE Emprestimo SET id_usuario=?, id_livro=?, retirada=?, devolucao=?, multa=?, devolvido=?, id_multa=? WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, emprestimo.getIdUsuario());
            stmt.setInt(2, emprestimo.getIdLivro());
            stmt.setDate(3, java.sql.Date.valueOf(emprestimo.getRetirada()));
            stmt.setDate(4, java.sql.Date.valueOf(emprestimo.getDevolucao()));
            stmt.setDouble(5, emprestimo.getMulta());
            stmt.setBoolean(6, emprestimo.isDevolvido());
            if (emprestimo.getIdMulta() != null) {
                stmt.setInt(7, emprestimo.getIdMulta());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }
            stmt.setInt(8, emprestimo.getId());

            stmt.executeUpdate();
        }
    }

    // 🔹 Listar todos
    public List<Emprestimo> listarTodos() throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimo";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprestimo e = new Emprestimo();
                e.setId(rs.getInt("ID"));
                e.setIdUsuario(rs.getInt("id_usuario"));
                e.setIdLivro(rs.getInt("id_livro"));
                e.setRetirada(rs.getDate("retirada").toLocalDate());
                e.setDevolucao(rs.getDate("devolucao").toLocalDate());
                e.setMulta(rs.getDouble("multa"));
                e.setDevolvido(rs.getBoolean("devolvido"));

                int idMulta = rs.getInt("id_multa");
                e.setIdMulta(rs.wasNull() ? null : idMulta);

                lista.add(e);
            }
        }
        return lista;
    }

    // 🔹 Buscar por ID
    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Emprestimo WHERE ID=?";
        Emprestimo e = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                e = new Emprestimo();
                e.setId(rs.getInt("ID"));
                e.setIdUsuario(rs.getInt("id_usuario"));
                e.setIdLivro(rs.getInt("id_livro"));
                e.setRetirada(rs.getDate("retirada").toLocalDate());
                e.setDevolucao(rs.getDate("devolucao").toLocalDate());
                e.setMulta(rs.getDouble("multa"));
                e.setDevolvido(rs.getBoolean("devolvido"));

                int idMulta = rs.getInt("id_multa");
                e.setIdMulta(rs.wasNull() ? null : idMulta);
            }
        }
        return e;
    }

    // 🔹 Deletar
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Emprestimo WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    
}
