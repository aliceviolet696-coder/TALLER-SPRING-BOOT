package com.krakedev.asistencias.service;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.krakedev.asistencias.model.Estudiante;

@Service
public class ServicioEstudiantes {

	private final ArrayList<Estudiante> estudiantes = new ArrayList<>();

	public void agregar(Estudiante estudiante) {
		validar(estudiante);
		if (buscarPorCedula(estudiante.getCedula()) != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"Ya existe un estudiante registrado con la cedula " + estudiante.getCedula());
		}
		estudiantes.add(estudiante);
	}

	public Estudiante buscarPorCedula(String cedula) {
		if (cedula == null) {
			return null;
		}
		for (Estudiante estudiante : estudiantes) {
			if (cedula.equals(estudiante.getCedula())) {
				return estudiante;
			}
		}
		return null;
	}

	public Estudiante obtenerPorCedula(String cedula) {
		Estudiante estudiante = buscarPorCedula(cedula);
		if (estudiante == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No existe un estudiante registrado con la cedula " + cedula);
		}
		return estudiante;
	}

	public void actualizar(String cedula, Estudiante nuevo) {
		Estudiante estudiante = obtenerPorCedula(cedula);
		if (nuevo == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo de la peticion esta vacio");
		}
		estudiante.setNombre(nuevo.getNombre());
		estudiante.setApellido(nuevo.getApellido());
	}

	public void eliminar(String cedula) {
		Estudiante estudiante = obtenerPorCedula(cedula);
		estudiantes.remove(estudiante);
	}

	public ArrayList<Estudiante> listar() {
		return estudiantes;
	}

	private void validar(Estudiante estudiante) {
		if (estudiante == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cuerpo de la peticion esta vacio");
		}
		if (estudiante.getCedula() == null || estudiante.getCedula().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cedula del estudiante es obligatoria");
		}
		if (estudiante.getNombre() == null || estudiante.getNombre().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del estudiante es obligatorio");
		}
		if (estudiante.getApellido() == null || estudiante.getApellido().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El apellido del estudiante es obligatorio");
		}
	}

}
