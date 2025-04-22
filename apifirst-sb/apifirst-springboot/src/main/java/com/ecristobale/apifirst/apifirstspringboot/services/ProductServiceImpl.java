package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.mappers.ProductMapper;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.ProductCreateDto;
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
    private final ProductMapper productMapper;

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
    public ProductDto saveNewProduct(ProductCreateDto product) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(product)));
    }
}
