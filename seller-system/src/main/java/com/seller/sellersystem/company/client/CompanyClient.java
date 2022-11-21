package com.seller.sellersystem.company.client;

import com.seller.sellersystem.company.views.CompanyRequest;
import com.seller.sellersystem.company.views.CompanyResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "company-service")
@CacheConfig(cacheNames = "companies")
public interface CompanyClient {

    @GetMapping("api/companies/{id}")
    @Cacheable
    CompanyResponse getById(@PathVariable Long id);

    @GetMapping("api/companies")
    @Cacheable(key = "'all'")
    List<CompanyResponse> getAll();

    @PostMapping("api/companies")
    @CacheEvict(key = "'all'")
    CompanyResponse createCompany(CompanyRequest dto);

    @PutMapping("api/companies/{id}")
    @CachePut(key = "#id")
    CompanyResponse updateCompany(@PathVariable Long id, CompanyRequest dto);
}
