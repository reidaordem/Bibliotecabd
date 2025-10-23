package model;

public class Multa {
    private int id;
    private int idEmprestimo;
    private double valor;
    private boolean pago;
    private String tipoPagamento;

    public Multa() {}

    public Multa(int id, int idEmprestimo, double valor, boolean pago, String tipoPagamento) {
        this.id = id;
        this.idEmprestimo = idEmprestimo;
        this.valor = valor;
        this.pago = pago;
        this.tipoPagamento = tipoPagamento;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdEmprestimo() { return idEmprestimo; }
    public void setIdEmprestimo(int idEmprestimo) { this.idEmprestimo = idEmprestimo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }

    public String getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    @Override
    public String toString() {
        return "Multa: R$" + valor + (pago ? " (paga)" : " (pendente)");
    }
}
