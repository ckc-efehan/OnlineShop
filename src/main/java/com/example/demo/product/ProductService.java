package com.example.demo.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
