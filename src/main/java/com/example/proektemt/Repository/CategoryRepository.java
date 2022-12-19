package com.example.proektemt.Repository;

import com.example.proektemt.Model.Category;
import com.example.proektemt.Model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Transactional
    Integer removeById(Long id);
}
