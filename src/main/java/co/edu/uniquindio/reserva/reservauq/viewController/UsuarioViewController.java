package co.edu.uniquindio.reserva.reservauq.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsuarioViewController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<?> tablaUsuarios;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcCorreo;

    @FXML
    private TableColumn<?, ?> tcID;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    void actualizarUsuarioEvent(ActionEvent event) {

    }

    @FXML
    void agregarUsuarioEvent(ActionEvent event) {

    }

    @FXML
    void eliminarUsuarioEvent(ActionEvent event) {

    }

}
