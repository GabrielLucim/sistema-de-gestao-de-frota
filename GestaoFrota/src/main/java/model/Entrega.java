package model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.enums.StatusEntrega;
import model.enums.StatusVeiculo;

@Entity
@Table(name = "Entregas")
public class Entrega {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String origem;
    private String destino;
    private LocalDateTime dataAgendada;
    private LocalDateTime prazoEstimadoConclusao;
    private StatusEntrega status;
    private double pesoKg;
    @ManyToOne 
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculoAlocado;
    @ManyToOne 
    @JoinColumn(name = "motorista_id")
    private Motorista motoristaAlocado;
    private LocalDateTime inicioViagem;
    private LocalDateTime fimViagem;
    private double distanciaKm;
    private double consumoLitros;
    private String motivoCancelamento;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDateTime getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDateTime dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public LocalDateTime getPrazoEstimadoConclusao() {
        return prazoEstimadoConclusao;
    }

    public void setPrazoEstimadoConclusao(LocalDateTime prazoEstimadoConclusao) {
        this.prazoEstimadoConclusao = prazoEstimadoConclusao;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public void setStatus(StatusEntrega status) {
        this.status = status;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Veiculo getVeiculoAlocado() {
        return veiculoAlocado;
    }

    public void setVeiculoAlocado(Veiculo veiculoAlocado) {
        this.veiculoAlocado = veiculoAlocado;
    }

    public Motorista getMotoristaAlocado() {
        return motoristaAlocado;
    }

    public void setMotoristaAlocado(Motorista motoristaAlocado) {
        this.motoristaAlocado = motoristaAlocado;
    }

    public LocalDateTime getInicioViagem() {
        return inicioViagem;
    }

    public void setInicioViagem(LocalDateTime inicioViagem) {
        this.inicioViagem = inicioViagem;
    }

    public LocalDateTime getFimViagem() {
        return fimViagem;
    }

    public void setFimViagem(LocalDateTime fimViagem) {
        this.fimViagem = fimViagem;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getConsumoLitros() {
        return consumoLitros;
    }

    public void setConsumoLitros(double consumoLitros) {
        this.consumoLitros = consumoLitros;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }
    
    
    
    public boolean alocar(Veiculo v, Motorista m) {
        // Validação 1: Veículo Ativo
        if (v.getStatus() != StatusVeiculo.ATIVO) {
            System.out.println("Erro de Alocação: Veículo não está ATIVO.");
            return false;
        }

        // Validação 2: Motorista Ativo e CNH Válida
        if (!m.isAtivo() || !m.cnhValida()) {
             System.out.println("Erro de Alocação: Motorista inativo ou CNH vencida.");
            return false;
        }
        
        // Validação 3: Compatibilidade CNH
        if (!m.podeDirigir(v)) {
            System.out.println("Erro de Alocação: Motorista incompatível com o veículo.");
            return false;
        }

        // Se todas as validações passarem, a alocação é realizada.
        this.setVeiculoAlocado(v);
        this.setMotoristaAlocado(m);
        System.out.println("Alocação realizada com sucesso.");
        return true;
    }
    
    public void marcarEmTransito() {
        if (this.status != StatusEntrega.PENDENTE) {
            System.out.println("Erro: A entrega não pode sair para trânsito. Status atual: " + this.status);
            return;
        }
        this.setStatus(StatusEntrega.EM_TRANSITO);
        this.setInicioViagem(LocalDateTime.now());
    }
    
    
    public void marcarConcluida(double distanciaKm, double consumoLitros) {
        
        this.setFimViagem(LocalDateTime.now());
        this.setDistanciaKm(distanciaKm);
        this.setConsumoLitros(consumoLitros);
        this.setStatus(StatusEntrega.ENTREGUE);

        if (this.veiculoAlocado != null) {
            this.veiculoAlocado.atualizarKm(distanciaKm);
        } else {
             System.out.println("Aviso: Veículo não estava alocado. KM do veículo não atualizado.");
        }
    }
    
    
    public void cancelar(String motivo) {
        if (this.status == StatusEntrega.PENDENTE) {
            this.setStatus(StatusEntrega.CANCELADA);
            this.setMotivoCancelamento(motivo);
            
            //Desalocar o veículo e motorista
            this.setVeiculoAlocado(null);
            this.setMotoristaAlocado(null);
            System.out.println("Entrega cancelada. Motivo: " + motivo);
        } else {
            System.out.println("Erro: Entrega só pode ser cancelada se estiver PENDENTE. Status atual: " + this.status);
        }
    }

}

