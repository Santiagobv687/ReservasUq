package co.edu.uniquindio.reserva.reservauq.model;

import co.edu.uniquindio.reserva.reservauq.exceptions.PersonaException;
import co.edu.uniquindio.reserva.reservauq.model.services.IGestionService;

import java.util.ArrayList;


public class Gestion implements IGestionService {
	ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	ArrayList<Empleado> listaEmpleados = new ArrayList<>();
	ArrayList<Reserva> listaReservas= new ArrayList<>();
	ArrayList<Evento> listaEventos= new ArrayList<>();


	public Gestion() {
	}

	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ArrayList<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}



	@Override
	public Empleado crearEmpleado(String ID, String nombre, String correo, ArrayList<Evento> listaEventos) throws PersonaException {
		Empleado nuevoEmpleado = null;
		boolean empleadoExiste = verificarEmpleadoExistente(ID);
		if(empleadoExiste){
			throw new PersonaException("El empleado con ID: "+ID+" ya existe");
		}else{
			nuevoEmpleado = new Empleado();
			nuevoEmpleado.setNombre(nombre);
			nuevoEmpleado.setID(ID);
			nuevoEmpleado.setCorreo(correo);
			nuevoEmpleado.setListaEventos(listaEventos);
		}
		return nuevoEmpleado;
	}

	public void agregarEmpleado(Empleado nuevoEmpleado) throws PersonaException {
		listaEmpleados.add(nuevoEmpleado);
	}

	@Override
	public boolean actualizarEmpleado(String IDActual, Empleado empleado) throws PersonaException {
		Empleado empleadoActual = obtenerEmpleado(IDActual);
		if(empleadoActual == null)
			throw new PersonaException("El empleado a actualizar no existe");
		else{
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setID(empleado.getID());
			empleadoActual.setCorreo(empleado.getCorreo());
			return true;
		}
	}

	@Override
	public Boolean eliminarEmpleado(String cedula) throws PersonaException {
		Empleado empleado = null;
		boolean flagExiste = false;
		empleado = obtenerEmpleado(cedula);
		if(empleado == null)
			throw new PersonaException("El empleado a eliminar no existe");
		else{
			getListaEmpleados().remove(empleado);
			flagExiste = true;
		}
		return flagExiste;
	}

	@Override
	public boolean verificarEmpleadoExistente(String ID) throws PersonaException {
		if(empleadoExiste(ID)){
			throw new PersonaException("El empleado con ID: "+ID+" ya existe");
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
}
