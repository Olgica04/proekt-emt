package com.example.proektemt.Service;

import com.example.proektemt.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    List <Product> findAllByManufacturerId(Long manufacturerId);
    List<Product> findAllSortedByPrice(boolean asc);
    Product findById(Long id);
 //   Product saveProduct(MultipartFile image, String name, Float price, Integer quantity, Long manufacturerId)throws IOException;


    Product saveProduct(Product product, MultipartFile image) throws IOException;

    Product updateProduct(Long id, Product product, MultipartFile image) throws IOException;
    void deleteById(Long id);


}
