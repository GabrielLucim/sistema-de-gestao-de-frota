package dao;

import dao.GenericDAO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public abstract class ImpGenericDAO<T, ID> implements GenericDAO<T, ID> {

	protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");
	private EntityManager em = emf.createEntityManager();
	

    private final Class<T> entityClass;

    protected ImpGenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    // Método auxiliar opcional para reabrir o EntityManager se estiver fechado
    protected void EntityManagerOpen() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
    }
    
    @Override
    @Transactional
    public T salvar(T entity) {
    	EntityManagerOpen();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar " + entityClass.getSimpleName(), e);
        } finally {
            em.close(); // Fecha o EntityManager
        }
    }
    @Override
    @Transactional
    public T atualizar(T entity) {
        EntityManagerOpen();
        try {
            em.getTransaction().begin();
            T merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar " + entityClass.getSimpleName(), e);
        } finally {
            em.close();
        }
    }

    @Override
    @Transactional
    public void remover(ID id) {
        EntityManagerOpen();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao remover " + entityClass.getSimpleName(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        EntityManagerOpen();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar " + entityClass.getSimpleName() + " por ID", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> buscarTodos() {
        EntityManagerOpen();
        try {
            return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                     .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar " + entityClass.getSimpleName(), e);
        } finally {
            em.close();
        }
    }
}

