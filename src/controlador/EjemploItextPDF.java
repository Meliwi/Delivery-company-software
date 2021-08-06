package controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.clipper.ClipperOffset;
import modelo.Persona;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EjemploItextPDF {

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        List<Persona> listaPersonas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Melissa G", "545135");
        listaPersonas.add(persona1);

        Persona persona2 = new Persona("Laura L", "512483");
        listaPersonas.add(persona2);

        Persona persona3 = new Persona("Camila G", "5121354");
        listaPersonas.add(persona3);

        System.out.println("La lista tiene:"+listaPersonas.size()+" agregadas");
        crearPDF(listaPersonas);
    }

    public static void crearPDF(List <Persona> lista) throws FileNotFoundException, DocumentException {
        //Se crea el documento
        Document documento = new Document();
        //el outputStream para el fichero donde crearemos el PDF
        FileOutputStream ficheroPDF = new FileOutputStream("Personas.pdf");
        //Se asocia el documento de OutPutStream
        PdfWriter.getInstance(documento, ficheroPDF);
        //Se abre el documento
        documento.open();
        //Parrafo
        Paragraph titulo = new Paragraph("Lista de personas \n\n",
                FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLUE)
        );
        //Añadimos el título al documento
        documento.add(titulo);
        //Creamos una tabla
        PdfPTable tabla = new PdfPTable(3);
        tabla.addCell("ID");
        tabla.addCell("NOMBRE");
        tabla.addCell("TELEFONO");
        for (int i=0 ;  i<lista.size(); i++){
            tabla.addCell(""+i);
            tabla.addCell(lista.get(i).getNombre());
            tabla.addCell(lista.get(i).getTelefono());
        }
        //Añadimos la tabla al documento
        documento.add(tabla);
        //Se cierra el documento
        documento.close();
    }

}