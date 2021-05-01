package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import modelo.Conexion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;


public class LoginController implements Initializable {
    @FXML
    private Button loginbutton;
    @FXML
    private TextField correo;
    @FXML
    private PasswordField contraseña;
    @FXML
    private Label UsuarioInvalido;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        //conexión a base de datos
        Conexion con  = new Conexion();
        try{
            //En resultado se guarda la consulta de buscar lo que el usuario ingreso en la base de datos
            ResultSet resultado = con.CONSULTAR("SELECT * FROM usuarios WHERE correo = '"+correo.getText().trim()+"'" +
                    "AND contrasena = '"+contraseña.getText().trim()+"';");
            if(resultado.next()){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
                Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
                modulesStage.setTitle("Gestión de módulos");
                modulesStage.setScene(new Scene(root, 900, 600));
                modulesStage.show();
            }
            else {
                UsuarioInvalido.setText("Los datos son incorrectos");
            }

        }
        catch (IOException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
