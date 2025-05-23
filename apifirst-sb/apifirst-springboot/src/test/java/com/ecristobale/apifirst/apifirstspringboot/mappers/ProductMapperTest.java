package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CategoryRepository;
import com.ecristobale.apifirst.model.DimensionsDto;
import com.ecristobale.apifirst.model.ImageDto;
import com.ecristobale.apifirst.model.ProductCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("Mapper: Product")
    @Test
    void productDtoToProduct() {

        //fail if no category found
        Category category = categoryRepository.findByCategoryCode("ELECTRONICS").orElseThrow();
        var productCreateDto = buildProductCreateDto(category.getCategoryCode());

        var product = productMapper.productDtoToProduct(productCreateDto);

        assertNotNull(product);
        assertEquals(productCreateDto.getDescription(), product.getDescription());
        assertEquals(productCreateDto.getCost(), product.getCost());
        assertEquals(productCreateDto.getPrice(), product.getPrice());
        assertEquals(productCreateDto.getDimensions().getHeight(), product.getDimensions().getHeight());
        assertEquals(productCreateDto.getDimensions().getWidth(), product.getDimensions().getWidth());
        assertEquals(productCreateDto.getDimensions().getLength(), product.getDimensions().getLength());
        assertEquals(productCreateDto.getImages().get(0).getUrl(), product.getImages().get(0).getUrl());
        assertEquals(productCreateDto.getImages().get(0).getAltText(), product.getImages().get(0).getAltText());
        assertEquals(productCreateDto.getCategories().get(0), product.getCategories().get(0).getCategoryCode());

        //test to catch changes, fail test if fields are added
        assertEquals(9, product.getClass().getDeclaredFields().length);
    }

    ProductCreateDto buildProductCreateDto(String cat) {
        return ProductCreateDto.builder()
                .price("1.0")
                .description("description")
                .images(Arrays.asList(ImageDto.builder()
                        .url("http://example.com/image.jpg")
                        .altText("Image Alt Text")
                        .build()))
                .categories(Arrays.asList(cat))
                .cost("1.0")
                .dimensions(DimensionsDto.builder()
                        .height(1)
                        .length(1)
                        .width(1)
                        .build())
                .build();
    }
}
