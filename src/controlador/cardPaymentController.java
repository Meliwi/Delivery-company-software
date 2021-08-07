package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import modelo.Connect;

import java.io.IOException;

import java.sql.ResultSet;
import java.sql.SQLException;


import static java.lang.Integer.parseInt;

public class cardPaymentController {


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
                    numeroTipo = "1";
                }
                if(debito.isSelected()){
                    numeroTipo = "2";
                }

                if(nombreTitular.equals(titular.getText().trim().toLowerCase()) & fechaVencimiento.equals(fecha.getText().trim().toLowerCase())
                   & cvcTarjeta.equals(cvc.getText().trim().toLowerCase()) & numeroTipo.equals(tipoTarjeta)  & saldo>=pago){
                    System.out.println("c++");
                }
                con.CERRAR();
            }
        }
        catch ( SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
