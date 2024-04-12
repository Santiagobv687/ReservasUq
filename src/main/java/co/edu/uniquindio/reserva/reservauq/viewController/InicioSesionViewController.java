package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.InicioSesionController;
import co.edu.uniquindio.reserva.reservauq.controller.RegistroController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class InicioSesionViewController {

    InicioSesionController InicioSesionControllerService;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtID;

    @FXML
    void cerrarAppEvent(ActionEvent event) {

    }

    @FXML
    void iniciarSesionEvent(ActionEvent event) {

    }

}
