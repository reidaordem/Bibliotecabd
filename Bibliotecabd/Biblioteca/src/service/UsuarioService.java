package service;

import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // CREATE
    public void cadastrarUsuario(Usuario usuario) {
        try {
            if (usuario.getNome().isEmpty() || usuario.getMatricula().isEmpty() || usuario.getEmail().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                return;
            }

            usuarioDAO.inserir(usuario);
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    // READ
    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarTodos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void atualizarUsuario(Usuario usuario) {
        try {
            usuarioDAO.atualizar(usuario);
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // DELETE
    public void deletarUsuario(int id) {
        try {
            usuarioDAO.deletar(id);
            JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir usuário: " + e.getMessage());
        }
    }

    public Usuario autenticar(String email, String senha) {
    try {
        return usuarioDAO.autenticar(email, senha);
    } catch (SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Erro ao autenticar: " + e.getMessage());
        return null;
    }
}

}
