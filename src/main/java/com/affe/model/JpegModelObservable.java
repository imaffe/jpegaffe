package com.affe.model;

import com.affe.image.DctMatrix;
import com.affe.image.YuvColor;
import com.affe.interfaces.Observable;
import com.affe.interfaces.Observer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.affe.view.MainApp.logger;

public class JpegModelObservable implements Observable {

    // factory using a static method

    // private structures
    private List<Observer> observerList;

    private BufferedImage image;

    private int width;

    private int height;

    private YuvColor yuvColor;

    private DctMatrix dctMatrix;
    // intermediate structures




    public JpegModelObservable() {
        observerList = new ArrayList<Observer>();
    }

    public JpegModelObservable(File file){
        observerList = new ArrayList<Observer>();
        // TODO a factory here is more appropriate

        // what if file does not exist ? better check before passed in
        try {
            image = ImageIO.read(file);
            if (image != null) logger.info("image read");
            else logger.info("image not read");
        } catch (IOException e){
            logger.info(e.getMessage());
        }
    }

    // TODO do we need to pass a File to create JpegModel
    // TODO or let JpegModel control the file opening ?

    public void registerObserver(Observer observer) {
        observerList.add(observer);
        // TODO looks like here we can use reflection to the concrete type of observer
        logger.info("registered observer");
    }

    public void removeObserver(Observer observer){
        int i = observerList.indexOf(observer);
        if (i >= 0) {
            observerList.remove(i);
        }
    }

    public void notifyObservers(){
        logger.info("notifying observers");
        for (int i = 0; i < observerList.size(); i++) {
            Observer observer =  observerList.get(i);
            observer.update(image);
        }
    }


    // getters and setter
    public BufferedImage getImage() {
        return image;
    }

    public List<Observer> getObserverList(){
        return observerList;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setImageByFile(File file) {
        try {
            image = ImageIO.read(file);
            if (image != null) {
                logger.info("image read");
                notifyObservers();
            }
            else logger.info("image not read");
        } catch (IOException e){
            logger.info(e.getMessage());
        }
    }

    // algorithms
    public JpegModelObservable compress(double quality) {
        this.step1ColorTransform()
            .step2Subsampling()
            .step3DctAndQuantization(quality)
            .step4EntropyCoding()
            .step5ComposeJpeg();
        return this;
    }

    public JpegModelObservable save(File file) {

        return this;
    }

    // private processing
    private JpegModelObservable step1ColorTransform() {
        width = image.getWidth();
        height = image.getHeight();
        // TODO shouldn't we use primitives here ?
        int[] pixelValues = new int[width * height];
        yuvColor = new YuvColor(width, height, pixelValues);

        return this;
    }

    private JpegModelObservable step2Subsampling() {
        yuvColor.subsampling();
        return this;
    }

    private JpegModelObservable step3DctAndQuantization(double quality) {
        dctMatrix = new DctMatrix(yuvColor);
        dctMatrix.dctAndQuantization(quality);
        return this;
    }

    private JpegModelObservable step4EntropyCoding() {
        return this;
    }

    private JpegModelObservable step5ComposeJpeg() {
        return this;
    }

}
