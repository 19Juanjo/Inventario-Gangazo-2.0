package com.example.InventarioGangazo2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InventarioGangazo2.dto.ShoppingCartItemRequestDTO;
import com.example.InventarioGangazo2.dto.ShoppingCartResponseDTO;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.UsersRepository;
import com.example.InventarioGangazo2.service.ShoppingCartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UsersRepository usersRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> getCart(@PathVariable Long userId){

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.getCartByUser(user).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> addProduct(@PathVariable Long userId, @RequestBody ShoppingCartItemRequestDTO request) {

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getProductId() == null || request.getProductId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.addProduct(user, request).orElseThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> updateProduct(@PathVariable Long userId,@RequestBody ShoppingCartItemRequestDTO request) {

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getProductId() == null || request.getProductId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.updateCart(user, request).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ShoppingCartResponseDTO> removeProduct(@PathVariable Long userId,@PathVariable Long productId) {

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (productId == null || productId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.removeProduct(user, productId).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        shoppingCartService.clearCart(user);

        return ResponseEntity.noContent().build();
    }
}