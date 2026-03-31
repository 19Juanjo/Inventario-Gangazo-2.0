package com.example.InventarioGangazo2.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotNull(message = "the user is required")
    private Long userId;
    @NotEmpty(message = "There must be at least one product in the order")
    private List<@Valid OrdenItemsRequestDTO> items;
}