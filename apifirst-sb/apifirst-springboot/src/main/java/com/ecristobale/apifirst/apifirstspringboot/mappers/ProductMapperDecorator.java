package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import com.ecristobale.apifirst.apifirstspringboot.domain.Image;
import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.apifirstspringboot.repositories.CategoryRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ImageRepository;
import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import com.ecristobale.apifirst.model.ProductUpdateDto;
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

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

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

    @Override
    public ProductUpdateDto productToProductUpdateDto(Product product) {
        if (product != null) {

            if (product.getCategories() != null) {
                List<String> categoryCodes = new ArrayList<>();

                product.getCategories().forEach(category -> {
                    categoryCodes.add(category.getCategoryCode());
                });

                ProductUpdateDto productUpdateDto = productMapperDelegate.productToProductUpdateDto(product);
                productUpdateDto.setCategories(categoryCodes);

                return productUpdateDto;
            }
        }
        return null;
    }

    @Override
    public Product productUpdateDtoToProduct(ProductUpdateDto productUpdateDto) {
        if (productUpdateDto != null) {
            Product product = productMapperDelegate.productUpdateDtoToProduct(productUpdateDto);

            if (productUpdateDto.getCategories() != null) {
                List<Category> categories = categoryCodesToCategories(productUpdateDto.getCategories());

                product.setCategories(categories);
            }

            if (productUpdateDto.getImages() != null) {
                product.setImages(new ArrayList<>());

                productUpdateDto.getImages().forEach(imageDto -> {
                    if (imageDto.getId() != null ) {
                        imageRepository.findById(imageDto.getId()).ifPresent(image -> {
                            Image existingImage = imageRepository.findById(imageDto.getId()).get();
                            imageMapper.updateImage(imageDto, existingImage);
                            product.getImages().add(existingImage);
                        });
                    }
                });
            }

            return product;
        }

        return null;
    }

    @Override
    public void updateProduct(ProductUpdateDto product, Product target) {
        productMapperDelegate.updateProduct(product, target);

        target.setImages(new ArrayList<>());

        if (product.getImages() != null) {
            product.getImages().forEach(imageDto -> {
                if (imageDto.getId() != null) {
                    imageRepository.findById(imageDto.getId()).ifPresent(image -> {
                        Image existingImage = imageRepository.findById(imageDto.getId()).get();
                        imageMapper.updateImage(imageDto, existingImage);
                        target.getImages().add(existingImage);
                    });
                }
            });
        }

        if (product.getCategories() != null) {
            List<Category> categories = categoryCodesToCategories(product.getCategories());
            target.setCategories(categories);
        }
    }

    //list of string to list of category
    private List<Category> categoryCodesToCategories(List<String> categoryCodes) {
        List<Category> categories = new ArrayList<>();

        categoryCodes.forEach(categoryCode -> {
            categoryRepository.findByCategoryCode(categoryCode).ifPresent(categories::add);
        });

        return categories;
    }
}
