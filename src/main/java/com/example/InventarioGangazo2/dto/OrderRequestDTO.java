package com.example.InventarioGangazo2.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long userId;
    private List<OrdenItemsRequestDTO> items;
}

