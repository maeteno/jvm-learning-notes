package com.maeteno.study.java.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 5YKF5Y+M5Lqu
 */
@WebServlet(urlPatterns = "/")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String name = req.getParameter("name");
        if (name == null) {
            name = "world";
        }

        PrintWriter pw;
        try {
            pw = resp.getWriter();
            pw.write("<h1>Hello, " + name + "!</h1>");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}