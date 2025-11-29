package service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import dao.VeiculoDAO;
import model.Veiculo;
import model.enums.StatusVeiculo;
import util.Validador;

public class VeiculoService {

    private final VeiculoDAO veiculoDAO;

    public VeiculoService() {
        this.veiculoDAO = new VeiculoDAO();
    }


    // Regra: validar placa e ano antes de salvar
    public Veiculo cadastrar(Veiculo v) {

        // Validação da placa (formato)
        if (!Validador.placaValida(v.getPlaca())) {
            throw new IllegalArgumentException("Placa inválida.");
        }

        // Verifica se a placa já existe no banco
        if (veiculoDAO.buscarPorPlaca(v.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("A placa informada já está cadastrada.");
        }

        // Validação do ano
        int anoAtual = LocalDate.now().getYear();
        if (v.getAnoFabricacao() > anoAtual) {
            throw new IllegalArgumentException("Ano de fabricação inválido.");
        }

        // Ao cadastrar, o veículo começa como ATIVO (a menos que defina outro)
        if (v.getStatus() == null) {
            v.setStatus(StatusVeiculo.ATIVO);
        }

        return veiculoDAO.salvar(v);
    }

    public Veiculo atualizarKm(Long veiculoId, double kmRodados) {
        Veiculo veiculo = veiculoDAO.buscarPorId(veiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        if (kmRodados < 0) {
            throw new IllegalArgumentException("KM rodados não pode ser negativo.");
        }

        veiculo.atualizarKm(kmRodados);
        return veiculoDAO.atualizar(veiculo);
    }


    // Disponível = status ATIVO
    public List<Veiculo> listarDisponiveis() {
        return veiculoDAO.buscarPorStatus(StatusVeiculo.ATIVO);
    }


    // Retorna todos os veículos que precisam de manutenção
    public List<Veiculo> verificarManutencaoPreventiva(int intervaloKm, int intervaloMeses) {

        List<Veiculo> todos = veiculoDAO.buscarTodos();

        return todos.stream()
                .filter(v -> v.necessitaManutencao(intervaloKm, intervaloMeses))
                .collect(Collectors.toList());
    }
}
