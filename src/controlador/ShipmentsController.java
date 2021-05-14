package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.Connect;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class ShipmentsController {
    @FXML
    public TextField direccionEnvio;
    @FXML
    public TextField valor;

    @FXML
    public TextField cantidad;

    @FXML
    public TextField seguro;

    @FXML
    public RadioButton efectivo;

    @FXML
    public RadioButton debito;

    @FXML
    public RadioButton credito;

    @FXML
    public Label mensaje;

    @FXML
    public ToggleGroup metodosPago;

    public void registrarButtonAction(ActionEvent actionEvent) {
        String envio = direccionEnvio.getText().trim();
        int precio = parseInt(valor.getText().trim());
        int numCantidad = parseInt(cantidad.getText().trim());
        int poliza = 0;
        if(!seguro.getText().trim().isEmpty()){
            poliza = parseInt(seguro.getText().trim());
        }
        String id_pos = "";
        String ciudad_pos = "";
        String cedulaCliente = controlador.ClientsController.getCedulaCliente();
        String cedulaUsuario = controlador.LoginController.getUserID();
        int numeroFactura = 0;
        int numeroPago = 0;
        int numeroGuiaEnvio = 0;
        String metodoPagoSeleccionado = "";

        Connect con  = new Connect();

        try {
            ResultSet resultadoFactura = con.CONSULTAR("INSERT INTO factura (direccion_de_envio, fecha, valor, cantidad_paquetes)"+
                    "VALUES ('"+envio+"',NOW(),'"+precio+"','"+numCantidad+"') RETURNING numero");

            if (resultadoFactura.next()) {
                    numeroFactura = parseInt(resultadoFactura.getString(1));
                }
            else {
                    mensaje.setText("No se ha podido registrar el envio");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(efectivo.isSelected()){
            metodoPagoSeleccionado = "1";
        }
        if(debito.isSelected()){
            metodoPagoSeleccionado = "2";
        }
        if(credito.isSelected()){
            metodoPagoSeleccionado = "3";
        }

        try {
            ResultSet resultadoPago = con.CONSULTAR("INSERT INTO pago (num_factura, id_metodo_pago, impuesto, seguro)"+
                    "VALUES ('"+numeroFactura+"','"+metodoPagoSeleccionado+"','"+19+"','"+ poliza+"') RETURNING numero");

            if (resultadoPago.next()) {
                numeroPago = parseInt(resultadoPago.getString(1));
            }
            else {
                mensaje.setText("No se ha podido registrar el envio");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ResultSet resultadoPOS = con.CONSULTAR("SELECT identificador_pos, ciudad FROM usuarios, pos WHERE cedula = '"+ cedulaUsuario+ "'");

            if (resultadoPOS.next()) {
                id_pos = resultadoPOS.getString(1);
                ciudad_pos = resultadoPOS.getString(2);
            }
            else {
                mensaje.setText("No se ha podido registrar el envio");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            ResultSet resultadoEnvio = con.CONSULTAR("INSERT INTO envio (id_estado_envio, locaLizacion)"+
                    "VALUES ('1','"+ciudad_pos+"') RETURNING num_guia");

            if (resultadoEnvio.next()) {
                numeroGuiaEnvio = parseInt(resultadoEnvio.getString(1));
            }
            else {
                mensaje.setText("No se ha podido registrar el envio");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        String resultadoGestionEnvios = "INSERT INTO gestion_envios (num_pago, id_envio, cedula_cliente, id_pos)"+
                "VALUES ('"+numeroPago+"','"+numeroGuiaEnvio+"','"+cedulaCliente+"','"+id_pos+"')";

        if (con.GUARDAR(resultadoGestionEnvios)) {
            mensaje.setText("El envio se registró con exito");
            direccionEnvio.setText("");
            valor.setText("");
            cantidad.setText("");
            seguro.setText("");
        }
        else {
            mensaje.setText("No se ha podido registrar el envio");
        }


    }


}
