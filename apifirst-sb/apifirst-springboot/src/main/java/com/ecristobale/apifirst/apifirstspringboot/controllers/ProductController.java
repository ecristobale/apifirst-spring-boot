package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import com.ecristobale.apifirst.apifirstspringboot.services.ProductService;
import com.ecristobale.apifirst.model.ProductPatchDto;
import com.ecristobale.apifirst.model.ProductUpdateDto;
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

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") UUID productId,
                                                    @RequestBody ProductUpdateDto product){
        ProductDto savedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> patchProduct(@PathVariable("productId") UUID productId,
                                                   @RequestBody ProductPatchDto product){
        ProductDto savedProduct = productService.patchProduct(productId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
