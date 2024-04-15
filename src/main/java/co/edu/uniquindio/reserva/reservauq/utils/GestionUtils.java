package co.edu.uniquindio.reserva.reservauq.utils;

import co.edu.uniquindio.reserva.reservauq.model.Evento;
import co.edu.uniquindio.reserva.reservauq.model.Gestion;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Calendar.APRIL;

public class GestionUtils {

    public static Gestion inicializarDatos() {
        Gestion gestion = new Gestion();
        Evento evento;

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

        ArrayList<Reserva> listaReservas=new ArrayList<>();
        evento=new Evento();
        evento.setIDEvento("111");
        evento.setNombreEvento("Cumpleaños");
        evento.setDescripcion("Celebra el cumpleaños de la forma que desees");
        evento.setCapacidadMax(30);
        evento.setEmpleadoEncargado(empleado);
        evento.setFecha(LocalDate.of(2024, APRIL, 14));
        evento.setListaReservas(listaReservas);
        gestion.getListaEventos().add(evento);

        evento=new Evento();
        evento.setIDEvento("2222");
        evento.setNombreEvento("Graduacion");
        evento.setDescripcion("Celebra el cumpleaños de la forma que desees");
        evento.setCapacidadMax(200);
        evento.setEmpleadoEncargado(empleado);
        evento.setFecha(LocalDate.of(2024, APRIL, 14));
        evento.setListaReservas(listaReservas);
        gestion.getListaEventos().add(evento);

        return gestion;
    }
}
