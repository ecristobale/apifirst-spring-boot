package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.model.Category;
import com.ecristobale.apifirst.model.Dimensions;
import com.ecristobale.apifirst.model.Image;
import com.ecristobale.apifirst.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProductControllerTest extends BaseTest {

    @DisplayName("Test Create Product")
    @Test
    void testCreateProduct() throws Exception {
        Product newProduct = Product.builder()
                .description("New Product")
                .cost("5.00")
                .price("8.95")
                .categories(Arrays.asList(Category.builder()
                        .category("New Category")
                        .description("New Category Description")
                        .build()))
                .images(Arrays.asList(Image.builder()
                        .url("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .dimensions(Dimensions.builder()
                        .length(10)
                        .width(10)
                        .height(10)
                        .build())
                .build();

        mockMvc.perform(post(ProductController.BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))) //convert to JSON
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @DisplayName("Test Get List Products")
    @Test
    void listProducts() throws Exception {
        mockMvc.perform(get(ProductController.BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @DisplayName("Test Get Product by ID")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(ProductController.BASE_PATH + "/{productId}", testProduct.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(testProduct.getId().toString())));
    }
}