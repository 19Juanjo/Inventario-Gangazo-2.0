package com.example.InventarioGangazo2.controller;

import java.util.List;
import java.util.Optional;

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
    public Optional<Products> create(@RequestBody ProductsRequestDTO product) {
        return productsService.add(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponseDTO> update(@PathVariable Long id, @RequestBody ProductsRequestDTO productsRequestDTO){
        ProductsResponseDTO response = productsService.update(id, productsRequestDTO).orElse(null);
        if(response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public Products delete(@PathVariable Long id) {
        return delete(id);
    }
}
