package com.example.Dormly.controller;


import com.example.Dormly.dto.CategoryDto;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Category;
import com.example.Dormly.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/Dormly/Categories")
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping(path = "filter")
    public ResponseEntity<List<ListingDtoResponse>> getAllCategoriesByName(@RequestParam("categoryName") String name) {
        List<ListingDtoResponse> listings = categoryService.findCategoriesByName(name);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }


}
