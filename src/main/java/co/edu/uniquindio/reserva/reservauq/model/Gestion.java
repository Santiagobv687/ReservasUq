package co.edu.uniquindio.reserva.reservauq.model;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.model.services.IGestionService;

import java.util.ArrayList;


public class Gestion implements IGestionService {
	ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	ArrayList<Empleado> listaEmpleados = new ArrayList<>();


	public Gestion() {
	}

	public ArrayList<Usuario> getListaClientes() {
		return listaUsuarios;
	}

	public void setListaClientes(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ArrayList<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}



	@Override
	public Empleado crearEmpleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) throws EmpleadoException{
		Empleado nuevoEmpleado = null;
		boolean empleadoExiste = verificarEmpleadoExistente(ID);
		if(empleadoExiste){
			throw new EmpleadoException("El empleado con cedula: "+ID+" ya existe");
		}else{
			nuevoEmpleado = new Empleado();
			nuevoEmpleado.setNombre(nombre);
			nuevoEmpleado.setID(ID);
			nuevoEmpleado.setCorreo(correo);
			nuevoEmpleado.setListaEventos(listaEventos);
		}
		return nuevoEmpleado;
	}

	public void agregarEmpleado(Empleado nuevoEmpleado) throws EmpleadoException{
		getListaEmpleados().add(nuevoEmpleado);
	}

	@Override
	public boolean actualizarEmpleado(String cedulaActual, Empleado empleado) throws EmpleadoException {
		Empleado empleadoActual = obtenerEmpleado(cedulaActual);
		if(empleadoActual == null)
			throw new EmpleadoException("El empleado a actualizar no existe");
		else{
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setID(empleado.getID());
			empleadoActual.setCorreo(empleado.getCorreo());
			return true;
		}
	}

	@Override
	public Boolean eliminarEmpleado(String cedula) throws EmpleadoException {
		Empleado empleado = null;
		boolean flagExiste = false;
		empleado = obtenerEmpleado(cedula);
		if(empleado == null)
			throw new EmpleadoException("El empleado a eliminar no existe");
		else{
			getListaEmpleados().remove(empleado);
			flagExiste = true;
		}
		return flagExiste;
	}

	@Override
	public boolean verificarEmpleadoExistente(String ID) throws EmpleadoException {
		if(empleadoExiste(ID)){
			throw new EmpleadoException("El empleado con ID: "+ID+" ya existe");
		}else{
			return false;
		}
	}


	@Override
	public Empleado obtenerEmpleado(String ID) {
		Empleado empleadoEncontrado = null;
		for (Empleado empleado : getListaEmpleados()) {
			if(empleado.getID().equalsIgnoreCase(ID)){
				empleadoEncontrado = empleado;
				break;
			}
		}
		return empleadoEncontrado;
	}

	@Override
	public ArrayList<Empleado> obtenerEmpleados() {
		// TODO Auto-generated method stub
		return getListaEmpleados();
	}

	public boolean empleadoExiste(String ID) {
		boolean empleadoEncontrado = false;
		for (Empleado empleado : getListaEmpleados()) {
			if(empleado.getID().equalsIgnoreCase(ID)){
				empleadoEncontrado = true;
				break;
			}
		}
		return empleadoEncontrado;
	}

	//Metodos relacionados con los usuarios

	@Override

	public void registrarUsuario(Usuario usuario) throws CampoVacioException, UsuarioExistenteException {
		validarCampoVacio(usuario.getNombre(),"Debe indicar su nombre de usuario");
		validarCampoVacio(usuario.getID(),"El usuario debe tener una ID");
		validarCampoVacio(usuario.getCorreo(),"Debe indicar su correo electronico");
		validarCampoVacio(usuario.getContrasenia(),"Debe indicar su contraseña");
		buscarYAgregarUsuario(usuario,0);
	}

	@Override

	public void validarCampoVacio(String cualquiera,String msg) throws CampoVacioException {
		if(cualquiera.isEmpty()||cualquiera==null)
		{
			throw new CampoVacioException(msg);
		}
	}

	@Override

	public void buscarYAgregarUsuario(Usuario usuario,int indice) throws UsuarioExistenteException {
		if(indice==listaUsuarios.size())
		{
			agregarUsuario(usuario);
		}
		else
		{
			if(listaUsuarios.get(indice).getID().equals(usuario.getID()))
			{
					throw new UsuarioExistenteException();
			}
			else
			{
				buscarYAgregarUsuario(usuario,indice+1);
			}
		}
	}

	@Override

	public void agregarUsuario(Usuario usuario) {
		getListaClientes().add(usuario);
	}

	@Override

	public Object iniciarSesion(String ID,String contrasenia) throws CampoVacioException, ContraseñaIncorrectaException, UsuarioNoRegistradoException {
		validarCampoVacio(ID,"El usuario debe tener una ID");
		validarCampoVacio(contrasenia,"Debe indicar su contraseña");
		Usuario isUsuario=buscarUsuario(ID,0);
		Empleado isEmpleado;
		if(isUsuario==null)
		{
			isEmpleado=buscarEmpleado(ID,0);
			if(isEmpleado==null)
			{
				throw new UsuarioNoRegistradoException();
			}
			validarContrasenia(contrasenia,0,isEmpleado);

			return isEmpleado;
		}
		validarContrasenia(contrasenia,0,isUsuario);

		return isUsuario;
	}

	@Override
	public void validarContrasenia(String contrasenia,int indice,Persona persona) throws ContraseñaIncorrectaException {
		if(!contrasenia.equals(persona.getContrasenia()))
		{
			throw new ContraseñaIncorrectaException();
		}
	}

	@Override
	public Usuario buscarUsuario(String ID, int indice)  {
		if(indice==listaUsuarios.size())
		{
			return null;
		}
		else
		{
			if(listaUsuarios.get(indice).getID().equals(ID))
			{
				return listaUsuarios.get(indice);
			}
			else
			{
					return buscarUsuario(ID,indice+1);
			}
		}
	}

	@Override

	public Empleado buscarEmpleado (String ID,int indice)  {
		if(indice==listaEmpleados.size())
		{
			return null;
		}
		else
		{
			if(listaEmpleados.get(indice).getID().equals(ID))
			{
				return listaEmpleados.get(indice);
			}
			else
			{
				return buscarEmpleado(ID,indice+1);
			}
		}
	}
}
