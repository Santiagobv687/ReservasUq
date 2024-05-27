package co.edu.uniquindio.reserva.reservauq.utils;

import co.edu.uniquindio.reserva.reservauq.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Persistencia {

    public static final String RUTA_ARCHIVO_COPIA = "src/main/resources/persistencia/respaldo/";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_EMPLEADOS = "src/main/resources/persistencia/archivos/archivoEmpleados.txt";
    public static final String RUTA_ARCHIVO_EVENTOS = "src/main/resources/persistencia/archivos/archivoEventos.txt";
    public static final String RUTA_ARCHIVO_RESERVAS = "src/main/resources/persistencia/archivos/archivoReservas.txt";

    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/ReservasUq_Log.txt";
    public static final String RUTA_ARCHIVO_MODELO_GESTION_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_GESTION_XML = "src/main/resources/persistencia/model.xml";


    public static void cargarDatosArchivos(Gestion gestion) throws FileNotFoundException, IOException {

        //cargar archivo de usuarios
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if(usuariosCargados.size() > 0)
            gestion.getListaUsuarios().addAll(usuariosCargados);

        //cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if(empleadosCargados.size() > 0)
            gestion.getListaEmpleados().addAll(empleadosCargados);

        //cargar archivo eventos
        ArrayList<Evento> eventosCargados = cargarEventos(empleadosCargados);
        if(eventosCargados.size() > 0)
            gestion.getListaEventos().addAll(eventosCargados);

        //cargar archivo reservas
        ArrayList<Reserva> reservasCargadas = cargarReservas(usuariosCargados, eventosCargados);
        if(reservasCargadas.size() > 0)
            gestion.getListaReservas().addAll(reservasCargadas);

    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param
     * @throws IOException
     */
    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        // TODO Auto-generated method stub
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getID()+
                    "@@"+usuario.getNombre()+
                    "@@"+usuario.getCorreo()+
                    "@@"+usuario.getContrasenia()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }


    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        for(Empleado empleado:listaEmpleados)
        {
            contenido+= empleado.getID()+
                    "@@"+empleado.getNombre()+
                    "@@"+empleado.getCorreo()+
                    "@@"+empleado.getContrasenia()+
                    "@@"+empleado.getRolEmpleado().toString()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
    }

    public static void guardarEventos(ArrayList<Evento> listaEventos){
        StringBuilder contenido=new StringBuilder();
        for(Evento evento:listaEventos){
            contenido.append(evento.getIDEvento())
                    .append("@@")
                    .append(evento.getNombreEvento())
                    .append("@@")
                    .append(evento.getDescripcion())
                    .append("@@")
                    .append(evento.getFecha().toString())
                    .append("@@")
                    .append(evento.getCapacidadMax())
                    .append("@@")
                    .append(evento.getEmpleadoEncargado().getID())
                    .append("\n");
        }
        try {
            ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EVENTOS, contenido.toString(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void guardarReservas(ArrayList<Reserva> listaReservas) throws IOException {
        StringBuilder contenido = new StringBuilder();
        for (Reserva reserva : listaReservas) {
            contenido.append(reserva.getIDReserva())
                    .append("@@")
                    .append(reserva.getUsuario().getID())
                    .append("@@")
                    .append(reserva.getEvento().getIDEvento())
                    .append("@@")
                    .append(reserva.getFechaSolicitud().toString())
                    .append("@@")
                    .append(reserva.getEstado().toString())
                    .append("\n");
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_RESERVAS, contenido.toString(), false);
    }


//	----------------------LOADS------------------------

    public static ArrayList<Empleado> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados =new ArrayList<Empleado>();
        ArrayList<Evento> listaEventos=new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);
            Empleado empleado = new Empleado();
            empleado.setID(linea.split("@@")[0]);
            empleado.setNombre(linea.split("@@")[1]);
            empleado.setCorreo(linea.split("@@")[2]);
            empleado.setContrasenia(linea.split("@@")[3]);
            empleado.setRolEmpleado(RolEmpleado.valueOf(linea.split("@@")[4]));
            empleado.setListaEventos(listaEventos);
            empleados.add(empleado);
        }
        return empleados;
    }

    public static ArrayList<Usuario> cargarUsuarios(){
        ArrayList<Usuario> usuarios=new ArrayList<>();
        ArrayList<Reserva> listaReservas=new ArrayList<>();
        try {
            ArrayList<String> contenido=ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
            String linea="";
            for(int i=0; i<contenido.size();i++){
                linea=contenido.get(i);
                Usuario usuario=new Usuario();
                usuario.setID(linea.split("@@")[0]);
                usuario.setNombre(linea.split("@@")[1]);
                usuario.setCorreo(linea.split("@@")[2]);
                usuario.setContrasenia(linea.split("@@")[3]);
                usuario.setListaReservas(listaReservas);
                usuarios.add(usuario);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public static ArrayList<Evento> cargarEventos(ArrayList<Empleado> listaEmpleados) {
        ArrayList<Evento> eventos = new ArrayList<>();
        ArrayList<Reserva> listaReservas=new ArrayList<>();
        try {
            ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EVENTOS);
            for (String linea : contenido) {
                String[] datos = linea.split("@@");
                Evento evento = new Evento();
                evento.setIDEvento(datos[0]);
                evento.setNombreEvento(datos[1]);
                evento.setDescripcion(datos[2]);
                evento.setFecha(LocalDate.parse(datos[3]));
                evento.setCapacidadMax(Integer.parseInt(datos[4]));

                // Buscar el empleado encargado en la lista de empleados
                for (Empleado empleado : listaEmpleados) {
                    if (empleado.getID().equals(datos[5])) {
                        evento.setEmpleadoEncargado(empleado);
                        empleado.getListaEventos().add(evento); // Agregar el evento a la lista de eventos del empleado
                        break;
                    }
                }
                evento.setListaReservas(listaReservas);
                eventos.add(evento);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return eventos;
    }

    public static ArrayList<Reserva> cargarReservas(ArrayList<Usuario> listaUsuarios, ArrayList<Evento> listaEventos) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        try {
            ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_RESERVAS);
            for (String linea : contenido) {
                String[] datos = linea.split("@@");
                Reserva reserva = new Reserva();
                reserva.setIDReserva(datos[0]);

                // Buscar el usuario en la lista de usuarios
                for (Usuario usuario : listaUsuarios) {
                    if (usuario.getID().equals(datos[1])) {
                        reserva.setUsuario(usuario);
                        usuario.getListaReservas().add(reserva); // Agregar la reserva a la lista de reservas del usuario
                        break;
                    }
                }

                // Buscar el evento en la lista de eventos
                for (Evento evento : listaEventos) {
                    if (evento.getIDEvento().equals(datos[2])) {
                        reserva.setEvento(evento);
                        evento.getListaReservas().add(reserva); // Agregar la reserva a la lista de reservas del evento
                        break;
                    }
                }

                reserva.setFechaSolicitud(LocalDate.parse(datos[3]));
                reserva.setEstado(EstadoReserva.valueOf(datos[4]));
                reservas.add(reserva);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reservas;
    }



    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

    //------------------------------------SERIALIZACIÓN  y XML


    public static Gestion cargarRecursoGestionBinario() {

        Gestion gestion = null;

        try {
            gestion = (Gestion)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_GESTION_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gestion;
    }

    public static void guardarRecursoGestionBinario(Gestion gestion) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_GESTION_BINARIO, gestion);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static Gestion cargarRecursoGestionXML() {

        Gestion gestion = null;

        try {
            gestion = (Gestion)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_GESTION_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gestion;

    }



    public static void guardarRecursoGestionXML(Gestion gestion) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_GESTION_XML, gestion);
            //ArchivoUtil.crearCopiaXML(RUTA_ARCHIVO_MODELO_GESTION_XML, RUTA_ARCHIVO_COPIA);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}