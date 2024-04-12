package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

public interface IInicioSesionControllerService {

    public boolean inicioSesion(UsuarioDto usuarioDto, EmpleadoDto empleadoDto);
}
