package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.ProductDto;
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
    public List<ProductDto> listProducts() {
//        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
//                .toList();
        return null;
    }

    @Override
    public ProductDto getProductById(UUID productId) {
//        return productRepository.findById(productId).orElseThrow();
        return null;
    }

    @Override
    public ProductDto saveNewProduct(ProductDto product) {
//        return productRepository.save(product);
        return null;
    }
}
