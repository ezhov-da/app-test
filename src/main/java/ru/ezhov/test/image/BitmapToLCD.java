package ru.ezhov.test.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//http://playground.arduino.cc/Code/PCD8544
public class BitmapToLCD {
    public static final int WIDTH = 84;
    public static final int HEIGHT = 48;

    public static void main(String[] args) {
        try {
            BufferedImage bufferedImage = ImageIO.read(BitmapToLCD.class.getResourceAsStream("/test.jpg"));
            convert(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convert(BufferedImage bufferedImage) {
        try {
            BufferedImage image = bufferedImage;

            // Get all the pixels
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            int[] rgbs = new int[w * h];
            image.getRGB(0, 0, w, h, rgbs, 0, w);

            //iterate through each pixel (and reduce to binary)
            int row = 0;
            int col = 0;
            int bit = 0;
            byte[][] ba = new byte[HEIGHT / 8][WIDTH];
            for (int i = 0; i < rgbs.length; i++) {
                byte val = (byte) (rgbs[i] & 0x01);
                //invert the value
                val = (byte) (val == 1 ? 0 : 1);
                ba[row][col] |= val << bit;

                //next column
                col++;

                //next bit
                if (col >= WIDTH) {
                    col = 0;
                    bit++;
                }

                //next data row
                if (bit >= 8) {
                    bit = 0;
                    for (int x = 0; x < WIDTH; x++) {
                        String s = Integer.toHexString((byte) ba[row][x]);
                        //Do some formatting
                        if (s.length() > 2) {
                            s = s.substring(s.length() - 2);
                        }
                        while (s.length() < 2) {
                            s = "0" + s;
                        }
                        System.out.print("0x" + s + ",");
                    }
                    System.out.println("");
                    row++;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
