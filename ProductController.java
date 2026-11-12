package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repo;
    public ProductController(ProductRepository repo){this.repo=repo;}

    @GetMapping public List<Product> all(){return repo.findAll();}
    @GetMapping("/{id}") public Product one(@PathVariable Long id){
        return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @PostMapping public Product create(@Valid @RequestBody Product p){return repo.save(p);}
    @PutMapping("/{id}") public Product update(@PathVariable Long id,@Valid @RequestBody Product p){
        Product old=one(id); old.setName(p.getName()); old.setDescription(p.getDescription());
        old.setPrice(p.getPrice()); old.setStock(p.getStock()); return repo.save(old);
    }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.delete(one(id));}
}
