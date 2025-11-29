package model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.enums.StatusManutencao;
import model.enums.StatusVeiculo;
import model.enums.TipoManutencao;

@Entity
@Table(name = "Manutenção")
public class Manutencao {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@ManyToOne 
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;
    private TipoManutencao tipo;
    private LocalDate dataAgendada;
    private double kmAgendado;
    private StatusManutencao status;
    private double custo;
    private String observacao;

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

    public TipoManutencao getTipo() {
        return tipo;
    }

    public void setTipo(TipoManutencao tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public double getKmAgendado() {
        return kmAgendado;
    }

    public void setKmAgendado(double kmAgendado) {
        this.kmAgendado = kmAgendado;
    }

    public StatusManutencao getStatus() {
        return status;
    }

    public void setStatus(StatusManutencao status) {
        this.status = status;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    
    
    public void concluir(LocalDate data, double km, double custo) {
        // 1 - Verifica a condição da Manutenção só poder ser concluída se estiver PENDENTE
        if (this.status != StatusManutencao.PENDENTE) {
            System.out.println("Erro: Manutenção já está concluída ou cancelada. Status: " + this.status);
            return;
        }

        // 2 - Atualiza os dados da Manutenção
        this.setStatus(StatusManutencao.CONCLUIDA);
        this.setCusto(custo); 
        
        
        // 3 - Atualiza os dados do Veículo 
        Veiculo v = this.getVeiculo();
        if (v != null) {
            
            v.setStatus(StatusVeiculo.ATIVO);
            v.setDataUltimaRevisao(data);
            v.setKmUltimaRevisao(km);
            v.atualizarKm(km); 
            System.out.println("Manutenção concluída. Veículo " + v.getPlaca() + " voltou ao status ATIVO.");
        } else {
            System.out.println("Erro: Não há veículo associado a esta manutenção.");
        }
    }
    
    public String observacaoManutencao(String observacao) {
    	return this.observacao = observacao;
    }
}

