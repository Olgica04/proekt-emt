package com.example.proektemt.Service.impl;


import com.example.proektemt.Model.Exceptions.ManufacturerNotFoundException;
import com.example.proektemt.Model.Exceptions.ThereAreProductsWithThisManufacturer;
import com.example.proektemt.Model.Manufacturer;
import com.example.proektemt.Repository.ManufacturerRepository;
import com.example.proektemt.Repository.ProductRepository;
import com.example.proektemt.Service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ProductRepository productRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

   @Override
   public List<Manufacturer> findAllByName(String name){
        return this.manufacturerRepository.findAllByName(name);
        //return null;
   }

    @Override
    public Manufacturer findById(Long id) {
        return this.manufacturerRepository.findById(id)
                .orElseThrow(() -> new ManufacturerNotFoundException(id));
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return this.manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Long id, Manufacturer manufacturer) {
        Manufacturer updatedManufacturer = this.findById(id);
        updatedManufacturer.setName(manufacturer.getName());
        return this.manufacturerRepository.save(updatedManufacturer);
    }

    @Override
    public Manufacturer updateName(Long id, String name){
        Manufacturer m=this.findById(id);
        m.setName(name);
        return this.manufacturerRepository.save(m);
    }

    @Override
    public Integer deleteById(Long id) {
        Manufacturer manufacturer = this.findById(id);
        if(this.productRepository.existsByManufacturerId(id))
        {
            throw new ThereAreProductsWithThisManufacturer();
        }
        return this.manufacturerRepository.removeById(id);
    }
    //74:20 pregledaj
}
