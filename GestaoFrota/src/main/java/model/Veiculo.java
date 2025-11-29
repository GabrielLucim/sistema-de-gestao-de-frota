package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.enums.CategoriaVeiculo;
import model.enums.StatusVeiculo;

@Entity
@Table(name = "Veículos")
public class Veiculo {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String placa;
	private String modelo;
	private String fabricante;
	private int anoFabricacao;
	private double kmAtual;
	private double consumoMedio;
	private StatusVeiculo status;
	private LocalDate dataUltimaRevisao;
	private double kmUltimaRevisao;
	private CategoriaVeiculo categoria;
	
	
	public CategoriaVeiculo getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaVeiculo categoria) {
		this.categoria = categoria;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public int getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(int anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	public double getKmAtual() {
		return kmAtual;
	}
	public void setKmAtual(double kmAtual) {
		this.kmAtual = kmAtual;
	}
	public double getConsumoMedio() {
		return consumoMedio;
	}
	public void setConsumoMedio(double consumoMedio) {
		this.consumoMedio = consumoMedio;
	}
	public StatusVeiculo getStatus() {
		return status;
	}
	public void setStatus(StatusVeiculo status) {
		this.status = status;
	}
	public LocalDate getDataUltimaRevisao() {
		return dataUltimaRevisao;
	}
	public void setDataUltimaRevisao(LocalDate dataUltimaRevisao) {
		this.dataUltimaRevisao = dataUltimaRevisao;
	}
	public double getKmUltimaRevisao() {
		return kmUltimaRevisao;
	}
	public void setKmUltimaRevisao(double kmUltimaRevisao) {
		this.kmUltimaRevisao = kmUltimaRevisao;
	}
	
	
	
	
	
	public void atualizarKm(double km) {
		if(km > 0) {
			kmAtual += km;
			System.out.println("Rodagem atualizada para: "+kmAtual+" Km");
		}
	}
	
	public double custoPorKm(double precoCombustivel) {
		if(consumoMedio <= 0) return 0;
		double custo = precoCombustivel/consumoMedio;
		System.out.println("Custo médio por km é: R$"+custo);
		return custo;
		
	}
	
	public boolean necessitaManutencao(int intervaloKm, int intervaloMeses) {
		if(dataUltimaRevisao == null) return true;
		
	    long meses = ChronoUnit.MONTHS.between(dataUltimaRevisao, LocalDate.now());
	    boolean porKm = (kmAtual - kmUltimaRevisao) >= intervaloKm;
	    boolean porTempo = meses >= intervaloMeses;
	    return porKm || porTempo;
	}
	
    public void marcarAtivo() {
        if (this.status == StatusVeiculo.MANUTENCAO) {
            System.out.println("Não é possível ativar o veículo. Ele ainda está em manutenção.");
            return;
        }
        this.status = StatusVeiculo.ATIVO;
        this.kmUltimaRevisao = this.kmAtual;
        this.dataUltimaRevisao = LocalDate.now();
        System.out.println("Veículo marcado como ATIVO e revisão registrada.");
    }
    
    public void marcarEmManutencao() {
        this.status = StatusVeiculo.MANUTENCAO;
        System.out.println("Veículo marcado como em MANUTENÇÃO. Indisponível para uso.");
    }

}
