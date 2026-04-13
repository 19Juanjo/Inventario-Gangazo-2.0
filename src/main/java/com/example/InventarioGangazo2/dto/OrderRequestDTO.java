package com.example.InventarioGangazo2.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "the user is required")
    @Positive(message = "The product ID must be greater than zero")
    private Long userId;
}