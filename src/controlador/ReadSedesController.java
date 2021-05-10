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
import modelo.Connect;
import modelo.Sedes;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

;

public class ReadSedesController implements Initializable {

    @FXML
    private TableView<Sedes> tableSedes;
    @FXML
    private TableColumn<Sedes, String> nombreColumn;
    @FXML
    private TableColumn<Sedes, String> ciudadColumn;
    @FXML
    private TableColumn<Sedes, String> telefonoColumn;
    @FXML
    private TableColumn<Sedes, String> direccionColumn;
    @FXML
    private ObservableList<Sedes> listaSedes;

    public void modulosButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }

    public void sedesButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/officesManagement.fxml"));
        Stage UserStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        UserStage.setTitle("Gestión de Usuarios");
        UserStage.setScene(new Scene(root, 900, 600));
        UserStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // el metodo setCellValueFactory que aplicamos sobre las columnas de la tabla se usa
        // para determinar qué atributos de la clase Users deben ser usados para cada columna particular
        nombreColumn.setCellValueFactory(new PropertyValueFactory<Sedes,String>("id_sede"));
        ciudadColumn.setCellValueFactory(new PropertyValueFactory<Sedes,String>("ciudad"));
        telefonoColumn.setCellValueFactory(new PropertyValueFactory<Sedes,String>("telefono"));
        direccionColumn.setCellValueFactory(new PropertyValueFactory<Sedes,String>("direccion"));
        //Se hace el llamado a la función
        listaSedes = fillTableSedes();
        //Se agregan los datos de observableList a la tabla
        tableSedes.setItems(listaSedes);
    }

    public static ObservableList<Sedes> fillTableSedes() {
        Connect con = new Connect();
        //Se inicializa una colección de datos de tipo Sedes aún vacía (clases de colección)
        ObservableList<Sedes> listaSedes = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_sede, ciudad, telefono,direccion FROM sede";

            ResultSet resultado = con.CONSULTAR(sql);
            while (resultado.next()) {
                listaSedes.add(
                        new Sedes(resultado.getString("id_sede"),
                                resultado.getString("ciudad"),
                                resultado.getString("telefono"),
                                resultado.getString("direccion")
                        )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sedes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSedes;
    }
}
