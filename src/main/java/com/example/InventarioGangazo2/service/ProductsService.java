package com.example.InventarioGangazo2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.ProductsRequestDTO;
import com.example.InventarioGangazo2.dto.ProductsResponseDTO;
import com.example.InventarioGangazo2.entity.Products;
import com.example.InventarioGangazo2.repository.ProductsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductsResponseDTO> AllProducts() {
        return productsRepository.findAll()
            .stream()
            .map(p -> {
                ProductsResponseDTO dto = new ProductsResponseDTO();
                dto.setId(p.getId());
                dto.setNombre(p.getName());
                dto.setDescripcion(p.getDescription());
                dto.setPrecio(p.getPrice());
                dto.setStock(p.getStock());
                return dto;
            })
            .toList();
    }

    public Optional<Products> add(ProductsRequestDTO product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new RuntimeException("the name is required");
        }
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            throw new RuntimeException("the description is required");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuntimeException("the price must be greater than 0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new RuntimeException("the stock not it can be negative");
        }

        Products p = new Products();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());

        return Optional.of(productsRepository.save(p));
    }

    public void delete(Long id) {
        if (id == null) {
            throw new RuntimeException("the id is required");
        }
        if (!productsRepository.existsById(id)) {
            throw new RuntimeException("the product not exist");
        }
        productsRepository.deleteById(id);
    }

    public Optional<ProductsResponseDTO> update(Long id, ProductsRequestDTO dto) {
        if (id == null) {
            throw new RuntimeException("the id is required");
        }
        Optional<Products> productexist = productsRepository.findById(id);

        if (productexist.isPresent()) {
            if (dto.getName() == null || dto.getName().isBlank()) {
                throw new RuntimeException("the name is required");
            }
            if (dto.getDescription() == null || dto.getDescription().isBlank()) {
                throw new RuntimeException("the descripción is required");
            }
            if (dto.getPrice() == null || dto.getPrice() <= 0) {
                throw new RuntimeException("the precio must be greater than 0");
            }
            if (dto.getStock() == null || dto.getStock() < 0) {
                throw new RuntimeException("the stock not it can be negative");
            }

            Products prod = productexist.get();

            prod.setName(dto.getName());
            prod.setDescription(dto.getDescription());
            prod.setPrice(dto.getPrice());
            prod.setStock(dto.getStock());

            Products productUpdate = productsRepository.save(prod);

            ProductsResponseDTO response = new ProductsResponseDTO();
            response.setId(productUpdate.getId());
            response.setNombre(productUpdate.getName());
            response.setDescripcion(productUpdate.getDescription());
            response.setPrecio(productUpdate.getPrice());
            response.setStock(productUpdate.getStock());

            return Optional.of(response);
        }
        return Optional.empty();
    }

    public Optional<ProductsResponseDTO> getById(Long id) {
        if (id == null) {
            throw new RuntimeException("the id is required");
        }
        Optional<Products> product = productsRepository.findById(id);
        if (product.isPresent()) {
            Products p = product.get();

            ProductsResponseDTO response = new ProductsResponseDTO();
            response.setId(p.getId());
            response.setNombre(p.getName());
            response.setDescripcion(p.getDescription());
            response.setPrecio(p.getPrice());
            response.setStock(p.getStock());

            return Optional.of(response);
        }
        return Optional.empty();
    }
}