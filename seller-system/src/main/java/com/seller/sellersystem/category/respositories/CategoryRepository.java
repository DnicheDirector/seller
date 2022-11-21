package com.seller.sellersystem.category.respositories;

import com.seller.sellersystem.category.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { }
