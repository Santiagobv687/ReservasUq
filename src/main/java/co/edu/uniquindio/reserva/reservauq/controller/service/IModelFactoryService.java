package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
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

	boolean agregarUsuario(UsuarioDto usuarioDto) throws UsuarioException;

	boolean eliminarUsuario(String ID) throws UsuarioException;

	boolean actualizarUsuario(String IDActual, UsuarioDto usuarioDto) throws UsuarioException;

	List<EventoDto> obtenerEventos();

	boolean agregarEvento(EventoDto eventoDto);

	boolean actualizarEvento(String IDActual, EventoDto eventoDto);

	boolean eliminarEvento(String ID);

	List<ReservaDto> obtenerReservas();

	boolean agregarReserva(ReservaDto reservaDto);

	boolean actualizarReserva(String IDActual, ReservaDto reservaDto);

	boolean eliminarReserva(String ID);
}
