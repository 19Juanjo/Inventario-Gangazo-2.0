package com.example.InventarioGangazo2.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.InventarioGangazo2.dto.ShoppingCartItemRequestDTO;
import com.example.InventarioGangazo2.dto.ShoppingCartResponseDTO;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.service.ShoppingCartService;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartResponseDTO getCart(Users user){
        return shoppingCartService.getCartByUser(user);
    }
    
    @PostMapping
    public ShoppingCartResponseDTO addProduct(@RequestBody Users user, @RequestBody ShoppingCartItemRequestDTO request) {
        return shoppingCartService.addProduct(user, request);
    }

    @PutMapping
    public ShoppingCartResponseDTO updateProduct(@RequestBody Users user, @RequestBody ShoppingCartItemRequestDTO request) {
        return shoppingCartService.updateCart(user, request);
    }

    @DeleteMapping("/{productoId}")
    public ShoppingCartResponseDTO removeProduct(@PathVariable Users user, @PathVariable Long productoId) {
        return shoppingCartService.removeProduct(user, productoId);
    }

    @DeleteMapping("/clear")
    public void clearCart(Users user) {
        shoppingCartService.clearCart(user);
    }
}
