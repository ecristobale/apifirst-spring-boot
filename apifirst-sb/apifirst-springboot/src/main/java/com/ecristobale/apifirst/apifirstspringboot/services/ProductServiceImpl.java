package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.apifirstspringboot.mappers.ProductMapper;
import com.ecristobale.apifirst.apifirstspringboot.repositories.OrderRepository;
import com.ecristobale.apifirst.apifirstspringboot.repositories.ProductRepository;
import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import com.ecristobale.apifirst.model.ProductPatchDto;
import com.ecristobale.apifirst.model.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<ProductDto> listProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(productMapper::productToProductDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(UUID productId) {
        return productMapper.productToProductDto(productRepository.findById(productId).orElseThrow(NotFoundException::new));
    }

    @Override
    public ProductDto saveNewProduct(ProductCreateDto product) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(product)));
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductUpdateDto product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        productMapper.updateProduct(product, existingProduct);

        return productMapper.productToProductDto(productRepository.save(existingProduct));
    }

    @Override
    public ProductDto patchProduct(UUID productId, ProductPatchDto product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        productMapper.patchProduct(product, existingProduct);

        return productMapper.productToProductDto(productRepository.save(existingProduct));
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.findById(productId).ifPresentOrElse(product -> {
            if (!orderRepository.findAllByOrderLines_Product(product).isEmpty()) {
                throw new ConflictException("Product is used in orders");
            }
            productRepository.deleteById(productId);
        }, () -> {
            throw new NotFoundException("Product Not Found");
        });
    }
}
