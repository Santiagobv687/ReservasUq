package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;

public interface IInicioSesionControllerService {

    public Object inicioSesion(String ID, String contrasenia) throws UsuarioNoRegistradoException, CampoVacioException, ContraseniaIncorrectaException;
}
