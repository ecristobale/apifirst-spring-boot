package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import com.ecristobale.apifirst.apifirstspringboot.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.BASE_PATH)
public class ProductController {
    public static final String BASE_PATH = "/api/v1/products";

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> listProducts(){
        return ResponseEntity.ok(productService.listProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewProduct(@RequestBody ProductCreateDto product) {
        ProductDto savedProduct = productService.saveNewProduct(product);

        UriComponents uriComponents = UriComponentsBuilder.fromPath(BASE_PATH + "/{productId}" )
                .buildAndExpand(savedProduct.getId());

        return ResponseEntity.created(URI.create(uriComponents.getPath())).build();
    }
}
