package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientsManagement {
    public void ModulosBotonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }

    public void regresarBotonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/clients.fxml"));
        Stage clientsStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        clientsStage.setTitle("Gestión de clientes");
        clientsStage.setScene(new Scene(root, 900, 600));
        clientsStage.show();
    }

    public void editarButtonAction(ActionEvent event) {
    }

    public void crearButtonAction(ActionEvent event) {
    }
}
