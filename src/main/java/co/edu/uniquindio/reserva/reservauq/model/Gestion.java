package co.edu.uniquindio.reserva.reservauq.model;
import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.model.services.IGestionService;

import java.io.Serializable;
import java.util.ArrayList;

public class Gestion implements IGestionService, Serializable {

	private static final long serialVersionUID=1L;
	ArrayList<Usuario> listaUsuarios = new ArrayList<>();
	ArrayList<Empleado> listaEmpleados = new ArrayList<>();
	ArrayList<Reserva> listaReservas= new ArrayList<>();
	ArrayList<Evento> listaEventos= new ArrayList<>();
	public static String usuarioIniciado="";

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

	public ArrayList<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(ArrayList<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}

	public ArrayList<Evento> getListaEventos() {
		return listaEventos;
	}

	public void setListaEventos(ArrayList<Evento> listaEventos) {
		this.listaEventos = listaEventos;
	}


	public void agregarEmpleado(Empleado nuevoEmpleado) throws EmpleadoException {
		listaEmpleados.add(nuevoEmpleado);
	}

	@Override
	public boolean actualizarEmpleado(String IDActual, Empleado empleado) throws EmpleadoException {
		Empleado empleadoActual = obtenerEmpleado(IDActual);
		if(empleadoActual == null)
			throw new EmpleadoException("El empleado a actualizar no existe");
		else{
			empleadoActual.setNombre(empleado.getNombre());
			empleadoActual.setID(empleado.getID());
			empleadoActual.setCorreo(empleado.getCorreo());
			empleadoActual.setRolEmpleado(empleado.getRolEmpleado());
			empleadoActual.setContrasenia(empleado.getContrasenia());
			empleadoActual.setListaEventos(empleado.getListaEventos());
			return true;
		}
	}

	@Override
	public Boolean eliminarEmpleado(String ID) throws EmpleadoException {
		Empleado empleado = null;
		boolean flagExiste = false;
		empleado = obtenerEmpleado(ID);
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

	public Usuario obtenerUsuarioDto(){
		Usuario usuario=obtenerUsuario(usuarioIniciado);
		return usuario;
	}

	@Override
	public void registrarUsuario(Usuario usuario) throws CampoVacioException, UsuarioExistenteException, ContraseniaIncorrectaException {
		validarCampoVacio(usuario.getNombre(),"Debe indicar su nombre de usuario");
		validarCampoVacio(usuario.getID(),"El usuario debe tener una ID");
		validarCampoVacio(usuario.getCorreo(),"Debe indicar su correo electronico");
		validarCampoVacio(usuario.getContrasenia(),"Debe indicar su contraseña");
		//validarCaracteresContrasenia(usuario.getContrasenia(),0,false,false,false);
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
	public void validarCaracteresContrasenia(String contrasenia,int indice,boolean yaVocal,boolean yaMayus,boolean yaCaracterEspecial) throws ContraseniaIncorrectaException {
		if(indice==contrasenia.length())
		{

			if(!yaVocal||!yaCaracterEspecial||!yaMayus)
			{
				throw new ContraseniaIncorrectaException("La contraseña debe contener por lo menos un caracter en mayuscula, una vocal y un caracter especial");
			}
		}
		else
		{
			if(!yaVocal && isVocal(contrasenia.charAt(indice)))
			{
				yaVocal=true;
			}

			if(!yaMayus && Character.isUpperCase(contrasenia.charAt(indice)))
			{
				yaMayus=true;
			}

			if(!yaCaracterEspecial && isCaracterEspecial(contrasenia.charAt(indice)))
			{
				yaCaracterEspecial=true;
			}

			validarCaracteresContrasenia(contrasenia,indice+1,yaVocal,yaMayus,yaCaracterEspecial);
		}
	}

	@Override

	public boolean isVocal ( char caracterContrasenia) {
		if(caracterContrasenia=='a'||caracterContrasenia=='e'||caracterContrasenia=='i'||caracterContrasenia=='o'||caracterContrasenia=='u'||caracterContrasenia=='A'||caracterContrasenia=='E'||caracterContrasenia=='I'||caracterContrasenia=='O'||caracterContrasenia=='U')
		{
			return true;
		}
		return false;
	}

	@Override

	public boolean isCaracterEspecial( char caracterContrasenia) {
		if(caracterContrasenia>=126&&caracterContrasenia<=254)
		{
			return true;
		}
		return false;
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
		getListaUsuarios().add(usuario);
	}

	@Override
	public Object iniciarSesion(String ID,String contrasenia) throws CampoVacioException, ContraseniaIncorrectaException, UsuarioNoRegistradoException {
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

		usuarioIniciado=isUsuario.getID();
		return isUsuario;
	}

	@Override
	public void validarContrasenia(String contrasenia,int indice,Persona persona) throws ContraseniaIncorrectaException {
		if(!contrasenia.equals(persona.getContrasenia()))
		{
			throw new ContraseniaIncorrectaException();
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

	@Override
	public boolean verificarUsuarioExistente(String ID) throws UsuarioException {
		if(usuarioExiste(ID)){
			throw new UsuarioException("El usuario con ID: " + ID + " ya existe");
		} else {
			return false;
		}
	}

	public boolean usuarioExiste(String ID) {
		boolean usuarioEncontrado = false;
		for (Usuario usuario : getListaUsuarios()) {
			if(usuario.getID().equalsIgnoreCase(ID)){
				usuarioEncontrado = true;
				break;
			}
		}
		return usuarioEncontrado;
	}

	@Override
	public boolean eliminarUsuario(String ID) throws UsuarioException {
		Usuario usuario = null;
		boolean flagExiste = false;
		usuario = obtenerUsuario(ID);
		if(usuario == null)
			throw new UsuarioException("El usuario a eliminar no existe");
		else {
			getListaUsuarios().remove(usuario);
			flagExiste = true;
		}
		return flagExiste;
	}

	@Override
	public Usuario obtenerUsuario(String ID) {
		Usuario usuarioEncontrado = null;
		for (Usuario usuario : getListaUsuarios()) {
			if(usuario.getID().equalsIgnoreCase(ID)){
				usuarioEncontrado = usuario;
				break;
			}
		}
		return usuarioEncontrado;
	}

	@Override
	public boolean actualizarUsuario(String IDActual, Usuario usuario) throws UsuarioException {
		Usuario usuarioActual = obtenerUsuario(IDActual);
		if(usuarioActual == null)
			throw new UsuarioException("El usuario a actualizar no existe");
		else {
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setID(usuario.getID());
			usuarioActual.setCorreo(usuario.getCorreo());
			usuarioActual.setContrasenia(usuario.getContrasenia());
			usuarioActual.setListaReservas(usuario.getListaReservas());
			return true;
		}
	}



	public boolean verificarEventoExistente(String ID) throws EventoException {
		boolean eventoEncontrado = false;
		for (Evento evento : getListaEventos()) {
			if(evento.getIDEvento().equalsIgnoreCase(ID)){
				eventoEncontrado = true;
				break;
			}
		}
		if(eventoEncontrado){
			throw new EventoException("El Evento con ID: "+ID+" ya existe");
		}else{
			return false;
		}
	}

	public boolean verificarIdExistente(String ID) throws IdRepetidaException {
		if (empleadoExiste(ID) || usuarioExiste(ID)) {
			throw new IdRepetidaException("La ID " + ID + " ya existe en el sistema.");
		}
		return false;
	}

	public void agregarEvento(Evento nuevoEvento) throws EventoException {
		listaEventos.add(nuevoEvento);
	}

	public boolean actualizarEvento(String IDActual, Evento evento) throws EventoInexistenteException {
		Evento eventoActual = obtenerEvento(IDActual);
		if (eventoActual == null) {
			throw new EventoInexistenteException("El evento a actualizar no existe");
		} else {
			eventoActual.setNombreEvento(evento.getNombreEvento());
			eventoActual.setIDEvento(evento.getIDEvento());
			eventoActual.setDescripcion(evento.getDescripcion());
			eventoActual.setFecha(evento.getFecha());
			eventoActual.setCapacidadMax(evento.getCapacidadMax());
			eventoActual.setEmpleadoEncargado(evento.getEmpleadoEncargado());
			eventoActual.setListaReservas(evento.getListaReservas());
			return true;
		}
	}

	public Evento obtenerEvento(String IDEvento) {
		Evento eventoEncontrado = null;
		for (Evento evento : getListaEventos()) {
			if (evento.getIDEvento().equalsIgnoreCase(IDEvento)) {
				eventoEncontrado = evento;
				break;
			}
		}
		return eventoEncontrado;
	}

	public boolean eliminarEvento(String ID) throws EventoInexistenteException {
		Evento evento = null;
		boolean flagExiste = false;
		evento = obtenerEvento(ID);
		if (evento == null)
			throw new EventoInexistenteException("El evento a eliminar no existe");
		else {
			getListaEventos().remove(evento);
			flagExiste = true;
		}
		return flagExiste;
	}

	//Metodos de Reservas

	public boolean verificarReservaExistente(String ID) throws ReservaException {
		boolean reservaEncontrada = false;
		for (Reserva reserva : getListaReservas()) {
			if(reserva.getIDReserva().equalsIgnoreCase(ID)){
				reservaEncontrada = true;
				break;
			}
		}
		if(reservaEncontrada){
			throw new ReservaException("La Reserva con ID: " + ID + " ya existe");
		} else {
			return false;
		}
	}

	public void agregarReserva(Reserva nuevaReserva) throws ReservaException {
		listaReservas.add(nuevaReserva);
	}

	public boolean actualizarReserva(String IDActual, Reserva reserva) throws ReservaInexistenteException {
		Reserva reservaActual = obtenerReserva(IDActual);
		if (reservaActual == null) {
			throw new ReservaInexistenteException("La reserva a actualizar no existe");
		} else {
			reservaActual.setIDReserva(reserva.getIDReserva());
			reservaActual.setUsuario(reserva.getUsuario());
			reservaActual.setEvento(reserva.getEvento());
			reservaActual.setFechaSolicitud(reserva.getFechaSolicitud());
			reservaActual.setEstado(reserva.getEstado());
			return true;
		}
	}

	public Reserva obtenerReserva(String IDReserva) {
		Reserva reservaEncontrada = null;
		for (Reserva reserva : getListaReservas()) {
			if (reserva.getIDReserva().equalsIgnoreCase(IDReserva)) {
				reservaEncontrada = reserva;
				break;
			}
		}
		return reservaEncontrada;
	}

	public boolean eliminarReserva(String ID) throws ReservaInexistenteException {
		Reserva reserva = null;
		boolean flagExiste = false;
		reserva = obtenerReserva(ID);
		if (reserva == null)
			throw new ReservaInexistenteException("La reserva a eliminar no existe");
		else {
			getListaReservas().remove(reserva);
			flagExiste = true;
		}
		return flagExiste;
	}
}
