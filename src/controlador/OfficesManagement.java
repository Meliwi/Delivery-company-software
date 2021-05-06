package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Connect;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OfficesManagement implements Initializable {
    @FXML
    private TextField id_sede;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField  telefono;
    @FXML
    private TextField direccion;
    @FXML
    private Label mensaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    private void crearButtonAction (ActionEvent event) throws IOException {
        String nit = "890399001";
        String name = id_sede.getText().trim();
        String city = ciudad.getText().trim();
        String telephoneNumber = telefono.getText().trim();
        String address = direccion.getText().trim();
        Connect con  = new Connect();
        try{
            String updaterQuery = "INSERT INTO sede(id_sede, nit_empresa, ciudad, telefono, direccion) VALUES ('" + name +"', '"+ nit + "', '" + city + "', '" + telephoneNumber + "', '" + address + "')";

            if (name != "" && con.GUARDAR(updaterQuery)){
                id_sede.setText("");
                ciudad.setText("");
                telefono.setText("");
                direccion.setText("");
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

    @FXML
    private void editarButtonAction (ActionEvent event) throws IOException {
        String nit = "890399001";
        String name = id_sede.getText().trim();
        String city = ciudad.getText();
        String telephoneNumber = telefono.getText();
        String address = direccion.getText();
        Connect con  = new Connect();
        try{
            if(name.isEmpty()){
                mensaje.setText("Porfavor ingrese el nombre de la sede");
            }
            else{
                if(!city.isEmpty()){
                    con.GUARDAR("UPDATE sede SET ciudad = '"+city+"'" +
                            "WHERE id_sede = '"+name+"'");
                }
                if(!telephoneNumber.isEmpty()){
                    con.GUARDAR("UPDATE sede SET telefono = '"+telephoneNumber+"'" +
                            "WHERE id_sede = '"+name+"'");
                }
                if(!address.isEmpty()){
                    con.GUARDAR("UPDATE sede SET direccion = '"+address+"'" +
                            "WHERE id_sede = '"+name+"'");
                }
                id_sede.setText("");
                ciudad.setText("");
                telefono.setText("");
                direccion.setText("");
                Parent root = FXMLLoader.load(getClass().getResource("/vista/updateUserWindow.fxml"));
                Stage actualizacionStage = new Stage();
                actualizacionStage.setTitle("Actualización");
                actualizacionStage.setScene(new Scene(root, 458, 100));
                actualizacionStage.show();

                con.CERRAR();
            }
        } catch (IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
