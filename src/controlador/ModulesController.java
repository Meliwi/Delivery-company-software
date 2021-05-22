

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
        Object combo = comboBox.getSelectionModel().getSelectedItem();
        if(combo == null){
            mensaje.setText("Seleccione un módulo.");
            return; 
        }
        Parent root;
        switch (LoginController.getUserRole()) {
            case "1": //Gerente
                switch (combo.toString()) {
                    case "Sedes" -> {
                        root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
                        Stage officesStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
                        officesStage.setTitle("Gestión de sedes");
                        officesStage.setScene(new Scene(root, 900, 600));
                        officesStage.show();
                        return;
                    }
                    case "Usuarios" -> {
                        root = FXMLLoader.load(getClass().getResource("/vista/users.fxml"));
                        Stage usersStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
                        usersStage.setTitle("Gestión de usuarios");
                        usersStage.setScene(new Scene(root, 900, 600));
                        usersStage.show();
                        return;
                    }
                    case "Reportes" -> {
                        mensaje.setText("Este módulo aún se encuentra en desarrollo");
                        return;
                    }
                    default -> {
                        mensaje.setText("Acceso Denegado");
                        return;
                    }
                }
            case "2": //Auxiliar de operaciones
                switch (combo.toString()){
                    case "POS":
                        root = FXMLLoader.load(getClass().getResource("/vista/shipments.fxml"));
                        Stage officesStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
                        officesStage.setTitle("Gestión de sedes");
                        officesStage.setScene(new Scene(root, 900, 600));
                        officesStage.show();
                        return;
                    default:
                        mensaje.setText("Acceso Denegado");
                        return;
                }
            case "3": //Secretaria
                switch (combo.toString()){
                    case "Usuarios":
                        root = FXMLLoader.load(getClass().getResource("/vista/users.fxml"));
                        Stage usersStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
                        usersStage.setTitle("Gestión de usuarios");
                        usersStage.setScene(new Scene(root, 900, 600));
                        usersStage.show();
                        return;
                    default:
                        mensaje.setText("Acceso Denegado");
                        return;
                }
            case "4": //Operador de oficina
                switch (combo.toString()){
                    case "POS":
                        root = FXMLLoader.load(getClass().getResource("/vista/shipments.fxml"));
                        Stage officesStage = ((Stage) (((Button) event.getSource()).getScene().getWindow()));
                        officesStage.setTitle("Gestión de sedes");
                        officesStage.setScene(new Scene(root, 900, 600));
                        officesStage.show();
                        return;
                    default:
                        mensaje.setText("Acceso Denegado");
                        break;
                }
            case "5": //Contador
                switch (combo.toString()){
                    case "Nomina":
                        mensaje.setText("Este módulo aún se encuentra en desarrollo");
                        return;
                    default:
                        mensaje.setText("Acceso Denegado");
                        return;
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
