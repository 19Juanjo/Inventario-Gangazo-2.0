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
@Table(name = "Pedido_items")
public class OrdenItems extends Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pedido_id")
    private Long orderId;

    @Column(name = "producto_id")
    private Long productId;

    @Column(name = "cantidad")
    private Integer Quantity;

    @Column(name = "precio")
    private Double price;
}
