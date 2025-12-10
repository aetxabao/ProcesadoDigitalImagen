package edu.masanz.da.prog.pdi;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PdiController {

    private Image originalImage;
    private Image transformedImage;

    @FXML
    private StackPane originalPane;

    @FXML
//    private ImageView originalView;
    private WrappedImageView originalView;

    @FXML
    private StackPane transformedPane;

    @FXML
//    private ImageView transformedView;
    private WrappedImageView transformedView;

    @FXML
    public void initialize() {
    }

    @FXML
    void load(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.bmp")
        );
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            originalImage = new Image(file.toURI().toString());
            originalView.setImage(originalImage);
            transformedView.setImage(originalImage);
        }
    }

    @FXML
    void transform(ActionEvent event) {
        if (originalImage != null) {
            transformedImage = Pdi.convertir(originalImage);
            transformedView.setImage(transformedImage);
        }
    }

    @FXML
    void save(ActionEvent event) {
        if (transformedImage != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar imagen transformada");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            File file = fc.showSaveDialog(stage);
            if (file != null) {
                try {
                    BufferedImage bImage = SwingFXUtils.fromFXImage(transformedImage, null);
                    ImageIO.write(bImage, "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
