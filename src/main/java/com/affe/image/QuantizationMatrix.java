package com.affe.image;

public class QuantizationMatrix extends Matrix8f{
    private double quality;

    public QuantizationMatrix() {
        // default quantization 0.9
        this(0.9);
    }

    public QuantizationMatrix(double quality) {
        // TODO why super must be the first statement ?
        super();
        double[] quant = new double[64];
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                quant[i * 8 + j] = 8* (1 + quality * (i + j - 1));
            }
        }
        super.setBuffer(quant);
    }
}
