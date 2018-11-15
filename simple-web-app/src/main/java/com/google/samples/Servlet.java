package com.google.samples;

import java.io.IOException;

/**
 * TODO: Add Comments
 */
public class Servlet extends javax.servlet.http.HttpServlet {

  protected void doGet(javax.servlet.http.HttpServletRequest request,
      javax.servlet.http.HttpServletResponse response)
      throws javax.servlet.ServletException, IOException {
    response.getOutputStream().println("Hello buddy.");
    response.getOutputStream().flush();
  }
}
