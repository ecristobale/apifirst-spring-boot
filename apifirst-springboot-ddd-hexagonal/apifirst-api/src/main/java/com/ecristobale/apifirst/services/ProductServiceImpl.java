package com.ecristobale.apifirst.services;

import com.ecristobale.apifirst.model.Product;
import com.ecristobale.apifirst.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Override
    public Product saveNewProduct(Product product) {
        return productRepository.save(product);
    }
}
