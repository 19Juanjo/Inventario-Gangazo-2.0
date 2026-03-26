package com.example.InventarioGangazo2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.getCartByUser(user).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> addProduct(@PathVariable Long userId,@RequestBody ShoppingCartItemRequestDTO request) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.addProduct(user, request).orElseThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> updateProduct(@PathVariable Long userId,@RequestBody ShoppingCartItemRequestDTO request) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.updateCart(user, request).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ShoppingCartResponseDTO> removeProduct(@PathVariable Long userId,@PathVariable Long productId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShoppingCartResponseDTO response = shoppingCartService.removeProduct(user, productId).orElseThrow();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        shoppingCartService.clearCart(user);

        return ResponseEntity.noContent().build();
    }
}
