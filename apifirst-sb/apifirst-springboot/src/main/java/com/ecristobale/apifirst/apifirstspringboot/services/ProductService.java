package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.ProductDto;

import java.util.List;
import java.util.UUID;


public interface ProductService {
    List<ProductDto> listProducts();

    ProductDto getProductById(UUID productId);

    ProductDto saveNewProduct(ProductDto product);
}
