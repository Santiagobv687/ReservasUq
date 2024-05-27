package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EventoController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.Reserva;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.Optional;

public class EventoViewController {

    EventoController eventoControllerService;
    ObservableList<EventoDto> listaEventosDto = FXCollections.observableArrayList();
    EventoDto eventoSeleccionado;
    ArrayList<EmpleadoDto> listaEmpleadosDto=new ArrayList<>();

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> comboEmpleado;

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

    private EventNotifier notifier;

    @FXML
    void initialize() {
        eventoControllerService = new EventoController();
        notifier = EventNotifier.getInstance(); // Obtener la instancia del notifier
        initView();
        setupEventListeners();
    }

    private void initView() {
        obtenerEventos();
        initDataBinding();

        tablaEventos.getItems().clear();
        tablaEventos.setItems(listaEventosDto);
        listenerSelection();
    }

    private void setupEventListeners() {
        notifier.addPropertyChangeListener(event -> {
            if ("empleadoAgregado".equals(event.getPropertyName()) || "empleadoEliminado".equals(event.getPropertyName())
                    || "empleadoActualizado".equals(event.getPropertyName())) {
                actualizar();
            }
        });
    }

    private void actualizar() {
        comboEmpleado.getItems().clear();
        obtenerEventos();
        tablaEventos.refresh();

        ObservableList<String> nombresEmpleados = FXCollections.observableArrayList();
        for (EmpleadoDto empleado : listaEmpleadosDto) {
            nombresEmpleados.add(empleado.nombre());
        }
        comboEmpleado.setItems(nombresEmpleados);
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
        tcEmpleado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().empleado().nombre()));
        ObservableList<String> nombresEmpleados = FXCollections.observableArrayList();
        for (EmpleadoDto empleado : listaEmpleadosDto) {
            nombresEmpleados.add(empleado.nombre());
        }
        comboEmpleado.setItems(nombresEmpleados);
    }

    private void obtenerEventos() {
        listaEventosDto.clear();
        listaEmpleadosDto.clear();
        listaEventosDto.addAll(eventoControllerService.obtenerEventos());
        listaEmpleadosDto.addAll(eventoControllerService.obtenerEmpleados());
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
        boolean eventoActualizado = false;

        // 1. Verificar el evento seleccionado
        if (eventoSeleccionado != null) {
            String IDActual = eventoSeleccionado.IDEvento();
            EventoDto eventoDto = construirEventoDto(false);

            // 2. Validar la información
            if (esValido(eventoDto)) {
                eventoActualizado = eventoControllerService.actualizarEvento(IDActual, eventoDto);

                if (eventoActualizado) {
                    listaEventosDto.remove(eventoSeleccionado);
                    listaEventosDto.add(eventoDto);
                    tablaEventos.refresh();
                    tablaEventos.getSelectionModel().clearSelection();
                    notifier.notify("eventoActualizado", eventoSeleccionado, eventoDto); // Notificar que se ha agregado un usuario
                    mostrarMensaje("Notificación", "Evento actualizado", "El evento se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposEvento();
                } else {
                    mostrarMensaje("Notificación", "Evento no actualizado", "No se ha podido actualizar el evento.", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarMensaje("Notificación evento", "Evento no seleccionado", "Seleccione un evento de la lista", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void agregarEvento(ActionEvent event) {
        EventoDto eventoDto=construirEventoDto(true);
        if(esValido(eventoDto)){
            if(eventoControllerService.agregarEvento(eventoDto)){
                listaEventosDto.add(eventoDto);
                notifier.notify("eventoAgregado", null, eventoDto); // Notificar que se ha agregado un usuario
                mostrarMensaje("Notificación Evento", "Evento Creado", "El evento se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEvento();
            }
            else{
                tablaEventos.refresh();
                mostrarMensaje("Notificación Evento", "Evento no creado", "No se ha podido crear el Evento. Revise los campos de información.", Alert.AlertType.ERROR);
            }
        }

    }

    @FXML
    void eliminarEvento(ActionEvent event) {
        boolean eventoEliminado = false;
        if (eventoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar el evento?")) {
                eventoEliminado = eventoControllerService.eliminarEvento(eventoSeleccionado.IDEvento());
                if (eventoEliminado) {
                    listaEventosDto.remove(eventoSeleccionado);
                    notifier.notify("eventoEliminado", eventoSeleccionado, null); // Notificar que se ha agregado un usuario
                    eventoSeleccionado = null;
                    tablaEventos.getSelectionModel().clearSelection();
                    limpiarCamposEvento();
                    mostrarMensaje("Notificación evento", "Evento eliminado", "El evento se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación evento", "Evento no eliminado", "El evento no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación evento", "Evento no seleccionado", "Seleccione un evento de la lista", Alert.AlertType.WARNING);
        }
    }

    private void limpiarCamposEvento() {
        txtID.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCapacidad.setText("");
        txtFecha.setValue(null);
        comboEmpleado.setValue(null);
    }

    public EventoDto construirEventoDto(boolean crearNuevo){
        String nombreEmpleado = comboEmpleado.getSelectionModel().getSelectedItem();
        EmpleadoDto empleadoSeleccionado = null;

        for (EmpleadoDto empleado : listaEmpleadosDto) {
            if (empleado.nombre().equals(nombreEmpleado)) {
                empleadoSeleccionado = empleado;
                break;
            }
        }
        ArrayList<Reserva> listaReservas;
        if (crearNuevo) {
            listaReservas = new ArrayList<Reserva>();
        } else {
            listaReservas = eventoSeleccionado.listaReservas();
        }
        return new EventoDto(
                txtID.getText(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                txtFecha.getValue(),
                Integer.parseInt(txtCapacidad.getText()),
                empleadoSeleccionado,
                listaReservas // Usar la lista de reservas existente
        );
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
            mensaje += "Debe completar el campo de Capacidad\n";

        if(mensaje.equals("")) {
            return true;
        } else {
            mostrarMensaje("Notificación Evento", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
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
