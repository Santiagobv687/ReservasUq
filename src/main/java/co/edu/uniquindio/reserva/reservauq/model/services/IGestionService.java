package co.edu.uniquindio.reserva.reservauq.model.services;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.Persona;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.util.ArrayList;


public interface IGestionService {
	public Empleado crearEmpleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) throws EmpleadoException;
	public Boolean eliminarEmpleado(String ID)throws EmpleadoException;
	boolean actualizarEmpleado(String IDActual, Empleado empleado) throws EmpleadoException;
	public boolean  verificarEmpleadoExistente(String ID) throws EmpleadoException;
	public Empleado obtenerEmpleado(String ID);
	public ArrayList<Empleado> obtenerEmpleados();

    void registrarUsuario(Usuario usuario) throws CampoVacioException, UsuarioExistenteException;

    public void validarCampoVacio(String cualquiera, String msg) throws CampoVacioException;

	public void buscarYAgregarUsuario(Usuario usuario, int indice) throws UsuarioExistenteException, UsuarioNoRegistradoException;

	public void agregarUsuario(Usuario usuario);

	public Object iniciarSesion(String ID,String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException;

	public void validarContrasenia(String contrasenia, int indice, Persona persona) throws ContraseñaIncorrectaException;

	public Usuario buscarUsuario(String ID,int indice) throws UsuarioNoRegistradoException;

	public Empleado buscarEmpleado(String ID,int indice) throws UsuarioNoRegistradoException;


	}

