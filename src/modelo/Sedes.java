package modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sedes {

    private StringProperty id_sede;
    private StringProperty ciudad;
    private StringProperty telefono;
    private StringProperty direccion;
    private static Sedes sedes;

    /*
    INSERT INTO sede(nit_empresa, ciudad, telefono, direccion)
	    VALUES ('890399001', 'Buga', '1234567', 'Avenida de porky #10-40')
	*/

    public Sedes(String id_sede,String ciudad, String telefono, String direccion) {
        this.id_sede = new SimpleStringProperty(id_sede);
        this.ciudad = new SimpleStringProperty(ciudad);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
    }

    //getters
    public String getId_sede() {
        return id_sede.get();
    }
    
    public String getCiudad() {
        return ciudad.get();
    }

    public String getTelefono() {
        return telefono.get();
    }

    public String getDireccion() {
        return direccion.get();
    }

    //setters
    public void setId_sede(String id_sede) {
        this.id_sede.set(id_sede);
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public static void setSedes(Sedes sedes) {
        Sedes.sedes = sedes;
    }

    //property
    public StringProperty id_sedeProperty() {
        return id_sede;
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

}
