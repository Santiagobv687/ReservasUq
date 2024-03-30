package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.GestionController;
import co.edu.uniquindio.reserva.reservauq.controller.service.IGestionControllerService;
import javafx.fxml.FXML;

public class GestionViewController {
    IGestionControllerService bancoControllerService;

    @FXML
    void initialize() {
        bancoControllerService = new GestionController();
    }
}
