package modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sedes {
    private StringProperty ciudad;
    private StringProperty telefono;
    private StringProperty direccion;
    private static Sedes sedes;

    /*
    INSERT INTO sede(nit_empresa, ciudad, telefono, direccion)
	    VALUES ('890399001', 'Buga', '1234567', 'Avenida de porky #10-40')
	*/

    public Sedes(String ciudad, String telefono, String direccion)
    {
        this.ciudad = new SimpleStringProperty(ciudad);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
    }
    public String getCiudad() {
        return ciudad.get();
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }


}


