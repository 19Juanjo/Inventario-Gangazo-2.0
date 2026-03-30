package com.example.InventarioGangazo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemResponseDTO {
    private Long productoId;
    private String nameProduct;
    private Double price;
    private Integer Quantity;
    private Double subtotal;
}
