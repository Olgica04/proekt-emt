/*package com.example.proektemt.Repository.impl;

import com.example.proektemt.Model.Manufacturer;
import com.example.proektemt.Repository.ManufacturerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("in-memory")
public class ManufacturerRepositoryInMemoryImpl implements ManufacturerRepository {
        
    private HashMap<Long, Manufacturer> manufacturers;
    private AtomicLong counter;

    public ManufacturerRepositoryInMemoryImpl() {
        this.manufacturers = new HashMap<>();
        this.counter = new AtomicLong(0);
    }
    
    @Override
    public List<Manufacturer> findAll(){
        return new ArrayList<>(this.manufacturers.values());
    } 
    
    @Override
    public List<Manufacturer> findAll(Sort sort){return null;}
    
    @Override
    public Page<Manufacturer> findAll(Pageable pageable){return null;}
    
    @Override
    public List<Manufacturer> findAllById(Iterable<Long> iterable){return null;}
    
    @Override
    public long count(){return 0;}
    
    @Override
    public <S extends Manufacturer> List<S> saveAll(Iterable <S> iterable){return null;}

    @Override
    public void flush(){}

    @Override
    public <S extends Manufacturer> S saveAndFlush(S s) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<Manufacturer> iterable) {
        
    }
    

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Manufacturer getOne(Long aLong) {
        return null;
    }

    @Override
    public <S extends Manufacturer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Manufacturer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Manufacturer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Manufacturer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }
    

    @Override
    public Optional<Manufacturer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public <S extends Manufacturer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Manufacturer> boolean exists(Example<S> example) {
      return false;
    }

    @Override
    public <S extends Manufacturer> S save(S s) {
       if(manufacturers.get()==null){
           manufacturers.setId(this.counter.getAndIncrement());
       }
       this.manufacturers.put(manufacturers.get(),manufacturers);
        return manufacturers;
    }

    @Override
    public void deleteById(Long id) {
        this.manufacturers.remove(id);
    }

    @Override
    public void delete(Manufacturer manufacturer) {

    }

    @Override
    public void deleteAll(Iterable<? extends Manufacturer> iterable) {

    }

    @Override
    public void deleteAll() {

    }

  @Override
    public Integer removeById(Long id) {
        return null;
    }

    @Override
    public List<Manufacturer> findAllByName(String name) {
        return null;
    }
}*/

