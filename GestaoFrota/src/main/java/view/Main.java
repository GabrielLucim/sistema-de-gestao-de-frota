package view;

import java.time.LocalDate;
import java.time.LocalDateTime;

import model.Veiculo;
import model.Motorista;
import model.Entrega;
import model.Manutencao;
import model.ConsumoCombustivel;

import model.enums.CategoriaCNH;
import model.enums.StatusVeiculo;
import model.enums.TipoManutencao;

import service.VeiculoService;
import service.MotoristaService;
import service.EntregaService;
import service.ManutencaoService;
import service.ConsumoService;

public class Main {

    public static void main(String[] args) {

        VeiculoService veiculoService = new VeiculoService();
        MotoristaService motoristaService = new MotoristaService();
        EntregaService entregaService = new EntregaService();
        ManutencaoService manutencaoService = new ManutencaoService();
        ConsumoService consumoService = new ConsumoService();

        try {
            // ============================================================
            // 1) CADASTRAR VEÍCULOS
            // ============================================================
            Veiculo v1 = new Veiculo();
            v1.setPlaca("ABC-1234");
            v1.setModelo("Fiorino");
            v1.setFabricante("Fiat");
            v1.setAnoFabricacao(2018);
            v1.setKmAtual(45600);
            v1.setStatus(StatusVeiculo.ATIVO);
            v1.setConsumoMedio(12.5);
            v1.setDataUltimaRevisao(LocalDate.of(2024, 5, 20));
            v1.setKmUltimaRevisao(43000);
            v1 = veiculoService.cadastrar(v1);

            Veiculo v2 = new Veiculo();
            v2.setPlaca("QWE-5678");
            v2.setModelo("Caminhão 3/4");
            v2.setFabricante("Volkswagen");
            v2.setAnoFabricacao(2020);
            v2.setKmAtual(90000);
            v2.setStatus(StatusVeiculo.ATIVO);
            v2.setConsumoMedio(6.8);
            v2.setDataUltimaRevisao(LocalDate.of(2024, 3, 15));
            v2.setKmUltimaRevisao(83000);
            v2 = veiculoService.cadastrar(v2);


            // ============================================================
            // 2) CADASTRAR MOTORISTAS
            // ============================================================
            Motorista m1 = new Motorista();
            m1.setNome("João da Silva");
            m1.setCnh("12345678900");
            m1.setCategoria(CategoriaCNH.B);
            m1.setValidadeCnh(LocalDate.of(2026, 8, 22));
            m1.setAtivo(true);
            m1 = motoristaService.cadastrar(m1);

            Motorista m2 = new Motorista();
            m2.setNome("Carlos Pereira");
            m2.setCnh("98765432155");
            m2.setCategoria(CategoriaCNH.C);
            m2.setValidadeCnh(LocalDate.of(2025, 12, 10));
            m2.setAtivo(true);
            m2 = motoristaService.cadastrar(m2);


            // ============================================================
            // 3) CADASTRAR ENTREGAS (sem alocar ainda)
            // ============================================================
            Entrega e1 = new Entrega();
            e1.setCodigo("ENT-001");
            e1.setOrigem("São Paulo");
            e1.setDestino("Campinas");
            e1.setPesoKg(150);
            e1.setDataAgendada(LocalDateTime.now().plusDays(1));
            e1.setPrazoEstimadoConclusao(LocalDateTime.now().plusDays(1).plusHours(2));
            e1 = entregaService.criarEntrega(e1);

            Entrega e2 = new Entrega();
            e2.setCodigo("ENT-002");
            e2.setOrigem("Guarulhos");
            e2.setDestino("Taubaté");
            e2.setPesoKg(90);
            e2.setDataAgendada(LocalDateTime.now().plusDays(2));
            e2.setPrazoEstimadoConclusao(LocalDateTime.now().plusDays(2).plusHours(4));
            e2 = entregaService.criarEntrega(e2);


            // ============================================================
            // 4) ALOCAR ENTREGAS
            // ============================================================
            e1 = entregaService.alocarEntrega(e1.getId(), v1.getId(), m1.getId());
            e2 = entregaService.alocarEntrega(e2.getId(), v2.getId(), m2.getId());


            // ============================================================
            // 5) INICIAR ENTREGAS
            // ============================================================
            e1 = entregaService.iniciarEntrega(e1.getId());
            e2 = entregaService.iniciarEntrega(e2.getId());


            // ============================================================
            // 6) CONCLUIR ENTREGAS
            // ============================================================
            e1 = entregaService.concluirEntrega(e1.getId(), 102.3, 8.4);
            e2 = entregaService.concluirEntrega(e2.getId(), 188.9, 20.2);


            // ============================================================
            // 7) AGENDAR MANUTENÇÕES
            // ============================================================
            Manutencao man1 = manutencaoService.agendarPreventiva(
                    v1.getId(),
                    LocalDate.now().plusDays(10),
                    v1.getKmAtual() + 500
            );

            Manutencao man2 = manutencaoService.registrarCorretiva(
                    v2.getId(),
                    LocalDate.now(),
                    "Troca de pastilha de freio"
            );


            // ============================================================
            // 8) CONCLUIR MANUTENÇÃO
            // ============================================================
            manutencaoService.concluirManutencao(man1.getId(), v1.getKmAtual() + 600, 350.00);


            // ============================================================
            // 9) REGISTRAR ABASTECIMENTOS
            // ============================================================
            ConsumoCombustivel c1 = new ConsumoCombustivel();
            c1.setVeiculo(v1);
            c1.setData(LocalDateTime.now());
            c1.setLitros(40);
            c1.setValorTotal(235.00);
            c1.setKmNoAbastecimento(v1.getKmAtual());
            consumoService.registrarAbastecimento(c1);

            ConsumoCombustivel c2 = new ConsumoCombustivel();
            c2.setVeiculo(v2);
            c2.setData(LocalDateTime.now());
            c2.setLitros(70);
            c2.setValorTotal(480.00);
            c2.setKmNoAbastecimento(v2.getKmAtual());
            consumoService.registrarAbastecimento(c2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
