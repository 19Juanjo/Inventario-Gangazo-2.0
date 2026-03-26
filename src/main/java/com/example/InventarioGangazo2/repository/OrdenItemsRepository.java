package com.example.InventarioGangazo2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.InventarioGangazo2.entity.OrdenItems;

public interface OrdenItemsRepository extends JpaRepository<OrdenItems, Long>{
    List<OrdenItems> findByPedidoId(Long pedidoId);
}
