package com.example.InventarioGangazo2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventarioGangazo2.entity.Products;
import com.example.InventarioGangazo2.entity.ShoppingCart;
import com.example.InventarioGangazo2.entity.ShoppingCartItem;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    List<ShoppingCartItem> findByShoppingCart(ShoppingCart cart);
    Optional<ShoppingCartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Products product);
    void deleteByShoppingCart(ShoppingCart shoppingCart);
}
