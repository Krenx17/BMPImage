import java.io.*;

public class BmpImageHandler {
    public static void main(String[] args) throws Exception {
        String opt = args[0];
        String file = args[1];
        File img = new File(file);

        //Imagenes RGB y sepia
        if (opt.equals("-basics") && img.exists() == true) {
            BmpHandlerCore.img(file);
        } 
        //Rotar 180° (1 foto sobre el eje horizontal y 1 foto sobre el eje vertical)
        else if (opt.equals("-rotate") && img.exists() == true) {
            BmpHandlerRotator.img(file);
        } 
        //Minimiza un 50% de ancho y 50% de alto (1 foto de algo y 1 foto de alto)
        else if (opt.equals("-resize") && img.exists() == true) {
            BmpHandlerResizer.img(file);
        } 
        //Hace todas las opciones
        else if (opt.equals("-all") && img.exists() == true) {
            BmpHandlerCore.img(file); 
            BmpHandlerResizer.img(file); 
            BmpHandlerRotator.img(file);
        } 
        //Muestra las opciones que existen
        else if (opt.equals("-help")) {
            System.err.println("\nOpciones: \n1. -basiscs \n2. -rotate \n3. -resize \n4. -all");
        } else if (img.exists() == false) {
            System.err.println("Foto no encontrada");
        } else {
            System.err.println("\nEsa opción no existe las opciones son: \n1. -basiscs \n2. -rotate \n3. -resize \n4. -all");
        }
    }
}