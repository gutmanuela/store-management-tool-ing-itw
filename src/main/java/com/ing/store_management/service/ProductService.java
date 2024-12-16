package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.model.entity.Category;
import com.ing.store_management.model.entity.ChangeType;
import com.ing.store_management.model.entity.Product;
import com.ing.store_management.model.entity.StockManagement;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.StockManagementRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockManagementRepository stockManagementRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, StockManagementRepository stockManagementRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.stockManagementRepository = stockManagementRepository;
        this.categoryService = categoryService;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductByCode(String code) {
        return productRepository.findByCode(code).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product with code " + code + " was not found."));
    }
  public List<ProductDto> getProductsByCategory(String categoryName) {
      Category category=  categoryService.findByCategoryName(categoryName);
        return productRepository.getProductsByCategory(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
  }

    @Transactional
    public void addProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        StockManagement stockManagement = new StockManagement();
        stockManagement.setProduct(product);
        stockManagement.setReason("New product was added.");
        stockManagement.setDate(new Timestamp(System.currentTimeMillis()));
        stockManagement.setChangeQuantity(product.getStockQuantity());
        stockManagement.setChangeType(ChangeType.ADDED);
        productRepository.save(product);
        stockManagementRepository.save(stockManagement);
    }

    @Transactional
    public void deleteProductByCode(String code) {
        productRepository.deleteByCode(code);
    }

    public Integer getStockForProduct(String code) {
        Product product = productRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Product with code " + code + " was not found."));
        return product.getStockQuantity();
    }

    public List<ProductDto> findProductsWithPriceLessThen(double value) {
        return productRepository.getProductsByPriceIsLessThan(value).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product.getCode(), product.getPrice(), product.getName(), product.getStockQuantity());
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setCode(productDto.getCode());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setStockQuantity(productDto.getStockQuantity());
        return product;
    }
}
