package controller;

import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import model.Emprestimo;
import service.EmprestimoService;

public class EmprestimoController {

    private EmprestimoService emprestimoService;

    public EmprestimoController() {
        this.emprestimoService = new EmprestimoService();
    }

    // 🔸 Cadastrar novo empréstimo
    public void cadastrarEmprestimo(int idUsuario, int idLivro, LocalDate retirada, LocalDate devolucao, double multa) {
        if (retirada == null || devolucao == null) {
            JOptionPane.showMessageDialog(null, "⚠️ As datas de retirada e devolução são obrigatórias!");
            return;
        }

        if (devolucao.isBefore(retirada)) {
            JOptionPane.showMessageDialog(null, "⚠️ A data de devolução não pode ser anterior à data de retirada!");
            return;
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdUsuario(idUsuario);
        emprestimo.setIdLivro(idLivro);
        emprestimo.setRetirada(retirada);
        emprestimo.setDevolucao(devolucao);
        emprestimo.setMulta(multa);
        emprestimo.setDevolvido(false); // padrão

        emprestimoService.cadastrarEmprestimo(emprestimo);
    }

    // 🔸 Listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoService.listarEmprestimos();
    }

    // 🔸 Atualizar empréstimo (geralmente usado para estender prazo)
    public void atualizarEmprestimo(int id, int idUsuario, int idLivro, LocalDate retirada, LocalDate devolucao, double multa) {
        Emprestimo emprestimo = new Emprestimo();
      emprestimo.setId(id);
        emprestimo.setIdUsuario(idUsuario);
        emprestimo.setIdLivro(idLivro);
        emprestimo.setRetirada(retirada);
        emprestimo.setDevolucao(devolucao);
        emprestimo.setMulta(multa);
        emprestimo.setDevolvido(false);

        emprestimoService.atualizarEmprestimo(emprestimo);
    }

    // 🔸 Marcar como devolvido
    public void marcarComoDevolvido(int id) {
        emprestimoService.marcarComoDevolvido(id);
    }

    // 🔸 Excluir empréstimo (geralmente se for cadastro errado)
    public void deletarEmprestimo(int id) {
        int opcao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o empréstimo com ID " + id + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            emprestimoService.deletarEmprestimo(id);
        }
    }

    public Emprestimo buscarPorId(int id) {
    return emprestimoService.buscarPorId(id);
}

public void cadastrarEmprestimoCliente(int idUsuario, int idLivro, LocalDate retirada, LocalDate devolucao) {
    emprestimoService.cadastrarEmprestimoCliente(idUsuario, idLivro, retirada, devolucao);
}

    public boolean devolverLivro(int idEmprestimo) {
        return emprestimoService.devolverLivro(idEmprestimo);
    }

   public boolean devolverComPagamento(int idEmprestimo, String tipoPagamento) {
    return emprestimoService.devolverComPagamento(idEmprestimo, tipoPagamento);
}


}
