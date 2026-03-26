package com.example.InventarioGangazo2.controller;

import java.util.List;

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
import com.example.InventarioGangazo2.service.ShoppingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ShoppingService shoppingService;

    @PostMapping("/buy")
    public ResponseEntity<OrderResponseDTO> buy(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO response = shoppingService.Makepurchase(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> Purchasehistory(@PathVariable Long userId) {
        List<OrderResponseDTO> response = shoppingService.Purchasehistory(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}