package co.edu.uniquindio.reserva.reservauq.exceptions;

public class UsuarioNoRegistradoException extends Exception {

    public UsuarioNoRegistradoException() {
        super("El usuario no se encuentra registrado");
    }
}
