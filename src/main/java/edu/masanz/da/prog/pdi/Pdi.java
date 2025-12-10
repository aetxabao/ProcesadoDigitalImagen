package edu.masanz.da.prog.pdi;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pdi {


//    public static Image convertir(Image image) { //convertToGrayScale
//        int width = (int) image.getWidth();
//        int height = (int) image.getHeight();
//
//        WritableImage grayImage = new WritableImage(width, height);
//        PixelWriter writer = grayImage.getPixelWriter();
//        PixelReader reader = image.getPixelReader();
//
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                Color color = reader.getColor(x, y);
//
//                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
//
//                Color grayColor = new Color(gray, gray, gray, color.getOpacity());
//
//                writer.setColor(x, y, grayColor);
//            }
//        }
//        return grayImage;
//    }

    public static Image convertir(Image imagenOriginal) { //convertToOnePixel
        double[][] filtro = {
                {-1, -1, -1},
                {-1,  8, -1},
                {-1, -1, -1}
        };
        int width = (int) imagenOriginal.getWidth();
        int height = (int) imagenOriginal.getHeight();

        WritableImage imagenTransformada = new WritableImage(width, height);
        PixelWriter writer = imagenTransformada.getPixelWriter();
        PixelReader reader = imagenOriginal.getPixelReader();

        // Ejemplo de como obtener un pixel concreto de la imagen
        int pixelX = 0;
        int pixelY = 0;
        Color color = reader.getColor(pixelX, pixelY);
        // cogemos el valor de cada color en el pixel dado, valor de 0-255
        int pixelRojo = (int) (color.getRed()*255);
        int pixelVerde = (int) (color.getGreen()*255);
        int pixelAzul = (int) (color.getBlue()*255);
        // Ahora podemos manipular el color del pixel cambiando esos valores, por ejemplo:
        pixelRojo = pixelRojo - 50;
        pixelVerde = pixelVerde + 100;
        pixelAzul = pixelAzul - 25;
        // Nos aseguramos de que no nos pasamos de los valores del 0 al 1.0
        double r = Math.max(0, Math.min(255, pixelRojo)) / 255.0;
        double g = Math.max(0, Math.min(255, pixelVerde)) / 255.0;
        double b = Math.max(0, Math.min(255, pixelAzul)) / 255.0;
        // Y ahora genero el pixel otra vez con los colores manipulados
        Color nuevoColor = new Color(r, g, b, color.getOpacity());
        // Y finalmente coloco ese pixel en la nueva imagen que estamos generando
        writer.setColor(pixelX, pixelY, nuevoColor);
        // listo, imagen manipulada y ya la podemos devolver

        return imagenTransformada;
    }

}
