package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.ReservaApplication;
import co.edu.uniquindio.reserva.reservauq.controller.InicioSesionController;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EditarUsuarioViewController {

    public static Usuario usuarioIniciado;

    @FXML
    void initialize() {
    }

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;


    public void mostrarVentanaGestion() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReservaApplication.class.getResource("EditarUsuario.fxml"));
            AnchorPane nuevaVentana = (AnchorPane) loader.load();

            // Crear un nuevo stage para la nueva ventana
            Stage nuevaStage = new Stage();
            nuevaStage.setTitle("Nueva Ventana");
            Scene nuevaScene = new Scene(nuevaVentana);
            nuevaStage.setScene(nuevaScene);
            
            nuevaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
