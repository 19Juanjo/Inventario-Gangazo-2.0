package com.example.InventarioGangazo2.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponseDTO {
    private Long carritoId;
    private List<ShoppingCartItemResponseDTO> items;
    private Double total;
}
