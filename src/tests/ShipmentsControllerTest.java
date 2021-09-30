package tests;
import static org.mockito.Mockito.*;
import controlador.LoginController;
import controlador.ShipmentsController;
import javafx.event.ActionEvent;
import modelo.Connect;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ShipmentsControllerTest {
    private ShipmentsController envioPaquetes;
    private Connect conexion;

    @Before
    public void setUp(){
        envioPaquetes = new ShipmentsController();
        conexion = new Connect();
        String paquetes[][] = {{"cra 35A #39-61", "0", "24", "Mismo departamento"},
                                {"calle 55", "1000", "5", "Interdepartamental"}};
        envioPaquetes.setPaquetes(paquetes);
        envioPaquetes.setAuxPaquetes(2);
    }
    @Test
    public void testcalcularImpuesto(){
        assertEquals(17000,envioPaquetes.calcularImpuesto(),0);
    }
    @Test
    public void testcalcularPrecio(){
        assertEquals(18000, envioPaquetes.calcularPrecio(envioPaquetes.calcularImpuesto()), 0);
    }
    @Test
    public void testinsertarFactura(){
        assertTrue(envioPaquetes.insertarFactura(conexion,
                   envioPaquetes.calcularPrecio(envioPaquetes.calcularImpuesto()) ,
                   envioPaquetes.calcularImpuesto()));
    }
    @Test
    public void testinsertarPago(){
        assertTrue(envioPaquetes.insertarPago(conexion, "1"));
    }



}
