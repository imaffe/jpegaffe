package com.affe.image;

import com.sun.javafx.geom.Matrix3f;
import java.awt.image.BufferedImage;


public class DctMatrix {
    private double[][] yFreq;

    private double[][] uFreq;

    private double[][] vFreq;

    private int height;

    private int width;

    public DctMatrix(YuvColor yuvColor) {
        // TODO java copy constructor !
        height = yuvColor.getHeight();
        width =  yuvColor.getWidth();
        // fill width and height up to be divided by 8
        width = ((width % 8) != 0) ? ((width + 8) - (width % 8)) : width;
        height = ((height % 8) != 0) ? ((height + 8) - (height % 8)) : height;

        yFreq = new double[height][width];
        uFreq = new double[height][width];
        vFreq = new double[height][width];

        for (int i = 0 ; i < yuvColor.getHeight(); i++) {
            for (int j = 0; j < yuvColor.getWidth(); j++) {
                yFreq[i][j] = yuvColor.getYValues()[i * yuvColor.getWidth() + j] - 128;
                uFreq[i][j] = yuvColor.getUValues()[i * yuvColor.getWidth() + j] - 128;
                vFreq[i][j] = yuvColor.getVValues()[i * yuvColor.getWidth() + j] - 128;
            }
        }

        // fill up the blank spaces
        for (int i = yuvColor.getHeight() ; i < height; i++) {
            for (int j = 0; j < yuvColor.getWidth(); j++) {
                yFreq[i][j] = yFreq[i-1][j];
                uFreq[i][j] = uFreq[i-1][j];
                vFreq[i][j] = vFreq[i-1][j];
            }
        }
        for (int j = yuvColor.getWidth(); j < width; j++) {
            for (int i = 0; i < height; i++){
                yFreq[i][j] = yFreq[i][j-1];
                uFreq[i][j] = uFreq[i][j-1];
                vFreq[i][j] = vFreq[i][j-1];
            }
        }
    }

    public DctMatrix doDCT(boolean isReverse) {
        for (int i = 0; i < height; i += 8) {
            for (int j = 0 ; j < width; j += 8) {
                Matrix8f yBlock = new Matrix8f(yFreq, i, j);
                Matrix8f yDct = TransformMatrix.getInstance(isReverse).multiply(yBlock).multiply(TransformMatrix.getInstance(!isReverse));

                Matrix8f uBlock = new Matrix8f(uFreq, i, j);
                Matrix8f uDct = TransformMatrix.getInstance(isReverse).multiply(uBlock).multiply(TransformMatrix.getInstance(!isReverse));

                Matrix8f vBlock = new Matrix8f(vFreq, i, j);
                Matrix8f vDct = TransformMatrix.getInstance(isReverse).multiply(vBlock).multiply(TransformMatrix.getInstance(!isReverse));

                for (int k = 0 ; k < 8 ; k++) {
                    for (int l = 0; l < 8; l++) {
                        yFreq[i+k][j+l] = yDct.get(k,l);
                        uFreq[i+k][j+l] = uDct.get(k,l);
                        vFreq[i+k][j+l] = vDct.get(k,l);
                    }
                }
            }
        }
        return this;
    }

    public DctMatrix doQuantization(double lumQuality, double chromQuality){
        QuantizationMatrix lum, chrom;
        lum = new QuantizationMatrix(true, lumQuality);
        chrom = new QuantizationMatrix(false, chromQuality);

        for (int i = 0 ; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                double lumQuantNumber = lum.get(i%8, j%8);
                double chromQuantNumber = chrom.get(i%8, j%8);
                yFreq[i][j] = Math.round(yFreq[i][j] / lumQuantNumber);
                uFreq[i][j] = Math.round(uFreq[i][j] / chromQuantNumber);
                vFreq[i][j] = Math.round(vFreq[i][j] / chromQuantNumber);
            }
        }
        return this;
    }

    public DctMatrix dctAndQuantization(double lumQuality, double chromQuality){
        this.doDCT(false);
        this.doQuantization(lumQuality, chromQuality);
        return this;
    }

    public DctMatrix revQuantizationAndDCT(double lumQuality, double chromQuality){
        QuantizationMatrix lum, chrom;
        lum = new QuantizationMatrix(true, lumQuality);
        chrom = new QuantizationMatrix(false, chromQuality);

        for (int i = 0 ; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                double lumQuantNumber = lum.get(i%8, j%8);
                double chromQuantNumber = chrom.get(i%8, j%8);
                yFreq[i][j] = yFreq[i][j] * lumQuantNumber;
                uFreq[i][j] = uFreq[i][j] * chromQuantNumber;
                vFreq[i][j] = vFreq[i][j] * chromQuantNumber;
            }
        }

        // reverse DCT
        this.doDCT(true);

        // add 128
        for (int i = 0 ; i < height ; i++) {
            for (int j = 0; j < width; j++) {
                yFreq[i][j] += 128;
                uFreq[i][j] += 128;
                vFreq[i][j] += 128;
            }
        }

        return this;
    }

    public BufferedImage dct2rgb(){

        int[] yValues = new int[width * height];
        int[] uValues = new int[width * height];
        int[] vValues = new int[width * height];

        YuvColor newYuvColor = new YuvColor(width, height);
        for(int i = 0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                yValues[i*width + j] = (int) Math.round(yFreq[i][j]);
                uValues[i*width + j] = (int) Math.round(uFreq[i][j]);
                vValues[i*width + j] = (int) Math.round(vFreq[i][j]);
            }
        }

        newYuvColor.setYValues(yValues);
        newYuvColor.setUValues(uValues);
        newYuvColor.setVValues(vValues);

        return newYuvColor.yuv2rgb();

    }



    // getters and setters
    // TODO add getters and setters here to support Java Bean
    public double[][] getYFreq() {return this.yFreq;}
    public double[][] getUFreq() {return this.uFreq;}
    public double[][] getVFreq() {return this.vFreq;}
    public int getHeight() {return this.height;}
    public int getWidth() {return this.width;}

}
