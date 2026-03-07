package br.com.ticket.sale.core.common.domain;

import java.util.List;
import java.util.Optional;

public interface IRepository<E extends AggregateRoot<ID>, ID> {

    void add(E entity);

    Optional<E> findById(ID id);

    List<E> findAll();

    void delete(ID id);

}
