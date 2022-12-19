package com.example.proektemt.Service.impl;

import com.example.proektemt.Model.Category;
import com.example.proektemt.Model.Exceptions.CategoryNotFoundException;
import com.example.proektemt.Repository.CategoryRepository;
import com.example.proektemt.Repository.ProductRepository;
import com.example.proektemt.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category updatedCategory = this.findById(id);
        updatedCategory.setName(category.getName());
        return this.categoryRepository.save(updatedCategory);
    }

    @Override
    public Integer deleteById(Long id) {
        return this.categoryRepository.removeById(id);
    }
}
