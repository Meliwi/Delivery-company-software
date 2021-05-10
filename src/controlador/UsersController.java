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
    @FXML
    private Label mensajeAlert;



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
        String idSedeUsuario = idSede.getText().trim();
        String idPosUsuario = idPos.getText().trim();

        Connect con  = new Connect();

        try {
            String creacion = "INSERT INTO usuarios (cedula, num_nomina, id_estado, codigo_rol, nit_empresa, id_pos, id_sede , nombre, apellido, telefono,correo, contrasena)"+
                    "VALUES ('"+cedulaUsuario+"','"+numNominaUsuario+"','"+idEstado+"','"+rolUsuario+"','"+nitEmpresa+"','"+idPosUsuario+"','"+idSedeUsuario+"','"+nombreUsuario+"','"+apellidoUsuario+"','"+telefonoUsuario+"','"+correoUsuario+"','"+contrasenaUsuario+"')";

            if (con.GUARDAR(creacion)) {
                cedula.setText("");
                nombre.setText("");
                apellido.setText("");
                telefono.setText("");
                rol.setText("");
                numNomina.setText("");
                correo.setText("");
                contrasena.setText("");
                idSede.setText("");
                idPos.setText("");
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
    public void ConsultarBotonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/readUsers.fxml"));
        Stage TableUserStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        TableUserStage.setTitle("Consulta de Usuarios");
        TableUserStage.setScene(new Scene(root, 900, 600));
        TableUserStage.show();
    }
    public void editarButtonAction(ActionEvent event) throws IOException, SQLException {

        Connect con  = new Connect();
        if(cedula.getText().trim().isEmpty()){
            mensaje.setText("Porfavor ingrese la cédula del usuario");
        }
        else{
            if(!nombre.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET nombre = '"+nombre.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!apellido.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET apellido = '"+apellido.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!telefono.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET telefono = '"+telefono.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!rol.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET codigo_rol = '"+rol.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!numNomina.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET num_nomina = '"+numNomina.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!correo.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET correo= '"+correo.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!contrasena.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET contrasena = '"+contrasena.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!idSede.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET id_sede = '"+idSede.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!idPos.getText().trim().isEmpty()){
                con.GUARDAR("UPDATE usuarios SET id_pos = '"+idPos.getText().trim()+"'" +
                        "WHERE cedula = '"+cedula.getText().trim()+"'");
            }
            if(!cedula.getText().trim().isEmpty()){
                ResultSet resultado = con.CONSULTAR("SELECT * FROM usuarios WHERE cedula = '"+cedula.getText().trim()+"';");
                if(!resultado.next()){
                    mensaje.setText("Porfavor ingrese una cédula válida");
                }
                else{
                    cedula.setText("");
                    nombre.setText("");
                    apellido.setText("");
                    telefono.setText("");
                    rol.setText("");
                    numNomina.setText("");
                    correo.setText("");
                    contrasena.setText("");
                    idSede.setText("");
                    idPos.setText("");
                    Parent root = FXMLLoader.load(getClass().getResource("/vista/updateUserWindow.fxml"));
                    Stage actualizacionStage = new Stage();
                    actualizacionStage.setTitle("Actualización");
                    actualizacionStage.setScene(new Scene(root, 458, 100));
                    actualizacionStage.show();
                    con.CERRAR();
                }
            }

        }
    }
    public void borrarButtonAction(ActionEvent event) throws IOException {
        Connect con  = new Connect();
        if(cedula.getText().trim().isEmpty()){
            mensaje.setText("Porfavor ingrese la cédula del usuario");
        }
        else{
            con.GUARDAR("UPDATE usuarios SET id_estado = '2' WHERE cedula = '"+cedula.getText().trim()+"'");
            cedula.setText(null);
            Parent root = FXMLLoader.load(getClass().getResource("/vista/deleteUserWindow.fxml"));
            Stage borrarStage = new Stage();
            borrarStage.setTitle("Desactivación");
            borrarStage.setScene(new Scene(root, 458, 100));
            borrarStage.show();
            con.CERRAR();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

}
