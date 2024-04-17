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
        //cargarDatosBase();
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

        /*if (gestion == null) {
            cargarDatosBase();
            // guardarResourceXML();
        }

         */
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
                guardarResourceXML();
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
            guardarResourceXML();
        } catch (EmpleadoException e) {
            registrarAccionesSistema("No se ha agregado el empleado: " + e.getMessage(), 2, "agregarEmpleado");

        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getGestion().actualizarEmpleado(IDActual, empleado);
            registrarAccionesSistema("Se ha actualizado al empleado: " + empleadoDto.ID(), 1, "actualizarEmpleado");
            guardarResourceXML();
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
    public void registraUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        gestion.registrarUsuario(usuario);
    }

    @Override
    public Object iniciarSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException {
        Object queEs = gestion.iniciarSesion(ID, contrasenia);
        Usuario usuarioIniciado;
        Empleado empleadoIniciado;
        if (queEs instanceof Usuario) {
            usuarioIniciado = (Usuario) queEs;
            return mapper.usuarioToUsuarioDto(usuarioIniciado);
        }
        empleadoIniciado = (Empleado) queEs;
        return mapper.empleadoToEmpleadoDto(empleadoIniciado);
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
                getGestion().agregarEvento(evento);
            }
            return true;
        } catch (EventoException e) {
            e.getMessage();
            return false;
        }
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
