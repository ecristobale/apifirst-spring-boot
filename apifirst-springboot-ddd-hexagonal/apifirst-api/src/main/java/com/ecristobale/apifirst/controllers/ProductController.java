package com.ecristobale.apifirst.controllers;

import com.ecristobale.apifirst.model.Product;
import com.ecristobale.apifirst.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.ecristobale.apifirst.controllers.ProductController.BASE_PATH;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
public class ProductController {
    public static final String BASE_PATH = "/api/v1/products";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts(){
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveNewProduct(product);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_PATH + "/{productId}" )
                .buildAndExpand(savedProduct.getId());

        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }
}
