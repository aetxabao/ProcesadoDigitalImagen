package edu.masanz.da.prog.pdi;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pdi {


    public static Image convertToGrayScale(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage grayImage = new WritableImage(width, height);
        PixelWriter writer = grayImage.getPixelWriter();
        PixelReader reader = image.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);

                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;

                Color grayColor = new Color(gray, gray, gray, color.getOpacity());

                writer.setColor(x, y, grayColor);
            }
        }
        return grayImage;
    }

    public static Image convertToRed(Image imagenOriginal) {
        int width = (int) imagenOriginal.getWidth();
        int height = (int) imagenOriginal.getHeight();

        WritableImage imagenTransformada = new WritableImage(width, height);
        PixelWriter writer = imagenTransformada.getPixelWriter();
        PixelReader reader = imagenOriginal.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                // cogemos el valor de cada color en el pixel dado, valor de 0-255
                int pixelRojo = (int) (color.getRed()*255);
                // Nos aseguramos de que no nos pasamos de los valores del 0 al 1.0
                double r = Math.max(0, Math.min(255, pixelRojo)) / 255.0;
                // Y ahora genero el pixel otra vez con los colores manipulados
                Color nuevoColor = new Color(r, 0, 0, color.getOpacity());
                // Y finalmente coloco ese pixel en la nueva imagen que estamos generando
                writer.setColor(x, y, nuevoColor);
                // listo, imagen manipulada y ya la podemos devolver
            }
        }
        return imagenTransformada;
    }

    public static Image convertToBW(Image imagenOriginal) {
        int width = (int) imagenOriginal.getWidth();
        int height = (int) imagenOriginal.getHeight();

        WritableImage imagenTransformada = new WritableImage(width, height);
        PixelWriter writer = imagenTransformada.getPixelWriter();
        PixelReader reader = imagenOriginal.getPixelReader();

        double r,g,b;
        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                Color color = reader.getColor(x, y);
                int pixelRojo = (int) (color.getRed()*255);
                int pixelVerde = (int) (color.getGreen()*255);
                int pixelAzul = (int) (color.getBlue()*255);
                // Thresholding
//                int promedio = (pixelRojo + pixelVerde + pixelAzul) / 3;
//                if (promedio < 30) {
                if (pixelRojo > 64 || pixelVerde > 64 || pixelAzul > 64) {
                    r = 1;
                    g = 1;
                    b = 1;
                } else {
                    r = 0;
                    g = 0;
                    b = 0;
                }
                // Y ahora genero el pixel otra vez con los colores manipulados
                Color nuevoColor = new Color(r, g, b, 1.0);
                // Y finalmente coloco ese pixel en la nueva imagen que estamos generando
                writer.setColor(x, y, nuevoColor);
                // listo, imagen manipulada y ya la podemos devolver
            }
        }
        return imagenTransformada;
    }


    public static Image convertToEdges(Image imagenOriginal) {
        double[][] filtro = {
                {-1, -1, -1},
                {-1,  8, -1},
                {-1, -1, -1}
        };
        return applyConvolution(imagenOriginal, filtro);
    }

    public static Image convertToBlur(Image imagenOriginal) {
        double[][] filtro = {
                {0.0625, 0.125, 0.0625},
                {0.125,  0.25, 0.125},
                {0.0625, 0.125, 0.0625}
        };
        return applyConvolution(imagenOriginal, filtro);
    }

    private static Image applyConvolution(Image imagenOriginal, double[][] filtro) {
        int width = (int) imagenOriginal.getWidth();
        int height = (int) imagenOriginal.getHeight();

        WritableImage imagenTransformada = new WritableImage(width, height);
        PixelWriter writer = imagenTransformada.getPixelWriter();
        PixelReader reader = imagenOriginal.getPixelReader();

        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                Color color;
                int pixelRojo = 0;
                int pixelVerde = 0;
                int pixelAzul = 0;
                for (int f = y-1; f <= y+1 ; f++) {
                    for (int c = x-1; c <= x+1 ; c++) {
                        color = reader.getColor(c, f);
                        pixelRojo += (int) (color.getRed()*255 * filtro[f - (y -1)][c - (x -1)]);
                        pixelVerde += (int) (color.getGreen()*255 * filtro[f - (y -1)][c - (x -1)]);
                        pixelAzul += (int) (color.getBlue()*255 * filtro[f - (y -1)][c - (x -1)]);
                    }
                }
                // Nos aseguramos de que no nos pasamos de los valores del 0 al 1.0
                double r = Math.max(0, Math.min(255, pixelRojo)) / 255.0;
                double g = Math.max(0, Math.min(255, pixelVerde)) / 255.0;
                double b = Math.max(0, Math.min(255, pixelAzul)) / 255.0;
                // Y ahora genero el pixel otra vez con los colores manipulados
                Color nuevoColor = new Color(r, g, b, 1.0);
                // Y finalmente coloco ese pixel en la nueva imagen que estamos generando
                writer.setColor(x, y, nuevoColor);
                // listo, imagen manipulada y ya la podemos devolver
            }
        }
        return imagenTransformada;
    }

    public static Image blurAndEdges(Image originalImage) {
        return  convertToBW (
                    convertToEdges(
                        convertToBlur(
                            convertToBlur(
                                originalImage) ) ) );
    }

    public static Image rotate(Image originalImage) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        // Cambio ancho y alto para la nueva imagen
        WritableImage imagenTransformada = new WritableImage(height, width);
        PixelWriter writer = imagenTransformada.getPixelWriter();
        PixelReader reader = originalImage.getPixelReader();
        // Rotamos 90 grados
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Leemos el color
                Color color = reader.getColor(x, y);
//                // Observa el cambio (contra horario)
//                writer.setColor(y, width - x -1, color);
                // Observa el cambio (sentido horario)
                writer.setColor(height-1-y, x, color);
            }
        }
        return imagenTransformada;
    }

}

