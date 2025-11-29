package model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import model.enums.CategoriaCNH;

@Entity
@Table(name = "Motoristas")
public class Motorista {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnh;
    private CategoriaCNH categoria;
    private LocalDate validadeCnh;
    private boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public CategoriaCNH getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCNH categoria) {
        this.categoria = categoria;
    }

    public LocalDate getValidadeCnh() {
        return validadeCnh;
    }

    public void setValidadeCnh(LocalDate validadeCnh) {
        this.validadeCnh = validadeCnh;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    
    
    public boolean cnhValida() {
        LocalDate hoje = LocalDate.now();
        
        // Retorna true se a validade for maior ou igual a hoje (0 ou um número positivo).
        return validadeCnh.compareTo(hoje) >= 0;
    }
    
    
    public boolean podeDirigir(Veiculo v) {
    	CategoriaCNH categoriaExigida;
    	switch(v.getCategoria()){
        	case PASSEIO:
        		categoriaExigida = CategoriaCNH.B;
        		break;
        	case CAMINHAO_LEVE:
        		categoriaExigida = CategoriaCNH.C;
        		break;
        	case ONIBUS:
        		categoriaExigida = CategoriaCNH.D;
        		break;
        	case CARRETA:
        		categoriaExigida = CategoriaCNH.E;
        		break;
        	default:
        		return false; 
    	}
    	
    	if (!this.ativo || !this.cnhValida()) {
            return false;
        }
    	
    	return this.categoria.ordinal() >= categoriaExigida.ordinal();
    }
    
    
    public void inativar() {
        if (!this.cnhValida()) {
            this.ativo = false;
        }
        // Se a CNH estiver válida, o motorista permanece no status atual.
    }
}


