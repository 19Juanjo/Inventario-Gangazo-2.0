package com.example.InventarioGangazo2.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.LoginRequestDTO;
import com.example.InventarioGangazo2.dto.LoginResponseDTO;
import com.example.InventarioGangazo2.dto.MessageResponseDTO;
import com.example.InventarioGangazo2.dto.RefreshTokenResponseDTO;
import com.example.InventarioGangazo2.dto.RegisterRequestDTO;
import com.example.InventarioGangazo2.entity.RolType;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    public MessageResponseDTO register(RegisterRequestDTO request) {
        MessageResponseDTO response = new MessageResponseDTO();

        if (request.getUsername() == null || request.getUsername().isBlank() ||
            request.getEmail() == null || request.getEmail().isBlank() ||
            request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("All fields are required");
        }

        if (usersRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("The username is already in use");
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRolId(RolType.CLIENTE.getId());

        usersRepository.save(user);

        response.setMessage("User successfully registered");
        return response;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        if (request.getUsername() == null || request.getUsername().isBlank() ||
            request.getPassword() == null || request.getPassword().isBlank()) {
            throw new RuntimeException("Username and password are required");
        }

        Optional<Users> user = usersRepository.findByUsername(request.getUsername());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Users userFound = user.get();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new RuntimeException("password incorrect");
        }

        String jwt = jwtService.generateToken(
                userFound.getId(),
                userFound.getUsername(),
                userFound.getRolId()
        );

        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login successful");
        response.setJwt(jwt);

        return response;
    }

    public RefreshTokenResponseDTO refreshToken(String token) {
        String jwt = jwtService.refreshToken(token);

        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setMessage("ok");
        response.setJwt(jwt);

        return response;
    }
}