package com.ing.store_management.repository;

import com.ing.store_management.model.StockManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockManagementRepository extends JpaRepository<StockManagement, Integer> {
}
