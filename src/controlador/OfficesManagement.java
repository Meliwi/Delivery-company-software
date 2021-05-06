package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Connect;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfficesManagement implements Initializable {
    @FXML
    private TextField nit_empresa;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField  telefono;
    @FXML
    private TextField direccion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    private void crearButtonAction (ActionEvent event) throws IOException {
        System.out.println(nit_empresa);
        String nit = nit_empresa.getText().trim();
        System.out.println(nit_empresa);
        String city = ciudad.getText().trim();
        String telephoneNumber = telefono.getText().trim();
        String address = direccion.getText().trim();
        Connect con  = new Connect();
        System.out.println("paso 2");
        try{
            String updaterQuery = "INSERT INTO sede(nit_empresa, ciudad, telefono, direccion) VALUES ('" + nit + "', '" + city + "', '" + telephoneNumber + "', '" + address + "')";
            System.out.println(updaterQuery);
            if (con.GUARDAR(updaterQuery)){
                nit_empresa.setText(null);
                ciudad.setText(null);
                telefono.setText(null);
                direccion.setText(null);
                Parent root = FXMLLoader.load(getClass().getResource("/vista/popupWindow.fxml"));
                Stage registroStage = new Stage();
                registroStage.setTitle("Registro");
                registroStage.setScene(new Scene(root, 458, 100));
                registroStage.show();
            }
            else{
                Parent root = FXMLLoader.load(getClass().getResource("/vista/alertWindow.fxml"));
                Stage registroStage = new Stage();
                registroStage.setTitle("Registro");
                registroStage.setScene(new Scene(root, 458, 100));
                registroStage.show();
                System.out.println("paso 3");

            }
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void AceptarButtonAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void ModulosBotonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }
}
