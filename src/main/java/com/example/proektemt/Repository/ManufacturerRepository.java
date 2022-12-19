package com.example.proektemt.Repository;

import com.example.proektemt.Model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository <Manufacturer,Long> {
    @Transactional
    Integer removeById(Long id);

    List<Manufacturer> findAllByName(String name);
}
