package com.ing.store_management.controller;

import com.ing.store_management.model.dto.StockManagementDto;
import com.ing.store_management.service.StockManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockManagementController {
    private final StockManagementService stockManagementService;

    public StockManagementController(StockManagementService stockManagementService) {
        this.stockManagementService = stockManagementService;
    }

    @GetMapping
    public ResponseEntity<List<StockManagementDto>> getAllStockTransitions() {
        return ResponseEntity.ok(stockManagementService.getAllStockManagements());
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<List<StockManagementDto>> getAllStockTransitionsyProductCode(@PathVariable String productCode) {
        return ResponseEntity.ok(stockManagementService.getStockMovementsForProduct(productCode));
    }
}
