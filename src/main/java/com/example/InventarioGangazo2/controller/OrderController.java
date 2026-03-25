package com.example.InventarioGangazo2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.InventarioGangazo2.dto.OrderRequestDTO;
import com.example.InventarioGangazo2.dto.OrderResponse;
import com.example.InventarioGangazo2.service.ShoppingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final ShoppingService shoppingService;

    @PostMapping("/buy")
    public ResponseEntity<OrderResponse> comprar(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.ok(shoppingService.RealizarCompra(request));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderResponse>> historial(@PathVariable Long userId) {
        return ResponseEntity.ok(shoppingService.historial(userId));
    }
}
