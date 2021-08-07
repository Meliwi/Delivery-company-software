package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import modelo.Connect;

import java.io.IOException;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import static java.lang.Integer.parseInt;

public class cardPaymentController implements Initializable {


    @FXML
    private TextField titular;

    @FXML
    private TextField numeroTarjeta;

    @FXML
    private TextField fecha;

    @FXML
    private TextField cvc;

    @FXML
    private RadioButton credito;

    @FXML
    private RadioButton debito;

    @FXML
    private Label totalPagar;

    @FXML
    private Label mensaje;

    private double precio = 0;

    private double impuesto = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        impuesto = ShipmentsController.calcularImpuesto();
        precio = ShipmentsController.calcularPrecio(impuesto);
        totalPagar.setText("Total a pagar: " + Integer.toString((int)precio));

        mensaje.setWrapText(true);
    }


    @FXML
    private void pagarButtonAction(ActionEvent event) throws IOException {
        Connect con  = new Connect();
        try{
            //En resultado se guarda la consulta de buscar lo que el usuario ingreso, en la base de datos
            ResultSet resultado = con.CONSULTAR("SELECT * FROM tarjetas_registradas WHERE numerotarjeta = '"+numeroTarjeta.getText().trim()+"'; " );

            if(resultado.next()){
                String nombreTitular = resultado.getString(2);
                String fechaVencimiento = resultado.getString(3);
                String cvcTarjeta = resultado.getString(4);
                String tipoTarjeta = resultado.getString(5);
                int saldo = parseInt(resultado.getString(6));
                int pago = parseInt(controlador.ShipmentsController.getPrecioEnvioTotal());
                String numeroTipo = "";
                if(credito.isSelected()){
                    numeroTipo = "3";
                }
                if(debito.isSelected()){
                    numeroTipo = "2";
                }

                if(nombreTitular.equals(titular.getText().trim().toLowerCase()) & fechaVencimiento.equals(fecha.getText().trim().toLowerCase())
                   & cvcTarjeta.equals(cvc.getText().trim().toLowerCase()) & numeroTipo.equals(tipoTarjeta)  & saldo>=pago){

                    int saldoRestante = saldo-pago;
                    con.GUARDAR("UPDATE tarjetas_registradas SET saldo="+ saldoRestante+"WHERE numerotarjeta='"
                    + numeroTarjeta.getText().trim() +"'");



                    int numeroGuiaEnvio[] = new int[ShipmentsController.getAuxPaquetes()];

                    controlador.ShipmentsController.insertarFactura(con,precio, impuesto);
                    controlador.ShipmentsController.insertarPago(con, numeroTipo);
                    controlador.ShipmentsController.consultarInformacionPOS(con);
                    if(controlador.ShipmentsController.gestionarEnvios(con, numeroGuiaEnvio)){
                        mensaje.setText("El envio se registró con exito");
                        controlador.ShipmentsController.setCopiaAuxPaquetes(controlador.ShipmentsController.getAuxPaquetes());
                        controlador.ShipmentsController.setAuxPaquetes(0);
                        controlador.ShipmentsController.setPagoHecho(true);


                    }
                    else{
                        mensaje.setText("No se ha podido registrar el envio");
                    }

                }
                else{
                    mensaje.setText("Por favor verifique el formato de la fecha de vencimiento (MM/YYYY) y los demás datos de su tarjeta");
                }

            }
        }
        catch ( SQLException throwables) {
            throwables.printStackTrace();
        }
        con.CERRAR();
    }


}
