package com.company.seller.category.services;

import com.company.seller.category.models.Category;
import com.company.seller.category.respositories.CategoryRepository;
import com.company.seller.services.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService<CategoryRepository, Category, Long> {

  public CategoryService(CategoryRepository repository) {
    super(repository);
  }

}
