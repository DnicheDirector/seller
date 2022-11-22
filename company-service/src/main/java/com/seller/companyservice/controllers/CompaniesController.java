package com.seller.companyservice.controllers;

import com.seller.companyservice.mappers.CompanyMapper;
import com.seller.companyservice.services.CompanyService;
import com.seller.companyservice.views.CompanyRequest;
import com.seller.companyservice.views.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompaniesController {

  private final CompanyService companyService;
  private final CompanyMapper companyMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyResponse createCompany(
      @RequestBody @Valid CompanyRequest dto
  ) {
    var company = companyMapper.fromCreateDto(dto);
    return companyMapper.toDto(
        companyService.create(company)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse getCompany(@PathVariable Long id) {
    return companyMapper.toDto(
        companyService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CompanyResponse> getAll() {
    return companyMapper.toDto(
        companyService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse updateCompany(
      @PathVariable Long id, @RequestBody @Valid CompanyRequest dto
  ) {
    var company = companyMapper.fromUpdateDto(dto, id);
    return companyMapper.toDto(
        companyService.update(id, company)
    );
  }

}
