package com.example.InventarioGangazo2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "CarritoItems")
@Entity
public class ShoppingCartItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;

    @Column(name = "carrito_id")
    private ShoppingCart shoppingCart;
    @Column(name = "producto_id")
    private Products product;
    @Column(name = "cantidad")
    private Integer quantity;
    
}
