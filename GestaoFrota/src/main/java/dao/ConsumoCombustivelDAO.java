package dao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.ConsumoCombustivel;

public class ConsumoCombustivelDAO extends ImpGenericDAO<ConsumoCombustivel, Long> {

    public ConsumoCombustivelDAO() {
        super(ConsumoCombustivel.class);
    }

    public List<ConsumoCombustivel> buscarPorVeiculoId(Long veiculoId) {
        if (veiculoId == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<ConsumoCombustivel> query = em.createQuery(
                "SELECT c FROM ConsumoCombustivel c WHERE c.veiculo.id = :id", ConsumoCombustivel.class);
            query.setParameter("id", veiculoId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consumo de combustível por veículo: " + veiculoId, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<ConsumoCombustivel> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<ConsumoCombustivel> query = em.createQuery(
                "SELECT c FROM ConsumoCombustivel c WHERE c.data BETWEEN :inicio AND :fim",
                ConsumoCombustivel.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consumo de combustível por período.", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}

