package co.edu.uniquindio.reserva.reservauq.controller.service;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;

import java.util.List;

public interface IUsuarioControllerService {

    List<UsuarioDto> obtenerUsuarios();

    boolean agregarUsuario(UsuarioDto usuarioDto);

    boolean eliminarUsuario(String ID);

    boolean actualizarUsuario(String IDActual, UsuarioDto usuarioDto);
}
