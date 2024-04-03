package co.edu.uniquindio.reserva.reservauq.model.services;

import co.edu.uniquindio.reserva.reservauq.exceptions.PersonaException;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Evento;

import java.util.ArrayList;


public interface IGestionService {
	public Empleado crearEmpleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) throws PersonaException;
	public Boolean eliminarEmpleado(String ID)throws PersonaException;
	boolean actualizarEmpleado(String IDActual, Empleado empleado) throws PersonaException;
	public boolean  verificarEmpleadoExistente(String ID) throws PersonaException;
	public Empleado obtenerEmpleado(String ID);
	public ArrayList<Empleado> obtenerEmpleados();


}
