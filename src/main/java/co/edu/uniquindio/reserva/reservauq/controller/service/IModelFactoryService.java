package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;

import java.util.List;


public interface IModelFactoryService {

	List<EmpleadoDto> obtenerEmpleados();
	boolean agregarEmpleado(EmpleadoDto empleadoDto);

	boolean eliminarEmpleado(String ID);

	boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto);


}
