package com.example.InventarioGangazo2.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.InventarioGangazo2.dto.LoginRequestDTO;
import com.example.InventarioGangazo2.dto.LoginResponseDTO;
import com.example.InventarioGangazo2.dto.RefreshTokenResponseDTO;
import com.example.InventarioGangazo2.entity.Users;
import com.example.InventarioGangazo2.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    
    public LoginResponseDTO login(LoginRequestDTO request){
        LoginResponseDTO reponse = new LoginResponseDTO();
        Optional<Users> user = usersRepository.findByUsername(request.getUsername());

        if(user.isEmpty() && (request.getUsername() == null || request.getUsername().isBlank())){
            reponse.setMessage("Este usuario no se encuentra regstrado");
            return reponse;
        }

        Users userFound = user.get();

        if (!passwordEncoder.matches(request.getPassword(), userFound.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");            
        }

        String jwt = jwtService.generateToken(userFound.getId(), userFound.getUsername(), userFound.getRol_id());

        reponse.setMessage("Inicio de sesion exitoso");
        reponse.setJwt(jwt);
        return reponse;
    }

    public RefreshTokenResponseDTO refreshToken(String token) {
        String jwt = jwtService.refreshToken(token);
        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setMessage("ok");
        response.setJwt(jwt);
        return response;
    }
}
