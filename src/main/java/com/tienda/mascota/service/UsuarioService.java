package com.tienda.mascota.service;

import com.tienda.mascota.model.Usuario;
import com.tienda.mascota.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        // NOTA: En producción, deberías codificar la contraseña
        // Por ahora la guardamos en texto plano para simplificar
        usuario.setFechaCreacion(java.time.LocalDateTime.now());
        
        // Por defecto no es administrador
        if (!usuario.isEsAdministrador()) {
            usuario.setEsAdministrador(false);
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // NOTA: En producción, compara contraseñas codificadas
        // Por ahora comparamos en texto plano
        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        // Ocultar la contraseña en la respuesta por seguridad
        usuario.setPassword(null);
        return usuario;
    }
    
    public Usuario obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Ocultar contraseña
        usuario.setPassword(null);
        return usuario;
    }
    
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Actualizar campos permitidos (no email ni contraseña aquí)
        usuario.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setDireccion(usuarioActualizado.getDireccion());
        
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    // Método adicional para actualizar contraseña
    @Transactional
    public Usuario actualizarContraseña(Long id, String nuevaContraseña) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPassword(nuevaContraseña);
        return usuarioRepository.save(usuario);
    }
}