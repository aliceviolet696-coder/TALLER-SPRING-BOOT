package com.krakedev.asistencias.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.asistencias.model.Asistencia;
import com.krakedev.asistencias.model.RegistroAsistencia;
import com.krakedev.asistencias.service.ServicioAsistencia;

@RestController
@RequestMapping("/asistencias")
@CrossOrigin(origins = "*")
public class ControladorAsistencia {

	private final ServicioAsistencia servicioAsistencia;

	public ControladorAsistencia(ServicioAsistencia servicioAsistencia) {
		this.servicioAsistencia = servicioAsistencia;
	}

	@PostMapping("/{cedula}")
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroAsistencia registrarAsistencia(@PathVariable String cedula) {
		return servicioAsistencia.registrarAsistencia(cedula);
	}

	@GetMapping("/{cedula}")
	public ArrayList<Asistencia> consultarAsistencia(@PathVariable String cedula) {
		return servicioAsistencia.consultarAsistencia(cedula);
	}

}
