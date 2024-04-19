package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EmpleadoController;
import co.edu.uniquindio.reserva.reservauq.controller.RegistroController;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseñaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import co.edu.uniquindio.reserva.reservauq.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class RegistroViewController {

    RegistroController registroControllerService;


    @FXML
    private Button btnRegistrarse;

    @FXML
    private TextField txtContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML

    void initialize() {
        registroControllerService = new RegistroController();
    }

    @FXML
    void registrarseEvent(ActionEvent event)  {
        ArrayList<Reserva> listaReservas=new ArrayList<Reserva>();
        UsuarioDto usuario=new UsuarioDto(txtID.getText(),txtNombre.getText(),txtCorreo.getText(),txtContraseña.getText(),listaReservas);
        try
        {
            registroControllerService.registrarUsuario(usuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El usuario ha sido registrado con exito");
            alert.show();
        }
        catch (UsuarioExistenteException | CampoVacioException | ContraseñaIncorrectaException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

}
