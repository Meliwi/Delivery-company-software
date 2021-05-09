package modelo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    //getters
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
    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }
    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }
    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    //property
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

    public static ObservableList<Sedes> fillTableSedes() {
        Connect con  = new Connect();
        //Se inicializa una colección de datos de tipo Sedes aún vacía (clases de colección)
        ObservableList<Sedes> listaSedes = FXCollections.observableArrayList();
        try {
            String sql = "SELECT id_sede, ciudad, telefono,direccion FROM sede" ;

            ResultSet resultado = con.CONSULTAR(sql);
            while (resultado.next()){
                listaSedes.add(
                        new Sedes(resultado.getString("ciudad"),
                                resultado.getString("telefono"),
                                resultado.getString("direccion")
                        )
                );
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Sedes.class.getName()).log(Level.SEVERE,null, ex);
        }
        return listaSedes;
    }
