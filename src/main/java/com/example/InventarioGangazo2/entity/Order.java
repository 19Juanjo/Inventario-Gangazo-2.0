package com.example.InventarioGangazo2.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Pedidos")
public class Order {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;
    
    @Column(name = "usuario_id")
    private Long usuario_id;
    @Column(name = "fecha")
    private Timestamp fecha;
    @Column(name = "total")
    private Double total;
}
