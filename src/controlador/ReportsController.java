package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Connect;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsController {

    @FXML
    public HBox HboxReports;

    @FXML
    public BarChart graph;

    @FXML
    public ComboBox comboBox;

    @FXML
    public Label mensaje;


    public void ContinuarControlBoton(ActionEvent actionEvent) {
        Object combo = comboBox.getSelectionModel().getSelectedItem();
        if(combo == null){
            mensaje.setText("Seleccione un reporte");
            return;
        }
        Connect con  = new Connect();
        switch (combo.toString()) {
            case "Ultimo mes" -> {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_envios = 0;
                String fecha = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(numero) as num_envios, fecha FROM factura " +
                            "WHERE EXTRACT(MONTH FROM to_date(fecha, 'YYYY-MM-DD')) = " +
                            "(SELECT EXTRACT(MONTH FROM NOW())) " +
                            "GROUP BY(fecha)");

                    while(resultadoEnvio.next()) {
                        num_envios = resultadoEnvio.getInt("num_envios");
                        fecha= resultadoEnvio.getString("fecha");
                        dataset.setValue(num_envios, fecha,fecha );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de envíos por día en el último mes", "fecha", "num_envios", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte ultimo mes",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
            case "Mes anterior" -> {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_envios = 0;
                String fecha = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(numero) as num_envios, fecha FROM factura " +
                            "WHERE EXTRACT(MONTH FROM to_date(fecha, 'YYYY-MM-DD')) = " +
                            "(SELECT EXTRACT(MONTH FROM NOW())-1) " +
                            "GROUP BY(fecha)");

                    while(resultadoEnvio.next()) {
                        num_envios = resultadoEnvio.getInt("num_envios");
                        fecha= resultadoEnvio.getString("fecha");
                        dataset.setValue(num_envios, fecha,fecha );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de envíos por día en el mes previo", "fecha", "num_envios", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte mes anterior",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
            case "Ultimo año" -> {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_envios = 0;
                String fecha = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(numero) as num_envios, fecha FROM factura " +
                            "WHERE EXTRACT(YEAR FROM to_date(fecha, 'YYYY-MM-DD')) =" +
                            "(SELECT EXTRACT(YEAR FROM NOW())) " +
                            "GROUP BY(fecha)");

                    while(resultadoEnvio.next()) {
                        num_envios = resultadoEnvio.getInt("num_envios");
                        fecha= resultadoEnvio.getString("fecha");
                        dataset.setValue(num_envios, fecha,fecha );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de envíos por día en el último año", "fecha", "num_envios", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte ultimo año",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            case "Despedidos por rol" -> {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_employees = 0;
                String rol = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(cedula) as num_empleados, nombre_rol FROM usuarios JOIN rol " +
                            "ON(codigo_rol = codigo) " +
                            "WHERE id_estado = '2' " +
                            "GROUP BY(nombre_rol)");

                    while(resultadoEnvio.next()) {
                        num_employees = resultadoEnvio.getInt("num_empleados");
                        rol= resultadoEnvio.getString("nombre_rol");
                        dataset.setValue(num_employees, rol,rol );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de empleados despedidos por rol", "nombre_rol", "num_empleados", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte despedidos por rol",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            case "Empleados por sede" -> {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_employees = 0;
                String sede = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(cedula) as num_empleados, id_sede FROM usuarios " +
                            "GROUP BY (id_sede)");

                    while(resultadoEnvio.next()) {
                        num_employees = resultadoEnvio.getInt("num_empleados");
                        sede= resultadoEnvio.getString("id_sede");
                        dataset.setValue(num_employees, sede,sede );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de empleados por sede", "id_sede", "num_empleados", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte empleados por sede",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            case "Ultimo mes por POS" -> {
                String id_pos = "";
                try {
                    ResultSet resultadoPOS = con.CONSULTAR("SELECT identificador_pos, ciudad FROM usuarios, pos WHERE cedula = '"+ controlador.LoginController.getUserID()+ "'");

                    if (resultadoPOS.next()) {
                        id_pos = resultadoPOS.getString(1);
                    }
                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                Integer num_envios = 0;
                String fecha = "";
                try {
                    ResultSet resultadoEnvio = con.CONSULTAR("SELECT COUNT(num_factura) as num_envios,fecha FROM gestion_envios JOIN pago as P " +
                            "ON(numero = num_pago) JOIN factura as F ON(F.numero = P.num_factura) " +
                            "WHERE EXTRACT(MONTH FROM to_date(fecha, 'YYYY-MM-DD')) = (SELECT EXTRACT(MONTH FROM NOW())) " +
                            "AND id_pos = '" + id_pos+
                            "' GROUP BY(fecha)");

                    while(resultadoEnvio.next()) {
                        num_envios = resultadoEnvio.getInt("num_envios");
                        fecha= resultadoEnvio.getString("fecha");
                        dataset.setValue(num_envios, fecha,fecha );
                    }

                    JFreeChart chart = ChartFactory.createBarChart3D("Número de envíos por día en el ultimo mes por POS", "fecha", "num_envios", dataset, PlotOrientation.VERTICAL, false, true, false);
                    CategoryPlot p = chart.getCategoryPlot();

                    //p.setRangeGridlinePaint(Color.BLUE);
                    ChartFrame frameGraph = new ChartFrame("Reporte ultimo mes por POS",chart);
                    //frameGraph.setLocationRelativeTo();
                    frameGraph.setLocation(500,170);
                    frameGraph.setVisible(true);
                    frameGraph.setSize(400,400);

                }
                catch(SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


            default -> {
                mensaje.setText("---");
                return;
            }
        }

    }

    public void RegresarControlBoton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/modules.fxml"));
        Stage modulesStage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));//new Stage();
        modulesStage.setTitle("Gestión de módulos");
        modulesStage.setScene(new Scene(root, 900, 600));
        modulesStage.show();
    }
}