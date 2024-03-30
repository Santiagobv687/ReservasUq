package co.edu.uniquindio.reserva.reservauq.utils;

import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.Gestion;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;

import java.util.ArrayList;

public class BancoUtils {

    public static Gestion inicializarDatos() {
        Gestion gestion = new Gestion();




        Empleado empleado = new Empleado();
        empleado.setNombre("juan");
        empleado.setID("1234");
        empleado.setCorreo("juanito@gmail.com");
        ArrayList<Evento> listaEventos1=new ArrayList<>();
        empleado.setListaEventos(listaEventos1);
        gestion.getListaEmpleados().add(empleado);

        empleado = new Empleado();
        empleado.setNombre("Ana");
        empleado.setID("4567");
        empleado.setCorreo("ana777@gmail.com");
        ArrayList<Evento> listaEventos2=new ArrayList<>();
        empleado.setListaEventos(listaEventos2);
        gestion.getListaEmpleados().add(empleado);

        empleado = new Empleado();
        empleado.setNombre("Pedro");
        empleado.setID("789");
        empleado.setCorreo("pedroSanchez@hotmail.com");
        ArrayList<Evento> listaEventos3=new ArrayList<>();
        empleado.setListaEventos(listaEventos3);
        gestion.getListaEmpleados().add(empleado);
        System.out.println("Información del gestion creada");
        return gestion;
    }
}
