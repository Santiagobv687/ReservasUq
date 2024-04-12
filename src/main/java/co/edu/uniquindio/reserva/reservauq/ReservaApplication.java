package co.edu.uniquindio.reserva.reservauq;

import co.edu.uniquindio.reserva.reservauq.viewController.GestionViewController;
import co.edu.uniquindio.reserva.reservauq.viewController.PaginaPrincipalViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservaApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestion UQ");
        mostrarVentanaPrincipal();
    }

    public void mostrarVentanaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReservaApplication.class.getResource("PaginaPrincipalView.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            PaginaPrincipalViewController paginaPrincipalViewController = loader.getController();
//            paginaPrincipalViewController.setAplicacion(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            //scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}