package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.mapping.mappers.GestionMapper;
import co.edu.uniquindio.reserva.reservauq.controller.service.IModelFactoryService;
import co.edu.uniquindio.reserva.reservauq.model.*;
import co.edu.uniquindio.reserva.reservauq.utils.GestionUtils;
import co.edu.uniquindio.reserva.reservauq.utils.Persistencia;

import java.io.IOException;
import java.util.List;

public class ModelFactoryController implements IModelFactoryService {
    Gestion gestion;
    GestionMapper mapper = GestionMapper.INSTANCE;

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        System.out.println("Invocación clase singleton");
        cargarDatosBase();
        //salvarDatosPrueba();

        //2. Cargar los datos de los archivos
		//cargarDatosDesdeArchivos();

        //3. Guardar y Cargar el recurso serializable binario
        //guardarResourceBinario();
        //cargarResourceBinario();


        //4. Guardar y Cargar el recurso serializable XML
        //guardarResourceXML();
        //cargarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if (gestion == null) {
            cargarDatosBase();
            // guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de la Aplicacion", 1, "inicioAplicacion");
    }



    public Gestion getGestion() {
        return gestion;
    }

    public void setGestion(Gestion gestion) {
        this.gestion = gestion;
    }


    @Override
    public List<EmpleadoDto> obtenerEmpleados() {
        return mapper.getEmpleadosDto(gestion.getListaEmpleados());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try {
            if (!gestion.verificarEmpleadoExistente(empleadoDto.ID())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getGestion().agregarEmpleado(empleado);
                registrarAccionesSistema("Se ha agregado al empleado: " + empleadoDto.ID(), 1, "agregarEmpleado");
            //    guardarResourceXML();
            }
            return true;
        } catch (EmpleadoException e) {
            registrarAccionesSistema("No se ha agregado al empleado: " + e.getMessage(), 2, "agregarEmpleado");

            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(String ID) {
        boolean flagExiste = false;
        try {
            flagExiste = getGestion().eliminarEmpleado(ID);
            registrarAccionesSistema("Se ha eliminado al empleado: " + ID, 1, "eliminarEmpleado");
          //  guardarResourceXML();
        } catch (EmpleadoException e) {
            registrarAccionesSistema("No se ha eliminado el empleado: " + e.getMessage(), 2, "agregarEmpleado");

        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getGestion().actualizarEmpleado(IDActual, empleado);
            registrarAccionesSistema("Se ha actualizado al empleado: " + empleadoDto.ID(), 1, "actualizarEmpleado");
           // guardarResourceXML();
            return true;
        } catch (EmpleadoException e) {
            registrarAccionesSistema("No se ha actualizado al empleado:" + e.getMessage(), 2, "agregarEmpleado");

            return false;
        }
    }

    //Metodos usuarios

    @Override
    public List<UsuarioDto> obtenerUsuario() {
        return mapper.getUsuariosDto(gestion.getListaUsuarios());

    }


    @Override
    public void registraUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseñaIncorrectaException {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        gestion.registrarUsuario(usuario);
        registrarAccionesSistema("Se ha registradoal usuario: " + usuario.getID(), 1, "registrarUsuario");
    }

    @Override
    public Object iniciarSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException {
        Object queEs = gestion.iniciarSesion(ID, contrasenia);
        Usuario usuarioIniciado;
        Empleado empleadoIniciado;
        if (queEs instanceof Usuario) {
            usuarioIniciado = (Usuario) queEs;
            registrarAccionesSistema("Se ha iniciado sesion como usuario: " + usuarioIniciado.getID(), 1, "IniciarSesion");
            return mapper.usuarioToUsuarioDto(usuarioIniciado);
        }
        empleadoIniciado = (Empleado) queEs;
        registrarAccionesSistema("Se ha iniciado sesion como empleado: " + empleadoIniciado.getID(), 1, "IniciarSesion");
        return mapper.empleadoToEmpleadoDto(empleadoIniciado);
    }

    @Override
    public boolean agregarUsuario(UsuarioDto usuarioDto) {
        try {
            if (!gestion.verificarUsuarioExistente(usuarioDto.ID())) {
                Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
                getGestion().agregarUsuario(usuario);
                registrarAccionesSistema("Se ha agregado al usuario: " + usuarioDto.ID(), 1, "agregarUsuario");
                //guardarResourceXML();
            }
            return true;
        } catch (UsuarioException e) {
            registrarAccionesSistema("No se ha agregado al usuario: " + e.getMessage(), 2, "agregarUsuario");
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String ID) {
        boolean flagExiste = false;
        try {
            flagExiste = getGestion().eliminarUsuario(ID);
            registrarAccionesSistema("Se ha eliminado al usuario: " + ID, 1, "eliminarUsuario");
            //guardarResourceXML();
        } catch (UsuarioException e) {
            registrarAccionesSistema("No se ha eliminado el usuario: " + e.getMessage(), 2, "agregarUsuario");
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarUsuario(String IDActual, UsuarioDto usuarioDto) {
        try {
            Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
            getGestion().actualizarUsuario(IDActual, usuario);
            registrarAccionesSistema("Se ha actualizado al usuario: " + usuarioDto.ID(), 1, "actualizarUsuario");
            //guardarResourceXML();
            return true;
        } catch (UsuarioException e) {
            registrarAccionesSistema("No se ha actualizado al usuario:" + e.getMessage(), 2, "agregarUsuario");
            return false;
        }
    }

    //Metodos Eventos
    @Override
    public List<EventoDto> obtenerEventos() {
        return mapper.getEventosDto(gestion.getListaEventos());
    }

    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        try {
            if (!gestion.verificarEventoExistente(eventoDto.IDEvento())) {
                Evento evento = mapper.eventoDtoToEvento(eventoDto);
                Empleado empleado=mapper.empleadoDtoToEmpleado(eventoDto.empleado());
                evento.setEmpleadoEncargado(empleado);
                getGestion().agregarEvento(evento);
                registrarAccionesSistema("Se ha creado el evento: " + eventoDto.IDEvento(), 1, "agregarEvento");
            }
            return true;
        } catch (EventoException e) {
            registrarAccionesSistema("No se ha agregado el evento: " + e.getMessage(), 2, "agregarEvento");
            return false;
        }
    }

    @Override
    public boolean actualizarEvento(String IDActual, EventoDto eventoDto) {
        try {
            Evento evento = mapper.eventoDtoToEvento(eventoDto);
            Empleado empleado = mapper.empleadoDtoToEmpleado(eventoDto.empleado());
            evento.setEmpleadoEncargado(empleado);
            getGestion().actualizarEvento(IDActual, evento);
            registrarAccionesSistema("Se ha actualizado el evento: " + eventoDto.IDEvento(), 1, "actualizarEvento");
            //guardarResourceXML();
            return true;
        } catch (EventoInexistenteException e) {
            registrarAccionesSistema("No se ha actualizado el evento: " + e.getMessage(), 2, "actualizarEvento");
            return false;
        }
    }

    @Override
    public boolean eliminarEvento(String ID) {
        boolean flagExiste = false;
        try {
            flagExiste = getGestion().eliminarEvento(ID);
            registrarAccionesSistema("Se ha eliminado el evento: " + ID, 1, "eliminarEvento");
            //guardarResourceXML();
        } catch (EventoInexistenteException e) {
            registrarAccionesSistema("No se ha eliminado el evento: " + e.getMessage(), 2, "eliminarEvento");
        }
        return flagExiste;
    }


    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    private void cargarDatosDesdeArchivos() {
        gestion = new Gestion();
        try {
            Persistencia.cargarDatosArchivos(gestion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarEmpleados(getGestion().getListaEmpleados());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarDatosBase() {
        gestion = GestionUtils.inicializarDatos();
    }
    private void cargarResourceXML() {
        gestion = Persistencia.cargarRecursoGestionXML();
    }

    private void guardarResourceXML() {
        Persistencia.guardarRecursoGestionXML(gestion);
    }

    private void cargarResourceBinario() {
        gestion = Persistencia.cargarRecursoGestionBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoGestionBinario(gestion);
    }


}
