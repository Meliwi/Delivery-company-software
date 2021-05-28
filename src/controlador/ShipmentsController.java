package controlador;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Connect;
import modelo.Paquete;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class ShipmentsController implements Initializable {

    @FXML
    private TableView<Paquete> tableShipments;
    @FXML
    private TableColumn<Paquete,String> dirColumn;
    @FXML
    private TableColumn<Paquete,String> pesoColumn;
    @FXML
    private TableColumn<Paquete,String> distanColumn;
    @FXML
    private TableColumn<Paquete,String> impuestoColumn;
    @FXML
    private TableColumn<Paquete,String> seguroColumn;


    @FXML
    public TextField direccionEnvio;

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

    private String paquetes[][] = new String[10][4];

    private int auxPaquetes = 0;

    private double impuestoPaquete[] = new double[10];

    public void registrarButtonAction(ActionEvent actionEvent) {
        Object seleccionDistancia = distancias.getSelectionModel().getSelectedItem();

        if (direccionEnvio.getText().trim().isEmpty()
                && seguro.getText().trim().isEmpty() && pesoPaquete.getText().trim().isEmpty()) {
            mensaje.setText("Campos incompletos");
        } else {
            paquetes[auxPaquetes][0] = direccionEnvio.getText().trim();
            paquetes[auxPaquetes][1] = "0";
            if (!seguro.getText().trim().isEmpty()) {
                paquetes[auxPaquetes][1] = seguro.getText().trim();
            }

            paquetes[auxPaquetes][2] = pesoPaquete.getText().trim();
            paquetes[auxPaquetes][3] = seleccionDistancia.toString();
            auxPaquetes++;
        }
        fillTableShipments();
    }


    /*Función encargada de calcular el impuesto del paquete, teniendo en cuenta el peso
    y la distancia a donde se quiera enviar */
    private double calcularImpuesto(){

        double impuestoTotal = 0;
        String distancia;
        double peso;
        double multDistancia;
        double categoriaPeso;
        for(int i=0; i<auxPaquetes; i++){
            distancia = paquetes[i][3];
            peso = parseInt(paquetes[i][2]);
            multDistancia = 1;
            categoriaPeso = 0;
            switch (distancia) {
                case "Mismo departamento" -> {
                    multDistancia = 1.3;
                }
                case "Interdepartamental" -> {
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
            impuestoPaquete[i] = categoriaPeso*multDistancia;
        }
        return impuestoTotal;
    }

    //Funcion que calcula el precio total, teniendo en cuenta el impuesto calculado y el seguro
    private double calcularPrecio( double impuesto) {
        int seguro = 0;
        for (int i = 0; i < auxPaquetes; i++) {
            seguro += parseInt(paquetes[i][1]);
        }
        return impuesto+seguro;
    }


    public void pagarButtonAction(ActionEvent actionEvent) {

        double impuesto = calcularImpuesto();
        double precio = calcularPrecio(impuesto);
        String id_pos = "";
        String ciudad_pos = "";
        String cedulaCliente = controlador.ClientsController.getCedulaCliente();
        String cedulaUsuario = controlador.LoginController.getUserID();
        int numeroFactura = 0;
        int numeroPago = 0;
        int numeroGuiaEnvio[] = new int[auxPaquetes];
        String metodoPagoSeleccionado = "";

        Connect con  = new Connect();

        try {
            ResultSet resultadoFactura = con.CONSULTAR("INSERT INTO factura ( fecha, valor, cantidad_paquetes, impuesto)"+
                    "VALUES (NOW(),'"+precio+"','" + auxPaquetes +"',"+ impuesto +") RETURNING numero");

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

        for (int i = 0; i < auxPaquetes; i++) {
            try {
                ResultSet resultadoEnvio = con.CONSULTAR("INSERT INTO envio (id_estado_envio, locaLizacion, direccion_envio, seguro)"+
                        "VALUES ('1','"+ciudad_pos+"','"+ paquetes[i][0] +"','"+paquetes[i][1]+"') RETURNING num_guia");

                if (resultadoEnvio.next()) {
                    numeroGuiaEnvio[i] = parseInt(resultadoEnvio.getString(1));
                }
                else {
                    mensaje.setText("No se ha podido registrar el envio");
                }
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            System.out.println(cedulaCliente);
            String resultadoGestionEnvios = "INSERT INTO gestion_envios (num_pago, id_envio, cedula_cliente, id_pos)"+
                    "VALUES ('"+numeroPago+"','"+numeroGuiaEnvio[i]+"','"+cedulaCliente+"','"+id_pos+"')";

            if (con.GUARDAR(resultadoGestionEnvios)) {
                mensaje.setText("El envio se registró con exito");
            }
            else {
                mensaje.setText("No se ha podido registrar el envio");
            }
        }
        auxPaquetes = 0;
    }
    private ObservableList<Paquete> fillTableShipments(){
        ObservableList<Paquete> listaInfo = FXCollections.observableArrayList();
        calcularImpuesto();
        for (int i = 0; i<auxPaquetes;i++){
            listaInfo.add(
                    new Paquete(paquetes[i][0],
                            paquetes[i][2],
                            paquetes[i][3],
                            impuestoPaquete[i],
                            paquetes[i][1])
            );
        }
        tableShipments.setItems(listaInfo);
        return listaInfo;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dirColumn.setCellValueFactory(new PropertyValueFactory<Paquete, String>("direccion"));
        pesoColumn.setCellValueFactory(new PropertyValueFactory<Paquete, String>("peso"));
        distanColumn.setCellValueFactory(new PropertyValueFactory<Paquete, String>("distancia"));
        impuestoColumn.setCellValueFactory(new PropertyValueFactory<Paquete,String>("impuesto"));
        seguroColumn.setCellValueFactory(new PropertyValueFactory<Paquete,String>("seguro"));

    }
}
