package com.affe.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


import com.affe.model.JpegModelObservable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    @FXML
    private Button importButton;

    @FXML
    private Label label;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;
    private JpegModelObservable jpeg;
    private Stage stage;
    private Logger logger;
    @FXML
    private void handleImportButtonAction(ActionEvent event) {
        logger.info("Importing Jpeg File...");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File jpegFile = fileChooser.showOpenDialog(stage);
        if (jpegFile == null) {
            logger.info("File is null, choose another file");
            // TODO : add error logger, and process logic (like print something)

            // TODO : abort or something
        }

        // the file is valid;

    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);

    }

    //getters and setters
    public Canvas getCanvas(){
        return this.canvas;
    }

    public Button getImportButton(){
        return this.importButton;
    }

    public Label getLabel(){
        return this.label;
    }

    public GraphicsContext getGraphicsContext(){
        return this.gc;
    }

    public JpegModelObservable getJpeg(){
        return this.jpeg;
    }

    public Stage getStage(){
        return this.stage;
    }

    public Logger getLogger(){
        return this.logger;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setJpeg(JpegModelObservable jpeg) {
        this.jpeg = jpeg;
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }
}
