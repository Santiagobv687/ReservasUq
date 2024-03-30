package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;

import java.util.List;


public interface IModelFactoryService {

	List<EmpleadoDto> obtenerEmpleados();
	boolean agregarEmpleado(EmpleadoDto empleadoDto);

	boolean eliminarEmpleado(String cedula);

	boolean actualizarEmpleado(String cedulaActual, EmpleadoDto empleadoDto);


}
