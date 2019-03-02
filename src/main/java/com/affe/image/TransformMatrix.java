package com.affe.image;

public class TransformMatrix extends Matrix8f{
    // TODO try to use a singleton here
    // TODO should we return a TransformMatrix or Matrix8f using getInstance()
    // TODO this singleton might be changed due to matrix operations, how to avoid that ?
    private volatile static Matrix8f transformMatrixSingleton;
    private volatile static Matrix8f transformMatrixTransposeSingleton;
    private TransformMatrix(double[] data){
        super(data,0, 0, 8, 8);
    }

    public static Matrix8f getInstance(boolean transpose) {
        if (transformMatrixSingleton == null) {
            synchronized (TransformMatrix.class) {
                if (transformMatrixSingleton == null) {
                    transformMatrixSingleton = new TransformMatrix(TransformMatrix.getDctBuffer());
                    transformMatrixTransposeSingleton = transformMatrixSingleton.transpose();
                }
            }
        }
        if (transpose)return transformMatrixTransposeSingleton;
        else return transformMatrixSingleton;
    }



    private static double[] getDctBuffer(){
        double [] buffer = new double[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buffer[i * 8 + j] = (i == 0) ? (0.5 / Math.sqrt(2)) : (0.5 * Math.cos((2*j+1)*i*Math.PI/16));
            }
        }
        return buffer;
    }
}
