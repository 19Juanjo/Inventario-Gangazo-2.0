package com.example.InventarioGangazo2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "pedido_items")
public class OrdenItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pedido_id")
    private Long orderId;
    @Column(name = "producto_id")
    private Long productId;
    @Column(name = "cantidad")
    private Integer quantity;
    @Column(name = "precio")
    private Double price;
}
