package dao;

import java.sql.*;
import java.util.*;
import model.Usuario;

public class UsuarioDAO {

    // 🔹 Inserir novo usuário
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nome, matricula, email, senha, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getMatricula());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipo());

            stmt.executeUpdate();
        }
    }

    // 🔹 Atualizar usuário
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET nome=?, matricula=?, email=?, senha=?, tipo=? WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getMatricula());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipo());
            stmt.setInt(6, usuario.getId());

            stmt.executeUpdate();
        }
    }

    // 🔹 Listar todos os usuários
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("ID"),
                    rs.getString("nome"),
                    rs.getString("matricula"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("tipo")
                );
                usuarios.add(u);
            }
        }
        return usuarios;
    }

    // 🔹 Buscar por email e senha (para login)
    public Usuario autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email=? AND senha=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("ID"),
                    rs.getString("nome"),
                    rs.getString("matricula"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("tipo")
                );
            }
        }
        return null; // usuário não encontrado
    }

    // 🔹 Deletar usuário
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
