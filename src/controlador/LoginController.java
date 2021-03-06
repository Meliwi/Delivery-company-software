package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import modelo.Connect;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;


public class LoginController implements Initializable {

    @FXML
    private TextField correo;
    @FXML
    private PasswordField contrasena;
    @FXML
    private Label UsuarioInvalido;
    @FXML
    private static String userRole;

    @FXML
    private static String userID;


    public static String getUserRole() {
        return userRole;
    }
    public static String getUserID() {
        return userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        //conexión a base de datos
        Connect con  = new Connect();
        try{
            //En resultado se guarda la consulta de buscar lo que el usuario ingreso, en la base de datos
            ResultSet resultado = con.CONSULTAR("SELECT * FROM usuarios WHERE correo = '"+correo.getText().trim()+"' " +
                    "AND contrasena = '"+contrasena.getText().trim()+"';");
            if(resultado.next()){
                userRole = resultado.getString(4);
                userID = resultado.getString(1);
                Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
                Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                modulesStage.setTitle("Gestión de módulos");
                modulesStage.setScene(new Scene(root, 900, 600));
                modulesStage.show();
                //para cerrar la conexión de la base de datos
                con.CERRAR();
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
