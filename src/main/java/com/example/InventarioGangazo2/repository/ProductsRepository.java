package com.example.InventarioGangazo2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventarioGangazo2.dto.ProductsRequestDTO;
import com.example.InventarioGangazo2.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{

    Optional<Products> save(ProductsRequestDTO product);
    
}
