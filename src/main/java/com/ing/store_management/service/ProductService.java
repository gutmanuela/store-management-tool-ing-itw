package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.model.entity.*;
import com.ing.store_management.repository.CategoryRepository;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.StockManagementRepository;
import com.ing.store_management.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public static final String NEW_PRODUCT_WAS_ADDED_RESON = "New product was added.";
    private final ProductRepository productRepository;
    private final StockManagementRepository stockManagementRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository   supplierRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository, StockManagementRepository stockManagementRepository, CategoryService categoryService, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.stockManagementRepository = stockManagementRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
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
        Category category = categoryService.findByCategoryName(categoryName);
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
        stockManagement.setReason(NEW_PRODUCT_WAS_ADDED_RESON);
        stockManagement.setDate(new Timestamp(System.currentTimeMillis()));
        stockManagement.setChangeQuantity(product.getStockQuantity());
        stockManagement.setChangeType(ChangeType.ADDED);
        Category category = categoryRepository.findByName(productDto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category with name " + productDto.getCategoryName() + " was not found."));
        logger.debug("Category was found: " + category.toString());
        product.setCategory(category);
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId()).orElseThrow(() -> new ResourceNotFoundException("Supplier with id " + productDto.getSupplierId() + " was not found."));
        product.getSuppliers().add(supplier);
        productRepository.save(product);
        logger.debug("Product was saved: " + product.toString());
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
        return new ProductDto(product.getCode(), product.getPrice(), product.getName(), product.getStockQuantity(), product.getCategory().getName());
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setCode(productDto.getCode());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setSuppliers(new ArrayList<>());

        return product;
    }
}
