package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class ProductsResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
