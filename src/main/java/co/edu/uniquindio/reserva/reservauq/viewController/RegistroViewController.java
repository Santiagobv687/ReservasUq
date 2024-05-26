package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.RegistroController;
import co.edu.uniquindio.reserva.reservauq.exceptions.CampoVacioException;
import co.edu.uniquindio.reserva.reservauq.exceptions.ContraseniaIncorrectaException;
import co.edu.uniquindio.reserva.reservauq.exceptions.UsuarioExistenteException;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
    private Button btnVisibilizar;

    @FXML
    private Button btnOcultar;

    @FXML
    private PasswordField PassFieldcontrasenia;

    @FXML

    void initialize() {
        registroControllerService = new RegistroController();
        txtContraseña.setVisible(false);
        btnOcultar.setVisible(false);
        txtContraseña.setText("");
        PassFieldcontrasenia.setText("");
    }

    @FXML
    void registrarseEvent(ActionEvent event)  {
        ArrayList<Reserva> listaReservas=new ArrayList<Reserva>();
        String contrasenia;
        if(PassFieldcontrasenia.getText().equals(""))
        {
            contrasenia=txtContraseña.getText();
        }
        else
        {
            contrasenia=PassFieldcontrasenia.getText();
        }
        UsuarioDto usuario=new UsuarioDto(txtID.getText(),txtNombre.getText(),txtCorreo.getText(),contrasenia,listaReservas);
        try
        {
            registroControllerService.registrarUsuario(usuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El usuario ha sido registrado con exito");
            alert.show();
        }
        catch (UsuarioExistenteException | CampoVacioException | ContraseniaIncorrectaException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void Visibilizar(ActionEvent actionEvent) {
        PassFieldcontrasenia.setVisible(false);
        txtContraseña.setText(PassFieldcontrasenia.getText());
        txtContraseña.setVisible(true);
        btnOcultar.setVisible(true);
        btnVisibilizar.setVisible(false);
    }

    public void Ocultar(ActionEvent actionEvent) {
        txtContraseña.setVisible(false);
        PassFieldcontrasenia.setText(txtContraseña.getText());
        PassFieldcontrasenia.setVisible(true);
        btnVisibilizar.setVisible(true);
        btnOcultar.setVisible(false);
    }
}
