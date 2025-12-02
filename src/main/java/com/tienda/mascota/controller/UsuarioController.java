package com.tienda.mascota.controller;

import com.tienda.mascota.model.Usuario;
import com.tienda.mascota.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, usuario);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Usuario actualizado exitosamente");
            response.put("usuario", actualizado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Usuario eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/verificar-email")
    public ResponseEntity<?> verificarEmail(@RequestParam String email) {
        try {
            boolean existe = usuarioService.existeUsuarioPorEmail(email);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("existe", existe);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/{id}/password")
    public ResponseEntity<?> actualizarContraseña(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String nuevaContraseña = request.get("password");
            if (nuevaContraseña == null || nuevaContraseña.trim().isEmpty()) {
                throw new RuntimeException("La contraseña no puede estar vacía");
            }
            
            Usuario actualizado = usuarioService.actualizarContraseña(id, nuevaContraseña);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Contraseña actualizada exitosamente");
            // No devolver el usuario con contraseña
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
