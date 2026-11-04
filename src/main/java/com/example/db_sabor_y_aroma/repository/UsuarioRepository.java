package com.example.db_sabor_y_aroma.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.db_sabor_y_aroma.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndPassword(String email, String password);
}