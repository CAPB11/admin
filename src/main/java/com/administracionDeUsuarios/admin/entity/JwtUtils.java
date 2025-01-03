package com.administracionDeUsuarios.admin.entity;

import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Component
public class JwtUtils {
	private final String jwtSecret = "your-very-secure-and-long-secret-key-which-is-at-least-32-characters";
	private final int jwtExpirationMs = 86400000; // Expiración de 24 horas
	private final SecretKey key; // Crear una clave secreta solo una vez

	// Constructor para inicializar la clave secreta
	public JwtUtils() {
		this.key = new SecretKeySpec(jwtSecret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
	}

	public String generarToken(String username, Rol rol) {
		return Jwts.builder().setSubject(username).claim("rol", rol.name()).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(key) // Usar la clave
																									// secreta
																									// inicializada
				.compact();
	}

	public boolean validarToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			// Manejar excepciones en caso de token inválido o expirado
			e.printStackTrace();
			return false;
		}
	}

	public String obtenerUsernameDeToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public String obtenerRolDeToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("rol", String.class);
	}
}