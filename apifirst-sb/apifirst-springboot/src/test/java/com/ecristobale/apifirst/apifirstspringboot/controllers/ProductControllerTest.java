package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProductControllerTest extends BaseTest {

    @DisplayName("Product: Create")
    @Test
    void testCreateProduct() throws Exception {
        ProductCreateDto newProduct = buildTestProductDto();

        mockMvc.perform(post(ProductController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))) //convert to JSON
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Transactional
    @DisplayName("Product: Update")
    @Test
    void testUpdateProduct() throws Exception {

        Product product = productRepository.findAll().iterator().next();

        ProductUpdateDto productUpdateDto = productMapper.productToProductUpdateDto(product);

        productUpdateDto.setDescription("Updated Description");

        mockMvc.perform(put(ProductController.BASE_PATH + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo("Updated Description")));
    }

    @Transactional
    @DisplayName("Product: Update Not Found")
    @Test
    void testUpdateProductNotFound() throws Exception {

        Product product = productRepository.findAll().iterator().next();

        ProductUpdateDto productUpdateDto = productMapper.productToProductUpdateDto(product);

        productUpdateDto.setDescription("Updated Description");

        mockMvc.perform(put(ProductController.BASE_PATH + "/{productId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Product: Get List")
    @Test
    void listProducts() throws Exception {
        mockMvc.perform(get(ProductController.BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Product: Get by ID")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(ProductController.BASE_PATH + "/{productId}", testProduct.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(testProduct.getId().toString())));
    }

    @DisplayName("Product: Get by ID Not Found")
    @Test
    void getProductByIdNotFound() throws Exception {
        mockMvc.perform(get(ProductController.BASE_PATH + "/{productId}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @DisplayName("Product: Patch")
    @Test
    void testPatchProduct() throws Exception {

        Product product = productRepository.findAll().iterator().next();

        ProductPatchDto productPatchDto = productMapper.productToProductPatchDto(product);

        productPatchDto.setDescription("PATCH Updated Description");

        mockMvc.perform(patch(ProductController.BASE_PATH + "/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productPatchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", equalTo("PATCH Updated Description")));
    }

    @Transactional
    @DisplayName("Product: Patch Not Found")
    @Test
    void testPatchProductNotFound() throws Exception {

        Product product = productRepository.findAll().iterator().next();

        ProductPatchDto productPatchDto = productMapper.productToProductPatchDto(product);

        productPatchDto.setDescription("PATCH Updated Description");

        mockMvc.perform(patch(ProductController.BASE_PATH + "/{productId}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productPatchDto)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Product: Delete")
    @Test
    void testDeleteProduct() throws Exception {
        ProductCreateDto product = buildTestProductDto();
        Product savedProduct = productRepository.save(productMapper.productDtoToProduct(product));

        mockMvc.perform(delete(ProductController.BASE_PATH + "/{productId}", savedProduct.getId()))
                .andExpect(status().isNoContent());

        assert productRepository.findById(savedProduct.getId()).isEmpty();
    }

    @Test
    @DisplayName("Product: Delete Not Found")
    void testDeleteProductNotFound() throws Exception {
        mockMvc.perform(delete(ProductController.BASE_PATH + "/{productId}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    private ProductCreateDto buildTestProductDto() {
        return ProductCreateDto.builder()
                .description("New Product")
                .cost("5.00")
                .price("8.95")
                .categories(Arrays.asList("ELECTRONICS"))
                .images(Arrays.asList(ImageDto.builder()
                        .url("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .dimensions(DimensionsDto.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();
    }
}