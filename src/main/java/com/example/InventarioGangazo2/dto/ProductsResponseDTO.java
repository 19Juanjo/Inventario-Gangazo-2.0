package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class ProductsResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}