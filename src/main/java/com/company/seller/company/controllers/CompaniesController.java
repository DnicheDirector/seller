package com.company.seller.company.controllers;

import com.company.seller.company.mappers.CompanyMapper;
import com.company.seller.company.services.CompanyService;
import com.company.seller.company.views.CompanyInputViewModel;
import com.company.seller.company.views.CompanyOutputViewModel;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompaniesController {

  private final CompanyService companyService;
  private final CompanyMapper companyMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyOutputViewModel createCompany(
      @RequestBody @Valid CompanyInputViewModel dto
  ) {
    var company = companyMapper.fromCreateDto(dto);
    return companyMapper.toDto(
        companyService.create(company)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyOutputViewModel getCompany(@PathVariable Long id) {
    return companyMapper.toDto(
        companyService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CompanyOutputViewModel> getAll() {
    return companyMapper.toDto(
        companyService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyOutputViewModel updateCompany(
      @PathVariable Long id, @RequestBody @Valid CompanyInputViewModel dto
  ) {
    var company = companyMapper.fromUpdateDto(dto, id);
    return companyMapper.toDto(
        companyService.update(id, company)
    );
  }

}
