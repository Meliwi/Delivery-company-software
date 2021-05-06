package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Users {
    private StringProperty cedula;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty telefono;
    private StringProperty correo;
    private StringProperty nombre_rol;
    private StringProperty nombre_estado;



    public Users(String cedula, String nombre, String apellido, String telefono, String correo, String nombre_rol, String nombre_estado)
    {
        this.cedula = new SimpleStringProperty(cedula);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.telefono = new SimpleStringProperty(telefono);
        this.correo = new SimpleStringProperty(correo);
        this.nombre_rol = new SimpleStringProperty(nombre_rol);
        this.nombre_estado = new SimpleStringProperty(nombre_estado);
    }

    public String getCedula() {
        return cedula.get();
    }
    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }
    public StringProperty cedulaProperty() {
        return cedula;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
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

    public String getCorreo() {
        return correo.get();
    }

    public StringProperty correoProperty() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public String getNombre_rol() {
        return nombre_rol.get();
    }

    public StringProperty nombre_rolProperty() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol.set(nombre_rol);
    }

    public String getNombre_estado() {
        return nombre_estado.get();
    }

    public StringProperty nombre_estadoProperty() {
        return nombre_estado;
    }

    public void setNombre_estado(String nombre_estado) {
        this.nombre_estado.set(nombre_estado);
    }

    public static ObservableList<Users> fillTableUsers() {
        Connect con  = new Connect();
        //Se inicializa una colección de datos de tipo Users aún vacía (clases de colección)
        ObservableList<Users> listaUsuarios = FXCollections.observableArrayList();
        try {
            String sql = "SELECT cedula, nombre, apellido, telefono, correo, nombre_rol, nombre_estado FROM usuarios, rol, estado " +
                    "WHERE codigo_rol = rol.codigo AND id_estado = estado.id" ;

            ResultSet resultado = con.CONSULTAR(sql);
            while (resultado.next()){
                listaUsuarios.add(
                        new Users(resultado.getString("cedula"),
                                resultado.getString("nombre"),
                                resultado.getString("apellido"),
                                resultado.getString("telefono"),
                                resultado.getString("correo"),
                                resultado.getString("nombre_rol"),
                                resultado.getString("nombre_estado"))
                );
            }
        }
        catch (SQLException ex){
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE,null, ex);
        }
        return listaUsuarios;
    }

}