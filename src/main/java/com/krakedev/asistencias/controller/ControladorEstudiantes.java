package com.krakedev.asistencias.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.asistencias.model.Estudiante;
import com.krakedev.asistencias.service.ServicioEstudiantes;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "*")
public class ControladorEstudiantes {

	private final ServicioEstudiantes servicioEstudiantes;

	public ControladorEstudiantes(ServicioEstudiantes servicioEstudiantes) {
		this.servicioEstudiantes = servicioEstudiantes;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estudiante agregar(@RequestBody Estudiante estudiante) {
		servicioEstudiantes.agregar(estudiante);
		return estudiante;
	}

	@GetMapping
	public ArrayList<Estudiante> listar() {
		return servicioEstudiantes.listar();
	}

	@GetMapping("/{cedula}")
	public Estudiante buscarPorCedula(@PathVariable String cedula) {
		return servicioEstudiantes.obtenerPorCedula(cedula);
	}

	@PutMapping("/{cedula}")
	public Estudiante actualizar(@PathVariable String cedula, @RequestBody Estudiante estudiante) {
		servicioEstudiantes.actualizar(cedula, estudiante);
		return servicioEstudiantes.obtenerPorCedula(cedula);
	}

	@DeleteMapping("/{cedula}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable String cedula) {
		servicioEstudiantes.eliminar(cedula);
	}

}
