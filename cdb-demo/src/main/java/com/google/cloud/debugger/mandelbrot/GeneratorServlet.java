package com.google.cloud.debugger.mandelbrot;


import java.io.*;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class GeneratorServlet extends HttpServlet {
  static {
    System.out.println("GeneratorServlet loaded");
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // Set the MIME type of the image.
    resp.setContentType("image/png");

    // Disable cache.
    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    resp.setDateHeader("Expires", 0);

    FractalCalculator calculator = new FractalCalculator();

    final int imageSize = 305;

    try {
      BufferedImage bufferedImage =
          new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

      long startTime = System.nanoTime();

      float cxminFrame = -2;
      float cyminFrame = -1.5f;
      float frameLength = 3;

      float scale = randomRange(0.7f, 1);
      float cxCenter = randomRange(cxminFrame, cxminFrame + frameLength);
      float cyCenter = randomRange(cyminFrame, cxminFrame + frameLength);

      float cxmin = cxCenter - scale * frameLength / 2;
      float cxmax = cxCenter + scale * frameLength / 2;
      float cymin = cyCenter - scale * frameLength / 2;
      float cymax = cyCenter + scale * frameLength / 2;

      // Compute points
      float[][] iterationMap = new float[imageSize][imageSize];
      for (int y = 0; y < imageSize; ++y)
        for (int x = 0; x < imageSize; ++x) {
          float cx = cxmin + (cxmax - cxmin) * x / imageSize;
          float cy = cymin + (cymax - cymin) * y / imageSize;

          iterationMap[x][y] = calculator.computePoint(cx, cy);
        }

      // Compute histogram
      int[] histogram = new int[256];
      for (int y = 0; y < imageSize; ++y)
        for (int x = 0; x < imageSize; ++x) {
          histogram[(int) iterationMap[x][y]]++;
        }

      // Compute count if 0..i
      float[] accumulatedWeight = new float[histogram.length];
      accumulatedWeight[0] = 0;
      for (int i = 1; i < histogram.length; ++i) {
        accumulatedWeight[i] =
            accumulatedWeight[i - 1] + (histogram[i] / (float) (imageSize * imageSize));
      }

      for (int y = 0; y < imageSize; ++y)
        for (int x = 0; x < imageSize; ++x) {
          float w;

          float iterationsCount = iterationMap[x][y];
          int left = (int) iterationsCount;
          if (left < 255) {
            w = weightedProportion(accumulatedWeight[left], accumulatedWeight[left + 1],
                iterationsCount - left);
          } else {
            w = accumulatedWeight[left];
          }

          int color = Color.HSBtoRGB(1 - w, 0.6f, (float) Math.pow(1 - w, 0.6));
          bufferedImage.setRGB(x, y, color);
        }

      long endTime = System.nanoTime();

      String message =
          String.format("Image computation time: %d milliseconds [0001]", (endTime - startTime) / 1000000);

      System.out.println(message);

      // Write the image as PNG.
      ImageIO.write(bufferedImage, "png", resp.getOutputStream());
    } catch (IOException ioe) {

    }
  }

  private static float weightedProportion(double left, double right, double pos) {
    if (pos < 0) {
      pos = 0;
    }

    if (pos > 1) {
      pos = 1;
    }

    return (float) (left + (right - left) * pos);
  }

  private float randomRange(float min, float max) {
    return weightedProportion(min, max, Math.random());
  }
}
