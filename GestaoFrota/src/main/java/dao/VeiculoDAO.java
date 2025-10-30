package dao;

import model.Veiculo;
import model.enums.StatusVeiculo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VeiculoDAO extends ImpGenericDAO<Veiculo, Long> {

    public VeiculoDAO() {
        super(Veiculo.class);
    }


    public Optional<Veiculo> buscarPorPlaca(String placa) {
        if (placa == null) {
            return Optional.empty();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Veiculo> query = em.createQuery(
                    "SELECT v FROM Veiculo v WHERE LOWER(v.placa) = :placa", Veiculo.class);
            query.setParameter("placa", placa.trim().toLowerCase());
            List<Veiculo> results = query.setMaxResults(1).getResultList();
            if (results.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(results.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Veiculo por placa: " + placa, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public List<Veiculo> buscarPorStatus(StatusVeiculo status) {
        if (status == null) {
            return Collections.emptyList();
        }

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Veiculo> query = em.createQuery(
                    "SELECT v FROM Veiculo v WHERE v.status = :status", Veiculo.class);
            query.setParameter("status", status);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Veiculos por status: " + status, e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}
