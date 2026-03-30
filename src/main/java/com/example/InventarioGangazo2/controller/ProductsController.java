package com.example.InventarioGangazo2.controller;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.InventarioGangazo2.dto.MessageResponseDTO;
import com.example.InventarioGangazo2.dto.ProductsRequestDTO;
import com.example.InventarioGangazo2.dto.ProductsResponseDTO;
import com.example.InventarioGangazo2.entity.Products;
import com.example.InventarioGangazo2.service.ProductsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ProductsResponseDTO>> AllProducts() {
        return ResponseEntity.ok(productsService.AllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Optional<Products>> create(@RequestBody ProductsRequestDTO product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.add(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> update(@PathVariable Long id,@RequestBody ProductsRequestDTO dto) {
        return ResponseEntity.ok(productsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {

        productsService.delete(id);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Product delete correctly");

        return ResponseEntity.ok(response);
    }
}
