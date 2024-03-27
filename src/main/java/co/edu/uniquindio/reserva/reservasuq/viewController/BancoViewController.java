package co.edu.uniquindio.reserva.reservasuq.viewController;

import co.edu.uniquindio.reserva.reservasuq.controller.BancoController;
import co.edu.uniquindio.reserva.reservasuq.controller.service.IBancoControllerService;
import javafx.fxml.FXML;

public class BancoViewController {
    IBancoControllerService bancoControllerService;

    @FXML
    void initialize() {
        bancoControllerService = new BancoController();
    }
}
