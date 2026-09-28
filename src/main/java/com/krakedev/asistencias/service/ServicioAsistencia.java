package com.krakedev.asistencias.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.krakedev.asistencias.model.Asistencia;
import com.krakedev.asistencias.model.Estudiante;
import com.krakedev.asistencias.model.RegistroAsistencia;

@Service
public class ServicioAsistencia {

	private final ArrayList<RegistroAsistencia> registros = new ArrayList<>();
	private final ServicioEstudiantes servicioEstudiantes;

	public ServicioAsistencia(ServicioEstudiantes servicioEstudiantes) {
		this.servicioEstudiantes = servicioEstudiantes;
	}

	public RegistroAsistencia registrarAsistencia(String cedula) {
		Estudiante estudiante = servicioEstudiantes.buscarPorCedula(cedula);
		if (estudiante == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No existe un estudiante registrado con la cedula " + cedula);
		}

		Asistencia asistencia = new Asistencia(
				LocalDate.now(),
				LocalDateTime.now(),
				Asistencia.PRESENTE);

		RegistroAsistencia registro = new RegistroAsistencia(estudiante, asistencia);
		registros.add(registro);
		return registro;
	}

	public ArrayList<Asistencia> consultarAsistencia(String cedula) {
		servicioEstudiantes.obtenerPorCedula(cedula);

		ArrayList<Asistencia> asistencias = new ArrayList<>();
		for (RegistroAsistencia registro : registros) {
			if (registro.getEstudiante().getCedula().equals(cedula)) {
				asistencias.add(registro.getAsistencia());
			}
		}
		return asistencias;
	}

}
