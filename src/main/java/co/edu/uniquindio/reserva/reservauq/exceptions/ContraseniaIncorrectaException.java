package co.edu.uniquindio.reserva.reservauq.exceptions;

public class ContraseniaIncorrectaException extends Exception {

    public ContraseniaIncorrectaException() {
        super("La contraseña es incorrecta");
    }

    public ContraseniaIncorrectaException(String message) {
        super(message);
    }
}
