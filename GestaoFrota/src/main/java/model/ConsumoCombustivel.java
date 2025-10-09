package model;

import java.time.LocalDateTime;

public class ConsumoCombustivel {
    private Long id;
    private Veiculo veiculo;
    private LocalDateTime data;
    private double litros;
    private double valorTotal;
    private double kmNoAbastecimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getKmNoAbastecimento() {
        return kmNoAbastecimento;
    }

    public void setKmNoAbastecimento(double kmNoAbastecimento) {
        this.kmNoAbastecimento = kmNoAbastecimento;
    }
    
    
    
    public double custoPorLitro() {
        
        if (this.litros > 0) {
            return this.valorTotal / this.litros;
        }
        return 0.0;
    }
}
