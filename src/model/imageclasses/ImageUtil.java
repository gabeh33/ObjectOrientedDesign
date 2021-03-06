package model.imageclasses;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and
 * simply print its contents. Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file. 
   */
  public static void readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
      }
    }
  }

  /**
   * Returns a BufferedImage object from the data provided from the .ppm file. This can be used
   * to easily view the image and see if any modifications took place.
   * @param filename The path to the file
   * @return A BufferedImage object where each pixel has the RGB value specified in the .ppm file.
   */
  public BufferedImage getBufferedImageFromPPM(String filename) {
    Scanner sc;
    try {
      sc = new Scanner(new FileReader(filename));
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    sc.nextInt();
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        img.setRGB(j, i, new Color(r, g, b).getRGB());
      }
    }
    return img;
  }

  /**
   * Creates a BufferedImage object from image at the specified location.
   * @param filename The location of the image
   * @return A BufferedImage object that is created from the given images
   */
  public BufferedImage getBufferedJPEGorBufferedPNG(String filename) {
    String filetarget = filename.substring(filename.lastIndexOf('/') + 1);
    String filetype = filetarget.substring(filetarget.length() - 5).toLowerCase();
    if (filetarget.length() < 5) {
      throw new IllegalArgumentException("Bad file ");
    } else if (!(filetype.contains(".jpeg")
            || filetype.contains(".jpg")
            || filetype.contains(".png"))) {
      throw new IllegalArgumentException("Non-valid file type");
    }
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("Invalid Pathname Inputted");
    }

    return img;
  }


}

