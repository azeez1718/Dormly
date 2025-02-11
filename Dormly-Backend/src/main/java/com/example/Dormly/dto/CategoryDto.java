package com.example.Dormly.dto;

import com.example.Dormly.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

    private String categoryName;


    public static CategoryDto fromCategory(Category category){
        return CategoryDto.builder()
                .categoryName(category.getName())
                .build();
    }
}
