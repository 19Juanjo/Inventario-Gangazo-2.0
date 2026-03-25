package com.example.InventarioGangazo2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventarioGangazo2.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUsuario_id(Long usuario_id);
}
