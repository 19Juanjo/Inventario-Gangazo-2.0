package com.example.InventarioGangazo2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductsRequestDTO {
    @NotBlank(message = "The name is required")
    private String name;
    @NotBlank(message = "The description is required")
    private String description;
    @NotNull(message = "The price is required")
    @Positive(message = "The price must be greater than 0")
    private Double price;
    @NotNull(message = "The stock is required")
    @Min(value = 0, message = "The stock cannot be negative")
    private Integer stock;
}
