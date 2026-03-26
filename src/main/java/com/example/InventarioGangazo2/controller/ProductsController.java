package com.example.InventarioGangazo2.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<Products> listar() {
        return productsService.listar();
    }

    @PostMapping
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody ProductsRequestDTO product) {
        Long rolId = (Long) request.getAttribute("rolId");

        if (rolId == null || rolId != 1L) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
        }

        return ResponseEntity.ok(productsService.add(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> update(HttpServletRequest request, @PathVariable Long id, @RequestBody ProductsRequestDTO productsRequestDTO) {
        Long rolId = (Long) request.getAttribute("rolId");

        if (rolId == null || rolId != 1L) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        ProductsResponseDTO response = productsService.update(id, productsRequestDTO).orElse(null);

        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Long id) {
        Long rolId = (Long) request.getAttribute("rolId");

        if (rolId == null || rolId != 1L) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
        }

        productsService.delete(id);
        return ResponseEntity.ok("Producto eliminado");
    }
}