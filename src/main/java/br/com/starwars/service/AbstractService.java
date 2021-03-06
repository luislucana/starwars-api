package br.com.starwars.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import br.com.starwars.web.exception.ResourceNotFoundException;

/**
 * Implementacao de servicos basicos.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 * 
 */
@Transactional
public abstract class AbstractService<T extends Serializable> implements IBasicOperations<T> {

    @Override
    @Transactional(readOnly = true)
    public T findOne(final Integer id) {
        return getDao().findById(id).orElseThrow(() -> new ResourceNotFoundException("Planet nao encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Page<T> findPaginated(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    public T create(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(final T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(final Integer entityId) {
        getDao().deleteById(entityId);
    }

    protected abstract PagingAndSortingRepository<T, Integer> getDao();
}