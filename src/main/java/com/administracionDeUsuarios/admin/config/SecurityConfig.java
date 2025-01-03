package com.administracionDeUsuarios.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/public/**").permitAll() // Acceso sin
																									// autenticación
				.anyRequest().authenticated() // Requiere autenticación para otras rutas
		).formLogin(form -> form.permitAll() // Permite el acceso al formulario de inicio de sesión para todos
		).logout(logout -> logout.permitAll() // Permite el acceso al cierre de sesión para todos
		);

		return http.build();
	}
}