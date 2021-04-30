package controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;


public class LoginController implements Initializable {
    @FXML
    private Button loginbutton;
    @FXML
    private TextField correo;
    @FXML
    private TextField contraseña;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        //if (correo.getText().isBlank() == false && contraseña.getText().isBlank()==false){
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        //Para cerrar la ventana de login
        //((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        modulesStage.show();
    }
}
