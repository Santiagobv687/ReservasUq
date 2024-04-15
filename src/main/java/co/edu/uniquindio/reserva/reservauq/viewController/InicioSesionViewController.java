package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.ReservaApplication;
import co.edu.uniquindio.reserva.reservauq.controller.InicioSesionController;
import co.edu.uniquindio.reserva.reservauq.controller.RegistroController;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseñaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Empleado;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    void iniciarSesionEvent(ActionEvent event) throws IOException {
        mostrarVentanaGestion();
        /*
        String ID=txtID.getText();
        String contrasenia=txtContrasenia.getText();
        UsuarioDto usuarioIniciado;
        EmpleadoDto empleadoIniciado;
        try
        {
            Object queEs=InicioSesionControllerService.inicioSesion(ID,contrasenia);
            if(queEs instanceof UsuarioDto)
            {
                usuarioIniciado=(UsuarioDto) queEs;
            }
            else if(queEs instanceof EmpleadoDto)
            {

            }
        }
        catch (UsuarioNoRegistradoException | CampoVacioException | ContraseñaIncorrectaException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }

         */

    }
    public void mostrarVentanaGestion() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReservaApplication.class.getResource("GestionView.fxml"));
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