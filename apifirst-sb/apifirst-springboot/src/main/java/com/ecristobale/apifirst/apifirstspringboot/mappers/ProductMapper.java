package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Product;
import com.ecristobale.apifirst.model.ProductCreateDto;
import com.ecristobale.apifirst.model.ProductDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Product productDtoToProduct(ProductCreateDto productCreateDto);

    ProductDto productToProductDto(Product product);
}
