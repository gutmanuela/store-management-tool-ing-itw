package com.ing.store_management.service;

import com.ing.store_management.model.dto.SupplierDto;
import com.ing.store_management.model.entity.Product;
import com.ing.store_management.model.entity.Supplier;
import com.ing.store_management.repository.ProductRepository;
import com.ing.store_management.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public SupplierService(SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void deleteSupplier(Long supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    public void addSupplier(SupplierDto supplierDto) {
        Supplier supplier = convertToEntity(supplierDto);
        supplierRepository.save(supplier);
    }

    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SupplierDto> getAllSuppliersForProduct(String code) {
        Product product = productRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getSuppliers().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void addSupplierForProduct(Long supplierId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new RuntimeException("Supplier not found"));
        product.getSuppliers().add(supplier);
        productRepository.save(product);
    }

    private SupplierDto convertToDto(Supplier supplier) {
        return new SupplierDto(supplier.getName(), supplier.getPhoneNumber(), supplier.getEmail(), supplier.getAddress());
    }

    private Supplier convertToEntity(SupplierDto productDto) {
        Supplier supplier = new Supplier();
        supplier.setAddress(productDto.getAddress());
        supplier.setEmail(productDto.getEmail());
        supplier.setPhoneNumber(productDto.getPhoneNumber());
        supplier.setName(productDto.getName());
        return supplier;
    }
}