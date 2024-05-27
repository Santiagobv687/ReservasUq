package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.verReservasController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.EstadoReserva;
import co.edu.uniquindio.reserva.reservauq.model.Gestion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class verReservasViewController {

    verReservasController verReservasControllerService;
    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();
    ReservaDto reservaSeleccionada;
    ArrayList<UsuarioDto> listaUsuariosDto=new ArrayList<>();
    ArrayList<EventoDto> listaEventosDto=new ArrayList<>();

    @FXML
    private Button btnActualizarReservas;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<String> comboEvento;

    @FXML
    private TableView<ReservaDto> tablaReservas;

    @FXML
    private TableColumn<ReservaDto, String> tcEstado;

    @FXML
    private TableColumn<ReservaDto, String> tcEvento;

    @FXML
    private TableColumn<ReservaDto, String> tcFecha;

    @FXML
    private TableColumn<ReservaDto, String> tcID;

    @FXML
    private TableColumn<ReservaDto, String> tcUsuario;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtID;

    private EventNotifier notifier;
    private UsuarioDto usuarioDto;


    @FXML
    void initialize() {
        verReservasControllerService = new verReservasController();
        usuarioDto=verReservasControllerService.obtenerUsuarioDto();
        notifier = EventNotifier.getInstance(); // Obtener la instancia del notifier
        initView();
        setupEventListeners(); // Configurar los listeners de eventos
    }

    private void initView() {
        obtenerReservas();
        initDataBinding();
        tablaReservas.getItems().clear();
        tablaReservas.setItems(listaReservasDto);
        listenerSelection();
    }

    private void setupEventListeners() {
        notifier.addPropertyChangeListener(event -> {
            if ("usuarioAgregado".equals(event.getPropertyName()) || "usuarioEliminado".equals(event.getPropertyName())
                    || "usuarioActualizado".equals(event.getPropertyName()) || "eventoAgregado".equals(event.getPropertyName())
                    || "eventoEliminado".equals(event.getPropertyName()) || "eventoActualizado".equals(event.getPropertyName())) {
                actualizar();
            }
        });
    }

    private void actualizar(){
        usuarioDto=verReservasControllerService.obtenerUsuarioDto();
        comboEvento.getItems().clear();
        obtenerReservas();
        tablaReservas.refresh();

        ObservableList<String> nombresEventos = FXCollections.observableArrayList();
        for (EventoDto evento : listaEventosDto) {
            nombresEventos.add(evento.nombreEvento());
        }

        comboEvento.setItems(nombresEventos);
    }


    private void initDataBinding() {
        tcID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().IDReserva()));
        tcEvento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().eventoReserva().nombreEvento()));
        tcUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().usuarioReserva().nombre()));
        tcEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().estado().toString()));
        tcFecha.setCellValueFactory(cellData -> {
            LocalDate fecha = cellData.getValue().fechaSolicitud();
            String fechaString = fecha != null ? fecha.toString() : "";
            return new SimpleStringProperty(fechaString);
        });

        ObservableList<String> nombresEventos = FXCollections.observableArrayList();
        for (EventoDto evento : listaEventosDto) {
            nombresEventos.add(evento.nombreEvento());
        }

        comboEvento.setItems(nombresEventos);
    }

    private void obtenerReservas() {
        listaReservasDto.clear();
        listaEventosDto.clear();
        listaUsuariosDto.clear();

        // Obtener todas las reservas
        List<ReservaDto> todasReservas = verReservasControllerService.obtenerReservas();

        // Filtrar las reservas para incluir solo las relacionadas con el usuario iniciado
        List<ReservaDto> reservasFiltradas = todasReservas.stream()
                .filter(reserva -> reserva.usuarioReserva().equals(usuarioDto))
                .collect(Collectors.toList());

        // Agregar las reservas filtradas a la lista
        listaReservasDto.addAll(reservasFiltradas);

        listaEventosDto.addAll(verReservasControllerService.obtenerEventos());
        listaUsuariosDto.addAll(verReservasControllerService.obtenerUsuarios());
    }

    private void listenerSelection() {
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            reservaSeleccionada = newSelection;
            mostrarInformacionReserva(reservaSeleccionada);
        });
    }

    private void mostrarInformacionReserva(ReservaDto reservaSeleccionada) {
        if (reservaSeleccionada != null) {
            txtID.setText(reservaSeleccionada.IDReserva());
            txtFecha.setValue(reservaSeleccionada.fechaSolicitud());
            comboEvento.setValue(reservaSeleccionada.eventoReserva().nombreEvento());
        }
    }

    @FXML
    void actualizarReserva(ActionEvent event) throws IOException {
        boolean reservaActualizada = false;

        // 1. Verificar la reserva seleccionada
        if (reservaSeleccionada != null) {
            String IDActual = reservaSeleccionada.IDReserva();
            ReservaDto reservaDto = construirReservaDto(false);

            // 2. Validar la información
            if (esValido(reservaDto)) {
                reservaActualizada = verReservasControllerService.actualizarReserva(IDActual, reservaDto);

                if (reservaActualizada) {
                    listaReservasDto.remove(reservaSeleccionada);
                    listaReservasDto.add(reservaDto);
                    tablaReservas.refresh();
                    tablaReservas.getSelectionModel().clearSelection();
                    mostrarMensaje("Notificación", "Reserva actualizada", "La reserva se ha actualizado con éxito.", Alert.AlertType.INFORMATION);
                    limpiarCamposReserva();
                } else {
                    mostrarMensaje("Notificación", "Reserva no actualizada", "No se ha podido actualizar la reserva.", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarMensaje("Notificación reserva", "Reserva no seleccionada", "Seleccione una reserva de la lista", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void agregarReserva(ActionEvent event) throws IOException {
        ReservaDto reservaDto = construirReservaDto(true);
        if (esValido(reservaDto)) {
            if (verReservasControllerService.agregarReserva(reservaDto)) {
                listaReservasDto.add(reservaDto);
                mostrarMensaje("Notificación Reserva", "Reserva Creada", "La reserva se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposReserva();
            }
            else{
                tablaReservas.refresh();
                mostrarMensaje("Notificación Reserva", "Reserva no creada", "No se ha podido crear la reserva. Revise los campos de información.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void eliminarReserva(ActionEvent event) {
        boolean reservaEliminada = false;
        if (reservaSeleccionada != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar la reserva?")) {
                reservaEliminada = verReservasControllerService.eliminarReserva(reservaSeleccionada.IDReserva());
                if (reservaEliminada) {
                    listaReservasDto.remove(reservaSeleccionada);
                    reservaSeleccionada = null;
                    tablaReservas.getSelectionModel().clearSelection();
                    limpiarCamposReserva();
                    mostrarMensaje("Notificación reserva", "Reserva eliminada", "La reserva se ha eliminado con éxito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificación reserva", "Reserva no eliminada", "La reserva no se puede eliminar", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Notificación reserva", "Reserva no seleccionada", "Seleccione una reserva de la lista", Alert.AlertType.WARNING);
        }
    }

    private void limpiarCamposReserva() {
        txtID.setText("");
        txtFecha.setValue(null);
        comboEvento.setValue(null);
    }

    public ReservaDto construirReservaDto(boolean crearNuevo) {
        String nombreEvento = comboEvento.getSelectionModel().getSelectedItem();

        EventoDto eventoSeleccionado = null;

        for (EventoDto evento : listaEventosDto) {
            if (evento.nombreEvento().equals(nombreEvento)) {
                eventoSeleccionado = evento;
                break;
            }
        }


        EstadoReserva estado = EstadoReserva.PENDIENTE;

        return new ReservaDto(
                txtID.getText(),
                usuarioDto,
                eventoSeleccionado,
                txtFecha.getValue(),
                estado
        );
    }


    private boolean esValido(ReservaDto reservaDto) {
        String mensaje = "";
        if (reservaDto.IDReserva() == null || reservaDto.IDReserva().isEmpty())
            mensaje += "Debe completar el campo de ID\n";
        if (reservaDto.eventoReserva().nombreEvento() == null || reservaDto.eventoReserva().nombreEvento().isEmpty())
            mensaje += "Debe completar el campo de Evento\n";
        if (reservaDto.usuarioReserva().nombre() == null || reservaDto.usuarioReserva().nombre().isEmpty())
            mensaje += "Debe completar el campo de Usuario\n";
        if (reservaDto.estado() == null)
            mensaje += "Debe completar el campo de Estado\n";
        if (reservaDto.fechaSolicitud() == null)
            mensaje += "Debe completar el campo de Fecha\n";

        if (mensaje.isEmpty()) {
            return true;
        } else {
            mostrarMensaje("Notificación Reserva", "Datos inválidos", mensaje, Alert.AlertType.WARNING);
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

    @FXML
    public void verReservas(ActionEvent actionEvent) {
        ArrayList<ReservaDto> reservas=verReservasControllerService.actualizarMensajesReservas();
        for(int i=0;i<reservas.size();i++)
        {
            if(reservas.get(i).usuarioReserva().ID().equals(Gestion.usuarioIniciado))
            {
                if(seEncuentraYa(reservas.get(i)))
                {
                    listaReservasDto.add(reservas.get(i));
                }

            }
        }
    }

    public boolean seEncuentraYa(ReservaDto reservas) {
        int contador=0;
        for(int i=0;i<listaReservasDto.size();i++)
        {
            if(!reservas.IDReserva().equals(listaReservasDto.get(i).IDReserva()))
            {
                contador++;
            }
        }
        if (contador==listaReservasDto.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


}