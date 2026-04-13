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
import com.example.InventarioGangazo2.service.JwtService;
import com.example.InventarioGangazo2.service.ProductsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;
    private final JwtService jwtService;
    /**
     * 
     * @return 200 OK with the list of products, or 204 NO CONTENT if empty
     */
    @GetMapping
    public ResponseEntity<List<ProductsResponseDTO>> AllProducts() {
        try {
            List<ProductsResponseDTO> response = productsService.AllProducts();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param id the product ID, must be positive
     * @return 200 OK with the product, or 404 NOT FOUND if it does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> getById(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(productsService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param product the DTO containing the product data
     * @param request this httpServeletRequest
     * @return 201 CREATED with the new product, or 400 BAD REQUEST if validation fails
     */
    @PostMapping
    public ResponseEntity<Optional<Products>> create(@Valid @RequestBody ProductsRequestDTO product, HttpServletRequest request) {
        try {
            Long role = jwtService.extractRolId(request.getHeader("Authorization").substring(7));
            if (role != 1L) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Optional.empty());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(productsService.add(product));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param id the product ID, must be positive
     * @param dto the DTO containing the updated product data
     * @param request this httpServeletRequest
     * @return 200 OK with the updated product, or 404 NOT FOUND if it does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProductsResponseDTO>> update(@Valid @PathVariable Long id,@RequestBody ProductsRequestDTO dto, HttpServletRequest request) {
        try {
            Long role = jwtService.extractRolId(request.getHeader("Authorization").substring(7));
            if (role != 1L) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Optional.empty());
            }
            return ResponseEntity.ok(productsService.update(id, dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    /**
     * 
     * @param id the product ID, must be positive
     * @param request this httpServeletRequest
     * @return 200 OK with a success message, or 404 NOT FOUND if it does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> delete(@Valid @PathVariable Long id, HttpServletRequest request) {
        try {
            Long role = jwtService.extractRolId(request.getHeader("Authorization").substring(7));
            if (role != 1L) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponseDTO("Access denied"));
            }
            productsService.delete(id);
            MessageResponseDTO response = new MessageResponseDTO();
            response.setMessage("Product delete correctly");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}