package com.affe.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.affe.interfaces.Observer;
import com.affe.model.JpegModelObservable;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import static com.affe.view.MainApp.logger;

public class FXMLController implements Initializable, Observer {
    // TODO do we need to implememt this Controller as Observer ?
    // TODO or to say, can we add a CanvasController class ?
    @FXML
    private Button importButton;

    @FXML
    private ImageView imageView;

    private Stage stage;

    private JpegModelObservable jpeg;
    // TODO make sure it can use Observer and not concrete class
    @FXML
    private void handleImportButtonAction(ActionEvent event) {
        logger.info("Importing Jpeg File...");
        // the file is valid then bind to a model how to add a listener ?
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        // add file filters
        // TODO can here use an factory method ?
        // TODO here we can use a bette design pattern
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG file", "*.png"),
                new FileChooser.ExtensionFilter("JPEG file","*.jpg"));

        // show the file chooser dialog
        File jpegFile = fileChooser.showOpenDialog(stage);
        if (jpegFile == null) {
            logger.info("File is null, choose another file");
            // TODO : add error logger, and process logic (like print something)

            // TODO : abort or something
        }


        // register this model to the observer
        logger.info("Passing value to create model");
        jpeg = new JpegModelObservable();
        jpeg.registerObserver(this);
        jpeg.setImageByFile(jpegFile);
    }

    @FXML
    private void handleCompressButttonAction(ActionEvent event) {
        // TODO why when using Observable it cannot resolve getJpeg()


        // TODO add a write file choose to go save to the selected file.
        // jpeg.compress().save();
    }

    @FXML
    public void update(BufferedImage image) {
        Image newimage = SwingFXUtils.toFXImage(image, null);
        if ( newimage != null) {
            imageView.setImage(newimage);
        } else {
            logger.info("Null image object");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //getters and setters
    public ImageView getImageView(){ return this.imageView; }

    public Button getImportButton(){
        return this.importButton;
    }

    public Stage getStage(){
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
