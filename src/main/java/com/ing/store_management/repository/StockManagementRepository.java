package com.ing.store_management.repository;

import com.ing.store_management.model.entity.Product;
import com.ing.store_management.model.entity.StockManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockManagementRepository extends JpaRepository<StockManagement, Long> {
    List<StockManagement> findByProduct(Product product);
}
