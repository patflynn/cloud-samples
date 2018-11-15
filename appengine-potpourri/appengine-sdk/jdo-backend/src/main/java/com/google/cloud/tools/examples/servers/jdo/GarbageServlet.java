package com.google.cloud.tools.examples.servers.jdo;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class GarbageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PersistenceManager persistenceManager = Persistence.PMF.getPersistenceManager();
        try {
            persistenceManager.makePersistent(new Garbage("Garbage" + Math.random()));
        } finally {
            persistenceManager.flush();
            persistenceManager.close();
        }
        resp.sendRedirect("/index.jsp");
    }
}
