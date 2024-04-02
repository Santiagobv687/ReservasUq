package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.GestionController;
import javafx.fxml.FXML;

public class GestionViewController {
    IGestionControllerService bancoControllerService;

    @FXML
    void initialize() {
        bancoControllerService = new GestionController();
    }
}
