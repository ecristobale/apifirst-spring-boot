package com.ecristobale.apifirst.apifirstspringboot.services;

import com.ecristobale.apifirst.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();
}
