package com.ecristobale.apifirst.apifirstspringboot.controllers;

import com.ecristobale.apifirst.apifirstspringboot.services.CategoryService;
import com.ecristobale.apifirst.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(CategoryController.BASE_PATH)
public class CategoryController {

    public static final String BASE_PATH = "/api/v1/categories";

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCustomers() {
        return ResponseEntity.ok(categoryService.listCategories());
    }
}
