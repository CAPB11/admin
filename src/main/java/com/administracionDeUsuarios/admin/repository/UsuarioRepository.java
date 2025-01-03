package com.administracionDeUsuarios.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.administracionDeUsuarios.admin.entity.Usuario;

import jakarta.validation.Valid;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	// Consultar todos los usuarios
	List<Usuario> findAll();

	// Consultar un usuario por ID
	Usuario findById(long id);

	// Verificar si un usuario existe por CURP
	boolean existsByCurp(String curp);
}