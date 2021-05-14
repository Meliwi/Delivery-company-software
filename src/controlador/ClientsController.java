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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ClientsController implements Initializable {

    @FXML
    private TextField cedula;
    @FXML
    private Label mensaje;

    private static String cedulaCliente;

    public static String getCedulaCliente() {
        return cedulaCliente;
    }

    public void ModulosBotonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }

    public void siguienteButtonAction(ActionEvent event) {
        cedulaCliente = cedula.getText().trim();
        Connect con  = new Connect();
        try{
            ResultSet resultado = con.CONSULTAR("SELECT * FROM cliente WHERE cedula = '"+cedula.getText().trim()+"';");
            if(resultado.next()){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/shipments.fxml"));
                Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                modulesStage.setTitle("Gestión de módulos");
                modulesStage.setScene(new Scene(root, 900, 600));
                modulesStage.show();
                con.CERRAR();
            }
            else {
                mensaje.setText("El cliente no se encuentra en el sistema");
            }
        }
        catch (IOException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void crearEditarButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/clientsManagement.fxml"));
        Stage clientsModuleStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        clientsModuleStage.setTitle("Gestión de clientes");
        clientsModuleStage.setScene(new Scene(root, 900, 600));
        clientsModuleStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
