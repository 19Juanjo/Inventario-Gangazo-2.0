package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class ProductsRequestDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
}
