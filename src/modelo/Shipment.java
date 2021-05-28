package modelo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Shipment {


    private StringProperty direccion;
    private StringProperty peso;
    private StringProperty distancia;



    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getPeso() {
        return peso.get();
    }

    public StringProperty pesoProperty() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso.set(peso);
    }

    public String getDistancia() {
        return distancia.get();
    }

    public StringProperty distanciaProperty() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia.set(distancia);
    }



    public Shipment(String direccion, String peso, String distancia)
    {
        this.direccion = new SimpleStringProperty(direccion);
        this.peso = new SimpleStringProperty(peso);
        this.distancia = new SimpleStringProperty(distancia);

    }
}
