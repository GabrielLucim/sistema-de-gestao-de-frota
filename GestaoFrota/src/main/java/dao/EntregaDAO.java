package dao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
			TypedQuery<Entrega> query = em.createQuery(
	                "SELECT e FROM Entrega e WHERE e.status = :status", Entrega.class);
	            query.setParameter("status", status);
	            return query.getResultList();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao buscar entregas por status: " + status, e);
	        } finally {
	            if (em != null && em.isOpen()) {
	                em.close();
	            }
	        }
	    }

	    public List<Entrega> buscarPendentes() {
	        return buscarPorStatus(StatusEntrega.PENDENTE);
	    }

	    public List<Entrega> buscarPorVeiculoId(Long veiculoId) {
	        if (veiculoId == null) {
	            return Collections.emptyList();
	        }

	        EntityManager em = null;
	        try {
	            em = emf.createEntityManager();
	            TypedQuery<Entrega> query = em.createQuery(
	                "SELECT e FROM Entrega e WHERE e.veiculoAlocado.id = :id", Entrega.class);
	            query.setParameter("id", veiculoId);
	            return query.getResultList();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao buscar entregas por veículo: " + veiculoId, e);
	        } finally {
	            if (em != null && em.isOpen()) {
	                em.close();
	            }
	        }
	    }

	    public List<Entrega> buscarPorMotoristaId(Long motoristaId) {
	        if (motoristaId == null) {
	            return Collections.emptyList();
	        }

	        EntityManager em = null;
	        try {
	            em = emf.createEntityManager();
	            TypedQuery<Entrega> query = em.createQuery(
	                "SELECT e FROM Entrega e WHERE e.motoristaAlocado.id = :id", Entrega.class);
	            query.setParameter("id", motoristaId);
	            return query.getResultList();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao buscar entregas por motorista: " + motoristaId, e);
	        } finally {
	            if (em != null && em.isOpen()) {
	                em.close();
	            }
	        }
	    }

	    public List<Entrega> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
	        if (inicio == null || fim == null) {
	            return Collections.emptyList();
	        }

	        EntityManager em = null;
	        try {
	            em = emf.createEntityManager();
	            TypedQuery<Entrega> query = em.createQuery(
	                "SELECT e FROM Entrega e WHERE e.dataAgendada BETWEEN :inicio AND :fim", Entrega.class);
	            query.setParameter("inicio", inicio);
	            query.setParameter("fim", fim);
	            return query.getResultList();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao buscar entregas por período.", e);
	        } finally {
	            if (em != null && em.isOpen()) {
	                em.close();
	            }
	        }
	    }
	}