package com.example.InventarioGangazo2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.InventarioGangazo2.dto.OrderRequestDTO;
import com.example.InventarioGangazo2.dto.OrderResponseDTO;
import com.example.InventarioGangazo2.service.JwtService;
import com.example.InventarioGangazo2.service.ShoppingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ShoppingService shoppingService;
    private final JwtService jwtService;

    @PostMapping("/buy")
    public ResponseEntity<OrderResponseDTO> buy(@Valid @RequestBody OrderRequestDTO request) {

        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getUserId() == null || request.getUserId() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        OrderResponseDTO response = shoppingService.MakePurchase(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> Purchasehistory(@Valid @PathVariable Long userId) {

        if (userId == null || userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<OrderResponseDTO> response = shoppingService.Purchasehistory(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(HttpServletRequest request) {

        Long role = jwtService.extractRolId(request.getHeader("Authorization").substring(7));
        if (role != 1L) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<OrderResponseDTO> response = shoppingService.getAllOrders();

        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}