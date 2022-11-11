package com.company.seller.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class AbstractCrudService<REPOSITORY extends JpaRepository<T, ID>, T, ID> {

  protected final REPOSITORY repository;

  public T getById(ID id) {
    return repository.findById(id).orElseThrow();
  }

  public List<T> getAll() {
    return repository.findAll();
  }

  public T create(T entity) {
    return repository.save(entity);
  }

  @Transactional
  public T update(ID id, T entity) {
    this.getById(id);
    return this.repository.save(entity);
  }

  public void delete(ID id) {
    this.repository.deleteById(id);
  }

}
