package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import com.ecristobale.apifirst.model.ProductPatchDto;
import com.ecristobale.apifirst.model.ProductUpdateDto;

import java.util.List;
import java.util.UUID;


public interface ProductService {
    List<ProductDto> listProducts();

    ProductDto getProductById(UUID productId);

    ProductDto saveNewProduct(ProductCreateDto product);

    ProductDto updateProduct(UUID productId, ProductUpdateDto product);

    ProductDto patchProduct(UUID productId, ProductPatchDto product);
}
