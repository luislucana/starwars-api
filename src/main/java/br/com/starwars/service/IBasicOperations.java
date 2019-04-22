package br.com.starwars.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Interface para definicao de operacoes basicas na camada de servico.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 * 
 */
public interface IBasicOperations<T extends Serializable> {

    T findOne(final Integer id);

    List<T> findAll();

    Page<T> findPaginated(int page, int size);

    T create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final Integer entityId);
}