package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class OrdenItemsResponseDTO {
    private Long productId;
    private String nombre;
    private int cantidad;
    private Double precio;
}
