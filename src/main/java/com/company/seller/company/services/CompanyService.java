package com.company.seller.company.services;

import com.company.seller.company.models.Company;
import com.company.seller.company.repositories.CompanyRepository;
import com.company.seller.services.AbstractCrudService;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService extends AbstractCrudService<CompanyRepository, Company, Long> {

  public CompanyService(CompanyRepository repository) {
    super(repository);
  }

  @Override
  @Transactional
  public Company create(Company company) {
    company.setCreated(ZonedDateTime.now());
    return super.create(company);
  }

  @Override
  @Transactional
  public Company update(Long id, Company company) {
    var existing = getById(id);
    company.setCreated(existing.getCreated());
    return repository.save(company);
  }
}
