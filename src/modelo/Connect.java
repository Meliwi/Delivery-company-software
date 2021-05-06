package modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
    //creacion de variable de conexion
    private Connection conexion;
    private Statement st;

    //constructor
    public Connect(){
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://ec2-54-167-152-185.compute-1.amazonaws.com:5432/d10eemjdpri947",
                    "fpuzukgqzwwfep","bc4841da67e4cb7b8fab4e52ee6b466ab3ec0a42754d6b3f116beb186e75b142");
            //creacion del statement
            st = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch(Exception ex){
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
    //retornar el resultado de consultar en la base de datos
    public ResultSet CONSULTAR(String sql) throws SQLException {
        return st.executeQuery(sql);
    }

    //Para guardar datos
    public boolean GUARDAR(String sql) {
        try {
            if(st.executeUpdate(sql)>0){
                System.out.println("Se ejecutó el query->"+sql);
                return true;
            }
            else{
                System.out.println("No se ejecutó el query->"+sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null,ex);
        }
        return false;
    }

    //para cerrar la conexión
    public void CERRAR(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
}
