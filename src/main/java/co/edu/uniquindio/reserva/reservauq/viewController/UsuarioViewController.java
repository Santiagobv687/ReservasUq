package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.UsuarioController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;

public class UsuarioViewController {
    UsuarioController usuarioControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto= FXCollections.observableArrayList();
    UsuarioDto usuarioSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<UsuarioDto> tablaUsuarios;

    @FXML
    private TableColumn<UsuarioDto, String> tcContra;

    @FXML
    private TableColumn<UsuarioDto, String> tcCorreoUsuario;

    @FXML
    private TableColumn<UsuarioDto, String> tcID;

    @FXML
    private TableColumn<UsuarioDto, String> tcNombreUsuario;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;



    @FXML
    void initialize() {
        usuarioControllerService = new UsuarioController();
        initView();
    }

    private void initView() {

        initDataBinding();
        obtenerUsuarios();
        tablaUsuarios.getItems().clear();
        tablaUsuarios.setItems(listaUsuariosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        this.tcID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ID()));
        this.tcNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        this.tcCorreoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        this.tcContra.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().contrasenia()));
    }

    private void obtenerUsuarios() {
        listaUsuariosDto.addAll(usuarioControllerService.obtenerUsuarios());
    }

    private void listenerSelection() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(UsuarioDto usuarioSeleccionado) {
        if(usuarioSeleccionado != null){
            txtNombre.setText(usuarioSeleccionado.nombre());
            txtCorreo.setText(usuarioSeleccionado.correo());
            txtID.setText(usuarioSeleccionado.ID());
            txtContrasenia.setText(usuarioSeleccionado.contrasenia());
        }
    }

    @FXML
    void actualizarUsuarioEvent(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void agregarUsuarioEvent(ActionEvent event) {
        crearUsuario();
    }

    @FXML
    void eliminarUsuarioEvent(ActionEvent event) {
        eliminarUsuario();
    }

    private void crearUsuario() {
        //1. Capturar los datos
        UsuarioDto usuarioDto = construirUsuarioDto();
        //2. Validar la información
        if(esValido(usuarioDto)){
            if(usuarioControllerService.agregarUsuario(usuarioDto)){
                listaUsuariosDto.add(usuarioDto);
                mostrarMensaje("Notificación usuario", "Usuario creado", "El usuario se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposUsuario();
            }else{
                tablaUsuarios.refresh();
                mostrarMensaje("Notificación usuario", "Usuario no creado", "No se ha podido crear al usuario. Revise los campos de información.", Alert.AlertType.ERROR);
            }
        }
    }

    private void eliminarUsuario() {
        boolean usuarioEliminado = false;
        if(usuarioSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al usuario?")){
                usuarioEliminado = usuarioControllerService.eliminarUsuario(usuarioSeleccionado.ID());
                if(usuarioEliminado == true){
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    usuarioSeleccionado = null;
                    tablaUsuarios.getSelectionModel().clearSelection();
                    limpiarCamposUsuario();
                    mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Seleccionado un usuario de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarUsuario() {
        boolean usuarioActualizado = false;
        //1. Capturar los datos

        //2. verificar el usuario seleccionado
        if(usuarioSeleccionado != null){
            String IDActual = usuarioSeleccionado.ID();
            UsuarioDto usuarioDto = construirUsuarioDto();
            //3. Validar la información
            if(esValido(usuarioDto)){
                usuarioActualizado = usuarioControllerService.actualizarUsuario(IDActual,usuarioDto);
                if(usuarioActualizado){
                    listaUsuariosDto.remove(usuarioSeleccionado);
                    listaUsuariosDto.add(usuarioDto);
                    tablaUsuarios.refresh();
                    mostrarMensaje("Notificación", "Usuario actualizado", "El usuario se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposUsuario();
                }else{
                    mostrarMensaje("Notificación", "Usuario no actualizado", "No se ha podido actualizar al usuario.", Alert.AlertType.INFORMATION);
                }
            }
        }
        else{
            mostrarMensaje("Notificación usuario", "Usuario no seleccionado", "Seleccionado un usuario de la lista", Alert.AlertType.WARNING);
        }
    }

    private UsuarioDto construirUsuarioDto() {
        ArrayList<Reserva> listaReservas=new ArrayList<>();
        return new UsuarioDto(
                txtID.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtContrasenia.getText(),
                listaReservas);
    }

    private void limpiarCamposUsuario() {
        txtID.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContrasenia.setText("");
    }

    private boolean esValido(UsuarioDto usuarioDto) {
        String mensaje = "";
        if(usuarioDto.nombre() == null || usuarioDto.nombre().equals(""))
            mensaje += "Debe de completar el campo de Nombre \n" ;
        if(usuarioDto.ID() == null || usuarioDto.ID().equals(""))
            mensaje += "Debe completar el campo de ID\n" ;
        if(usuarioDto.correo() == null || usuarioDto.correo().equals(""))
            mensaje += "Debe completar el campo de correo \n" ;
        if(usuarioDto.contrasenia() == null || usuarioDto.contrasenia().equals(""))
            mensaje += "Debe completar el campo de contraseña \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación usuario","Datos invalidos",mensaje, Alert.AlertType.WARNING);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }


}
