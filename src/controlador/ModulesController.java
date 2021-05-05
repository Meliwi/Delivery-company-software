//SOLO SE COPIO, AUN FALTA

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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModulesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML
    public void usuariosControlBoton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
        Stage UserStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        UserStage.setTitle("Gestión de Usuarios");
        UserStage.setScene(new Scene(root, 900, 600));
        UserStage.show();
    }
    @FXML
    public void RegresarControlBoton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/login.fxml"));
        Stage loginStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, 900, 600));
        loginStage.show();
    }
}
