package com.administracionDeUsuarios.admin.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USUARIO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOMBRE", length = 100, nullable = false) // Añade la longitud en lugar de columnDefinition
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@Column(name = "CURP", length = 18, unique = true) // Añadido unique = true si CURP debe ser único
	@Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z]{6}\\d{2}", message = "Formato de CURP no válido")
	private String curp;

	@Column(name = "CODIGO_POSTAL", length = 5)
	@Pattern(regexp = "\\d{5}", message = "Formato de código postal no válido")
	private String codigoPostal;

	@Column(name = "RFC", length = 13, unique = true)
	@Pattern(regexp = "[A-Z]{4}\\d{6}[A-Z0-9]{3}", message = "Formato de RFC no válido")
	private String rfc;

	@Column(name = "TELEFONO", length = 10)
	@Pattern(regexp = "\\d{10}", message = "Formato de teléfono no válido")
	private String telefono;

	@Column(name = "FECHA_NACIMIENTO")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Solo para controladores o si se maneja directamente en vistas
	private LocalDate fechaNacimiento; // Reemplazado java.sql.Date con java.time.LocalDate

	@Enumerated(EnumType.STRING)
	@Column(name = "ROL")
	private Rol rol; // Enum Rol debe existir en el mismo paquete o importado

	// Getters y Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}