package dao;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Manutencao;
import model.enums.StatusManutencao;

public class ManutencaoDAO extends ImpGenericDAO<Manutencao, Long> {

    public ManutencaoDAO() {
        super(Manutencao.class);
    }

    public List<Manutencao> buscarPorVeiculoId(Long veiculoId) {
        if (veiculoId == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Manutencao> query = em.createQuery(
                "SELECT m FROM Manutencao m WHERE m.veiculo.id = :id", Manutencao.class);
            query.setParameter("id", veiculoId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar manutenções por veículo: " + veiculoId, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Manutencao> buscarPorStatus(StatusManutencao status) {
        if (status == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Manutencao> query = em.createQuery(
                "SELECT m FROM Manutencao m WHERE m.status = :status", Manutencao.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar manutenções por status: " + status, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Manutencao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Manutencao> query = em.createQuery(
                "SELECT m FROM Manutencao m WHERE m.dataAgendada BETWEEN :inicio AND :fim", Manutencao.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar manutenções por período.", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}

