package com.example.proektemt.Service.impl;

import com.example.proektemt.Model.Exceptions.ProductIsAlreadyInShoppingCartException;
import com.example.proektemt.Model.Exceptions.ProductNotFoundException;
import com.example.proektemt.Model.Manufacturer;
import com.example.proektemt.Model.Product;
import com.example.proektemt.Repository.ProductRepository;
import com.example.proektemt.Service.ManufacturerService;
import com.example.proektemt.Service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerService manufacturerService;

    public ProductServiceImpl(ProductRepository productRepository, ManufacturerService manufacturerService) {
        this.productRepository = productRepository;
        this.manufacturerService = manufacturerService;
    }


    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> findAllByManufacturerId(Long manufacturerId) {
        return this.productRepository.findAllByManufacturerId(manufacturerId);
    }



    @Override
    public List<Product> findAllSortedByPrice(boolean asc) {

        if(asc){
            return this.productRepository.findAllByOrderByPriceAsc();
        }
        return this.productRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

 /*  @Override
    public Product saveProduct(MultipartFile image,String name, Float price, Integer quantity, Long manufacturerId)throws IOException{
       Manufacturer manufacturer= this.manufacturerService.findById(manufacturerId);
       Product product = new Product(null,name, price, quantity, manufacturer);
       if(image != null && !image.getName().isEmpty()){
           byte[] bytes=image.getBytes();
           String base64Image= String.format("data:%s;base64,%s",image.getContentType(), Base64.getEncoder().encodeToString(bytes));
           product.setBase64image(base64Image);
       }
        return this.productRepository.save(product);
    }*/

  @Override
   public Product saveProduct(Product product, MultipartFile image) throws IOException{
        Manufacturer manufacturer = this.manufacturerService.findById(product.getManufacturer().getId());
        product.setManufacturer(manufacturer);
      if (image != null && !image.getName().isEmpty()) {
          byte[] bytes = image.getBytes();
          String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//            product.setImage(image);
          product.setBase64image(base64Image);
      }
        return this.productRepository.save(product);
   }

    @Override
    public Product updateProduct(Long id, Product product, MultipartFile image) throws IOException {
        Product p =this.findById(id);
        Manufacturer manufacturer=this.manufacturerService.findById(product.getManufacturer().getId());
        p.setManufacturer(manufacturer);
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());
       if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            product.setBase64image(base64Image);
        }
        return this.productRepository.save(p);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product= this.findById(id);
        if(product.getShoppingCarts().size() > 0){
            throw new ProductIsAlreadyInShoppingCartException(product.getName());
        }
        this.productRepository.deleteById(id);
    }
}
