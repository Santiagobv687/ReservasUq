package co.edu.uniquindio.reserva.reservauq.model.services;

import co.edu.uniquindio.reserva.reservauq.exceptions.EmpleadoException;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Evento;

import java.util.ArrayList;


public interface IGestionService {
	public Empleado crearEmpleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) throws EmpleadoException;
	public Boolean eliminarEmpleado(String ID)throws EmpleadoException;
	boolean actualizarEmpleado(String IDActual, Empleado empleado) throws EmpleadoException;
	public boolean  verificarEmpleadoExistente(String ID) throws EmpleadoException;
	public Empleado obtenerEmpleado(String ID);
	public ArrayList<Empleado> obtenerEmpleados();


}
