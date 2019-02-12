package com.affe.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;


import com.affe.interfaces.Observable;
import com.affe.interfaces.Observer;
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

import static com.affe.view.MainApp.logger;

public class FXMLController implements Initializable {
    // TODO do we need to implememt this Controller as Observer ?
    // TODO or to say, can we add a CanvasController class ?
    @FXML
    private Button importButton;

    @FXML
    private Label label;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    private Stage stage;
    // TODO make sure it can use Observer and not concrete class
    private Observer observer;
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
        Observable jpeg = new JpegModelObservable(jpegFile);
        jpeg.registerObserver(observer);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);


        // set observer
        observer = new JpegModelObserver();
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

    public Stage getStage(){
        return this.stage;
    }

    public Observer getObserver() {
        return this.observer;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}
