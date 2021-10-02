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

public class ClientsManagement implements Initializable {
    @FXML
    public TextField cedula;
    @FXML
    public TextField nombre;
    @FXML
    public TextField apellido;
    @FXML
    public TextField telefono;
    @FXML
    public TextField ciudad;
    @FXML
    public TextField direccion;
    @FXML
    public Label mensaje;

    public String nitEmpresa = "890399001";

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

    public void crearButtonAction(ActionEvent event) throws SQLException {
        Connect con  = new Connect();
        String cedulaCliente = cedula.getText().trim();
        String nombreCliente = nombre.getText().trim();
        String apellidoCliente = apellido.getText().trim();
        String ciudadCliente = ciudad.getText().trim();
        String telefonoCliente = telefono.getText().trim();
        String direccionCliente = direccion.getText().trim();

        String creacionCliente = "INSERT INTO cliente (cedula, nit_empresa, nombre, apellido, ciudad, telefono, direccion)"+
                "VALUES ('"+cedulaCliente+"','"+nitEmpresa+"','"+nombreCliente+"','"+apellidoCliente+"','"+ciudadCliente+"','"+telefonoCliente+"','"+direccionCliente+"')";

        if (con.GUARDAR(creacionCliente)){
            mensaje.setText("El cliente fue creado con éxito");
            cedula.setText("");
            nombre.setText("");
            apellido.setText("");
            ciudad.setText("");
            telefono.setText("");
            direccion.setText("");
        }
    }

    public void editarButtonAction (ActionEvent actionEvent) throws SQLException, IOException {
        Connect con  = new Connect();
        if(cedula.getText().trim().isEmpty()){
            mensaje.setText("Porfavor ingrese la cédula del usuario");
        }
        else{
            if(!nombre.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE cliente SET nombre = '"+nombre.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!apellido.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE cliente SET apellido = '"+apellido.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!ciudad.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE cliente SET ciudad = '"+ciudad.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!telefono.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE cliente SET telefono = '"+telefono.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!direccion.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE cliente SET direccion = '"+direccion.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!cedula.getText().trim().isEmpty()){
                ResultSet resultado = con.CONSULTAR("SELECT * FROM cliente WHERE cedula = '"+cedula.getText().trim()+"';");
                if(!resultado.next()){
                    mensaje.setText("Porfavor ingrese una cédula válida");
                }
                else{
                    cedula.setText("");
                    nombre.setText("");
                    apellido.setText("");
                    ciudad.setText("");
                    telefono.setText("");
                    direccion.setText("");
                    Parent root = FXMLLoader.load(getClass().getResource("/vista/updateClientWindow.fxml"));
                    Stage actualizacionStage = new Stage();
                    actualizacionStage.setTitle("Actualización Cliente");
                    actualizacionStage.setScene(new Scene(root, 458, 100));
                    actualizacionStage.show();
                    con.CERRAR();
                }
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
