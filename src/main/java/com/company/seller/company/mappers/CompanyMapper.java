package com.company.seller.company.mappers;

import com.company.seller.company.models.Company;
import com.company.seller.company.views.CompanyInputViewModel;
import com.company.seller.company.views.CompanyOutputViewModel;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
  Company fromCreateDto(CompanyInputViewModel dto);
  Company fromUpdateDto(CompanyInputViewModel dto, Long id);

  CompanyOutputViewModel toDto(Company company);
  List<CompanyOutputViewModel> toDto(List<Company> company);
}
