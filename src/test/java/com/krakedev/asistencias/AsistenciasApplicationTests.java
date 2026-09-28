package com.krakedev.asistencias;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.krakedev.asistencias.model.Asistencia;
import com.krakedev.asistencias.model.Estudiante;
import com.krakedev.asistencias.model.RegistroAsistencia;
import com.krakedev.asistencias.service.ServicioAsistencia;
import com.krakedev.asistencias.service.ServicioEstudiantes;

@SpringBootTest
class AsistenciasApplicationTests {

	@Autowired
	private ServicioEstudiantes servicioEstudiantes;

	@Autowired
	private ServicioAsistencia servicioAsistencia;

	@Test
	void elContextoCarga() {
		assertNotNull(servicioEstudiantes);
		assertNotNull(servicioAsistencia);
	}

	@Test
	void agregarNoPermiteDuplicados() {
		Estudiante estudiante = new Estudiante("1111111111", "Ana", "Ruiz");
		servicioEstudiantes.agregar(estudiante);

		assertThat(servicioEstudiantes.buscarPorCedula("1111111111")).isSameAs(estudiante);
		assertThrows(ResponseStatusException.class,
				() -> servicioEstudiantes.agregar(new Estudiante("1111111111", "Otro", "Estudiante")));
	}

	@Test
	void losServiciosCompartenLaMismaInstanciaDeEstudiantes() {
		Estudiante estudiante = new Estudiante("6666666666", "Sofia", "Diaz");
		servicioEstudiantes.agregar(estudiante);

		RegistroAsistencia registro = servicioAsistencia.registrarAsistencia("6666666666");

		assertThat(registro.getEstudiante()).isSameAs(estudiante);
	}

	@Test
	void registrarAsistenciaGuardaFechaEstadoYHora() {
		Estudiante estudiante = new Estudiante("2222222222", "Luis", "Torres");
		servicioEstudiantes.agregar(estudiante);

		RegistroAsistencia registro = servicioAsistencia.registrarAsistencia("2222222222");

		assertThat(registro.getAsistencia().getEstado()).isEqualTo(Asistencia.PRESENTE);
		assertThat(registro.getAsistencia().getFechaClase()).isEqualTo(java.time.LocalDate.now());
		assertThat(registro.getAsistencia().getFechaHoraRegistro()).isNotNull();
		assertThat(servicioAsistencia.consultarAsistencia("2222222222")).hasSize(1);
	}

	@Test
	void fallaSiElEstudianteNoExiste() {
		assertThrows(ResponseStatusException.class, () -> servicioAsistencia.registrarAsistencia("3333333333"));
		assertThrows(ResponseStatusException.class, () -> servicioAsistencia.consultarAsistencia("3333333333"));
		assertThrows(ResponseStatusException.class, () -> servicioEstudiantes.obtenerPorCedula("3333333333"));
	}

	@Test
	void actualizarYEliminar() {
		Estudiante estudiante = new Estudiante("4444444444", "Maria", "Cruz");
		servicioEstudiantes.agregar(estudiante);

		servicioEstudiantes.actualizar("4444444444", new Estudiante(null, "Maria Jose", "Cruz Soto"));
		assertThat(servicioEstudiantes.buscarPorCedula("4444444444").getNombre()).isEqualTo("Maria Jose");
		assertThat(servicioEstudiantes.buscarPorCedula("4444444444").getApellido()).isEqualTo("Cruz Soto");
		assertThat(servicioEstudiantes.buscarPorCedula("4444444444").getCedula()).isEqualTo("4444444444");

		servicioEstudiantes.eliminar("4444444444");
		assertThat(servicioEstudiantes.buscarPorCedula("4444444444")).isNull();
	}

	@Test
	void listarDevuelveLosEstudiantesRegistrados() {
		int antes = servicioEstudiantes.listar().size();
		servicioEstudiantes.agregar(new Estudiante("5555555555", "Pedro", "Gomez"));

		assertThat(servicioEstudiantes.listar()).hasSize(antes + 1);
		assertTrue(servicioEstudiantes.listar().stream().anyMatch(e -> e.getCedula().equals("5555555555")));
	}

	@Test
	void validarCamposObligatorios() {
		assertThrows(ResponseStatusException.class, () -> servicioEstudiantes.agregar(new Estudiante(null, "X", "Y")));
		assertThrows(ResponseStatusException.class, () -> servicioEstudiantes.agregar(new Estudiante("  ", "X", "Y")));
		assertThrows(ResponseStatusException.class, () -> servicioEstudiantes.agregar(new Estudiante("7777777777", null, "Y")));
		assertThrows(ResponseStatusException.class, () -> servicioEstudiantes.agregar(new Estudiante("7777777777", "X", null)));
	}

}
