package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import model.enums.StatusVeiculo;

public class Veiculo {
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
		}
	}
	
	public double custoPorKm(double precoCombustivel) {
		if(consumoMedio <= 0) return 0;
		return precoCombustivel/consumoMedio;
		
	}
	
	public boolean necessitaManutencao(int intervaloKm, int intervaloMeses) {
	    long meses = ChronoUnit.MONTHS.between(dataUltimaRevisao, LocalDate.now());
	    boolean porKm = (kmAtual - kmUltimaRevisao) >= intervaloKm;
	    boolean porTempo = meses >= intervaloMeses;
	    return porKm || porTempo;
	}

}
