package dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import model.Entrega;
import model.enums.StatusEntrega;

public class EntregaDAO extends ImpGenericDAO<Entrega, Long> {
	
	public EntregaDAO() {
		super(Entrega.class);
	}
	
	public List<Entrega> buscarPorStatus(StatusEntrega status){
		if(status == null) {
			return Collections.emptyList();
		}
		
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			
		}
	}
}
