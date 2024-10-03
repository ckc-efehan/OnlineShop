package com.example.demo.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> update(Long Id, ProductEntity productEntity) {
        return productRepository.findAllByProductId(Id)
                .map(productEntity1 -> {
                    productEntity1.setProductName(productEntity.getProductName());
                    productEntity1.setProductPrice(productEntity.getProductPrice());
                    productEntity1.setProductDescription(productEntity.getProductDescription());
                    return productRepository.save(productEntity);
                });
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}