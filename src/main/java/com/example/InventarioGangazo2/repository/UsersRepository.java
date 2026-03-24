package com.example.InventarioGangazo2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.InventarioGangazo2.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{

    Optional<Users> findByUsername(String username);
    
} 
