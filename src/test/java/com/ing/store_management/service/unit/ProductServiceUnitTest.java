package com.ing.store_management.service.unit;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.model.entity.Category;
import com.ing.store_management.model.entity.ChangeType;
import com.ing.store_management.model.entity.Product;
import com.ing.store_management.model.entity.StockManagement;
import com.ing.store_management.repository.CategoryRepository;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.StockManagementRepository;
import com.ing.store_management.service.CategoryService;
import com.ing.store_management.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {

    public static final String PRODUCT_CODE = "P001";
    public static final String CATEGORY_NAME = "Category1";
    public static final String PRODUCT_NAME = "Product1";
    public static final double PRICE = 10.0;
    public static final int STOCK_QUANTITY = 100;
    public static final String INVALID_CATEGORY_NAME = "InvalidCategory";
    public static final String PRODUCT_NAME_1 = "Laptop";
    public static final String PRODUCT_NAME_2 = "Smartphone";
    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockManagementRepository stockManagementRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService testee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product = getProduct();
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        product.setCategory(category);

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDto> products = testee.getAllProducts();

        assertEquals(1, products.size());
        assertEquals(PRODUCT_CODE, products.get(0).getCode());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductByCode() {
        Product product = getProduct();
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        product.setCategory(category);

        when(productRepository.findByCode(PRODUCT_CODE)).thenReturn(Optional.of(product));

        ProductDto productDto = testee.getProductByCode(PRODUCT_CODE);

        assertNotNull(productDto);
        assertEquals(PRODUCT_CODE, productDto.getCode());
        verify(productRepository, times(1)).findByCode(PRODUCT_CODE);
    }

    @Test
    void testGetProductByCodeThrowsException() {
        when(productRepository.findByCode(PRODUCT_CODE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> testee.getProductByCode(PRODUCT_CODE));
        verify(productRepository, times(1)).findByCode(PRODUCT_CODE);
    }

    @Test
    void testAddProduct() {
        ProductDto productDto = new ProductDto(PRODUCT_CODE, PRICE, PRODUCT_NAME, STOCK_QUANTITY, CATEGORY_NAME);
        Category category = new Category();
        category.setName(CATEGORY_NAME);

        when(categoryRepository.findByName(CATEGORY_NAME)).thenReturn(Optional.of(category));

        testee.addProduct(productDto);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        ArgumentCaptor<StockManagement> stockCaptor = ArgumentCaptor.forClass(StockManagement.class);

        verify(productRepository, times(1)).save(productCaptor.capture());
        verify(stockManagementRepository, times(1)).save(stockCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertEquals(PRODUCT_CODE, savedProduct.getCode());
        assertEquals(STOCK_QUANTITY, savedProduct.getStockQuantity());

        StockManagement savedStock = stockCaptor.getValue();
        assertEquals(ChangeType.ADDED, savedStock.getChangeType());
        assertEquals("New product was added.", savedStock.getReason());
    }

    @Test
    void testAddProductThrowsExceptionForInvalidCategory() {
        ProductDto productDto = new ProductDto(PRODUCT_CODE, PRICE, PRODUCT_NAME, STOCK_QUANTITY, INVALID_CATEGORY_NAME);

        when(categoryRepository.findByName(INVALID_CATEGORY_NAME)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> testee.addProduct(productDto));
        verify(categoryRepository, times(1)).findByName(INVALID_CATEGORY_NAME);
    }

    @Test
    void testDeleteProductByCode() {
        doNothing().when(productRepository).deleteByCode(PRODUCT_CODE);

        testee.deleteProductByCode(PRODUCT_CODE);

        verify(productRepository, times(1)).deleteByCode(PRODUCT_CODE);
    }

    @Test
    void testGetStockForProduct() {
        Product product = new Product();
        product.setCode(PRODUCT_CODE);
        product.setStockQuantity(STOCK_QUANTITY);

        when(productRepository.findByCode(PRODUCT_CODE)).thenReturn(Optional.of(product));

        int stock = testee.getStockForProduct(PRODUCT_CODE);

        assertEquals(STOCK_QUANTITY, stock);
        verify(productRepository, times(1)).findByCode(PRODUCT_CODE);
    }

    @Test
    void testGetStockForProductThrowsException() {
        when(productRepository.findByCode(PRODUCT_CODE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> testee.getStockForProduct(PRODUCT_CODE));
        verify(productRepository, times(1)).findByCode(PRODUCT_CODE);
    }

    @Test
    void testFindProductsWithPriceLessThan() {
        Product product = new Product();
        product.setCode(PRODUCT_CODE);
        product.setPrice(5.0);
        product.setName(PRODUCT_NAME);
        product.setStockQuantity(STOCK_QUANTITY);
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        product.setCategory(category);

        when(productRepository.getProductsByPriceIsLessThan(PRICE)).thenReturn(List.of(product));

        List<ProductDto> products = testee.findProductsWithPriceLessThen(PRICE);

        assertEquals(1, products.size());
        assertEquals(PRODUCT_CODE, products.get(0).getCode());
        verify(productRepository, times(1)).getProductsByPriceIsLessThan(PRICE);
    }

    @Test
    void getProductsByCategory_ShouldReturnProducts_WhenCategoryExists() {

        String categoryName = "Electronics";
        Category category = new Category();
        category.setName(categoryName);

        Product product1 = new Product(category,1L, PRODUCT_NAME_1);
        Product product2 = new Product(category,2L, PRODUCT_NAME_2);

        when(categoryService.findByCategoryName(categoryName)).thenReturn(category);
        when(productRepository.getProductsByCategory(category)).thenReturn(List.of(product1, product2));

        List<ProductDto> productDtos = testee.getProductsByCategory(categoryName);

        assertEquals(2, productDtos.size());
        assertEquals(PRODUCT_NAME_1, productDtos.get(0).getName());
        assertEquals(PRODUCT_NAME_2, productDtos.get(1).getName());

        verify(categoryService, times(1)).findByCategoryName(categoryName);
        verify(productRepository, times(1)).getProductsByCategory(category);
    }

    @Test
    void getProductsByCategory_ShouldReturnEmptyList_WhenNoProductsFound() {

        Category category = new Category();
        category.setName(CATEGORY_NAME);

        when(categoryService.findByCategoryName(CATEGORY_NAME)).thenReturn(category);
        when(productRepository.getProductsByCategory(category)).thenReturn(Collections.emptyList());

        List<ProductDto> productDtos = testee.getProductsByCategory(CATEGORY_NAME);

        assertTrue(productDtos.isEmpty());

        verify(categoryService, times(1)).findByCategoryName(CATEGORY_NAME);
        verify(productRepository, times(1)).getProductsByCategory(category);
    }

    @Test
    void getProductsByCategory_ShouldThrowException_WhenCategoryNotFound() {

        when(categoryService.findByCategoryName(INVALID_CATEGORY_NAME)).thenThrow(new RuntimeException("Category not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            testee.getProductsByCategory(INVALID_CATEGORY_NAME);
        });

        assertEquals("Category not found", exception.getMessage());

        verify(categoryService, times(1)).findByCategoryName(INVALID_CATEGORY_NAME);
        verifyNoInteractions(productRepository);
    }

    private static Product getProduct() {
        Product product = new Product();
        product.setCode(PRODUCT_CODE);
        product.setName(PRODUCT_NAME);
        product.setPrice(PRICE);
        product.setStockQuantity(STOCK_QUANTITY);
        return product;
    }
}

