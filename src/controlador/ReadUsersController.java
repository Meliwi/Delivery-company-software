package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
;

public class ReadUsersController implements Initializable {

    @FXML
    private TableView<Users> tableUsers;
    @FXML
    private TableColumn<Users,String> cedulaColumn;
    @FXML
    private TableColumn<Users, String> nombreColumn;
    @FXML
    private TableColumn<Users, String> apellidoColumn;
    @FXML
    private TableColumn<Users, String> telefonoColumn;
    @FXML
    private TableColumn<Users, String> correoColumn;
    @FXML
    private TableColumn<Users, String> rolColumn;
    @FXML
    private TableColumn<Users, String> estadoColumn;
    @FXML
    private ObservableList<Users> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // el metodo setCellValueFactory que aplicamos sobre las columnas de la tabla se usa
        // para determinar qué atributos de la clase Users deben ser usados para cada columna particular
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("cedula"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("nombre"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("apellido"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("telefono"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("correo"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("nombre_rol"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("nombre_estado"));
        //Se hace el llamado a la función
        listaUsuarios = modelo.Users.fillTableUsers();
        //Se agregan los datos de observableList a la tabla
        tableUsers.setItems(listaUsuarios);
    }

    public void modulosButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }

    public void usuariosButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/users.fxml"));
        Stage UserStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        UserStage.setTitle("Gestión de Usuarios");
        UserStage.setScene(new Scene(root, 900, 600));
        UserStage.show();
    }
}
