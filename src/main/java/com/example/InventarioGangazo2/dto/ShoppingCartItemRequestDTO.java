package com.example.InventarioGangazo2.dto;

import lombok.Data;

@Data
public class ShoppingCartItemRequestDTO {
    private Long productId;
    private Integer Quantity;
}
