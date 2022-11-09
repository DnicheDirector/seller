package com.company.seller.company.services;

import com.company.seller.company.models.Company;
import com.company.seller.company.repositories.CompanyRepository;
import com.company.seller.services.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends AbstractCrudService<CompanyRepository, Company, Long> {

  public CompanyService(CompanyRepository repository) {
    super(repository);
  }
}
