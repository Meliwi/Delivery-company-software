package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sedes {
    private StringProperty nit_empresa;
    private StringProperty ciudad;
    private StringProperty telefono;
    private StringProperty direccion;

    private static Sedes sedes;

    /*
    INSERT INTO sede(nit_empresa, ciudad, telefono, direccion)
	    VALUES ('890399001', 'Buga', '1234567', 'Avenida de porky #10-40')
	*/

    public Sedes( String nit_empresa, String ciudad, String telefono, String direccion)
    {
        this.nit_empresa = new SimpleStringProperty(nit_empresa);
        this.ciudad = new SimpleStringProperty(ciudad);
        this.telefono = new SimpleStringProperty(telefono);
        this.nit_empresa = new SimpleStringProperty(nit_empresa);
        this.direccion = new SimpleStringProperty(direccion);
    }

    public static Sedes getInstanceUser(String nit_empresa, String ciudad, String telefono, String direccion) {
        if (sedes == null) {
            sedes = new Sedes(nit_empresa, ciudad, telefono,direccion);
        }
        return sedes;
    }
}


