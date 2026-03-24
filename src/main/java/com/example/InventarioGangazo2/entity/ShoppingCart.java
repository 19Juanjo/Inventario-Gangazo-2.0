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
@Table (name = "Carrito")
public class ShoppingCart extends ShoppingCartItem{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;

    @Column(name = "usuario_id")
    private Long usuario_id;
}
