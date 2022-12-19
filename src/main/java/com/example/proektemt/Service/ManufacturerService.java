package com.example.proektemt.Service;

import com.example.proektemt.Model.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    List<Manufacturer> findAllByName(String name);
    Manufacturer findById(Long id);
    Manufacturer save(Manufacturer manufacturer);
    Manufacturer update(Long id, Manufacturer manufacturer);
    Manufacturer updateName(Long id, String name);
    Integer deleteById(Long id);



}
