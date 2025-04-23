package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Transactional
    @DisplayName("Test Product Repository: Persistence of Image")
    @Test
    void testImagePersistence() {
        // add CascadeType ALL and FetchType.EAGER to images in product
        Product product = productRepository.findAll().iterator().next();

        assertNotNull(product);
        assertNotNull(product.getImages());
        assertTrue(product.getImages().size() > 0);
    }
}
