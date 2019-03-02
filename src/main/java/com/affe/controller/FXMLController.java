package com.affe.controller;

import java.io.*;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import static com.affe.view.MainApp.logger;

public class FXMLController implements Initializable, Observer {
    // TODO do we need to implememt this Controller as Observer ?
    // TODO or to say, can we add a CanvasController class ?
    @FXML
    private Button importButton, compressButton, saveButton;

    @FXML
    private ImageView imageView, resultView, icon, dctView;

    private Stage stage;

    private JpegModelObservable jpeg;
    // TODO make sure it can use Observer and not concrete class

    // quatization table quality. loss ratio
    private double lumQuality = 1;
    private double chromQuality = 1;
    private boolean isDefault = true;

    @FXML
    private RadioButton defaultBtn, qualityBtn;
    @FXML
    ToggleGroup group = new ToggleGroup();

    @FXML
    private Slider lumQualitySlider, chromQualitySlider;


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
        jpeg.setOriImageByFile(jpegFile);
    }

    @FXML
    private void handleCompressButtonAction(ActionEvent event) {
        if(jpeg == null || !jpeg.hasOriImage()){
            logger.info("You have to import image first");
            return ;
        }

        // TODO why when using Observable it cannot resolve getJpeg()
        logger.info("start to compress image");

        // compress the image
        System.out.println(isDefault);
        if(isDefault){
            jpeg.compress(1, 1);
            jpeg.extract(1, 1);
        }
        else{
            jpeg.compress(lumQuality, chromQuality);
            jpeg.extract(lumQuality, chromQuality);
        }


    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        if(jpeg == null || !jpeg.hasResImage()) {
            logger.info("You have to compress image first");
            return ;
        }

        logger.info("start to save image");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Result Image");
        // add file filters
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG", "*.jpg"));
        fileChooser.setInitialFileName(".jpg");

        // show the file chooser dialog
        File outputFile = fileChooser.showSaveDialog(stage);
        jpeg.saveResultImageByFile(outputFile);

    }


    @FXML
    public void update(BufferedImage image, int flag) {
        Image newimage = SwingFXUtils.toFXImage(image, null);
        if ( newimage != null) {
            if(flag == 0){
                imageView.setImage(newimage);
            }
            else if(flag == 1){
                resultView.setImage(newimage);
            }
            else {
                dctView.setImage(newimage);
            }

        } else {
            logger.info("Null image object");
        }
    }

    // Radio button listener
    @FXML
    private void initRadioButton(){

        defaultBtn.setUserData("default");
        defaultBtn.setToggleGroup(group);
        defaultBtn.setSelected(true);
        qualityBtn.setUserData("quality");
        qualityBtn.setToggleGroup(group);

        ChangeListener<Toggle> cLt = new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle){
                if (new_toggle != null){
                    if(group.getSelectedToggle().getUserData().toString() == "default"){
                        // default quatization table
                        isDefault = true;
                    }
                    else{
                        // quality quatization table
                        isDefault = false;
                    }
                }
                else{
                    isDefault = true;
                }
            }
        };

        group.selectedToggleProperty().addListener(cLt);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lumQualitySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                lumQuality = newValue.doubleValue();
            }
        });

        chromQualitySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                chromQuality = newValue.doubleValue();
            }
        });

        initRadioButton();

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
