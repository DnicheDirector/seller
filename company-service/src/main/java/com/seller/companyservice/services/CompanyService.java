package com.seller.companyservice.services;

import com.seller.companyservice.models.Company;
import com.seller.companyservice.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository repository;

  public Company getById(Long id) {
    return repository.findById(id).orElseThrow();
  }

  public List<Company> getAll() {
    return repository.findAll();
  }

  public Company create(Company company) {
    company.setCreated(ZonedDateTime.now());
    return repository.save(company);
  }

  @Transactional
  public Company update(Long id, Company company) {
    var existing = getById(id);
    company.setCreated(existing.getCreated());
    return repository.save(company);
  }
}
