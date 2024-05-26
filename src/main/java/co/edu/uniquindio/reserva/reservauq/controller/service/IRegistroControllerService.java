package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public interface IRegistroControllerService {

    public void registrarUsuario(UsuarioDto usuarioDto) throws UsuarioExistenteException, CampoVacioException, ContraseniaIncorrectaException;
}
