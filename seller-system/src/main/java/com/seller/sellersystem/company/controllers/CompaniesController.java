package com.seller.sellersystem.company.controllers;

import com.seller.sellersystem.company.client.CompanyClient;
import com.seller.sellersystem.company.views.CompanyRequest;
import com.seller.sellersystem.company.views.CompanyResponse;
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

  private final CompanyClient companyClient;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyResponse createCompany(
      @RequestBody @Valid CompanyRequest dto
  ) {
    return companyClient.createCompany(dto);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse getCompany(@PathVariable Long id) {
    return companyClient.getById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CompanyResponse> getAll() {
    return companyClient.getAll();
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CompanyResponse updateCompany(
      @PathVariable Long id, @RequestBody @Valid CompanyRequest dto
  ) {
    return companyClient.updateCompany(id, dto);
  }

}
