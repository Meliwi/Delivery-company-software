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
    public TextField pesoPaquete;

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

    @FXML
    public ComboBox distancias;

    private String paquetes[][] = new String[10][5];

    private int auxPaquetes = 0;

    public void registrarButtonAction(ActionEvent actionEvent) {
        Object seleccionDistancia = distancias.getSelectionModel().getSelectedItem();
        if(direccionEnvio.getText().trim().isEmpty() && valor.getText().trim().isEmpty()
                && seguro.getText().trim().isEmpty() && pesoPaquete.getText().trim().isEmpty()){
            mensaje.setText("Campos incompletos");
        }
        else{
            paquetes[auxPaquetes][0]= direccionEnvio.getText().trim();
            paquetes[auxPaquetes][1]= valor.getText().trim();
            paquetes[auxPaquetes][2]= "0";
            if(!seguro.getText().trim().isEmpty()){
                paquetes[auxPaquetes][2]= seguro.getText().trim();
            }

            paquetes[auxPaquetes][3]= pesoPaquete.getText().trim();
            paquetes[auxPaquetes][4]= seleccionDistancia.toString();
            auxPaquetes++;

        }

    }

    private float calcularImpuesto(){
        float impuestoTotal = 0;
        for(int i=0; i<auxPaquetes; i++){
            String distancia = paquetes[i][4];
            double peso = parseInt(paquetes[i][3]);
            double multDistancia = 1;
            double categoriaPeso = 0;
            switch (distancia) {
                case "departamento" -> {
                    multDistancia = 1.3;
                }
                case "interdepartamental" -> {
                    multDistancia = 2;
                }
            }
            if(peso<=5){
                categoriaPeso = 2000;
            }
            else if(peso<=10){
                categoriaPeso = 4000;
            }
            else if(peso<=15){
                categoriaPeso = 6000;
            }
            else if(peso<=20){
                categoriaPeso = 8000;
            }
            else{
                categoriaPeso = 10000;
            }
            impuestoTotal += categoriaPeso*multDistancia;
        }
        return impuestoTotal;
    }

    private float calcularPrecio(){
        return 0;
    }


    public void pagarButtonAction(ActionEvent actionEvent) {

        String envio = direccionEnvio.getText().trim();
        int precio = parseInt(valor.getText().trim());
        int poliza = 0;
        if(!seguro.getText().trim().isEmpty()){
            poliza = parseInt(seguro.getText().trim());
        }
        int numPaquetes = auxPaquetes;
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
            ResultSet resultadoFactura = con.CONSULTAR("INSERT INTO factura ( fecha, valor, cantidad_paquetes, impuesto)"+
                    "VALUES (NOW(),'"+precio+"','" + numPaquetes +"',"+ 19 +") RETURNING numero");

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
            ResultSet resultadoPago = con.CONSULTAR("INSERT INTO pago (num_factura, id_metodo_pago)"+
                    "VALUES ('"+numeroFactura+"','"+metodoPagoSeleccionado+"') RETURNING numero");

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
            ResultSet resultadoEnvio = con.CONSULTAR("INSERT INTO envio (id_estado_envio, locaLizacion, direccion_envio, seguro)"+
                    "VALUES ('1','"+ciudad_pos+"','"+envio+"','"+poliza+"') RETURNING num_guia");

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
