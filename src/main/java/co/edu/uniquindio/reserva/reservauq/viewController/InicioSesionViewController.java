package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.ReservaApplication;
import co.edu.uniquindio.reserva.reservauq.controller.InicioSesionController;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioNoRegistradoException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.RolEmpleado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private Button btnOcultar;

    @FXML
    private PasswordField PassFieldcontrasenia;

    @FXML
    private Button btnVisible;

    @FXML

    void initialize() {
        InicioSesionControllerService = new InicioSesionController();
        txtContrasenia.setVisible(false);
        btnOcultar.setVisible(false);
        txtContrasenia.setText("");
        PassFieldcontrasenia.setText("");
    }


    @FXML
    void cerrarAppEvent(ActionEvent event) {

    }

    @FXML
    void iniciarSesionEvent(ActionEvent event) throws IOException {
        String ID=txtID.getText();
        String contrasenia;
        if(btnVisible.isVisible())
        {
            contrasenia=PassFieldcontrasenia.getText();
        }
        else
        {
            contrasenia=txtContrasenia.getText();
        }
        UsuarioDto usuarioIniciado;
        EmpleadoDto empleadoIniciado;
        try
        {
            Object queEs=InicioSesionControllerService.inicioSesion(ID,contrasenia);
            if(queEs instanceof UsuarioDto)
            {
                usuarioIniciado=(UsuarioDto) queEs;
                mostrarVentanaUsuarios(usuarioIniciado);
            }
            else if(queEs instanceof EmpleadoDto)
            {
                if(((EmpleadoDto) queEs).rolEmpleado()== RolEmpleado.ADMINISTRADOR || ((EmpleadoDto) queEs).rolEmpleado()== RolEmpleado.EMPLEADO )
                {
                    mostrarVentanaGestion();
                }
            }
        }
        catch (UsuarioNoRegistradoException | CampoVacioException | ContraseniaIncorrectaException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        catch(NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText("No se ha obtenido acceso a nuestra base de datos");
            alert.show();
        }
    }
    public void mostrarVentanaGestion() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReservaApplication.class.getResource("GestionView.fxml"));
            AnchorPane nuevaVentana = (AnchorPane) loader.load();

            // Crear un nuevo stage para la nueva ventana
            Stage nuevaStage = new Stage();
            nuevaStage.setTitle("ReservasUQ");
            Scene nuevaScene = new Scene(nuevaVentana);
            nuevaStage.setScene(nuevaScene);

            nuevaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarVentanaUsuarios(UsuarioDto usuarioDto) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReservaApplication.class.getResource("PrincipalUsuariosView.fxml"));
            AnchorPane nuevaVentana = (AnchorPane) loader.load();

            // Crear un nuevo stage para la nueva ventana
            Stage nuevaStage = new Stage();
            nuevaStage.setTitle("ReservasUQ");
            Scene nuevaScene = new Scene(nuevaVentana);
            nuevaStage.setScene(nuevaScene);
            nuevaStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Ocultar(ActionEvent actionEvent) {
        txtContrasenia.setVisible(false);
        PassFieldcontrasenia.setText(txtContrasenia.getText());
        PassFieldcontrasenia.setVisible(true);
        btnVisible.setVisible(true);
        btnOcultar.setVisible(false);
    }

    public void Visbilizar(ActionEvent actionEvent) {
        PassFieldcontrasenia.setVisible(false);
        txtContrasenia.setText(PassFieldcontrasenia.getText());
        txtContrasenia.setVisible(true);
        btnOcultar.setVisible(true);
        btnVisible.setVisible(false);
    }
}