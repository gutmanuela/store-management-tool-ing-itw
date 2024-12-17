package com.ing.store_management.controller;

import com.ing.store_management.model.dto.SupplierDto;
import com.ing.store_management.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/product/{productCode}")
    public ResponseEntity<List<SupplierDto>> getAllSuppliersForProduct(@PathVariable String productCode) {
        return ResponseEntity.ok(supplierService.getAllSuppliersForProduct(productCode));
    }

    @PatchMapping
    public ResponseEntity<SupplierDto> linkSupplierToProduct(@RequestParam Long supplierId, @RequestParam Long productId) {
        supplierService.addSupplierForProduct(supplierId, productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<SupplierDto> addSupplier(@RequestBody SupplierDto supplierDto) {
        supplierService.addSupplier(supplierDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}