package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EditarUsuarioController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.util.Objects;
import java.util.Optional;

public class EditarUsuarioViewController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEliminarPerfil;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;


    EditarUsuarioController editarUsuarioControllerService;
    ObservableList<UsuarioDto> listaUsuariosDto= FXCollections.observableArrayList();
    UsuarioDto usuarioIniciado;
    private EventNotifier notifier;
    String idUsuario;

    @FXML
    void initialize() {
        editarUsuarioControllerService= new EditarUsuarioController();
        notifier = EventNotifier.getInstance(); // Obtener la instancia del notifier
        initView();
    }

    public void initView(){
        this.usuarioIniciado=editarUsuarioControllerService.obtenerUsuarioDto();
        txtNombre.setText(usuarioIniciado.nombre());
        txtID.setText(usuarioIniciado.ID());
        txtCorreo.setText(usuarioIniciado.correo());
        txtContrasenia.setText(usuarioIniciado.contrasenia());
    }

    @FXML
    void actualizarUsuarioEvent(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void eliminarPerfilEvent(ActionEvent event) {
        eliminarPerfil();

    }
    private UsuarioDto construirUsuarioDto() {

        return new UsuarioDto(
                txtID.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtContrasenia.getText(),
                usuarioIniciado.listaReservas());
    }

    private void actualizarUsuario() {
        UsuarioDto usuarioModificado=null;
        String IDActual=usuarioIniciado.ID();
        boolean usuarioActualizado=false;
        if(!Objects.equals(txtID.getText(), usuarioIniciado.ID()) || !Objects.equals(txtNombre.getText(), usuarioIniciado.nombre()) || !Objects.equals(txtCorreo.getText(), usuarioIniciado.correo())
        || !Objects.equals(txtContrasenia.getText(), usuarioIniciado.correo())){
            usuarioModificado=construirUsuarioDto();
            if(esValido(usuarioModificado)){
                usuarioActualizado = editarUsuarioControllerService.actualizarUsuario(IDActual,usuarioModificado);
                if(usuarioActualizado){
                    mostrarMensaje("Notificación", "Perfil actualizado", "El perfil se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                }
            else{
                    mostrarMensaje("Notificación", "Perfil no actualizado", "No se ha podido actualizar al perfil.", Alert.AlertType.INFORMATION);
                }
            }
        }
        else{
            mostrarMensaje("Notificación Perfil", "Perfil no Modificado", "No hay ningun cambio para actualizar", Alert.AlertType.WARNING);
        }
    }

    private void eliminarPerfil(){
        boolean usuarioEliminado = false;
        if(mostrarMensajeConfirmacion("¿Estas seguro de eliminar al usuario?")){
            usuarioEliminado = editarUsuarioControllerService.eliminarUsuario(usuarioIniciado.ID());
            if(usuarioEliminado){
                mostrarMensaje("Notificación usuario", "Usuario eliminado", "El usuario se ha eliminado con éxito", Alert.AlertType.INFORMATION);
            }else{
                mostrarMensaje("Notificación usuario", "Usuario no eliminado", "El usuario no se puede eliminar", Alert.AlertType.ERROR);
            }
        }
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
