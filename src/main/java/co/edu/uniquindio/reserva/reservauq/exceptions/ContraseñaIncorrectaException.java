package co.edu.uniquindio.reserva.reservauq.exceptions;

public class ContraseñaIncorrectaException extends Exception {

    public ContraseñaIncorrectaException() {
        super("La contraseña es incorrecta");
    }

    public ContraseñaIncorrectaException(String message) {
        super(message);
    }
}
