package com.affe.image;

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
            int red = ((pixelValues[index] & 0x0e0ff0000) >> 16);
            int green =((pixelValues[index] & 0x0000ff00) >> 8);
            int blue = ((pixelValues[index] & 0x000000ff) );
            yValues[index] = (int)((0.299 * (float)red) + (0.587 * (float)green) + (0.114 * (float)blue));            yValues[index] = (int)((0.299 * (float)red) + (0.587 * (float)green) + (0.114 * (float)blue));
            uValues[index] = (int)((-0.147 * (float)red) + (-0.289 * (float)green) + (0.436 * (float)blue));
            vValues[index] = (int)((0.615 * (float)red) + (-0.515 * (float)green) + (-0.100 * (float)blue));
        }
    }


    // subsampling
    public YuvColor subsampling() {
        for (int i = 0; i < height; i += 2) {
            for (int j = 0; j < width; j += 2) {
                uValues[i * width + j + 1] = uValues[i * width + j];
                uValues[(i+1) * width + j] = uValues[i * width + j];
                uValues[(i+1) * width + j + 1] = uValues[i * width + j];

                vValues[i * width + j] = vValues[(i+1) * width];
                vValues[i * width + j + 1] = vValues[(i+1) * width];
                vValues[(i+1) * width + j + 1] = vValues[(i+1) * width];
            }
        }
        return this;
    }
    // getters and setter
    // TODO what is the javabean of 'one letter + a word'
    public int[] getYValues() { return yValues; }

    public int[] getUValues() { return uValues; }

    public int[] getVValues() { return vValues; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    public void setYValues(int [] yValues) { this.yValues = yValues; }

    public void setUValues(int [] uValues) { this.yValues = uValues; }

    public void setValues(int [] values) { this.yValues = values; }

    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) {this.height = height; }
}

