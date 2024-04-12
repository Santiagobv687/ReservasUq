package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.mapping.mappers.BancoMapper;
import co.edu.uniquindio.reserva.reservauq.controller.service.IModelFactoryService;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import co.edu.uniquindio.reserva.reservauq.utils.BancoUtils;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Gestion;

import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryService {
    Gestion gestion;
    BancoMapper mapper = BancoMapper.INSTANCE;

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
        gestion = BancoUtils.inicializarDatos();
    }

    public Gestion getBanco() {
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
                getBanco().agregarEmpleado(empleado);
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
            flagExiste = getBanco().eliminarEmpleado(ID);
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
            getBanco().actualizarEmpleado(IDActual, empleado);
            return true;
        } catch (EmpleadoException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Metodos usuarios

    @Override
     public List<UsuarioDto> obtenerUsuario() {
        return mapper.getUsuariosDto(gestion.getListaClientes());
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
}
