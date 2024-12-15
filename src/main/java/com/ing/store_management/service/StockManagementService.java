package com.ing.store_management.service;

import com.ing.store_management.exception.ResourceNotFoundException;
import com.ing.store_management.model.dto.StockManagementDto;
import com.ing.store_management.model.entity.Product;
import com.ing.store_management.model.entity.StockManagement;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.StockManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockManagementService {

    private final StockManagementRepository stockManagementRepository;
    private final ProductRepository productRepository;

    public StockManagementService(StockManagementRepository stockManagementRepository, ProductRepository productRepository) {
        this.stockManagementRepository = stockManagementRepository;
        this.productRepository = productRepository;
    }


    public List<StockManagementDto> getAllStockManagements() {
        return stockManagementRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<StockManagementDto> getStockMovementsForProduct(String code) {
        Product product = productRepository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException("Product with code " + code + " was not found."));
        return stockManagementRepository.findByProduct(product)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private StockManagementDto convertToDto(StockManagement stockManagement) {
        return new StockManagementDto(stockManagement.getDate(),
                stockManagement.getReason(),
                stockManagement.getChangeQuantity(),
                stockManagement.getProduct().getCode(),
                stockManagement.getChangeType());
    }

    private StockManagement convertToEntity(StockManagementDto stockManagementDto) {
        StockManagement stockManagement = new StockManagement();
        stockManagement.setDate(stockManagementDto.getDate());
        stockManagement.setChangeQuantity(stockManagementDto.getChangeQuantity());
        stockManagement.setReason(stockManagementDto.getReason());
        return stockManagement;
    }
}
