package com.ecristobale.apifirst.services;

import com.ecristobale.apifirst.model.Product;

import java.util.List;
import java.util.UUID;


public interface ProductService {
    List<Product> listProducts();

    Product getProductById(UUID productId);

    Product saveNewProduct(Product product);
}
