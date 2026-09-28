package com.krakedev.asistencias.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Asistencia {

	public static final String PRESENTE = "P";
	public static final String AUSENTE = "A";

	private LocalDate fechaClase;
	private LocalDateTime fechaHoraRegistro;
	private String estado;

	public Asistencia() {
	}

	public Asistencia(LocalDate fechaClase, LocalDateTime fechaHoraRegistro, String estado) {
		this.fechaClase = fechaClase;
		this.fechaHoraRegistro = fechaHoraRegistro;
		this.estado = estado;
	}

	public LocalDate getFechaClase() {
		return fechaClase;
	}

	public void setFechaClase(LocalDate fechaClase) {
		this.fechaClase = fechaClase;
	}

	public LocalDateTime getFechaHoraRegistro() {
		return fechaHoraRegistro;
	}

	public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Asistencia [fechaClase=" + fechaClase + ", fechaHoraRegistro=" + fechaHoraRegistro + ", estado="
				+ estado + "]";
	}

}
