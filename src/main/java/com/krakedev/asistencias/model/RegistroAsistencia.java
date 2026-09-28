package com.krakedev.asistencias.model;

public class RegistroAsistencia {

	private Estudiante estudiante;
	private Asistencia asistencia;

	public RegistroAsistencia() {
	}

	public RegistroAsistencia(Estudiante estudiante, Asistencia asistencia) {
		this.estudiante = estudiante;
		this.asistencia = asistencia;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Asistencia getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(Asistencia asistencia) {
		this.asistencia = asistencia;
	}

	@Override
	public String toString() {
		return "RegistroAsistencia [estudiante=" + estudiante + ", asistencia=" + asistencia + "]";
	}

}
