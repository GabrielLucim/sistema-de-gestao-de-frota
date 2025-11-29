package service;

import dao.ConsumoCombustivelDAO;
import dao.VeiculoDAO;
import model.ConsumoCombustivel;
import model.Veiculo;

public class ConsumoService {

    private final ConsumoCombustivelDAO consumoDAO;
    private final VeiculoDAO veiculoDAO;

    public ConsumoService() {
        this.consumoDAO = new ConsumoCombustivelDAO();
        this.veiculoDAO = new VeiculoDAO();
    }


    public ConsumoCombustivel registrarAbastecimento(ConsumoCombustivel c) {

        Veiculo v = veiculoDAO.buscarPorId(c.getVeiculo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado."));

        if (c.getLitros() <= 0) {
            throw new IllegalArgumentException("Quantidade de litros deve ser maior que zero.");
        }

        if (c.getValorTotal() <= 0) {
            throw new IllegalArgumentException("Valor total deve ser maior que zero.");
        }

        return consumoDAO.salvar(c);
    }
}

