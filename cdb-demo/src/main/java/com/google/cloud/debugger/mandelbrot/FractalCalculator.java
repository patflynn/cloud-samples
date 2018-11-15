package com.google.cloud.debugger.mandelbrot;

public class FractalCalculator {
  /**
   * 
   * @param x real part of C
   * @param y imaginary part of C
   * @return number of iterations until Zn crosses 2 (capped by the maximum iterations).
   */
  public float computePoint(float x, float y) {
    int iterationsCount = 0;
    
    // Start with Z0 = 0
    float curRe = 0;
    float curIm = 0;
    
    do {
      // Compute Zn+1 = Zn ^ 2 + c
      float nextRe = curRe * curRe - curIm * curIm + x;
      float nextIm = 2 * curRe * curIm + y;
      
      curRe = nextRe;
      curIm = nextIm;
      
      ++iterationsCount;
    } while ((iterationsCount < 32) && (complexAbs(curRe, curIm) < 2));
    
    // Smooth coloring algorithm. See
    // http://en.wikipedia.org/wiki/Mandelbrot_set#Continuous_.28smooth.29_coloring
    // for more details.
    if (iterationsCount >= 32) {
      return 32;
    }
    
    return iterationsCount
           + 1
           - (float) (Math.log(Math.log(complexAbs(curRe, curIm)) / Math.log(2)) / Math.log(2));
  }
  
  private static double complexAbs(double re, double im) {
    return Math.hypot(re, im);
  }
}
