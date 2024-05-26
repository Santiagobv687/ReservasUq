package co.edu.uniquindio.reserva.reservauq.controller;

import co.edu.uniquindio.reserva.reservauq.config.RabbitFactory;
import co.edu.uniquindio.reserva.reservauq.exceptions.*;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.mapping.mappers.GestionMapper;
import co.edu.uniquindio.reserva.reservauq.controller.service.IModelFactoryService;
import co.edu.uniquindio.reserva.reservauq.model.*;
import co.edu.uniquindio.reserva.reservauq.utils.BoundedSemaphore;
import co.edu.uniquindio.reserva.reservauq.utils.GestionUtils;
import co.edu.uniquindio.reserva.reservauq.utils.Persistencia;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ModelFactoryController implements IModelFactoryService, Runnable {
    Gestion gestion;
    GestionMapper mapper = GestionMapper.INSTANCE;
    BoundedSemaphore semaphore = new BoundedSemaphore(1);
    String mensaje = "";
    int nivel = 0;
    String accion = "";
    Thread hilo1GuardarXml;
    Thread hilo2GuardarLog;
    Thread hiloServicioConsumer1;
    Thread hiloServicioConsumer2;
    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;
    final String FILAACTUALIZARRESERVA="fila actualizar reserva";
    final String FILAAGREGARRESERVA="fila agregar reserva";
    ReservaDto reserva;
    ArrayList<ReservaDto> reservas;

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
		cargarDatosDesdeArchivos();

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
        initRabbitConnection();
        registrarAccionesSistema("Inicio de la Aplicacion", 1, "inicioAplicacion");
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecidad");
    }

    public void consumirMensajesServicio1(){
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }

    public void consumirMensajesServicio2(){
        hiloServicioConsumer2 = new Thread(this);
        hiloServicioConsumer2.start();
    }




    public Gestion getGestion() {
        return gestion;
    }

    public void setGestion(Gestion gestion) {
        this.gestion = gestion;
    }

    //Metodos Empleados
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
    public UsuarioDto obtenerUsuarioDto(){
        Usuario usuario=getGestion().obtenerUsuarioDto();
        UsuarioDto usuarioDto=mapper.usuarioToUsuarioDto(usuario);
        return usuarioDto;
    }


    @Override
    public void registraUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseniaIncorrectaException {
        Usuario usuario = mapper.usuarioDtoToUsuario(usuarioDto);
        gestion.registrarUsuario(usuario);
        registrarAccionesSistema("Se ha registradoal usuario: " + usuario.getID(), 1, "registrarUsuario");
    }

    @Override
    public Object iniciarSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseniaIncorrectaException {
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
        List<Evento> listaEventos = gestion.getListaEventos();
        List<EventoDto> listaEventosDto = new ArrayList<>();

        for (Evento evento : listaEventos) {
            // Convertir usuario y evento a sus respectivos DTOs
            EmpleadoDto empleadoDto= mapper.empleadoToEmpleadoDto(evento.getEmpleadoEncargado());

            // Crear un nuevo ReservaDto con los DTOs convertidos
            EventoDto eventoDto = new EventoDto(
                    evento.getIDEvento(),
                    evento.getNombreEvento(),
                    evento.getDescripcion(),
                    evento.getFecha(),
                    evento.getCapacidadMax(),
                    empleadoDto,
                    evento.getListaReservas()
            );

            // Añadir a la lista de ReservaDto
            listaEventosDto.add(eventoDto);
        }

        return listaEventosDto;
    }
    @Override
    public boolean agregarEvento(EventoDto eventoDto) {
        try {
            if (!gestion.verificarEventoExistente(eventoDto.IDEvento())) {
                Evento evento = mapper.eventoDtoToEvento(eventoDto);
                Empleado empleado = mapper.empleadoDtoToEmpleado(eventoDto.empleado());

                // Asignar el empleado al evento
                evento.setEmpleadoEncargado(empleado);

                // Agregar el evento a la lista de eventos del empleado
                ArrayList<Evento> listaEventosEmpleado = empleado.getListaEventos(); // Asumiendo que existe este método
                if (listaEventosEmpleado == null) {
                    listaEventosEmpleado = new ArrayList<>();
                    empleado.setListaEventos(listaEventosEmpleado); // Asumiendo que existe este método
                }
                listaEventosEmpleado.add(evento);

                // Guardar el evento en la gestión
                getGestion().agregarEvento(evento);

                // Registrar la acción del sistema
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
            // Obtener el evento actual antes de actualizar
            Evento eventoActual = getGestion().obtenerEvento(IDActual);
            Empleado empleadoAnterior = eventoActual.getEmpleadoEncargado();

            // Mapear el DTO al modelo de evento
            Evento evento = mapper.eventoDtoToEvento(eventoDto);
            Empleado empleadoNuevo = mapper.empleadoDtoToEmpleado(eventoDto.empleado());
            evento.setEmpleadoEncargado(empleadoNuevo);

            // Verificar si el empleado ha cambiado
            if (!empleadoAnterior.getID().equals(empleadoNuevo.getID())) {
                // Eliminar el evento de la lista de eventos del empleado anterior
                List<Evento> listaEventosEmpleadoAnterior = empleadoAnterior.getListaEventos();
                if (listaEventosEmpleadoAnterior != null) {
                    listaEventosEmpleadoAnterior.remove(eventoActual);
                }
            }

            // Actualizar el evento en la gestión
            getGestion().actualizarEvento(IDActual, evento);

            // Agregar el evento a la lista de eventos del nuevo empleado
            ArrayList<Evento> listaEventosEmpleadoNuevo = empleadoNuevo.getListaEventos();
            if (listaEventosEmpleadoNuevo == null) {
                listaEventosEmpleadoNuevo = new ArrayList<>();
                empleadoNuevo.setListaEventos(listaEventosEmpleadoNuevo);
            }
            listaEventosEmpleadoNuevo.add(evento);

            // Registrar la acción del sistema
            registrarAccionesSistema("Se ha actualizado el evento: " + eventoDto.IDEvento(), 1, "actualizarEvento");

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
            // Buscar el evento antes de eliminarlo
            Evento evento = getGestion().obtenerEvento(ID);
            Empleado empleadoEncargado = evento.getEmpleadoEncargado();

            // Eliminar el evento de la lista de eventos del empleado encargado
            if (empleadoEncargado != null) {
                ArrayList<Evento> listaEventosEmpleado = empleadoEncargado.getListaEventos();
                if (listaEventosEmpleado != null) {
                    listaEventosEmpleado.remove(evento);
                }
            }

            // Eliminar el evento en la gestión
            flagExiste = getGestion().eliminarEvento(ID);

            registrarAccionesSistema("Se ha eliminado el evento: " + ID, 1, "eliminarEvento");
            //guardarResourceXML();
        } catch (EventoInexistenteException e) {
            registrarAccionesSistema("No se ha eliminado el evento: " + e.getMessage(), 2, "eliminarEvento");
        }
        return flagExiste;
    }


    //Metodos de Reservas

    @Override
    public List<ReservaDto> obtenerReservas() {
        List<Reserva> listaReservas = gestion.getListaReservas();
        List<ReservaDto> listaReservasDto = new ArrayList<>();

        for (Reserva reserva : listaReservas) {
            // Convertir usuario y evento a sus respectivos DTOs
            UsuarioDto usuarioDto = mapper.usuarioToUsuarioDto(reserva.getUsuario());
            EventoDto eventoDto = mapper.eventoToEventoDto(reserva.getEvento());

            // Crear un nuevo ReservaDto con los DTOs convertidos
            ReservaDto reservaDto = new ReservaDto(
                    reserva.getIDReserva(),
                    usuarioDto,
                    eventoDto,
                    reserva.getFechaSolicitud(),
                    reserva.getEstado()
            );

            // Añadir a la lista de ReservaDto
            listaReservasDto.add(reservaDto);
        }

        return listaReservasDto;
    }

    @Override
    public boolean agregarReserva(ReservaDto reservaDto) {
        try {
            if (!gestion.verificarReservaExistente(reservaDto.IDReserva())) {
                Reserva reserva = mapper.reservaDtoToReserva(reservaDto);
                Usuario usuario = mapper.usuarioDtoToUsuario(reservaDto.usuarioReserva());
                Evento evento = mapper.eventoDtoToEvento(reservaDto.eventoReserva());

                // Establecer relaciones
                reserva.setUsuario(usuario);
                reserva.setEvento(evento);

                // Agregar reserva a las listas de reservas de Usuario y Evento
                usuario.getListaReservas().add(reserva);
                evento.getListaReservas().add(reserva);

                // Agregar reserva a la gestión
                getGestion().agregarReserva(reserva);

                registrarAccionesSistema("Se ha creado la reserva: " + reservaDto.IDReserva(), 1, "agregarReserva");
            }
            return true;
        } catch (ReservaException e) {
            registrarAccionesSistema("No se ha agregado la reserva: " + e.getMessage(), 2, "agregarReserva");
            return false;
        }
    }


    @Override
    public boolean actualizarReserva(String IDActual, ReservaDto reservaDto) {
        try {
            Reserva reservaExistente = getGestion().obtenerReserva(IDActual);
            Reserva reservaNueva = mapper.reservaDtoToReserva(reservaDto);

            Usuario usuarioNuevo = mapper.usuarioDtoToUsuario(reservaDto.usuarioReserva());
            Evento eventoNuevo = mapper.eventoDtoToEvento(reservaDto.eventoReserva());

            // Establecer relaciones para la nueva reserva
            reservaNueva.setUsuario(usuarioNuevo);
            reservaNueva.setEvento(eventoNuevo);

            // Actualizar listas de reservas en Usuario y Evento
            if (!reservaExistente.getUsuario().equals(usuarioNuevo)) {
                reservaExistente.getUsuario().getListaReservas().remove(reservaExistente);
                usuarioNuevo.getListaReservas().add(reservaNueva);
            }

            if (!reservaExistente.getEvento().equals(eventoNuevo)) {
                reservaExistente.getEvento().getListaReservas().remove(reservaExistente);
                eventoNuevo.getListaReservas().add(reservaNueva);
            }

            // Actualizar reserva en la gestión
            getGestion().actualizarReserva(IDActual, reservaNueva);

            registrarAccionesSistema("Se ha actualizado la reserva: " + reservaDto.IDReserva(), 1, "actualizarReserva");
            //guardarResourceXML();
            return true;
        } catch (ReservaInexistenteException e) {
            registrarAccionesSistema("No se ha actualizado la reserva: " + e.getMessage(), 2, "actualizarReserva");
            return false;
        }
    }


    @Override
    public boolean eliminarReserva(String ID) {
        boolean flagExiste = false;
        try {
            // Buscar la reserva antes de eliminarla
            Reserva reserva = getGestion().obtenerReserva(ID);

            // Eliminar la reserva de las listas de reservas de Usuario y Evento
            Usuario usuario = reserva.getUsuario();
            Evento evento = reserva.getEvento();

            if (usuario != null) {
                usuario.getListaReservas().remove(reserva);
            }

            if (evento != null) {
                evento.getListaReservas().remove(reserva);
            }

            // Eliminar la reserva en la gestión
            flagExiste = getGestion().eliminarReserva(ID);

            registrarAccionesSistema("Se ha eliminado la reserva: " + ID, 1, "eliminarReserva");
            //guardarResourceXML();
        } catch (ReservaInexistenteException e) {
            registrarAccionesSistema("No se ha eliminado la reserva: " + e.getMessage(), 2, "eliminarReserva");
        }
        return flagExiste;
    }




    //Metodos de flujo de archivos
    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        this.mensaje = mensaje;
        this.nivel = nivel;
        this.accion = accion;
        hilo2GuardarLog = new Thread(this);
        hilo2GuardarLog.start();
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
            Persistencia.guardarUsuarios(getGestion().getListaUsuarios());
            Persistencia.guardarEventos(getGestion().getListaEventos());
            Persistencia.guardarReservas(getGestion().getListaReservas());
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
        hilo1GuardarXml = new Thread(this);
        hilo1GuardarXml.start();
    }

    private void cargarResourceBinario() {
        gestion = Persistencia.cargarRecursoGestionBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoGestionBinario(gestion);
    }


    //Hilos
    @Override
    public void run() {
        Thread hiloActual = Thread.currentThread();
        ocupar();
        if(hiloActual == hilo1GuardarXml){
            Persistencia.guardarRecursoGestionXML(gestion);
            liberar();
        }
        if(hiloActual == hilo2GuardarLog){
            Persistencia.guardaRegistroLog(mensaje, nivel, accion);
            liberar();
        }
        if(hiloActual == hiloServicioConsumer1){
            consumirMensajes(FILAACTUALIZARRESERVA);
        }
        if(hiloActual == hiloServicioConsumer1){
            consumirMensajes(FILAAGREGARRESERVA);
        }

    }

    private void ocupar() {
        try {
            semaphore.ocupar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void liberar() {
        try {
            semaphore.liberar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void consumirMensajes(String queue) {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queue, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                byte[] objetoSerializado=delivery.getBody();
                try
                {
                    Object objetoDeserializado=deserializarObjeto(objetoSerializado);
                    reserva=(ReservaDto) objetoDeserializado;
                    reservas.add(reserva);
                }
                catch (ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
            };
            while (true)
            {
                channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void producirObjeto(String queue, Object objeto) throws IOException {
        byte objetoSerializado[]=serializarObjeto(objeto);
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, objetoSerializado);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public byte[] serializarObjeto(Object objeto) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(objeto);
        return bos.toByteArray();
    }

    public Object deserializarObjeto(byte[] objetoSerializado) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(objetoSerializado);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object objeto=new Object();
        objeto=ois.readObject();
        return objeto;
    }
}