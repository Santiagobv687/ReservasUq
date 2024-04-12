package co.edu.uniquindio.reserva.reservauq.viewController;

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
import javafx.scene.control.Alert;
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
        catch (UsuarioNoRegistradoException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        catch (CampoVacioException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
        catch (ContraseñaIncorrectaException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

}
