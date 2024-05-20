package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EventoController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.chrono.Chronology;

public class EventoViewController {

    EventoController eventoControllerService;
    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    EventoDto eventoSeleccionado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<EmpleadoDto> comboEmpleado;

    @FXML
    private TableView<EventoDto> tablaEventos;

    @FXML
    private TableColumn<EventoDto, String> tcCapacidad;

    @FXML
    private TableColumn<EventoDto, String> tcDescripcion;

    @FXML
    private TableColumn<EventoDto, String> tcEmpleado;

    @FXML
    private TableColumn<EventoDto, String> tcFecha;

    @FXML
    private TableColumn<EventoDto, String> tcID;

    @FXML
    private TableColumn<EventoDto, String> tcNombre;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    void initialize() {
        eventoControllerService = new EventoController();
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEventos();
        tablaEventos.getItems().clear();
        tablaEventos.setItems(listaEventosDto);
        listenerSelection();
    }



    private void initDataBinding() {
        tcID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().IDEvento()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreEvento()));
        tcDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().descripcion()));
        tcFecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().fecha();
            String fechaString = fecha != null ? fecha.toString() : "";
            return new SimpleStringProperty(fechaString);
        });
        tcCapacidad.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().capacidadMax())));
        //tcEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreEmpleado()));
    }

    private void obtenerEventos() {
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
    }

    private void listenerSelection() {
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            eventoSeleccionado = newSelection;
            mostrarInformacionEvento(eventoSeleccionado);
    });
}

    private void mostrarInformacionEvento(EventoDto eventoSeleccionado) {
        if(eventoSeleccionado!=null){
            txtID.setText(eventoSeleccionado.IDEvento());
            txtNombre.setText(eventoSeleccionado.nombreEvento());
            txtDescripcion.setText(eventoSeleccionado.descripcion());
            txtCapacidad.setText(String.valueOf(eventoSeleccionado.capacidadMax()));
            txtFecha.setValue(eventoSeleccionado.fecha());
        }
    }

    @FXML
    void actualizarEvento(ActionEvent event) {

    }

    @FXML
    void agregarEvento(ActionEvent event) {
        EventoDto eventoDto=construirEventoDto();
        if(esValido(eventoDto)){
            if(eventoControllerService.agregarEvento(eventoDto)){

            }
        }

    }

    @FXML
    void eliminarEvento(ActionEvent event) {

    }

    public EventoDto construirEventoDto(){
        return new EventoDto(
                txtID.getText(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                txtFecha.getValue(),
                Integer.parseInt(txtCapacidad.getText()));
              //  comboEmpleado.getSelectionModel().getSelectedItem().toString());
    }

    private boolean esValido(EventoDto eventoDto) {
        String mensaje = "";
        if(eventoDto.nombreEvento() == null || eventoDto.nombreEvento().equals(""))
            mensaje += "Debe de completar el campo de Nombre\n" ;
        if(eventoDto.IDEvento() == null || eventoDto.IDEvento().equals(""))
            mensaje += "Debe completar el campo de ID\n" ;
        if(eventoDto.descripcion() == null || eventoDto.descripcion().equals(""))
            mensaje += "Debe completar el campo de Descripción\n" ;
        if(eventoDto.fecha() == null)
            mensaje += "Debe completar el campo de Fecha\n" ;
        if(eventoDto.capacidadMax()<=0)
            mensaje += "Debe completar el campo de Fecha\n";
        //if(eventoDto.nombreEmpleado() == null || eventoDto.nombreEmpleado().equals(""))
          //  mensaje += "Debe completar el campo de Nombre del Empleado\n" ;

        if(mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación cliente", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
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

}
