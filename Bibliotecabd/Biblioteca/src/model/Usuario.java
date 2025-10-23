package model;

public class Usuario {
    private int id;
    private String nome;
    private String matricula;
    private String email;
    private String senha;     // 🔹 novo campo
    private String tipo;      // 🔹 CLIENTE ou FUNCIONARIO

    public Usuario() {}

    public Usuario(int id, String nome, String matricula, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override
    public String toString() {
        // 🔹 Mostra o nome de forma limpa em JComboBox e tabelas
        return nome + " (" + matricula + ")";
    }
}
