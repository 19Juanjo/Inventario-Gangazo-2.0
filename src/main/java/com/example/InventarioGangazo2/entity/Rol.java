package com.example.InventarioGangazo2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
