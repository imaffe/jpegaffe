package com.affe.image;

import com.sun.javafx.geom.Matrix3f;

public class Matrix8f {
    // TODO we can modify this to support multiple matrixes
    private double[] buffer;

    public Matrix8f() {
        buffer = new double[64];
    }

    public Matrix8f(double[] data, int row, int col, int rowSize, int colSize) {
        // TODO : what we should in a constructor in failed
        // TODO : what will happen if the exception is thrown ?
        buffer = new double[64];
        if (row < 0 || col < 0 || row + 8 > rowSize || col + 8 > colSize)
            throw new IndexOutOfBoundsException();

        for (int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buffer[i * 8 + j] = data[(row + i) * 8 + j + col];
            }
        }
    }

    public Matrix8f(double [][] data, int row, int col) {
        buffer = new double[64];
        int rowSize = data.length;
        if (rowSize == 0) throw new IndexOutOfBoundsException();
        int colSize = data[0].length;
        if (colSize == 0) throw new IndexOutOfBoundsException();
        if (row < 0 || col < 0 || row + 8 > rowSize || col + 8 > colSize)
            throw new IndexOutOfBoundsException();

        for (int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buffer[i * 8 + j] = data[row][col];
            }
        }
    }


    public double get(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7)
            throw new ArrayIndexOutOfBoundsException();
        return buffer[row * 8 + col];
    }

    public void set(int row, int col, double value) {
        if (row < 0 || row > 7 || col < 0 || col > 7)
            throw new ArrayIndexOutOfBoundsException();

        buffer[row * 8 + col] = value;
    }

    public double[] getBuffer() {
        return buffer;
    }

    public void setBuffer(double[] buffer) {
        this.buffer = buffer;
    }

    // define some operations
    public Matrix8f multiply(Matrix8f rightMat) {
        // TODO : should we produce a new one or a modify in the leftMat ?
        double [] temp = new double[64];
        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                double sum = 0;
                for (int k = 0; k < 8; k++) {
                    sum += this.get(i, k) * rightMat.get(k, j);
                }
                temp[i * 8 + j] = sum;
            }
        }
        return new Matrix8f(temp, 0 , 0, 8, 8);
    }

    public Matrix8f transpose() {
        double [] temp = new double[64];
        for(int i = 0 ; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // TODO this transpose might be wrong, need double check
                // TODO it is right, but wait for whole test
                temp[i * 8 + j] = buffer[j * 8 + i];
            }
        }

        return new Matrix8f(temp, 0, 0, 8, 8);
    }
}
