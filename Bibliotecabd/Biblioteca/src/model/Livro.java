package model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String categoria;
    private boolean disponivel; // 👈 novo campo

    public Livro() {}

    public Livro(int id, String titulo, String autor, int anoPublicacao, String categoria, boolean disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.categoria = categoria;
        this.disponivel = disponivel;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    @Override
    public String toString() {
        return "Livro { " +
                "ID = " + id +
                ", Título = '" + titulo + '\'' +
                ", Autor = '" + autor + '\'' +
                ", Ano = " + anoPublicacao +
                ", Categoria = '" + categoria + '\'' +
                ", Disponível = " + (disponivel ? "Sim" : "Não") +
                " }";
    }
}
