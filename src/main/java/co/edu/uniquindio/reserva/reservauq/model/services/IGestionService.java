package co.edu.uniquindio.reserva.reservauq.model.services;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.Persona;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.util.ArrayList;


public interface IGestionService {

	Boolean eliminarEmpleado(String ID)throws EmpleadoException;

    Empleado crearEmpleado(String ID, String nombre, String correo, String contrasenia, ArrayList<Evento> listaEventos) throws EmpleadoException;

    boolean actualizarEmpleado(String IDActual, Empleado empleado) throws EmpleadoException;
	boolean  verificarEmpleadoExistente(String ID) throws EmpleadoException;
	Empleado obtenerEmpleado(String ID);
	ArrayList<Empleado> obtenerEmpleados();

    void registrarUsuario(Usuario usuario) throws CampoVacioException, UsuarioExistenteException, ContraseñaIncorrectaException;

    void validarCampoVacio(String cualquiera, String msg) throws CampoVacioException;

	void buscarYAgregarUsuario(Usuario usuario, int indice) throws UsuarioExistenteException, UsuarioNoRegistradoException;

	void agregarUsuario(Usuario usuario);

	Object iniciarSesion(String ID,String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException;

	void validarContrasenia(String contrasenia, int indice, Persona persona) throws ContraseñaIncorrectaException;

	Usuario buscarUsuario(String ID,int indice) throws UsuarioNoRegistradoException;

	 Empleado buscarEmpleado(String ID,int indice) throws UsuarioNoRegistradoException;

	void validarCaracteresContrasenia(String contrasenia,int indice,boolean yaVocal,boolean yaMayus,boolean yaCaracterEspecial) throws ContraseñaIncorrectaException;

	boolean isVocal(char caracterContrasenia);

	boolean isCaracterEspecial(char caracterContrasenia);

	boolean verificarUsuarioExistente(String ID) throws UsuarioException;

	boolean eliminarUsuario(String ID) throws UsuarioException;

	Usuario obtenerUsuario(String ID);

	boolean actualizarUsuario(String IDActual, Usuario usuario) throws UsuarioException;

}

