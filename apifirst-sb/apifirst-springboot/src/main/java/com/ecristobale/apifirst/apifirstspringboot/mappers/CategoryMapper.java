package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Category;
import com.ecristobale.apifirst.model.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category category);

    @Mapping(target = "products", ignore = true)
    Category categoryDtoToCategory(CategoryDto categoryDto);
}
