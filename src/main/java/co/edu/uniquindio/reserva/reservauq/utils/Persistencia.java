package co.edu.uniquindio.reserva.reservauq.utils;

import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Gestion;
import co.edu.uniquindio.reserva.reservauq.model.RolEmpleado;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {


    //bancoUq/src/main/resources/persistencia/archivoClientes.txt

    public static final String RUTA_ARCHIVO_COPIA = "src/main/resources/persistencia/respaldo/";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_EMPLEADOS = "src/main/resources/persistencia/archivos/archivoEmpleados.txt";
    public static final String RUTA_ARCHIVO_EVENTOS = "/src/main/resources/persistencia/archivos/archivoEventos.txt";
    public static final String RUTA_ARCHIVO_RESERVAS = "/src/main/resources/persistencia/archivos/archivoReservas.txt";

    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/ReservasUq_Log.txt";
    public static final String RUTA_ARCHIVO_MODELO_GESTION_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_GESTION_XML = "src/main/resources/persistencia/model.xml";
//	C:\td\persistencia



    public static void cargarDatosArchivos(Gestion gestion) throws FileNotFoundException, IOException {
       /*
        //cargar archivo de clientes
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if(usuariosCargados.size() > 0)
            gestion.getListaUsuarios().addAll(usuariosCargados);


        */
        //cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if(empleadosCargados.size() > 0)
            gestion.getListaEmpleados().addAll(empleadosCargados);

        //cargar archivo transcciones


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
            contenido+= usuario.getID()+"@@"+usuario.getNombre()+"@@"+usuario.getCorreo()+"@@"+usuario.getContrasenia()+"\n";
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



//	----------------------LOADS------------------------
    /*
    /**
     *
     * @param
     * @param
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException

    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException
    {
        ArrayList<Usuario> usuarios =new ArrayList<Usuario>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CLIENTES);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);//juan,arias,125454,Armenia,uni1@,12454,125444
            Cliente cliente = new Cliente();
            cliente.setNombre(linea.split(",")[0]);
            cliente.setApellido(linea.split(",")[1]);
            cliente.setCedula(linea.split(",")[2]);
            cliente.setDireccion(linea.split(",")[3]);
            cliente.setCorreo(linea.split(",")[4]);
            cliente.setFechaNacimiento(linea.split(",")[5]);
            cliente.setTelefono(linea.split(",")[6]);
            clientes.add(cliente);
        }
        return clientes;
    }


     */

    public static ArrayList<Empleado> cargarEmpleados() throws FileNotFoundException, IOException {
        ArrayList<Empleado> empleados =new ArrayList<Empleado>();
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
            empleados.add(empleado);
        }
        return empleados;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

/*
    public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, EmpleadoException {

        if(validarUsuario(usuario,contrasenia)) {
            return true;
        }else {
            throw new EmpleadoException("Usuario no existe");
        }

    }
*/
    /*
    private static boolean validarUsuario(String id, String contrasenia) throws FileNotFoundException, IOException
    {
        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);

        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++)
        {
            Usuario usuarioAux = usuarios.get(indiceUsuario);
            if(usuarioAux.getID().equalsIgnoreCase(id) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
                return true;
            }
        }
        return false;
    }
*/
    /*
    public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios =new ArrayList<Usuario>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
        String linea="";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Usuario usuario = new Usuario();
            usuario.setUsuario(linea.split(",")[0]);
            usuario.setContrasenia(linea.split(",")[1]);

            usuarios.add(usuario);
        }
        return usuarios;
    }


     */

//	----------------------SAVES------------------------
/*
    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     * @param
     * @param ruta
     * @throws IOException
     */

    /*
    public static void guardarObjetos(ArrayList<Cliente> listaClientes, String ruta) throws IOException  {
        String contenido = "";

        for(Cliente clienteAux:listaClientes) {
            contenido+= clienteAux.getNombre()+","+clienteAux.getApellido()+","+clienteAux.getCedula()+clienteAux.getDireccion()
                    +","+clienteAux.getCorreo()+","+clienteAux.getFechaNacimiento()+","+clienteAux.getTelefono()+"\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }

*/



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
            ArchivoUtil.crearCopiaXML(RUTA_ARCHIVO_MODELO_GESTION_XML, RUTA_ARCHIVO_COPIA);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }










}