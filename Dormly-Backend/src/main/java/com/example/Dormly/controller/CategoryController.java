package com.example.Dormly.controller;


import com.example.Dormly.dto.CategoryDto;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Category;
import com.example.Dormly.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/Dormly/Categories")
public class CategoryController {


    private final CategoryService categoryService;

    /**
     * users are able to filter listings based on our set categories
     * @param name - deserialized the value from the query param to our string name -represents category
     * @return ListingDTO - hide internals and return a collection of listings matching the category
     */
    @GetMapping(path = "filter")
    public ResponseEntity<List<ListingDtoResponse>> getAllCategoriesByName(@RequestParam("Category") String name) {

        System.out.println("--------------------------");
        log.info(name);
        List<ListingDtoResponse> listings = categoryService.findCategoriesByName(URLDecoder.decode(name));
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }


}
