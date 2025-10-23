package dao;
import java.sql.*;     // Importa todas as classes do pacote java.sql
import java.util.*;    // Importa listas, ArrayList, etc.
import model.Livro;  // Importa nossa classe de modelo


public class LivroDAO {
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livro (titulo,autor,anoPublicacao,categoria) VALUES (?,?,?,?)";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                 stmt.setString(1, livro.getTitulo());
                 stmt.setString(2, livro.getAutor());
                 stmt.setInt(3, livro.getAnoPublicacao());
                 stmt.setString(4, livro.getCategoria());
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
                    rs.getString("Categoria")
                );
                livros.add(l);
            }
        }
        return livros;
    }


    public void atualizar(Livro livro) throws SQLException{
        String sql = "UPDATE Livro SET titulo=?, autor=?, anoPublicacao=?, categoria=? WHERE ID=?";
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

                 stmt.setString(1, livro.getTitulo());
                 stmt.setString(2, livro.getAutor());
                 stmt.setInt(3, livro.getAnoPublicacao());
                 stmt.setString(4, livro.getCategoria());
                 stmt.setInt(5, livro.getId());
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
}
