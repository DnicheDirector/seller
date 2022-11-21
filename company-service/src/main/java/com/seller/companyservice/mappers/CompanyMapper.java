package com.seller.companyservice.mappers;

import com.seller.companyservice.models.Company;
import com.seller.companyservice.views.CompanyRequest;
import com.seller.companyservice.views.CompanyResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
  Company fromCreateDto(CompanyRequest dto);
  Company fromUpdateDto(CompanyRequest dto, Long id);

  CompanyResponse toDto(Company company);
  List<CompanyResponse> toDto(List<Company> company);
}
