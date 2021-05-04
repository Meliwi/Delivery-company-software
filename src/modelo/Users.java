package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.String;

public class Users {
    private StringProperty cedula;
    private StringProperty num_nomina;
    private StringProperty id_estado;
    private StringProperty codigo_rol;
    private StringProperty nit_empresa;
    private IntegerProperty id_pos;
    private IntegerProperty id_sede;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty telefono;
    private StringProperty correo;
    private StringProperty contrasena;
    private static Users usuarios;

    public Users(String cedula, String num_nomina, String id_estado, String codigo_rol, String nit_empresa,
                 int id_pos, int id_sede, String nombre, String apellido, String telefono, String correo, String contrasena)
    {
        this.cedula = new SimpleStringProperty(cedula);
        this.num_nomina = new SimpleStringProperty(num_nomina);
        this.id_estado = new SimpleStringProperty(id_estado);
        this.codigo_rol = new SimpleStringProperty(codigo_rol);
        this.nit_empresa = new SimpleStringProperty(nit_empresa);
        this.id_pos = new SimpleIntegerProperty(id_pos);
        this.id_sede = new SimpleIntegerProperty(id_sede);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.telefono = new SimpleStringProperty(telefono);
        this.correo = new SimpleStringProperty(correo);
        this.contrasena = new SimpleStringProperty(contrasena);

    }

    public static Users getInstanceUser(String cedula, String num_nomina, String id_estado, String codigo_rol, String nit_empresa, int id_pos, int id_sede, String nombre, String apellido, String telefono, String correo, String contrasena) {
        if (usuarios == null) {
            usuarios = new Users(cedula, num_nomina,id_estado,codigo_rol,nit_empresa,id_pos,id_sede,nombre,apellido,telefono,correo,contrasena);
        }
        return usuarios;
    }

}