package dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    T salvar(T entity);
    T atualizar(T entity);
    void remover(ID id);
    Optional<T> buscarPorId(ID id);
    List<T> buscarTodos();
}
