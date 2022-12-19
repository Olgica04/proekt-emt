package com.example.proektemt.Web.RestControllers;

import com.example.proektemt.Model.Category;
import com.example.proektemt.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;


    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll()
    {
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id)
    {
        return this.categoryService.findById(id);
    }

    @PostMapping
    public Category save(Category category)
    {
        return this.categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, Category category)
    {
        return this.categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable Long id)
    {
        return this.categoryService.deleteById(id);
    }

}
