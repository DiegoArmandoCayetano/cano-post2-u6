package com.universidad.mvc.controller;

import java.io.IOException;
import java.util.Locale;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/idioma")
public class IdiomaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String lang = req.getParameter("lang");

        if (lang != null) {
            Locale locale = new Locale(lang);

            HttpSession session = req.getSession();
            session.setAttribute("locale", locale);
        }

        String referer = req.getHeader("Referer");

        resp.sendRedirect(referer != null
                ? referer
                : req.getContextPath() + "/productos");
    }
}