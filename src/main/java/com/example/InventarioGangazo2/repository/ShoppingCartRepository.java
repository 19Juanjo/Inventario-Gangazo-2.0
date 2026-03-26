package com.example.InventarioGangazo2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventarioGangazo2.entity.ShoppingCart;
import com.example.InventarioGangazo2.entity.Users;

import java.util.Optional;


public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

    Optional<ShoppingCart> findByUser(Users user);
}
