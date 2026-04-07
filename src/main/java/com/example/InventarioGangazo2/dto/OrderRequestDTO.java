package com.example.InventarioGangazo2.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "the user is required")
    private Long userId;
}