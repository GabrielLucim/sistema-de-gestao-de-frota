package service;

import java.util.List;

import dao.MotoristaDAO;
import model.Motorista;
import util.Validador;

public class MotoristaService {

    private final MotoristaDAO motoristaDAO;

    public MotoristaService(MotoristaDAO motoristaDAO) {
        this.motoristaDAO = motoristaDAO;
    }


    // Regras:
    // - CNH deve ter formato válido
    // - CNH deve ser única
    // - CNH não pode estar vencida
    public Motorista cadastrar(Motorista m) {

        // Valida formato da CNH
        if (!Validador.cnhValidaFormat(m.getCnh())) {
            throw new IllegalArgumentException("Formato de CNH inválido.");
        }

        // Verifica se a CNH já existe no banco
        if (motoristaDAO.buscarPorCnh(m.getCnh()).isPresent()) {
            throw new IllegalArgumentException("Essa CNH já está cadastrada no sistema.");
        }

        // Valida data de validade da CNH
        if (!m.cnhValida()) {
            throw new IllegalArgumentException("A CNH está vencida. Motorista não pode ser cadastrado como ativo.");
        }

        // Motorista começa ativo se tudo estiver correto
        m.setAtivo(true);

        return motoristaDAO.salvar(m);
    }


    // Se a CNH estiver vencida → motorista fica INATIVO
    public void validarCNH(Long motoristaId) {
        Motorista motorista = motoristaDAO.buscarPorId(motoristaId)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado."));

        if (!motorista.cnhValida()) {
            motorista.setAtivo(false);
            motoristaDAO.atualizar(motorista);
        }
    }


    public List<Motorista> listarAtivos() {
        return motoristaDAO.buscarAtivos();
    }
}

