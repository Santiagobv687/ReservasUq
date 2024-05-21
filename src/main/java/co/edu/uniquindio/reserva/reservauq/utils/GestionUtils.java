package co.edu.uniquindio.reserva.reservauq.utils;

import co.edu.uniquindio.reserva.reservauq.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.JUNE;

public class GestionUtils {

    public static Gestion inicializarDatos() {
        Gestion gestion = new Gestion();
        Evento evento;

        Empleado empleado = new Empleado();
        empleado.setNombre("Santiago");
        empleado.setID("1234");
        empleado.setCorreo("santiagobv687@gmail.com");
        empleado.setContrasenia("dante");
        empleado.setRolEmpleado(RolEmpleado.ADMINISTRADOR);
        ArrayList<Evento> listaEventos1 = new ArrayList<>();
        empleado.setListaEventos(listaEventos1);
        gestion.getListaEmpleados().add(empleado);

        ArrayList<Reserva> listaReservas = new ArrayList<>();
        evento = new Evento();
        evento.setIDEvento("111");
        evento.setNombreEvento("Cumpleaños");
        evento.setDescripcion("Celebra el cumpleaños de la forma que desees");
        evento.setCapacidadMax(30);
        evento.setEmpleadoEncargado(empleado);
        evento.setFecha(LocalDate.of(2024, APRIL, 14));
        evento.setListaReservas(listaReservas);
        evento.setEmpleadoEncargado(empleado);
        gestion.getListaEventos().add(evento);

        Usuario usuario;
        usuario = new Usuario();
        usuario.setID("68766");
        usuario.setNombre("Julian");
        usuario.setCorreo("julian@gmail.com");
        usuario.setContrasenia("escarlata");
        usuario.setListaReservas(listaReservas);
        gestion.getListaUsuarios().add(usuario);

        Reserva reserva;
        reserva=new Reserva();
        reserva.setIDReserva("1");
        reserva.setUsuario(usuario);
        System.out.println(usuario.getNombre());
        reserva.setEvento(evento);
        System.out.println(evento.getNombreEvento());
        reserva.setFechaSolicitud(LocalDate.of(2024, APRIL, 14));
        reserva.setEstado(EstadoReserva.PENDIENTE);
        gestion.getListaReservas().add(reserva);

        empleado = new Empleado();
        empleado.setNombre("Ana");
        empleado.setID("4567");
        empleado.setCorreo("ana777@gmail.com");
        empleado.setContrasenia("aeiou");
        empleado.setRolEmpleado(RolEmpleado.EMPLEADO);
        ArrayList<Evento> listaEventos2=new ArrayList<>();
        empleado.setListaEventos(listaEventos2);
        gestion.getListaEmpleados().add(empleado);

        empleado = new Empleado();
        empleado.setNombre("Pedro");
        empleado.setID("789");
        empleado.setCorreo("pedroSanchez@hotmail.com");
        empleado.setContrasenia("pedrox");
        empleado.setRolEmpleado(RolEmpleado.EMPLEADO);
        ArrayList<Evento> listaEventos3=new ArrayList<>();
        empleado.setListaEventos(listaEventos3);
        gestion.getListaEmpleados().add(empleado);

        evento=new Evento();
        evento.setIDEvento("2222");
        evento.setNombreEvento("Graduacion");
        evento.setDescripcion("Celebra el cumpleaños de la forma que desees");
        evento.setCapacidadMax(200);
        evento.setEmpleadoEncargado(empleado);
        evento.setFecha(LocalDate.of(2024, APRIL, 14));
        evento.setListaReservas(listaReservas);
        evento.setEmpleadoEncargado(empleado);
        gestion.getListaEventos().add(evento);




        usuario=new Usuario();
        usuario.setID("1111");
        usuario.setNombre("Roman");
        usuario.setCorreo("roman123@gmail.com");
        usuario.setContrasenia("tribal");
        usuario.setListaReservas(listaReservas);
        gestion.getListaUsuarios().add(usuario);

        reserva=new Reserva();
        reserva.setIDReserva("1");
        reserva.setUsuario(usuario);
        reserva.setEvento(evento);
        reserva.setFechaSolicitud(LocalDate.of(2024, JUNE, 16));
        reserva.setEstado(EstadoReserva.RECHAZADA);
        gestion.getListaReservas().add(reserva);

        usuario=new Usuario();
        usuario.setID("5454");
        usuario.setNombre("Cody");
        usuario.setCorreo("cody43@gmail.com");
        usuario.setContrasenia("holabuenas");
        usuario.setListaReservas(listaReservas);
        gestion.getListaUsuarios().add(usuario);

        return gestion;


    }
}
