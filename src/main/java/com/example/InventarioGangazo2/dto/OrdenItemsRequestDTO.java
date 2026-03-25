package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class OrdenItemsRequestDTO {
    private Long productId;
    private int cantidad;
}
