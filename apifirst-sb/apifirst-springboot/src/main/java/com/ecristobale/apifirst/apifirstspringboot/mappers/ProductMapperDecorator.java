package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CategoryRepository;
import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class ProductMapperDecorator implements ProductMapper {

    @Autowired
    @Qualifier("delegate")
    private ProductMapper productMapperDelegate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        return productMapperDelegate.productDtoToProduct(productDto);
    }

    @Override
    public Product productDtoToProduct(ProductCreateDto productCreateDto) {
        if (productCreateDto != null) {
            Product product = productMapperDelegate.productDtoToProduct(productCreateDto);

            if (productCreateDto.getCategories() != null) {
                List<Category> categories = new ArrayList<>();

                productCreateDto.getCategories().forEach(categoryCode -> {
                    categoryRepository.findByCategoryCode(categoryCode).ifPresent(categories::add);
                });

                product.setCategories(categories);

                return product;
            }
        }

        return null;
    }

    @Override
    public ProductDto productToProductDto(Product product) {
        return productMapperDelegate.productToProductDto(product);
    }
}
