package com.example.InventarioGangazo2.entity;

public enum RolType {
    ADMIN(1L),
    CLIENTE(2L);

    private final Long id;

    RolType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}