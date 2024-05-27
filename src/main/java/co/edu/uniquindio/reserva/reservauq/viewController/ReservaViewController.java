package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EventoController;
import co.edu.uniquindio.reserva.reservauq.controller.ReservaController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EventoDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.ReservaDto;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.UsuarioDto;
import co.edu.uniquindio.reserva.reservauq.model.EstadoReserva;
import co.edu.uniquindio.reserva.reservauq.model.Evento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class ReservaViewController {
    ReservaController reservaControllerService;
    ObservableList<ReservaDto> listaReservasDto = FXCollections.observableArrayList();
    ReservaDto reservaSeleccionada;
    ArrayList<UsuarioDto> listaUsuariosDto=new ArrayList<>();
    ArrayList<EventoDto> listaEventosDto=new ArrayList<>();

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEnvioReservas;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnExportar;

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private ComboBox<String> comboEvento;

    @FXML
    private ComboBox<String> comboUsuario;

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


    @FXML
    void initialize() {
        reservaControllerService = new ReservaController();
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
        comboUsuario.getItems().clear();
        comboEvento.getItems().clear();
        obtenerReservas();
        tablaReservas.refresh();

        ObservableList<String> nombresEventos = FXCollections.observableArrayList();
        ObservableList<String> nombresUsuarios = FXCollections.observableArrayList();

        for (EventoDto evento : listaEventosDto) {
            nombresEventos.add(evento.nombreEvento());
        }
        for (UsuarioDto usuario : listaUsuariosDto) {
            nombresUsuarios.add(usuario.nombre());
        }

        comboEvento.setItems(nombresEventos);
        comboUsuario.setItems(nombresUsuarios);
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
        ObservableList<String> nombresUsuarios = FXCollections.observableArrayList();
        ObservableList<String> estadosReserva = FXCollections.observableArrayList(EstadoReserva.APROBADA.toString(), EstadoReserva.PENDIENTE.toString(), EstadoReserva.RECHAZADA.toString());

        for (EventoDto evento : listaEventosDto) {
            nombresEventos.add(evento.nombreEvento());
        }
        for (UsuarioDto usuario : listaUsuariosDto) {
            nombresUsuarios.add(usuario.nombre());
        }

        comboEvento.setItems(nombresEventos);
        comboUsuario.setItems(nombresUsuarios);
        comboEstado.setItems(estadosReserva);
    }

    private void obtenerReservas() {
        listaReservasDto.clear();
        listaEventosDto.clear();
        listaUsuariosDto.clear();
        listaReservasDto.addAll(reservaControllerService.obtenerReservas());
        listaEventosDto.addAll(reservaControllerService.obtenerEventos());
        listaUsuariosDto.addAll(reservaControllerService.obtenerUsuarios());
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
            comboUsuario.setValue(reservaSeleccionada.usuarioReserva().nombre());
            comboEstado.setValue(reservaSeleccionada.estado().toString());
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
                reservaActualizada = reservaControllerService.actualizarReserva(IDActual, reservaDto);

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
            if (reservaControllerService.agregarReserva(reservaDto)) {
                listaReservasDto.add(reservaDto);
                mostrarMensaje("Notificación Reserva", "Reserva Creada", "La reserva se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposReserva();
            }
        }
    }

    @FXML
    void eliminarReserva(ActionEvent event) {
        boolean reservaEliminada = false;
        if (reservaSeleccionada != null) {
            if (mostrarMensajeConfirmacion("¿Estás seguro de eliminar la reserva?")) {
                reservaEliminada = reservaControllerService.eliminarReserva(reservaSeleccionada.IDReserva());
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
        comboEstado.setValue(null);
        txtFecha.setValue(null);
        comboEvento.setValue(null);
        comboUsuario.setValue(null);
    }

    public ReservaDto construirReservaDto(boolean crearNuevo) {
        String nombreEvento = comboEvento.getSelectionModel().getSelectedItem();
        String nombreUsuario = comboUsuario.getSelectionModel().getSelectedItem();
        String estadoReserva = comboEstado.getSelectionModel().getSelectedItem();

        EventoDto eventoSeleccionado = null;
        UsuarioDto usuarioSeleccionado = null;

        for (EventoDto evento : listaEventosDto) {
            if (evento.nombreEvento().equals(nombreEvento)) {
                eventoSeleccionado = evento;
                break;
            }
        }
        for (UsuarioDto usuario : listaUsuariosDto) {
            if (usuario.nombre().equals(nombreUsuario)) {
                usuarioSeleccionado = usuario;
                break;
            }
        }

        EstadoReserva estado = EstadoReserva.valueOf(estadoReserva.toUpperCase());

        return new ReservaDto(
                txtID.getText(),
                usuarioSeleccionado,
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

    @FXML
    void exportarReservas(ActionEvent event) {
        // Crear un FileChooser para que el usuario elija la ruta y el nombre del archivo CSV
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reservas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        // Mostrar el diálogo de guardar archivo y obtener el archivo seleccionado por el usuario
        File archivoCSV = fileChooser.showSaveDialog(btnExportar.getScene().getWindow());

        if (archivoCSV != null) {
            // Exportar las reservas al archivo CSV seleccionado por el usuario
            try {
                FileWriter writer = new FileWriter(archivoCSV);

                // Escribir el encabezado del archivo CSV
                writer.append("ID Reserva,Evento,Usuario,Estado,Fecha Solicitud\n");

                // Escribir cada reserva en una nueva línea del archivo CSV
                for (ReservaDto reserva : listaReservasDto) {
                    writer.append(reserva.IDReserva()).append(",");
                    writer.append(reserva.eventoReserva().nombreEvento()).append(",");
                    writer.append(reserva.usuarioReserva().nombre()).append(",");
                    writer.append(reserva.estado().toString()).append(",");
                    writer.append(reserva.fechaSolicitud().toString()).append("\n");
                }

                // Cerrar el FileWriter
                writer.flush();
                writer.close();

                // Mostrar mensaje de éxito
                mostrarMensaje("Éxito", "Exportación Exitosa", "Las reservas se han exportado correctamente a " + archivoCSV.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                // Mostrar mensaje de error si ocurre algún problema durante la exportación
                mostrarMensaje("Error", "Error al Exportar", "Ha ocurrido un error al exportar las reservas: " + e.getMessage(), Alert.AlertType.ERROR);
            }
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
        ArrayList<ReservaDto> reservas=reservaControllerService.actualizarMensajesReservas();
        int contador=0;
        for(int i=0;i< reservas.size();i++)
        {
              for(int j=0;j<listaReservasDto.size();j++)
              {
                  if(reservas.get(i).equals(listaReservasDto.get(j)))
                  {
                     listaReservasDto.set(j,reservas.get(i));
                  }
                  else
                  {
                      contador++;
                  }
              }

              if(contador==listaReservasDto.size())
              {
                  listaReservasDto.add(reservas.get(i));
              }

              contador=0;
        }
    }

}