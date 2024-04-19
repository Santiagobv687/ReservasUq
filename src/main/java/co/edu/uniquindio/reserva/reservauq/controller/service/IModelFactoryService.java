package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseñaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.util.ArrayList;
import java.util.List;


public interface IModelFactoryService {

	List<EmpleadoDto> obtenerEmpleados();
	boolean agregarEmpleado(EmpleadoDto empleadoDto);

	boolean eliminarEmpleado(String ID);

	boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto);

	List<UsuarioDto> obtenerUsuario();

	void registraUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseñaIncorrectaException;

	Object iniciarSesion(String ID,String contrasenia)  throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException;

	List<EventoDto> obtenerEventos();

	boolean agregarEvento(EventoDto eventoDto);
}
