module co.edu.uniquindio.banco.bancouq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.mapstruct;
    requires java.desktop;
    requires java.logging;
    requires com.rabbitmq.client;

    opens co.edu.uniquindio.reserva.reservauq to javafx.fxml;
    exports co.edu.uniquindio.reserva.reservauq;
    exports co.edu.uniquindio.reserva.reservauq.viewController;
    opens co.edu.uniquindio.reserva.reservauq.viewController to javafx.fxml;
    exports co.edu.uniquindio.reserva.reservauq.controller;
    exports co.edu.uniquindio.reserva.reservauq.mapping.dto;
    exports co.edu.uniquindio.reserva.reservauq.mapping.mappers;
    exports co.edu.uniquindio.reserva.reservauq.model;
    opens co.edu.uniquindio.reserva.reservauq.controller to javafx.fxml;
}