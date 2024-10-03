package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:1573"})
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ProductEntity addProduct(@RequestBody ProductEntity product) {
        return productService.save(product);
    }

    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    public Optional<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody ProductEntity product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
