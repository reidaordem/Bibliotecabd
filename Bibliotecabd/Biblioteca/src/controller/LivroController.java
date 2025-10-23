package controller;

import java.util.List;
import javax.swing.JOptionPane;
import model.Livro;
import service.LivroService;

public class LivroController {

    private LivroService livroService;

    public LivroController() {
        this.livroService = new LivroService();
    }

    // 🔸 Cadastrar novo livro
    public void cadastrarLivro(String titulo, String autor, int anoPublicacao, String categoria) {
        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Todos os campos são obrigatórios!");
            return;
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setCategoria(categoria);

        livroService.cadastrarLivro(livro);
    }

    // 🔸 Listar todos os livros
    public List<Livro> listarLivros() {
        return livroService.listarLivros();
    }

    // 🔸 Atualizar livro existente
    public void atualizarLivro(int id, String titulo, String autor, int anoPublicacao, String categoria) {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setCategoria(categoria);

        livroService.atualizarLivro(livro);
    }

    // 🔸 Deletar livro
    public void deletarLivro(int id) {
        int opcao = JOptionPane.showConfirmDialog(null, 
            "Tem certeza que deseja excluir o livro com ID " + id + "?",
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            livroService.deletarLivro(id);
        }
    }
}
