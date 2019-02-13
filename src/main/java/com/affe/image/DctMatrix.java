package com.affe.image;

import com.sun.javafx.geom.Matrix3f;

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
        yFreq = new double[height][width];
        uFreq = new double[height][width];
        vFreq = new double[height][width];

        for (int i = 0 ; i  < height; i++) {
            for (int j = 0; j < width; j++) {
                yFreq[i][j] = yuvColor.getYValues()[i * width + j] - 128;
                uFreq[i][j] = yuvColor.getUValues()[i * width + j] - 128;
                vFreq[i][j] = yuvColor.getVValues()[i * height + j] - 128;
            }
        }
    }

    public DctMatrix dctAndQuantization(double quality) {
        // TODO there can be border check
        QuantizationMatrix quant = new QuantizationMatrix(quality);
        for (int i = 0; i < height; i += 8) {
            for (int j = 0 ; j < width; j += 8) {
                Matrix8f yBlock = new Matrix8f(yFreq, i, j);
                Matrix8f yDct = TransformMatrix.getInstance(false).multiply(yBlock).multiply(TransformMatrix.getInstance(true));

                Matrix8f uBlock = new Matrix8f(yFreq, i, j);
                Matrix8f uDct = TransformMatrix.getInstance(false).multiply(uBlock).multiply(TransformMatrix.getInstance(true));

                Matrix8f vBlock = new Matrix8f(yFreq, i, j);
                Matrix8f vDct = TransformMatrix.getInstance(false).multiply(vBlock).multiply(TransformMatrix.getInstance(true));

                for (int k = 0 ; k < 8 ; k++) {
                    for (int l = 0; l < 8; l++) {
                        double quantNumber = quant.get(k, l);
                        yFreq[i+k][j+l] = Math.round(yDct.get(k,l) / quantNumber);
                        uFreq[i+k][j+l] = Math.round(uDct.get(k,l) / quantNumber);
                        vFreq[i+k][j+l] = Math.round(vDct.get(k,l) / quantNumber);
                    }
                }
            }
        }
        return this;
    }
    // getters and setters
    // TODO add getters and setters here to support Java Bean
}
