package controller;

import model.Usuario;
import service.UsuarioService;

import java.util.List;

public class UsuarioController {
    private UsuarioService service;

    public UsuarioController() {
        this.service = new UsuarioService();
    }

    public void cadastrarUsuario(String nome, String matricula, String email, String senha, String tipo) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setMatricula(matricula);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipo(tipo);

        service.cadastrarUsuario(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }

    public void atualizarUsuario(int id, String nome, String matricula, String email, String senha, String tipo) {
        Usuario usuario = new Usuario(id, nome, matricula, email, senha, tipo);
        service.atualizarUsuario(usuario);
    }

    public void deletarUsuario(int id) {
        service.deletarUsuario(id);
    }

    // 🔹 Novo método: autenticação de login
    public Usuario autenticar(String email, String senha) {
        return service.autenticar(email, senha);
    }
}
