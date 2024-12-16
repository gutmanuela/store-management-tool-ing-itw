package com.ing.store_management.service.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.store_management.controller.ProductController;
import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProductControllerIntegrationTest {

    public static final String CATEGORY_NAME = "Category 1";
    public static final String PRODUCT_NAME = "Product 1";
    public static final String PRODUCT_CODE = "P001";
    public static final double PRICE = 100.0;
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDto product1 = new ProductDto(PRODUCT_CODE, PRODUCT_NAME, PRICE, CATEGORY_NAME);
        ProductDto product2 = new ProductDto("P002", "Product 2", 150.0, "Category 2");
        List<ProductDto> products = Arrays.asList(product1, product2);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value(PRODUCT_CODE))
                .andExpect(jsonPath("$[1].code").value("P002"));
    }

    @Test
    public void testGetProductByCode() throws Exception {
        ProductDto product = new ProductDto(PRODUCT_CODE, PRODUCT_NAME, PRICE, CATEGORY_NAME);
        when(productService.getProductByCode(PRODUCT_CODE)).thenReturn(product);

        mockMvc.perform(get("/products/{code}", PRODUCT_CODE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(PRODUCT_CODE))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME));
    }

    @Test
    public void testGetProductStockByCode() throws Exception {
        when(productService.getStockForProduct(PRODUCT_CODE)).thenReturn(10);

        mockMvc.perform(get("/products/stock/{code}", PRODUCT_CODE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(10));
    }

    @Test
    public void testGetProductsWithPriceLessThan() throws Exception {
        ProductDto product1 = new ProductDto(PRODUCT_CODE, PRODUCT_NAME, PRICE, CATEGORY_NAME);
        List<ProductDto> products = Arrays.asList(product1);
        when(productService.findProductsWithPriceLessThen(PRICE)).thenReturn(products);

        mockMvc.perform(get("/products/price?price=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value(PRODUCT_CODE));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/{code}", PRODUCT_CODE))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProductByCode(PRODUCT_CODE);
    }
}
