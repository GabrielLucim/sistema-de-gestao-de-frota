package dao;

import model.Motorista;
import model.enums.CategoriaCNH;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MotoristaDAO extends ImpGenericDAO<Motorista, Long>{
	
	public MotoristaDAO() {
		super(Motorista.class);
	}
	
	public Optional<Motorista> buscarPorCnh(String cnh){
		if(cnh == null) {
			return Optional.empty();
		}
		
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			TypedQuery<Motorista> query = em.createQuery(
					"SELECT m FROM Motorista m WHERE LOWER(m.cnh) = :cnh", Motorista.class);
			query.setParameter("cnh", cnh.trim().toLowerCase());
            List<Motorista> results = query.setMaxResults(1).getResultList();
            if (results.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Motorista por CNH: " + cnh, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
		}
	}
	
    public List<Motorista> buscarPorCategoria(CategoriaCNH categoria) {
        if (categoria == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Motorista> query = em.createQuery(
                    "SELECT m FROM Motorista m WHERE m.categoria = :categoria", Motorista.class);
            query.setParameter("categoria", categoria);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Motoristas por categoria: " + categoria, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Motorista> buscarAtivos() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Motorista> query = em.createQuery(
                    "SELECT m FROM Motorista m WHERE m.ativo = true", Motorista.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Motoristas ativos", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
