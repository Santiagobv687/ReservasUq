package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.util.ArrayList;
import java.util.List;


public interface IModelFactoryService {

	List<EmpleadoDto> obtenerEmpleados();
	boolean agregarEmpleado(EmpleadoDto empleadoDto);

	boolean eliminarEmpleado(String cedula);

	boolean actualizarEmpleado(String cedulaActual, EmpleadoDto empleadoDto);

	List<UsuarioDto> obtenerUsuario();

	void registraUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException;
}
