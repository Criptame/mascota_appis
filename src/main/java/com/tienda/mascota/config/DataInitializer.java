package com.tienda.mascota.config;

import com.tienda.mascota.model.Categoria;
import com.tienda.mascota.model.Producto;
import com.tienda.mascota.model.Usuario;
import com.tienda.mascota.repository.CategoriaRepository;
import com.tienda.mascota.repository.ProductoRepository;
import com.tienda.mascota.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        
        return args -> {
            // Crear usuario admin si no existe
            if (usuarioRepository.count() == 0) {
                System.out.println("Creando datos iniciales...");
                
                // Usuario administrador
                Usuario admin = new Usuario();
                admin.setEmail("admin@tienda.com");
                admin.setPassword("admin123"); // En producción, codificar esto
                admin.setNombreCompleto("Administrador Principal");
                admin.setTelefono("555-1234");
                admin.setDireccion("Calle Principal 123");
                admin.setEsAdministrador(true);
                usuarioRepository.save(admin);
                
                // Usuario cliente de ejemplo
                Usuario cliente = new Usuario();
                cliente.setEmail("cliente@tienda.com");
                cliente.setPassword("cliente123"); // En producción, codificar esto
                cliente.setNombreCompleto("Juan Pérez");
                cliente.setTelefono("555-5678");
                cliente.setDireccion("Avenida Central 456");
                cliente.setEsAdministrador(false);
                usuarioRepository.save(cliente);
                
                System.out.println("Usuarios creados: admin@tienda.com / admin123");
                System.out.println("                 cliente@tienda.com / cliente123");
            }
            
            // Crear categorías si no existen
            if (categoriaRepository.count() == 0) {
                Categoria alimentos = new Categoria();
                alimentos.setNombre("Alimentos");
                alimentos.setDescripcion("Alimentos balanceados para perros y gatos");
                categoriaRepository.save(alimentos);
                
                Categoria juguetes = new Categoria();
                juguetes.setNombre("Juguetes");
                juguetes.setDescripcion("Juguetes interactivos y divertidos para mascotas");
                categoriaRepository.save(juguetes);
                
                Categoria accesorios = new Categoria();
                accesorios.setNombre("Accesorios");
                accesorios.setDescripcion("Collares, correas, camas y más");
                categoriaRepository.save(accesorios);
                
                Categoria higiene = new Categoria();
                higiene.setNombre("Higiene");
                higiene.setDescripcion("Productos de limpieza y cuidado personal");
                categoriaRepository.save(higiene);
                
                // Crear productos de ejemplo
                Producto producto1 = new Producto();
                producto1.setNombre("Croquetas Premium Perro Adulto");
                producto1.setDescripcion("Alimento balanceado para perros adultos de todas las razas");
                producto1.setPrecio(new BigDecimal("450.00"));
                producto1.setStock(50);
                producto1.setCategoria(alimentos);
                producto1.setUrlImagen("img/perro1.png");
                producto1.setDestacado(true);
                productoRepository.save(producto1);
                
                Producto producto2 = new Producto();
                producto2.setNombre("Juguete Hueso de Goma Resistente");
                producto2.setDescripcion("Hueso de goma duradero para perros de todas las tallas");
                producto2.setPrecio(new BigDecimal("120.00"));
                producto2.setStock(30);
                producto2.setCategoria(juguetes);
                producto2.setUrlImagen("img/juguete1.png");
                producto2.setDestacado(false);
                productoRepository.save(producto2);
                
                Producto producto3 = new Producto();
                producto3.setNombre("Collar Ajustable con Placa");
                producto3.setDescripcion("Collar de nylon ajustable con placa para identificación");
                producto3.setPrecio(new BigDecimal("85.50"));
                producto3.setStock(25);
                producto3.setCategoria(accesorios);
                producto3.setUrlImagen("img/collar1.png");
                producto3.setDestacado(true);
                productoRepository.save(producto3);
                
                System.out.println("Categorías y productos de ejemplo creados");
            }
        };
    }
}
