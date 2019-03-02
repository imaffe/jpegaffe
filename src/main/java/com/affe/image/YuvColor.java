package com.affe.image;

import java.awt.image.BufferedImage;


public class YuvColor {

    private int width;

    private int height;

    private int[] yValues;

    private int[] uValues;

    private int[] vValues;

    // TODO why this not using a factory ?

    public YuvColor(int width, int height, int[] pixelValues) {
        yValues = new int[width * height];
        uValues = new int[width * height];
        vValues = new int[width * height];
        this.width = width;
        this.height = height;
        for (int index = 0; index < height * width; ++index){
            int red = ((pixelValues[index] & 0x00ff0000) >> 16);
            int green =((pixelValues[index] & 0x0000ff00) >> 8);
            int blue = ((pixelValues[index] & 0x000000ff) );

            yValues[index] = (int)((0.299 * (float)red) + (0.587 * (float)green) + (0.114 * (float)blue));            yValues[index] = (int)((0.299 * (float)red) + (0.587 * (float)green) + (0.114 * (float)blue));
            uValues[index] = (int)((-0.147 * (float)red) + (-0.289 * (float)green) + (0.436 * (float)blue));
            vValues[index] = (int)((0.615 * (float)red) + (-0.515 * (float)green) + (-0.100 * (float)blue));
        }
    }

    public YuvColor(int width, int height){
        yValues = new int[width * height];
        uValues = new int[width * height];
        vValues = new int[width * height];
        this.width = width;
        this.height = height;
    }

    // subsampling
    public YuvColor subsampling() {
        for (int i = 0; i < height-1; i += 2) {
            for (int j = 0; j < width-1; j += 2) {
                uValues[i * width + j + 1] = uValues[i * width + j];
                uValues[(i+1) * width + j] = uValues[i * width + j];
                uValues[(i+1) * width + j + 1] = uValues[i * width + j];

                vValues[i * width + j] = vValues[(i+1) * width + j];
                vValues[i * width + j + 1] = vValues[(i+1) * width + j];
                vValues[(i+1) * width + j + 1] = vValues[(i+1) * width + j];
            }
        }
        return this;
    }

    public BufferedImage yuv2rgb(){
        BufferedImage rgb_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int rgb = 0;
                int r = (int) (yValues[i*width + j] + 1.13983 * vValues[i*width + j]);
                int g = (int) (yValues[i*width + j] - 0.39465 * uValues[i*width + j] - 0.58060 * vValues[i*width + j]);
                int b = (int) (yValues[i*width + j] + 2.03211 * uValues[i*width + j]);
                rgb = (rgb + r) << 8;
                rgb = ((rgb + g) << 8) + b;
                rgb_image.setRGB(j, i, rgb);
            }
        }

        return rgb_image;
    }


    // getters and setter
    // TODO what is the javabean of 'one letter + a word'
    public int[] getYValues() { return yValues; }

    public int[] getUValues() { return uValues; }

    public int[] getVValues() { return vValues; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public void setYValues(int [] yValues) { this.yValues = yValues; }

    public void setUValues(int [] uValues) { this.uValues = uValues; }

    public void setVValues(int [] vValues) { this.vValues = vValues; }

    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) {this.height = height; }
}

