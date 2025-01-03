package com.administracionDeUsuarios.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.administracionDeUsuarios.admin.entity.Usuario;
import com.administracionDeUsuarios.admin.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Método para crear un usuario (Solo Admin)
	public Usuario crearUsuario(Usuario usuario) {
		// Verificar si el usuario tiene permisos de Admin
		if (!tieneRol("ADMIN")) {
			throw new RuntimeException("No tiene permiso para crear usuarios");
		}
		return usuarioRepository.save(usuario);
	}

	// Método para listar todos los usuarios (Todos los roles pueden ver)
	public List<Usuario> listarUsuarios() {
		if (tieneRol("CLIENTE")) {
			throw new RuntimeException("No tiene permiso para ver todos los usuarios");
		}
		return usuarioRepository.findAll();
	}

	// Método para consultar un usuario por su ID (Todos los roles pueden ver)
	public Usuario obtenerUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	// Método para actualizar un usuario (Solo Admin y Operador)
	public Usuario actualizarUsuario(Long id, Usuario usuario) {
		if (!tieneRol("ADMIN") && !tieneRol("OPERADOR")) {
			throw new RuntimeException("No tiene permiso para actualizar usuarios");
		}

		// Obtener el usuario existente de la base de datos
		Usuario usuarioExistente = usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Actualizar los campos del usuario existente
		usuarioExistente.setNombre(usuario.getNombre());
		usuarioExistente.setCurp(usuario.getCurp());
		usuarioExistente.setRfc(usuario.getRfc());
		usuarioExistente.setTelefono(usuario.getTelefono());
		usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());

		// Guardar los cambios
		return usuarioRepository.save(usuarioExistente);
	}

	// Método para eliminar un usuario (Solo Admin)
	public void eliminarUsuario(Long id) {
		if (!tieneRol("ADMIN")) {
			throw new RuntimeException("No tiene permiso para eliminar usuarios");
		}
		usuarioRepository.deleteById(id);
	}

	// Método para verificar el rol actual del usuario
	private boolean tieneRol(String rol) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + rol));
	}
}