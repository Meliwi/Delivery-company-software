package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import modelo.Connect;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private TextField cedula;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField telefono;
    @FXML
    private TextField idSede;
    @FXML
    private TextField idPos;
    @FXML
    private TextField rol;
    @FXML
    private TextField numNomina;
    @FXML
    private TextField correo;
    @FXML
    private TextField contrasena;
    @FXML
    private Label mensaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML
    private void crearButtonAction (ActionEvent event) throws IOException {
        String cedulaUsuario = cedula.getText().trim();
        String nombreUsuario = nombre.getText().trim();
        String apellidoUsuario = apellido.getText().trim();
        //Por defecto el estado en la creción de un usuario es activo(1)
        String idEstado = "1";
        //El nit de la empresa siempre es el mismo
        String nitEmpresa = "890399001";
        String telefonoUsuario = telefono.getText().trim();
        String rolUsuario = rol.getText().trim();
        String numNominaUsuario = numNomina.getText().trim();
        String correoUsuario = correo.getText().trim();
        String contrasenaUsuario = contrasena.getText().trim();
        int idSedeUsuario = Integer.parseInt(idSede.getText().trim());
        int idPosUsuario = Integer.parseInt(idPos.getText().trim());
        Connect con  = new Connect();

        try {
            String creacion = "INSERT INTO usuarios (cedula, num_nomina, id_estado, codigo_rol, nit_empresa, id_pos, id_sede , nombre, apellido, telefono,correo, contrasena)"+
                    "VALUES ('"+cedulaUsuario+"','"+numNominaUsuario+"','"+idEstado+"','"+rolUsuario+"','"+nitEmpresa+"','"+idPosUsuario+"','"+idSedeUsuario+"','"+nombreUsuario+"','"+apellidoUsuario+"','"+telefonoUsuario+"','"+correoUsuario+"','"+contrasenaUsuario+"')";

            if (con.GUARDAR(creacion)) {
                cedula.setText(null);
                nombre.setText(null);
                apellido.setText(null);
                telefono.setText(null);
                rol.setText(null);
                numNomina.setText(null);
                correo.setText(null);
                contrasena.setText(null);
                idSede.setText(null);
                idPos.setText(null);
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
        }
        catch (IOException throwables) {
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
