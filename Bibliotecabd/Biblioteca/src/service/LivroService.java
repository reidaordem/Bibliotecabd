package service;

import dao.LivroDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Livro;

public class LivroService {

    private LivroDAO livroDAO;

    public LivroService() {
        this.livroDAO = new LivroDAO();
    }

    // CREATE
    public void cadastrarLivro(Livro livro) {
        try {
            if (livro.getTitulo().isEmpty() || livro.getAutor().isEmpty() || livro.getCategoria().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                return;
            }

            livroDAO.inserir(livro);
            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    // READ
    public List<Livro> listarLivros() {
        try {
            return livroDAO.listarTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar livros: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void atualizarLivro(Livro livro) {
        try {
            livroDAO.atualizar(livro);
            JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar livro: " + e.getMessage());
        }
    }

    // DELETE
    public void deletarLivro(int id) {
        try {
            livroDAO.deletar(id);
            JOptionPane.showMessageDialog(null, "Livro excluído com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir livro: " + e.getMessage());
        }
    }

    public List<Livro> pesquisar(String campo, String valor) throws SQLException {
    return livroDAO.pesquisar(campo, valor);
}

public boolean livroJaEmprestado(int idLivro) {
    try {
        return livroDAO.livroJaEmprestado(idLivro);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao verificar disponibilidade do livro: " + e.getMessage());
        return false;
    }
}

public void atualizarDisponibilidade(int idLivro, boolean disponivel) {
        try {
            livroDAO.atualizarDisponibilidade(idLivro, disponivel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
