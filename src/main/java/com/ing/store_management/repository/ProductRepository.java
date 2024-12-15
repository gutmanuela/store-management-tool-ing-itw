package com.ing.store_management.repository;

import com.ing.store_management.model.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByCode(@NotBlank String code);

    void deleteByCode(@NotBlank String code);

    List<Product> getProductsByPriceIsLessThan(@NotNull double priceIsLessThan);
}
