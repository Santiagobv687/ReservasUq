package co.edu.uniquindio.reserva.reservauq.viewController;

import co.edu.uniquindio.reserva.reservauq.controller.EmpleadoController;
import co.edu.uniquindio.reserva.reservauq.mapping.dto.EmpleadoDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    }

    @FXML
    void agregarEmpleadoAction(ActionEvent event) {

    }

    @FXML
    void eliminarEmpleadoAction(ActionEvent event) {

    }

}
