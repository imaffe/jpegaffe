package com.affe.model;

import java.awt.image.BufferedImage;
import java.io.File;

public class JpegModel {
    private BufferedImage image;

    // TODO change the public constructor to a factory
    public JpegModel(File file){
        // TODO what if file does not exist ?

    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }
}
