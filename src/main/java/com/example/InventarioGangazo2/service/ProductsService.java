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

    public List<Products> listar() {
        return productsRepository.findAll();
    }

    public Optional<Products> add(ProductsRequestDTO product) {
        return productsRepository.save(product);
    }

    public void delete(Long id) {
        productsRepository.deleteById(id);
    }

    public Optional<ProductsResponseDTO> update(Long id, ProductsRequestDTO dto) {

        Optional<Products> productexist = productsRepository.findById(id);

        if (productexist.isPresent()) {

            Products prod = productexist.get();

            prod.setName(dto.getNombre());
            prod.setDescription(dto.getDescripcion());
            prod.setPrice(dto.getPrecio());
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
}