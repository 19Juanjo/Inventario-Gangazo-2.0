package com.example.InventarioGangazo2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrdenItemsRequestDTO {
    @NotNull(message = "The product is required")
    private Long productId;
    @NotNull(message = "The quantity is required")
    @Positive(message = "the quantity must be greater than 0")
    private Integer quantity;
}
