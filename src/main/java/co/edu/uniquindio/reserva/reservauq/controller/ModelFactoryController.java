package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.mapping.mappers.GestionMapper;
import co.edu.uniquindio.reserva.reservauq.controller.service.IModelFactoryService;
import co.edu.uniquindio.reserva.reservauq.model.*;
import co.edu.uniquindio.reserva.reservauq.utils.GestionUtils;

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
        System.out.println("invocación clase singleton");
        cargarDatosBase();
    }

    private void cargarDatosBase() {
        gestion = GestionUtils.inicializarDatos();
    }

    public Gestion getGestion() {
        return gestion;
    }

    public void setBanco(Gestion gestion) {
        this.gestion = gestion;
    }


    @Override
    public List<EmpleadoDto> obtenerEmpleados() {
       return  mapper.getEmpleadosDto(gestion.getListaEmpleados());
    }

    @Override
    public boolean agregarEmpleado(EmpleadoDto empleadoDto) {
        try{
            if(!gestion.verificarEmpleadoExistente(empleadoDto.ID())) {
                Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
                getGestion().agregarEmpleado(empleado);
            }
            return true;
        }catch (EmpleadoException e){
            e.getMessage();
            return false;
        }
    }

    @Override
    public boolean eliminarEmpleado(String ID) {
        boolean flagExiste = false;
        try {
            flagExiste = getGestion().eliminarEmpleado(ID);
        } catch (EmpleadoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flagExiste;
    }

    @Override
    public boolean actualizarEmpleado(String IDActual, EmpleadoDto empleadoDto) {
        try {
            Empleado empleado = mapper.empleadoDtoToEmpleado(empleadoDto);
            getGestion().actualizarEmpleado(IDActual, empleado);
            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
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
        Usuario usuario=mapper.usuarioDtoToUsuario(usuarioDto);
        gestion.registrarUsuario(usuario);
    }

    @Override
    public Object iniciarSesion(String ID,String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseñaIncorrectaException {
        Object queEs=gestion.iniciarSesion(ID,contrasenia);
        Usuario usuarioIniciado;
        Empleado empleadoIniciado;
        if(queEs instanceof Usuario)
        {
            usuarioIniciado=(Usuario) queEs;
            return mapper.usuarioToUsuarioDto(usuarioIniciado);
        }
        empleadoIniciado=(Empleado)queEs;
        return mapper.empleadoToEmpleadoDto(empleadoIniciado);
    }

    //Metodos Eventos
    @Override
    public List<EventoDto> obtenerEventos() {
        return mapper.getEventosDto(gestion.getListaEventos());
    }

    @Override
    public boolean agregarEvento(EventoDto eventoDto){
        try{
            if(!gestion.verificarEventoExistente(eventoDto.IDEvento())){
                Evento evento= mapper.eventoDtoToEvento(eventoDto);
                getGestion().agregarEvento(evento);
            }
            return true;
        }
         catch (EventoException e) {
            e.getMessage();
            return false;
        }
    }
}
