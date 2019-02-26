package com.affe.model;

import com.affe.image.DctMatrix;
import com.affe.image.YuvColor;
import com.affe.interfaces.Observable;
import com.affe.interfaces.Observer;

import java.io.*;

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

    private BufferedImage oriImage, resImage, dctImage;

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
            oriImage = ImageIO.read(file);
            if (oriImage != null) logger.info("image read");
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

    public void notifyObservers(int flag){
        logger.info("notifying observers");
        for (int i = 0; i < observerList.size(); i++) {
            Observer observer =  observerList.get(i);
            if(flag == 0) {
                observer.update(oriImage, 0);
            }
            else if(flag == 1){
                observer.update(resImage, 1);
            }
            else{
                observer.update(dctImage, 2);
            }
        }
    }


    // getters and setter
    public BufferedImage getOriImage() {
        return oriImage;
    }

    public List<Observer> getObserverList(){
        return observerList;
    }

    public void setOriImage(BufferedImage oriImage) {
        this.oriImage = oriImage;
    }

    public void setOriImageByFile(File file) {
        try {
            oriImage = ImageIO.read(file);
            if (oriImage != null) {
                logger.info("image read");
                notifyObservers(0);
            }
            else logger.info("image not read");
        } catch (IOException e){
            logger.info(e.getMessage());
        }

    }

    public void saveResultImageByFile(File file){
        try {
            ImageIO.write(resImage, "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // algorithms
    public JpegModelObservable compress(double lumQuality, double chromQuality) {
        this.step1ColorTransform()
            .step2Subsampling()
            .step3DctAndQuantization(lumQuality, chromQuality)
            .step4ShowDctImage();

        return this;
    }

    public JpegModelObservable extract(double lumQuality, double chromQuality){
        this.step5ExtractJpeg(lumQuality, chromQuality);
        this.notifyObservers(1);

        return this;
    }


    // private processing
    private JpegModelObservable step1ColorTransform() {
        width = oriImage.getWidth();
        height = oriImage.getHeight();
        // TODO shouldn't we use primitives here ?
        int[] pixelValues = new int[width * height];
        for(int i = 0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                pixelValues[i * width + j] = oriImage.getRGB(j, i);
            }
        }

        yuvColor = new YuvColor(width, height, pixelValues);

        return this;
    }

    private JpegModelObservable step2Subsampling() {
        yuvColor.subsampling();
        return this;
    }

    private JpegModelObservable step3DctAndQuantization(double lumQuality, double chromQuality) {
        dctMatrix = new DctMatrix(yuvColor);
        dctMatrix.dctAndQuantization(lumQuality, chromQuality);
        return this;
    }

    private JpegModelObservable step4ShowDctImage() {
        dctImage = dctMatrix.dct2rgb();
        notifyObservers(2);

        return this;
    }

    private JpegModelObservable step5ExtractJpeg(double lumQuality, double chromQuality) {
        dctMatrix.revQuantizationAndDCT(lumQuality, chromQuality);

        resImage = dctMatrix.dct2rgb();
        return this;
    }

    public boolean hasOriImage() {return (oriImage != null);}

    public boolean hasResImage() {return (resImage != null);}

}
