package controller;

import java.util.List;
import model.Multa;
import service.MultaService;

public class MultaController {
    private MultaService service;

    public MultaController() {
        this.service = new MultaService();
    }

    public void cadastrarMulta(int idEmprestimo, double valor, boolean pago, String tipoPagamento) {
        Multa m = new Multa();
        m.setIdEmprestimo(idEmprestimo);
        m.setValor(valor);
        m.setPago(pago);
        m.setTipoPagamento(tipoPagamento);
        service.cadastrarMulta(m);
    }

    public void atualizarMulta(int id, int idEmprestimo, double valor, boolean pago, String tipoPagamento) {
        Multa m = new Multa(id, idEmprestimo, valor, pago, tipoPagamento);
        service.atualizarMulta(m);
    }

    public void deletarMulta(int id) {
        service.deletarMulta(id);
    }

    public List<Multa> listarMultas() {
        return service.listarMultas();
    }

    public void pagarMulta(int id) {
        service.pagarMulta(id);
    }

    public void pagarMulta(int idMulta, String tipoPagamento) {
    service.pagarMulta(idMulta, tipoPagamento);
}

}
