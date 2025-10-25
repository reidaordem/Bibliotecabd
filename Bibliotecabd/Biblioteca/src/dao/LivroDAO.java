package dao;
import java.sql.*;     // Importa todas as classes do pacote java.sql
import java.util.*;    // Importa listas, ArrayList, etc.
import model.Livro;  // Importa nossa classe de modelo


public class LivroDAO {
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livro (titulo, autor, anoPublicacao, categoria, disponivel) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, livro.getTitulo());
                stmt.setString(2, livro.getAutor());
                stmt.setInt(3, livro.getAnoPublicacao());
                stmt.setString(4, livro.getCategoria());
                stmt.setBoolean(5, livro.isDisponivel());
                stmt.executeUpdate();

            }
    }


    public List<Livro> listarTodos() throws SQLException{
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro l = new Livro(
                    rs.getInt("ID"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anoPublicacao"),
                    rs.getString("Categoria"),
                    rs.getBoolean("disponivel")
                );
                livros.add(l);
            }
        }
        return livros;
    }

    

    public void atualizar(Livro livro) throws SQLException{
        String sql = "UPDATE Livro SET titulo=?, autor=?, anoPublicacao=?, categoria=?, disponivel=? WHERE ID=?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                 stmt.setString(1, livro.getTitulo());
                 stmt.setString(2, livro.getAutor());
                 stmt.setInt(3, livro.getAnoPublicacao());
                 stmt.setString(4, livro.getCategoria());
                 stmt.setBoolean(5, livro.isDisponivel());
                 stmt.setInt(6, livro.getId());
                 stmt.executeUpdate();
                
            }
    }


    public void deletar(int id) throws SQLException{
        String sql = "DELETE FROM Livro WHERE ID=?";
        try(Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, id);
                stmt.executeUpdate();
             }
    }


    public List<Livro> pesquisar(String campo, String valor) throws SQLException {
    List<Livro> livros = new ArrayList<>();
    String sql = "SELECT * FROM Livro WHERE " + campo + " LIKE ?";

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + valor + "%");
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Livro l = new Livro();
                l.setId(rs.getInt("ID"));
                l.setTitulo(rs.getString("titulo"));
                l.setAutor(rs.getString("autor"));
                l.setAnoPublicacao(rs.getInt("anoPublicacao"));
                l.setCategoria(rs.getString("categoria"));
                l.setDisponivel(rs.getBoolean("disponivel"));
                livros.add(l);
            }
        }
    }
    return livros;
}

public void atualizarDisponibilidade(int idLivro, boolean disponivel) throws SQLException {
    String sql = "UPDATE Livro SET disponivel=? WHERE ID=?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setBoolean(1, disponivel);
        stmt.setInt(2, idLivro);
        stmt.executeUpdate();
    }
}

public boolean livroJaEmprestado(int idLivro) throws SQLException {
    String sql = "SELECT disponivel FROM Livro WHERE ID = ?";
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idLivro);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Se o campo 'disponivel' for false, significa que está emprestado
                return !rs.getBoolean("disponivel");
            }
        }
    }
    // Se não encontrou o livro, ou algo deu errado, consideramos que não está emprestado
    return false;
}


}
