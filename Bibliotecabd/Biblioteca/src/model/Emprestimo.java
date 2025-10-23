package model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private int idUsuario;
    private int idLivro;
    private LocalDate retirada;
    private LocalDate devolucao;
    private double multa;
    private boolean devolvido;
    private Integer idMulta; // 🔹 referência à tabela Multa (pode ser null)

    public Emprestimo() {}

    public Emprestimo(int id, int idUsuario, int idLivro, LocalDate retirada, LocalDate devolucao,
                      double multa, boolean devolvido, Integer idMulta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.retirada = retirada;
        this.devolucao = devolucao;
        this.multa = multa;
        this.devolvido = devolvido;
        this.idMulta = idMulta;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

    public LocalDate getRetirada() { return retirada; }
    public void setRetirada(LocalDate retirada) { this.retirada = retirada; }

    public LocalDate getDevolucao() { return devolucao; }
    public void setDevolucao(LocalDate devolucao) { this.devolucao = devolucao; }

    public double getMulta() { return multa; }
    public void setMulta(double multa) { this.multa = multa; }

    public boolean isDevolvido() { return devolvido; }
    public void setDevolvido(boolean devolvido) { this.devolvido = devolvido; }

    public Integer getIdMulta() { return idMulta; }
    public void setIdMulta(Integer idMulta) { this.idMulta = idMulta; }

    @Override
    public String toString() {
        return "Empréstimo de Usuário ID: " + idUsuario + " | Livro ID: " + idLivro;
    }
}
