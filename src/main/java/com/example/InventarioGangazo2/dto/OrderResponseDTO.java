package com.example.InventarioGangazo2.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;
    private Timestamp date;
    private Double total;
    private List<OrdenItemsResponseDTO> items;
}
