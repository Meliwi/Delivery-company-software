

package controlador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;



public class ModulesController implements Initializable {
    @FXML
    public ComboBox comboBox;
    @FXML
    public Label mensaje;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
    @FXML
    public void usuariosControlBoton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
        Stage UserStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
        UserStage.setTitle("Gestión de Sedes");
        UserStage.setScene(new Scene(root, 900, 600));
        UserStage.show();
    }
    @FXML
    public void ContinuarControlBoton(ActionEvent event) throws IOException {

        if(controlador.LoginController.getUserRole().equals("1")){
            if(comboBox.getSelectionModel().getSelectedItem().equals("Sedes")){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
                Stage officesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                officesStage.setTitle("Gestión de sedes");
                officesStage.setScene(new Scene(root, 900, 600));
                officesStage.show();
            }
            if(comboBox.getSelectionModel().getSelectedItem().equals("Usuarios")){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/users.fxml"));
                Stage usersStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                usersStage.setTitle("Gestión de usuarios");
                usersStage.setScene(new Scene(root, 900, 600));
                usersStage.show();
            }
            if(comboBox.getSelectionModel().getSelectedItem().equals("Reportes")){
                mensaje.setText("Este módulo aún se encuentra en desarrollo");
            }
            else{
                mensaje.setText("Acceso Denegado");
            }
        }
        if(controlador.LoginController.getUserRole().equals("2")){
            if(comboBox.getSelectionModel().getSelectedItem().equals("POS")){
                mensaje.setText("Este módulo aún se encuentra en desarrollo");
            }
            else{
                mensaje.setText("Acceso Denegado");
            }
        }
        if(controlador.LoginController.getUserRole().equals("3")){
            if(comboBox.getSelectionModel().getSelectedItem().equals("Sedes")){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
                Stage officesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                officesStage.setTitle("Gestión de sedes");
                officesStage.setScene(new Scene(root, 900, 600));
                officesStage.show();
            }
            if(comboBox.getSelectionModel().getSelectedItem().equals("Usuarios")){
                Parent root = FXMLLoader.load(getClass().getResource("/vista/users.fxml"));
                Stage usersStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                usersStage.setTitle("Gestión de usuarios");
                usersStage.setScene(new Scene(root, 900, 600));
                usersStage.show();
            }
            else{
                mensaje.setText("Acceso Denegado");
            }
        }
        if(controlador.LoginController.getUserRole().equals("4")){
            if(comboBox.getSelectionModel().getSelectedItem().equals("POS")){
                mensaje.setText("Este módulo aún se encuentra en desarrollo");
            }
            else{
                mensaje.setText("Acceso Denegado");
            }
        }
        if(controlador.LoginController.getUserRole().equals("5")){
            if(comboBox.getSelectionModel().getSelectedItem().equals("Nomina")){
                mensaje.setText("Este módulo aún se encuentra en desarrollo");
            }
            else{
                mensaje.setText("Acceso Denegado");
            }
        }
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
