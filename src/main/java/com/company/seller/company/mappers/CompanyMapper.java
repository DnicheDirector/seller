package com.company.seller.company.mappers;

import com.company.seller.company.models.Company;
import com.company.seller.company.views.CompanyRequest;
import com.company.seller.company.views.CompanyResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
  Company fromCreateDto(CompanyRequest dto);
  Company fromUpdateDto(CompanyRequest dto, Long id);

  CompanyResponse toDto(Company company);
  List<CompanyResponse> toDto(List<Company> company);
}
