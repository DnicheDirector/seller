package com.seller.sellersystem.category.services;

import com.seller.sellersystem.category.models.Category;
import com.seller.sellersystem.category.respositories.CategoryRepository;
import com.seller.sellersystem.services.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService<CategoryRepository, Category, Long> {

  public CategoryService(CategoryRepository repository) {
    super(repository);
  }

}
