package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EmpleadoController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;

public class EmpleadoViewController {

    EmpleadoController empleadoControllerService;
    ObservableList<EmpleadoDto> listaEmpleadosDto = FXCollections.observableArrayList();
    EmpleadoDto empleadoSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableView<EmpleadoDto> tableEmpleados;

    @FXML
    private TableColumn<EmpleadoDto, String> tcCorreo;

    @FXML
    private TableColumn<EmpleadoDto, String> tcNombre;

    @FXML
    private TableColumn<EmpleadoDto, String> tcID;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {
        empleadoControllerService = new EmpleadoController();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEmpleados();
        tableEmpleados.getItems().clear();
        tableEmpleados.setItems(listaEmpleadosDto);
        listenerSelection();
    }

    private void initDataBinding() {
        tcID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().ID()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
    }

    private void obtenerEmpleados() {
        listaEmpleadosDto.addAll(empleadoControllerService.obtenerEmpleados());
    }

    private void listenerSelection() {
        tableEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            empleadoSeleccionado = newSelection;
            mostrarInformacionEmpleado(empleadoSeleccionado);
        });
    }

    private void mostrarInformacionEmpleado(EmpleadoDto empleadoSeleccionado) {
        if(empleadoSeleccionado != null){
            txtNombre.setText(empleadoSeleccionado.nombre());
            txtCorreo.setText(empleadoSeleccionado.correo());
            txtID.setText(empleadoSeleccionado.ID());
        }
    }

    @FXML
    void actualizarEmpleadoAction(ActionEvent event) {
        actualizarEmpleado();
    }

    @FXML
    void agregarEmpleadoAction(ActionEvent event) {
        crearEmpleado();
    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) {
        eliminarEmpleado();
    }

    private void crearEmpleado() {
        //1. Capturar los datos
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. Validar la información
        if(datosValidos(empleadoDto)){
            if(empleadoControllerService.agregarEmpleado(empleadoDto)){
                listaEmpleadosDto.add(empleadoDto);
                mostrarMensaje("Notificación empleado", "Empleado creado", "El empleado se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEmpleado();
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "El empleado no se ha creado con éxito", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void eliminarEmpleado() {
        boolean empleadoEliminado = false;
        if(empleadoSeleccionado != null){
            if(mostrarMensajeConfirmacion("¿Estas seguro de elmininar al empleado?")){
                empleadoEliminado = empleadoControllerService.eliminarEmpleado(empleadoSeleccionado.ID());
                if(empleadoEliminado == true){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    empleadoSeleccionado = null;
                    tableEmpleados.getSelectionModel().clearSelection();
                    limpiarCamposEmpleado();
                    mostrarMensaje("Notificación empleado", "Empleado eliminado", "El empleado se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no eliminado", "El empleado no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        }else{
            mostrarMensaje("Notificación empleado", "Empleado no seleccionado", "Seleccionado un empleado de la lista", Alert.AlertType.WARNING);
        }
    }

    private void actualizarEmpleado() {
        boolean clienteActualizado = false;
        //1. Capturar los datos
        String IDActual = empleadoSeleccionado.ID();
        EmpleadoDto empleadoDto = construirEmpleadoDto();
        //2. verificar el empleado seleccionado
        if(empleadoSeleccionado != null){
            //3. Validar la información
            if(datosValidos(empleadoSeleccionado)){
                clienteActualizado = empleadoControllerService.actualizarEmpleado(IDActual,empleadoDto);
                if(clienteActualizado){
                    listaEmpleadosDto.remove(empleadoSeleccionado);
                    listaEmpleadosDto.add(empleadoDto);
                    tableEmpleados.refresh();
                    mostrarMensaje("Notificación empleado", "Empleado actualizado", "El empleado se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposEmpleado();
                }else{
                    mostrarMensaje("Notificación empleado", "Empleado no actualizado", "El empleado no se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                }
            }else{
                mostrarMensaje("Notificación empleado", "Empleado no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
            }

        }
    }

    private EmpleadoDto construirEmpleadoDto() {
        ArrayList<Evento> listaEventos=new ArrayList<>();
        return new EmpleadoDto(
                txtID.getText(),
                txtNombre.getText(),
                txtCorreo.getText());

    }

    private void limpiarCamposEmpleado() {
        txtID.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
    }

    private boolean datosValidos(EmpleadoDto empleadoDto) {
        String mensaje = "";
        if(empleadoDto.nombre() == null || empleadoDto.nombre().equals(""))
            mensaje += "El nombre es invalido \n" ;
        if(empleadoDto.ID() == null || empleadoDto.ID().equals(""))
            mensaje += "El ID es invalido \n" ;
        if(empleadoDto.correo() == null || empleadoDto.correo().equals(""))
            mensaje += "El correo es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación cliente","Datos invalidos",mensaje, Alert.AlertType.WARNING);
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