package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class EditarUsuarioViewController {

    private UsuarioDto usuarioIniciado;

    @FXML
    void initialize() {
        System.out.println(usuarioIniciado);
    }

    public void setearUsuario(UsuarioDto usuarioDto) {
        this.usuarioIniciado=usuarioDto;
    }

    @FXML
    private Button btnActualizar;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;


    @FXML
    public void actualizarUsuarioEvent(ActionEvent actionEvent) {

    }

    @FXML
    public void eliminarUsuarioEvent(ActionEvent actionEvent) {

    }
}
