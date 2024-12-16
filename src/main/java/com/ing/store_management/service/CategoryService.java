package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.CategoryDto;
import com.ing.store_management.model.entity.Category;
import com.ing.store_management.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategorys() {
        return categoryRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void addCategory(CategoryDto categoryDto) {
        categoryRepository.save(convertToEntity(categoryDto));
    }

    @Transactional
    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    private CategoryDto convertToDto(Category category) {
        return new CategoryDto(category.getName(), category.getDescription());
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name " + categoryName + " was not found."));
    }
}
