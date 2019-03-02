package com.affe.test;

import java.io.*;
import com.affe.image.DctMatrix;
import com.affe.image.YuvColor;
import java.awt.image.BufferedImage;

public class Test{

    public void test(){
        int[] a = new int[64];
        YuvColor yuvColor = new YuvColor(8, 8, a);

        int[] data = {200, 202, 189, 188, 189, 175, 175, 175,
                200, 203, 198, 188, 189, 182, 178, 175,
                203, 200, 200, 195, 200, 187, 185, 185,
                200, 200, 200, 200, 197, 187, 187, 187,
                200, 205, 200, 200, 195, 188, 187, 175,
                200, 200, 200, 200, 200, 190, 187, 175,
                205, 200, 199, 200, 191, 187, 187, 175,
                210, 200, 200, 200, 188, 185, 187, 186
        };

        int[] data2 = {
            2, 0, 0, 0, 0, 0, 0, 2,
            0, 2, 0, 0, 0, 0, 2, 0,
            0, 0, 2, 0, 0, 2, 0, 0,
            0, 0, 0, 2, 2, 0, 0, 0,
            0, 0, 0, 2, 2, 0, 0, 0,
            0, 0, 2, 0, 0, 1, 0, 0,
            0, 2, 0, 0, 0, 0, 2, 0,
            2, 0, 0, 0, 0, 0, 0, 2
        };

        yuvColor.setYValues(data);
        yuvColor.setUValues(data2);
        yuvColor.setVValues(data);

        DctMatrix dctMatrix = new DctMatrix(yuvColor);
        dctMatrix.dctAndQuantization(1, 1);
        dctMatrix.revQuantizationAndDCT(1, 1);

        for(int i = 0; i<8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print((int) (dctMatrix.getYFreq()[i][j]));
                System.out.print(" ");
            }
            System.out.println(" ");
        }

    }

    public void testGetRGB(BufferedImage image){
        System.out.println(image.getRGB(0, 0));
    }

}