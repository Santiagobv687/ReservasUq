package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.InicioSesionController;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import javafx.fxml.FXML;

public class EditarUsuarioViewController {

    Usuario usuarioIniciado;

    @FXML

    void initialize() {
        usuarioIniciado = new Usuario();
    }


}
