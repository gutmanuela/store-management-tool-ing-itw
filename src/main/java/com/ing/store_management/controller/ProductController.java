package com.ing.store_management.controller;

import com.ing.store_management.model.dto.ProductDto;
import com.ing.store_management.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDto> getProductByCode(@PathVariable String code) {
        return ResponseEntity.ok(productService.getProductByCode(code));
    }

    @GetMapping("stock/{code}")
    public ResponseEntity<Integer> getProductStockByCode(@PathVariable String code) {
        return ResponseEntity.ok(productService.getStockForProduct(code));
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductDto>> getProductsWithPriceLessThen(@RequestParam Integer price) {
        return ResponseEntity.ok(productService.findProductsWithPriceLessThen(Double.valueOf(price)));
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryName));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
        productService.deleteProductByCode(code);
        return ResponseEntity.noContent().build();
    }
}
