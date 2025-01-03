package com.administracionDeUsuarios.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.administracionDeUsuarios.admin.entity.Usuario;
import com.administracionDeUsuarios.admin.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	// Crear un nuevo usuario
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
		try {
			Usuario nuevoUsuario = usuarioService.crearUsuario(usuario); // Lógica de creación
			return ResponseEntity.status(201).body(nuevoUsuario); // 201 para recurso creado
		} catch (Exception e) {
			// Manejo de error si se produce una excepción al crear el usuario
			return ResponseEntity.status(400).body(null); // Retornar error en caso de falla
		}
	}

	// Listar usuarios
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'OPERADOR')")
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		try {
			List<Usuario> usuarios = usuarioService.listarUsuarios(); // Lógica para listar usuarios
			return ResponseEntity.ok(usuarios); // Respuesta con código HTTP 200
		} catch (Exception e) {
			// Manejo de error si ocurre algún problema al listar los usuarios
			return ResponseEntity.status(500).body(null); // Retornar error en caso de fallo
		}
	}

	// Consultar un usuario por ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE', 'OPERADOR')")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
		try {
			Usuario usuario = usuarioService.obtenerUsuario(id); // Lógica para obtener un usuario
			return ResponseEntity.ok(usuario); // Respuesta con código HTTP 200
		} catch (Exception e) {
			// Manejo de error si el usuario no es encontrado
			return ResponseEntity.status(404).body(null); // Retornar error 404 si no existe
		}
	}

	// Actualizar un usuario
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'OPERADOR')")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
		try {
			Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario); // Lógica de actualización
			return ResponseEntity.ok(usuarioActualizado); // Respuesta con código HTTP 200
		} catch (Exception e) {
			// Manejo de error si ocurre algún problema al actualizar el usuario
			return ResponseEntity.status(400).body(null); // Retornar error en caso de fallo
		}
	}

	// Eliminar un usuario
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
		try {
			usuarioService.eliminarUsuario(id); // Lógica para eliminar usuario
			return ResponseEntity.noContent().build(); // 204 No Content
		} catch (Exception e) {
			// Manejo de error si no se puede eliminar el usuario
			return ResponseEntity.status(404).build(); // Retornar error 404 si el usuario no existe
		}
	}
}
