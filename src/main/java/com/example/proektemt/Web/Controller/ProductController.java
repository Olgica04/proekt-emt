package com.example.proektemt.Web.Controller;

import com.example.proektemt.Model.Category;
import com.example.proektemt.Model.Exceptions.ProductIsAlreadyInShoppingCartException;
import com.example.proektemt.Model.Manufacturer;
import com.example.proektemt.Model.Product;
import com.example.proektemt.Repository.CategoryRepository;
import com.example.proektemt.Service.ManufacturerService;
import com.example.proektemt.Service.ProductService;
import org.dom4j.rule.Mode;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ManufacturerService manufacturerService;
    private CategoryRepository categoryRepository;


    public ProductController(ProductService productService, ManufacturerService manufacturerService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getProductPage(Model model){
        List<Product> products=this.productService.findAll();
        model.addAttribute("products",products);
        return "products";
    }

  //  @GetMapping("/add-new")
   //     public String addNewProductPage(Model model){
    //    List<Product> products= this.productService.findAll();
     //   model.addAttribute("products",products);
     // return "add-product";
   // }

 // @RequestMapping(method = RequestMethod.POST, value = "/add-new")
  @RequestMapping("/add-new")
    public String addNewProductPage(Model model){
        List<Manufacturer> manufacturers=this.manufacturerService.findAll();
        model.addAttribute("manufacturers",manufacturers);
        model.addAttribute("category", this.categoryRepository.findAll());
        model.addAttribute("product",new Product());
     // this.productService.saveProduct();
        return "add-product";
    }

    @RequestMapping("/{id}/edit")
    public String edit( @PathVariable Long id,Model model) {

        try {
            Product product = this.productService.findById(id);
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("product", product);
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("category", this.categoryRepository.findAll());
            return "add-product";
        }
        catch (RuntimeException ex) {
            return "redirect:/products?error=" + ex.getMessage();
        }
    }

    @PostMapping("/addproduct")
  //  @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
   // @Secured("ROLE_ADMIN")
    public String saveProduct(
            //@RequestParam String name,
//                              @RequestParam Float price,
//                              @RequestParam Integer quantity,
//                              @RequestParam Long manufacturerId,
                              @Valid Product product,
                              BindingResult bindingResult,
                              @RequestParam MultipartFile image,
                              Model model){
        if (bindingResult.hasErrors()) {
            List<Manufacturer> manufacturers = this.manufacturerService.findAll();
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("category", this.categoryRepository.findAll());
            return "add-product";
        }
        try {
            this.productService.saveProduct(product, image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProductWithPost(@PathVariable Long id) {
        try {
            this.productService.deleteById(id);
        } catch (ProductIsAlreadyInShoppingCartException ex) {
            return String.format("redirect:/products?error=%s", ex.getMessage());
        }
        return "redirect:/products";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProductWithDelete(@PathVariable Long id){
        this.productService.deleteById(id);
        return "redirect:/products";
    }
}
