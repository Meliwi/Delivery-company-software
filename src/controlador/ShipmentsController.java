package controlador;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.Connect;
import modelo.Paquete;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
    public Label precioTotal;

    @FXML
    public Label alert;

    @FXML
    public TextField pesoPaquete;

    @FXML
    public TextField seguro;

    @FXML
    public RadioButton efectivo;

    @FXML
    public RadioButton tarjeta;

    @FXML
    public Label mensaje;

    @FXML
    public ToggleGroup metodosPago;

    @FXML
    public ComboBox distancias;

    private String  direccionEnvioCliente;

    private static String id_pos = "";

    private static String ciudad_pos = "";

    private static String numeroFactura = "";

    private static int numeroPago = 0;

    private static String fechaFactura;

    public static String[][] getPaquetes() {
        return paquetes;
    }

    public static void setPaquetes(String[][] shipment) {
        paquetes = shipment;
    }

    public static String paquetes[][] = new String[10][4];

    public static int auxPaquetes = 0;

    public static int getCopiaAuxPaquetes() {
        return copiaAuxPaquetes;
    }

    public static int copiaAuxPaquetes = 0;

    private static String precioEnvioTotal;

    private static double impuestoPaquete[] = new double[10];

    public static boolean pagoHecho = false;

    public static boolean isPagoHecho() {
        return pagoHecho;
    }



    public static String getPrecioEnvioTotal() {
        return precioEnvioTotal;
    }

    public static int getAuxPaquetes() {
        return auxPaquetes;
    }

    public static void setCopiaAuxPaquetes(int copia){
        copiaAuxPaquetes = copia;
    }

    public static void setAuxPaquetes(int aux){
        auxPaquetes = aux;
    }

    public static void setPagoHecho(boolean pago){
        pagoHecho = pago;
    }


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
        direccionEnvioCliente = direccionEnvio.getText().trim();
    }


    /*Función encargada de calcular el impuesto del paquete, teniendo en cuenta el peso
    y la distancia a donde se quiera enviar */
    public static double calcularImpuesto(){

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
    public static double calcularPrecio( double impuesto) {
        int seguro = 0;
        for (int i = 0; i < auxPaquetes; i++) {
            seguro += parseInt(paquetes[i][1]);
        }
        return impuesto+seguro;
    }




    public static boolean insertarFactura(Connect con , double precio, double impuesto){


        try {
            ResultSet resultadoFactura = con.CONSULTAR("INSERT INTO factura (fecha, valor, cantidad_paquetes, impuesto)"+
                    "VALUES (NOW()::date,'"+precio+"','" + auxPaquetes +"',"+ impuesto +") RETURNING numero, fecha");

            if (resultadoFactura.next()) {
                numeroFactura = resultadoFactura.getString(1);
                fechaFactura = resultadoFactura.getString(2);
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean insertarPago(Connect con, String metodoPagoSeleccionado){
        try {
            ResultSet resultadoPago = con.CONSULTAR("INSERT INTO pago (num_factura, id_metodo_pago)"+
                    "VALUES ('"+numeroFactura+"','"+metodoPagoSeleccionado+"') RETURNING numero");

            if (resultadoPago.next()) {
                numeroPago = parseInt(resultadoPago.getString(1));
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean consultarInformacionPOS(Connect con ){

        try {
            ResultSet resultadoPOS = con.CONSULTAR("SELECT identificador_pos, ciudad FROM usuarios, pos WHERE cedula = '"+ controlador.LoginController.getUserID()+ "'");

            if (resultadoPOS.next()) {
                id_pos = resultadoPOS.getString(1);
                ciudad_pos = resultadoPOS.getString(2);
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static boolean gestionarEnvios(Connect con,int numeroGuiaEnvio[] ){
        for (int i = 0; i < auxPaquetes; i++) {
            try {
                ResultSet resultadoEnvio = con.CONSULTAR("INSERT INTO envio (id_estado_envio, locaLizacion, direccion_envio, seguro)"+
                        "VALUES ('1','"+ciudad_pos+"','"+ paquetes[i][0] +"','"+paquetes[i][1]+"') RETURNING num_guia");

                if (resultadoEnvio.next()) {
                    numeroGuiaEnvio[i] = parseInt(resultadoEnvio.getString(1));
                }
                else {
                    return false;
                }
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            String resultadoGestionEnvios = "INSERT INTO gestion_envios (num_pago, id_envio, cedula_cliente, id_pos)"+
                    "VALUES ('"+numeroPago+"','"+numeroGuiaEnvio[i]+"','"+controlador.ClientsController.getCedulaCliente()+"','"+id_pos+"')";

            if (con.GUARDAR(resultadoGestionEnvios)) {

            }
            else {
                return false;
            }
        }
        return true;
    }


    public void pagarButtonAction(ActionEvent actionEvent) throws IOException  {

        double impuesto = calcularImpuesto();
        double precio = calcularPrecio(impuesto);

        precioEnvioTotal = Integer.toString((int)precio);

        int numeroGuiaEnvio[] = new int[auxPaquetes];
        String metodoPagoSeleccionado = "";

        Connect con  = new Connect();

        //Agregar la verificacion

        if(efectivo.isSelected()){
            metodoPagoSeleccionado = "1";
            insertarFactura(con,precio, impuesto);
            insertarPago(con, metodoPagoSeleccionado);
            consultarInformacionPOS(con);
            if(gestionarEnvios(con, numeroGuiaEnvio)){
                mensaje.setText("El envio se registró con exito");

                copiaAuxPaquetes = auxPaquetes;
                auxPaquetes = 0;

                precioTotal.setText("Total a pagar: " + precioEnvioTotal);
                pagoHecho = true;
            }
            else{
                mensaje.setText("No se ha podido registrar el envio");
            }
        }
        else{
            metodoPagoSeleccionado = "2";
            Parent root = FXMLLoader.load(getClass().getResource("/vista/cardPayment.fxml"));
            Stage cardPayment = new Stage();
            cardPayment .setTitle("Pago con tarjeta");
            cardPayment.setScene(new Scene(root, 393, 377));
            cardPayment.show();
        }

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

    public void generarFactura(MouseEvent mouseEvent) throws IOException, DocumentException {
        if (pagoHecho == true) {
            Calendar cal = Calendar.getInstance();
            Integer actual_month = cal.get(Calendar.MONTH);
            Integer actual_year = cal.get(Calendar.YEAR);
            File directorio = new File("src/Bills/"+actual_year+"/"+actual_month);

            //Esto es una prueba para ver si genera distintos pdfs
            if (!directorio.exists()){
                if(directorio.mkdir()){
                    System.out.println("Directorio creado");
                }
                else {
                    System.out.println("Error al crear el directorio");
                }
            }
            try {
                FileOutputStream file = new FileOutputStream(directorio.getPath() + "/bill" + controlador.ClientsController.getCedulaCliente() + ".pdf");
                Document doc = new Document();
                //Se asocia el documento de OutPutStream
                PdfWriter.getInstance(doc, file);
                doc.open();

                //Creación de tabla con dos columnas
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);

                //---------------------------------------------------------------------------------------------------------
                //Se añade el título y el membrete de la factura
                Paragraph title = new Paragraph(25, "FACTURA \n\n", FontFactory.getFont("Oswald", 32, Font.BOLD, BaseColor.BLACK));
                Paragraph titleCompany = new Paragraph(5, "Flash \n\n", FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                Paragraph address = new Paragraph(5, "Carrera 85 #34-33 \n\n", FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                Paragraph city = new Paragraph(5, "Cali, Valle del Cauca \n\n", FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                PdfPCell titleCell = new PdfPCell();
                titleCell.addElement(title);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                titleCell.setBorder(0);
                titleCell.addElement(titleCompany);
                titleCell.addElement(address);
                titleCell.addElement(city);
                table.addCell(titleCell);

                //---------------------------------------------------------------------------------------------------------
                //Añadimos el logo
                String filename = "src/Images/logoModificado.png";
                Image logo = Image.getInstance(filename);
                logo.scaleToFit(100, 50);
                PdfPCell logoCell = new PdfPCell(logo);
                logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                logoCell.setBorder(0);
                table.addCell(logoCell);

                //---------------------------------------------------------------------------------------------------------
                //Información del cliente y de la factura
                PdfPTable informacion = new PdfPTable(3);
                informacion.setWidthPercentage(100);

                //información del cliente
                Paragraph tituloInfo = new Paragraph(65, "Facturar a: ", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.BLACK));
                Paragraph cedula = new Paragraph("Cédula: " + controlador.ClientsController.getCedulaCliente(), FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                Paragraph nombre = new Paragraph("Nombre: " + controlador.ClientsController.getNombreCliente(), FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                Paragraph telefono = new Paragraph("Teléfono: " + controlador.ClientsController.getTelefonoCliente(), FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                Paragraph direccionCliente = new Paragraph("Dirección: " + controlador.ClientsController.getDireccionCliente(), FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                PdfPCell tituloInfoCell = new PdfPCell();
                tituloInfoCell.setBorder(0);
                tituloInfoCell.addElement(tituloInfo);
                tituloInfoCell.addElement(cedula);
                tituloInfoCell.addElement(nombre);
                tituloInfoCell.addElement(telefono);
                tituloInfoCell.addElement(direccionCliente);
                informacion.addCell(tituloInfoCell);

                //Forma de pago
                Paragraph pagoTitulo= new Paragraph(65, "Forma de pago: " , FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.BLACK));
                pagoTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell pagoTituloCell = new PdfPCell();
                pagoTituloCell.setBorder(0);
                pagoTituloCell.addElement(pagoTitulo);
                informacion.addCell(pagoTituloCell);

                //información de la factura
                Paragraph facturaTitulo = new Paragraph(65, "N° de factura: " + numeroFactura, FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.BLACK));
                Paragraph facturaDate = new Paragraph("Fecha:  " + fechaFactura, FontFactory.getFont("Franklin Gothic Heavy", 10, Font.NORMAL, BaseColor.BLACK));
                facturaTitulo.setAlignment(Element.ALIGN_RIGHT);
                facturaDate.setAlignment(Element.ALIGN_RIGHT);
                PdfPCell facturaTituloCell = new PdfPCell();
                facturaTituloCell.setBorder(0);
                facturaTituloCell.addElement(facturaTitulo);
                facturaTituloCell.addElement(facturaDate);
                informacion.addCell(facturaTituloCell);


                //--------------------------------------------------------------------------------------------------------
                //Información de los envios del cliente
                PdfPTable envios = new PdfPTable(5);
                envios.setWidthPercentage(100);
                envios.setWidths(new int[]{2, 1, 2, 1, 1});

                //Peso de los paquetes
                Paragraph direccionTitulo = new Paragraph("Dirección", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.WHITE));
                direccionTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell direccionTituloCell = new PdfPCell();
                direccionTituloCell.setBorderColor(new BaseColor(0, 110, 225));
                direccionTituloCell.setBackgroundColor(new BaseColor(0, 110, 225));
                direccionTituloCell.addElement(direccionTitulo);
                envios.addCell(direccionTituloCell);


                //Peso de los paquetes
                Paragraph pesoTitulo = new Paragraph("Peso", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.WHITE));
                pesoTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell enviosTituloCell = new PdfPCell();
                enviosTituloCell.setBorderColor(new BaseColor(0, 110, 225));
                enviosTituloCell.setBackgroundColor(new BaseColor(0, 110, 225));
                enviosTituloCell.addElement(pesoTitulo);
                envios.addCell(enviosTituloCell);

                //Distancias de los paquetes
                Paragraph distanciaTitulo = new Paragraph("Distancia", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.WHITE));
                distanciaTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell enviosDistanciaCell = new PdfPCell();
                enviosDistanciaCell.setBorderColor(new BaseColor(0, 110, 225));
                enviosDistanciaCell.setBackgroundColor(new BaseColor(0, 110, 225));
                enviosDistanciaCell.addElement(distanciaTitulo);
                envios.addCell(enviosDistanciaCell);

                //Impuestos de los paquetes
                Paragraph impuestoTitulo = new Paragraph("Impuesto ", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.WHITE));
                impuestoTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell enviosImpuestoCell = new PdfPCell();
                enviosImpuestoCell.setBorderColor(new BaseColor(0, 110, 225));
                enviosImpuestoCell.setBackgroundColor(new BaseColor(0, 110, 225));
                enviosImpuestoCell.addElement(impuestoTitulo);
                envios.addCell(enviosImpuestoCell);

                //Seguros de los paquetes
                Paragraph seguroTitulo = new Paragraph("Seguro", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.WHITE));
                seguroTitulo.setAlignment(Element.ALIGN_CENTER);
                PdfPCell enviosSeguroCell = new PdfPCell();
                enviosSeguroCell.setBorderColor(new BaseColor(0, 110, 225));
                enviosSeguroCell.setBackgroundColor(new BaseColor(0, 110, 225));
                enviosSeguroCell.addElement(seguroTitulo);
                envios.addCell(enviosSeguroCell);

                //Espacio
                Paragraph space = new Paragraph("\n\n");

                //Manejo de información de envios
                PdfPTable enviosInfo = new PdfPTable(5);
                enviosInfo.setWidthPercentage(100);
                enviosInfo.setWidths(new int[]{2, 1, 2, 1, 1});

                PdfPCell direccionInfoCell = new PdfPCell();
                PdfPCell pesoInfoCell = new PdfPCell();
                PdfPCell distanciaInfoCell = new PdfPCell();
                PdfPCell impuestoInfoCell = new PdfPCell();
                PdfPCell seguroInfoCell = new PdfPCell();

                direccionInfoCell.setBorder(0);
                pesoInfoCell.setBorder(0);
                distanciaInfoCell.setBorder(0);
                impuestoInfoCell.setBorder(0);
                seguroInfoCell.setBorder(0);

                Paragraph direccionParagraphs[]= new Paragraph[copiaAuxPaquetes];
                Paragraph pesoParagraphs[]= new Paragraph[copiaAuxPaquetes];
                Paragraph distanciaParagraphs[]= new Paragraph[copiaAuxPaquetes];
                Paragraph impuestoParagraphs[]= new Paragraph[copiaAuxPaquetes];
                Paragraph seguroParagraphs[]= new Paragraph[copiaAuxPaquetes];
                for (int i = 0; i<copiaAuxPaquetes;i++){
                    direccionParagraphs[i] = new Paragraph(paquetes[i][0]);
                    direccionParagraphs[i].setAlignment(Element.ALIGN_CENTER);
                    pesoParagraphs[i] = new Paragraph(paquetes[i][2]);
                    pesoParagraphs[i].setAlignment(Element.ALIGN_CENTER);
                    distanciaParagraphs[i] = new Paragraph(paquetes[i][3]);
                    distanciaParagraphs[i].setAlignment(Element.ALIGN_CENTER);
                    impuestoParagraphs[i] = new Paragraph(String.valueOf((int)impuestoPaquete[i]));
                    impuestoParagraphs[i].setAlignment(Element.ALIGN_CENTER);
                    seguroParagraphs[i] = new Paragraph(paquetes[i][1]);
                    seguroParagraphs[i].setAlignment(Element.ALIGN_CENTER);

                    direccionInfoCell.addElement(direccionParagraphs[i]);
                    pesoInfoCell.addElement(pesoParagraphs[i]);
                    distanciaInfoCell.addElement(distanciaParagraphs[i]);
                    impuestoInfoCell.addElement(impuestoParagraphs[i]);
                    seguroInfoCell.addElement(seguroParagraphs[i]);
                }
                enviosInfo.addCell(direccionInfoCell);
                enviosInfo.addCell(pesoInfoCell);
                enviosInfo.addCell(distanciaInfoCell);
                enviosInfo.addCell(impuestoInfoCell);
                enviosInfo.addCell(seguroInfoCell);

                //Total a pagar
                Paragraph totalEnvio = new Paragraph(65,"Total: " + precioEnvioTotal , FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.BLACK));
                totalEnvio.setAlignment(Element.ALIGN_RIGHT);

                //Pie de factura
                Paragraph dudaFactura = new Paragraph("Si tiene alguna duda.. ", FontFactory.getFont("Franklin Gothic Heavy", 13, Font.BOLD, BaseColor.BLACK));


                //Añadimos información al documento
                doc.add(table);
                doc.add(informacion);
                doc.add(space);
                doc.add(envios);
                doc.add(enviosInfo);
                doc.add(totalEnvio);

                System.out.println("Factura generada exitosamente");
                doc.close();
            } catch (Exception e) {
                System.out.println("Something went wrong " + e);
            }
        }
        else{
            alert.setText("Debe realizar un pago para poder generar una factura");
        }
    }

    @FXML
    public void regresarModulos(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }
}
