module co.edu.uniquindio.banco.bancouq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.mapstruct;

    opens co.edu.uniquindio.reserva.reservasuq to javafx.fxml;
    exports co.edu.uniquindio.reserva.reservasuq;
    exports co.edu.uniquindio.reserva.reservasuq.viewController;
    opens co.edu.uniquindio.reserva.reservasuq.viewController to javafx.fxml;
    exports co.edu.uniquindio.reserva.reservasuq.controller;
    exports co.edu.uniquindio.reserva.reservasuq.mapping.dto;
    exports co.edu.uniquindio.reserva.reservasuq.mapping.mappers;
    exports co.edu.uniquindio.reserva.reservasuq.model;
    opens co.edu.uniquindio.reserva.reservasuq.controller to javafx.fxml;
}