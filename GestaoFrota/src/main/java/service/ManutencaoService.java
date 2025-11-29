package service;

import java.time.LocalDate;
import java.util.List;

import dao.ManutencaoDAO;
import dao.VeiculoDAO;
import model.Manutencao;
import model.Veiculo;
import model.enums.StatusManutencao;
import model.enums.TipoManutencao;
import model.enums.StatusVeiculo;

public class ManutencaoService {

    private final ManutencaoDAO manutencaoDAO;
    private final VeiculoDAO veiculoDAO;

    public ManutencaoService() {
        this.manutencaoDAO = new ManutencaoDAO();
        this.veiculoDAO = new VeiculoDAO();
    }


    public Manutencao agendarPreventiva(Long veiculoId, LocalDate data, double kmAgendado) {

        Veiculo v = veiculoDAO.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        Manutencao m = new Manutencao();
        m.setVeiculo(v);
        m.setTipo(TipoManutencao.PREVENTIVA);
        m.setDataAgendada(data);
        m.setKmAgendado(kmAgendado);
        m.setStatus(StatusManutencao.PENDENTE);

        return manutencaoDAO.salvar(m);
    }


    public Manutencao registrarCorretiva(Long veiculoId, LocalDate data, String descricao) {

        Veiculo v = veiculoDAO.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        Manutencao m = new Manutencao();
        m.setVeiculo(v);
        m.setTipo(TipoManutencao.CORRETIVA);
        m.setDataAgendada(data);
        m.setObservacao(descricao);
        m.setStatus(StatusManutencao.PENDENTE);

        return manutencaoDAO.salvar(m);
    }


    public Manutencao concluirManutencao(Long manutencaoId, double km, double custo) {

        Manutencao m = manutencaoDAO.buscarPorId(manutencaoId)
                .orElseThrow(() -> new IllegalArgumentException("Manutenção não encontrada."));

        m.setCusto(custo);
        m.setKmAgendado(km);
        m.setStatus(StatusManutencao.CONCLUIDA);

        // Atualiza o veículo
        Veiculo v = m.getVeiculo();
        v.setDataUltimaRevisao(LocalDate.now());
        v.setKmUltimaRevisao(km);
        v.setStatus(StatusVeiculo.ATIVO);

        veiculoDAO.atualizar(v);

        return manutencaoDAO.atualizar(m);
    }
}
