package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;

import java.util.ArrayList;

public interface IRegistroControllerService {

    public void registrarUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException;
}
