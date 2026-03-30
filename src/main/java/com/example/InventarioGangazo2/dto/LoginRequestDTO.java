package com.example.InventarioGangazo2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "The username is required")
    private String username;
    @NotBlank(message = "The password is required")
    private String password;
}
