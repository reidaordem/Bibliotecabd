package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Multa;

public class MultaDAO {

    // 🔹 Inserir nova multa
    public void inserir(Multa multa) throws SQLException {
        String sql = "INSERT INTO Multa (id_emprestimo, valor, pago, tipo_pagamento) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, multa.getIdEmprestimo());
            stmt.setDouble(2, multa.getValor());
            stmt.setBoolean(3, multa.isPago());
            stmt.setString(4, multa.getTipoPagamento());

            stmt.executeUpdate();
        }
    }

    // 🔹 Atualizar multa existente
    public void atualizar(Multa multa) throws SQLException {
        String sql = "UPDATE Multa SET id_emprestimo=?, valor=?, pago=?, tipo_pagamento=? WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, multa.getIdEmprestimo());
            stmt.setDouble(2, multa.getValor());
            stmt.setBoolean(3, multa.isPago());
            stmt.setString(4, multa.getTipoPagamento());
            stmt.setInt(5, multa.getId());

            stmt.executeUpdate();
        }
    }

    // 🔹 Deletar multa
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Multa WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // 🔹 Listar todas as multas
    public List<Multa> listarTodas() throws SQLException {
        List<Multa> multas = new ArrayList<>();
        String sql = "SELECT * FROM Multa";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Multa m = new Multa(
                        rs.getInt("ID"),
                        rs.getInt("id_emprestimo"),
                        rs.getDouble("valor"),
                        rs.getBoolean("pago"),
                        rs.getString("tipo_pagamento")
                );
                multas.add(m);
            }
        }
        return multas;
    }

    // 🔹 Buscar multa por ID
    public Multa buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Multa WHERE ID=?";
        Multa m = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                m = new Multa(
                        rs.getInt("ID"),
                        rs.getInt("id_emprestimo"),
                        rs.getDouble("valor"),
                        rs.getBoolean("pago"),
                        rs.getString("tipo_pagamento")
                );
            }
        }
        return m;
    }

    // 🔹 Buscar multa pelo ID do empréstimo (1:1)
    public Multa buscarPorEmprestimo(int idEmprestimo) throws SQLException {
        String sql = "SELECT * FROM Multa WHERE id_emprestimo=?";
        Multa m = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                m = new Multa(
                        rs.getInt("ID"),
                        rs.getInt("id_emprestimo"),
                        rs.getDouble("valor"),
                        rs.getBoolean("pago"),
                        rs.getString("tipo_pagamento")
                );
            }
        }
        return m;
    }

    public void pagarMulta(int idMulta, String tipoPagamento) throws SQLException {
    String sql = "UPDATE Multa SET pago = TRUE, tipoPagamento = ? WHERE ID = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, tipoPagamento);
        stmt.setInt(2, idMulta);
        stmt.executeUpdate();
    }
}

}
