package com.seller.companyservice.repositories;

import com.seller.companyservice.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
