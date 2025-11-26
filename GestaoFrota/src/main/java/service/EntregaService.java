package service;

import java.time.LocalDateTime;
import java.util.List;

import dao.EntregaDAO;
import dao.MotoristaDAO;
import dao.VeiculoDAO;
import model.Entrega;
import model.Motorista;
import model.Veiculo;
import model.enums.StatusEntrega;
import model.enums.StatusVeiculo;

public class EntregaService {

    private final EntregaDAO entregaDAO;
    private final VeiculoDAO veiculoDAO;
    private final MotoristaDAO motoristaDAO;

    public EntregaService(EntregaDAO entregaDAO, VeiculoDAO veiculoDAO, MotoristaDAO motoristaDAO) {
        this.entregaDAO = entregaDAO;
        this.veiculoDAO = veiculoDAO;
        this.motoristaDAO = motoristaDAO;
    }


    public Entrega criarEntrega(Entrega e) {
        e.setStatus(StatusEntrega.PENDENTE);
        return entregaDAO.salvar(e);
    }


    // ALOCAR MOTORISTA E VEÍCULO
    // Regras:
    // - motorista deve ser ativo e com CNH válida
    // - veículo deve estar ATIVO
    public Entrega alocarEntrega(Long entregaId, Long veiculoId, Long motoristaId) {

        Entrega entrega = entregaDAO.buscarPorId(entregaId)
                .orElseThrow(() -> new IllegalArgumentException("Entrega não encontrada."));

        if (entrega.getStatus() != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Entrega só pode ser alocada se estiver PENDENTE.");
        }

        Veiculo veiculo = veiculoDAO.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        if (veiculo.getStatus() != StatusVeiculo.ATIVO) {
            throw new IllegalStateException("Veículo não está disponível.");
        }

        Motorista motorista = motoristaDAO.buscarPorId(motoristaId)
                .orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado."));

        if (!motorista.isAtivo() || !motorista.cnhValida()) {
            throw new IllegalStateException("Motorista não está apto para dirigir.");
        }

        // ALLOCAÇÃO
        entrega.setVeiculoAlocado(veiculo);
        entrega.setMotoristaAlocado(motorista);

        return entregaDAO.atualizar(entrega);
    }

    public Entrega iniciarEntrega(Long entregaId) {
        Entrega entrega = entregaDAO.buscarPorId(entregaId)
                .orElseThrow(() -> new IllegalArgumentException("Entrega não encontrada."));

        if (entrega.getStatus() != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Entrega só pode ser iniciada se estiver PENDENTE.");
        }

        entrega.setInicioViagem(LocalDateTime.now());
        entrega.setStatus(StatusEntrega.EM_TRANSITO);

        return entregaDAO.atualizar(entrega);
    }


    public Entrega concluirEntrega(Long entregaId, double distanciaKm, double consumoLitros) {

        Entrega entrega = entregaDAO.buscarPorId(entregaId)
                .orElseThrow(() -> new IllegalArgumentException("Entrega não encontrada."));

        if (entrega.getStatus() != StatusEntrega.EM_TRANSITO) {
            throw new IllegalStateException("Entrega não está em trânsito.");
        }

        entrega.setFimViagem(LocalDateTime.now());
        entrega.setDistanciaKm(distanciaKm);
        entrega.setConsumoLitros(consumoLitros);
        entrega.setStatus(StatusEntrega.ENTREGUE);

        // Atualiza o km do veículo
        Veiculo v = entrega.getVeiculoAlocado();
        v.atualizarKm(distanciaKm);
        veiculoDAO.atualizar(v);

        return entregaDAO.atualizar(entrega);
    }


    public void cancelarEntrega(Long entregaId, String motivo) {

        Entrega entrega = entregaDAO.buscarPorId(entregaId)
                .orElseThrow(() -> new IllegalArgumentException("Entrega não encontrada."));

        if (entrega.getStatus() != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Só é possível cancelar entregas PENDENTES.");
        }

        entrega.setMotivoCancelamento(motivo);
        entrega.setStatus(StatusEntrega.CANCELADA);

        entregaDAO.atualizar(entrega);
    }
}

