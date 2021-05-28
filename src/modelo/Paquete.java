package modelo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Paquete {

    private SimpleStringProperty direccion;
    private SimpleStringProperty peso;
    private SimpleStringProperty distancia;
    private SimpleDoubleProperty impuesto;
    private SimpleStringProperty seguro;

    public Paquete(String direcccion, String peso, String distancia, Double impuesto, String seguro){
        this.direccion = new SimpleStringProperty(direcccion);
        this.peso = new SimpleStringProperty(peso);
        this.distancia = new SimpleStringProperty(distancia);
        this.impuesto = new SimpleDoubleProperty(impuesto);
        this.seguro = new SimpleStringProperty(seguro);
    }

    //Getters
    public String getDireccion() {return direccion.get();}
    public String getPeso() {return peso.get();}
    public String getDistancia() {return distancia.get();}
    public double getImpuesto() {return impuesto.get();}
    public String getSeguro() {return seguro.get(); }


    //Setters
    public void setDireccion(String direccion) {this.direccion.set(direccion);}
    public void setPeso(String peso) {this.peso.set(peso);}
    public void setDistancia(String distancia) {this.distancia.set(distancia);}
    public void setImpuesto(Double valor) {this.impuesto.set(valor);}
    public void setSeguro(String seguro) {this.seguro.set(seguro); }


    //Propery
    public SimpleStringProperty direccionProperty() { return direccion; }
    public SimpleStringProperty pesoProperty() { return peso; }
    public SimpleStringProperty distanciaProperty() { return distancia; }
    public SimpleDoubleProperty impuestoProperty() {return impuesto;}
    public SimpleStringProperty seguroProperty() { return seguro; }


}
