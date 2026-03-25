package com.example.InventarioGangazo2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemResponseDTO {
    private Long productoId;
    private String nombreProducto;
    private Double precio;
    private Integer cantidad;
    private Double subtotal;
}
