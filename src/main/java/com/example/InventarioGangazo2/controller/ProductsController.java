package com.example.InventarioGangazo2.controller;

import java.util.List;
import java.util.Optional;

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

        List<ProductsResponseDTO> response = productsService.AllProducts();

        if (response == null || response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> getById(@PathVariable Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        return ResponseEntity.ok(productsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Optional<Products>> create(@RequestBody ProductsRequestDTO product) {

        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        if (product.getName() == null || product.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        if (product.getStock() == null || product.getStock() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.add(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> update(@PathVariable Long id,@RequestBody ProductsRequestDTO dto) {

        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        if (dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        if (dto.getName() == null || dto.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Optional.empty());
        }

        return ResponseEntity.ok(productsService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@PathVariable Long id) {

        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO("Invalid ID"));
        }

        productsService.delete(id);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Product delete correctly");

        return ResponseEntity.ok(response);
    }
}