package com.example.proektemt.Service;

import com.example.proektemt.Model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    Category update(Long id, Category category);
    Integer deleteById(Long id);
}
