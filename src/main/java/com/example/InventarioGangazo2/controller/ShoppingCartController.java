package com.example.InventarioGangazo2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InventarioGangazo2.dto.ShoppingCartItemRequestDTO;
import com.example.InventarioGangazo2.dto.ShoppingCartResponseDTO;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.UsersRepository;
import com.example.InventarioGangazo2.service.ShoppingCartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UsersRepository usersRepository;
    /**
     * 
     * @param userId the user ID, must be positive
     * @return 200 OK with the cart, or 404 NOT FOUND if the user does not exist
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> getCart(@Valid @PathVariable Long userId){
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            ShoppingCartResponseDTO response = shoppingCartService.getCartByUser(user).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param userId the user ID, must be positive
     * @param request the DTO containing productId and quantity
     * @return 201 CREATED with the updated cart, or 404 NOT FOUND if user or product not found
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> addProduct(@Valid @PathVariable Long userId, @RequestBody ShoppingCartItemRequestDTO request) {
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            ShoppingCartResponseDTO response = shoppingCartService.addProduct(user, request).orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param userId the user ID, must be positive
     * @param request the DTO containing productId and new quantity
     * @return 200 OK with the updated cart, or 404 NOT FOUND if user or product not found
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ShoppingCartResponseDTO> updateProduct(@Valid @PathVariable Long userId,@RequestBody ShoppingCartItemRequestDTO request) {
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            ShoppingCartResponseDTO response = shoppingCartService.updateCart(user, request).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param userId the user ID, must be positive
     * @param productId the product ID to remove, must be positive 
     * @return 200 OK with the updated cart, or 404 NOT FOUND if user or product not found
     */
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ShoppingCartResponseDTO> removeProduct(@Valid @PathVariable Long userId,@PathVariable Long productId) {
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            ShoppingCartResponseDTO response = shoppingCartService.removeProduct(user, productId).orElseThrow();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param userId the user ID, must be positive
     * @return 204 NO CONTENT if successful, or 404 NOT FOUND if user does not exist
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        try {
            Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            shoppingCartService.clearCart(user);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}