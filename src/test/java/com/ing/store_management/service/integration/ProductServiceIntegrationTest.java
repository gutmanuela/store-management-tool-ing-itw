package com.ing.store_management.service.integration;

import com.ing.store_management.StoreManagementApplication;
import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.model.entity.Category;
import com.ing.store_management.model.entity.Product;
import com.ing.store_management.repository.CategoryRepository;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.StockManagementRepository;
import com.ing.store_management.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = StoreManagementApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class ProductServiceIntegrationTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StockManagementRepository stockManagementRepository;

    @BeforeEach
    void setUp() {
        // Clean up and set up test data
        stockManagementRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Electronics category");
        categoryRepository.save(electronics);

        Product phone = new Product();
        phone.setCode("P001");
        phone.setName("Phone");
        phone.setPrice(699.99);
        phone.setStockQuantity(50);
        phone.setCategory(electronics);
        productRepository.save(phone);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<ProductDto> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Phone", products.get(0).getName());
    }

    @Test
    void getProductByCode_ShouldReturnCorrectProduct() {
        ProductDto product = productService.getProductByCode("P001");

        assertNotNull(product);
        assertEquals("Phone", product.getName());
        assertEquals(699.99, product.getPrice());
    }

    @Test
    void getProductByCode_ShouldThrowExceptionWhenProductNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductByCode("INVALID_CODE"));
    }

    @Test
    void getProductsByCategory_ShouldReturnProductsInCategory() {
        List<ProductDto> products = productService.getProductsByCategory("Electronics");

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Phone", products.get(0).getName());
    }

    @Test
    void addProduct_ShouldAddNewProduct() {
        ProductDto newProduct = new ProductDto("P002", 199.99, "Tablet", 30, "Electronics");
        productService.addProduct(newProduct);

        Optional<Product> savedProduct = productRepository.findByCode("P002");
        assertTrue(savedProduct.isPresent());
        assertEquals("Tablet", savedProduct.get().getName());
        assertEquals(199.99, savedProduct.get().getPrice());
    }

    @Test
    void addProduct_ShouldThrowExceptionWhenCategoryNotFound() {
        ProductDto newProduct = new ProductDto("P003", 299.99, "Laptop", 20, "NonExistingCategory");

        assertThrows(ResourceNotFoundException.class, () -> productService.addProduct(newProduct));
    }

    @Test
    void deleteProductByCode_ShouldDeleteProduct() {
        productService.deleteProductByCode("P001");

        Optional<Product> deletedProduct = productRepository.findByCode("P001");
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    void getStockForProduct_ShouldReturnCorrectStock() {
        int stock = productService.getStockForProduct("P001");

        assertEquals(50, stock);
    }

    @Test
    void getStockForProduct_ShouldThrowExceptionWhenProductNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> productService.getStockForProduct("INVALID_CODE"));
    }

    @Test
    void findProductsWithPriceLessThan_ShouldReturnMatchingProducts() {
        List<ProductDto> products = productService.findProductsWithPriceLessThen(700);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Phone", products.get(0).getName());
    }

    @Test
    void findProductsWithPriceLessThan_ShouldReturnEmptyListWhenNoMatches() {
        List<ProductDto> products = productService.findProductsWithPriceLessThen(100);

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }
}