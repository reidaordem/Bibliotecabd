package service;

import dao.MultaDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import model.Multa;

public class MultaService {
    private MultaDAO dao;

    public MultaService() {
        this.dao = new MultaDAO();
    }

    // 🔹 Cadastrar nova multa
    public void cadastrarMulta(Multa multa) {
        try {
            dao.inserir(multa);
            JOptionPane.showMessageDialog(null, "✅ Multa cadastrada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar multa: " + e.getMessage());
        }
    }

    // 🔹 Atualizar multa
    public void atualizarMulta(Multa multa) {
        try {
            dao.atualizar(multa);
            JOptionPane.showMessageDialog(null, "✅ Multa atualizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar multa: " + e.getMessage());
        }
    }

    // 🔹 Deletar
    public void deletarMulta(int id) {
        try {
            dao.deletar(id);
            JOptionPane.showMessageDialog(null, "🗑️ Multa deletada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar multa: " + e.getMessage());
        }
    }

    // 🔹 Listar todas
    public List<Multa> listarMultas() {
        try {
            return dao.listarTodas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar multas: " + e.getMessage());
            return null;
        }
    }

    // 🔹 Buscar por empréstimo
    public Multa buscarPorEmprestimo(int idEmprestimo) {
        try {
            return dao.buscarPorEmprestimo(idEmprestimo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar multa: " + e.getMessage());
            return null;
        }
    }

   

    public void pagarMulta(int idMulta, String tipoPagamento) {
    try {
        dao.pagarMulta(idMulta, tipoPagamento);
        JOptionPane.showMessageDialog(null, "💰 Multa paga com sucesso!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao pagar multa: " + e.getMessage());
    }
}

}

