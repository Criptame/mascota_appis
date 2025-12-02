package com.tienda.mascota.controller;

import com.tienda.mascota.model.Usuario;
import com.tienda.mascota.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class AuthController {
    
    private final UsuarioService usuarioService;
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Validar campos requeridos
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new RuntimeException("El email es requerido");
            }
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                throw new RuntimeException("La contraseña es requerida");
            }
            if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
                throw new RuntimeException("El nombre completo es requerido");
            }
            
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Usuario registrado exitosamente");
            response.put("usuario", nuevoUsuario);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            
            if (email == null || email.trim().isEmpty()) {
                throw new RuntimeException("El email es requerido");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new RuntimeException("La contraseña es requerida");
            }
            
            Usuario usuario = usuarioService.autenticarUsuario(email, password);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login exitoso");
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "API funcionando");
        return ResponseEntity.ok(response);
    }
}
