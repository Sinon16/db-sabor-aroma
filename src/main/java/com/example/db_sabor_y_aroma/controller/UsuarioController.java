package com.example.db_sabor_y_aroma.controller;

import com.example.db_sabor_y_aroma.model.Usuario;
import com.example.db_sabor_y_aroma.security.JwtUtils;
import com.example.db_sabor_y_aroma.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuario", description = "Gestión de cuentas de usuarios y administradores")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;

    public UsuarioController(UsuarioService usuarioService, JwtUtils jwtUtils) {
        this.usuarioService = usuarioService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario y generar token JWT")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Faltan credenciales"));
        }

        Optional<Usuario> usuarioOpt = usuarioService.obtenerTodos().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getPassword() != null && usuario.getPassword().equals(password)) {
                // Determinar rol según el campo esadmin
                boolean esAdmin = Boolean.TRUE.equals(usuario.getEsadmin());
                String rol = esAdmin ? "ADMIN" : "CLIENTE";
                String token = jwtUtils.generateToken(usuario.getEmail(), rol);

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("token", token);
                respuesta.put("usuario", usuario);
                respuesta.put("rol", rol);

                return ResponseEntity.ok(respuesta);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("mensaje", "Correo o contraseña incorrectos"));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios registrados")
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por su ID")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear o registrar un nuevo usuario")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        if (usuario.getEmail() != null && usuario.getEmail().toLowerCase().endsWith("@profesor.duoc.cl")) {
            usuario.setEsadmin(true);
        }
        return usuarioService.guardar(usuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un usuario existente")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        if (usuarioService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.ok(usuarioService.guardar(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario por ID")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        if (usuarioService.obtenerPorId(id).isPresent()) {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}