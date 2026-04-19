package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoService service = new ProductoService();

    // ========================= DO GET =========================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarSesion(req, resp)) return;

        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar" -> listar(req, resp);
            case "formulario" -> mostrarFormulario(req, resp);
            case "editar" -> mostrarEdicion(req, resp);
            case "eliminar" -> eliminar(req, resp);
            default -> resp.sendError(404, "Acción no encontrada");
        }
    }

    // ========================= DO POST =========================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!verificarSesion(req, resp)) return;

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String accion = req.getParameter("accion");

        if ("guardar".equals(accion)) {
            guardar(req, resp);
        } else if ("actualizar".equals(accion)) {
            actualizar(req, resp);
        } else {
            resp.sendError(400, "Acción inválida");
        }
    }

    // ========================= LISTAR =========================
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("productos", service.obtenerTodos());
        forward(req, resp, "/WEB-INF/views/lista.jsp");
    }

    // ========================= FORMULARIO =========================
    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }

    // ========================= EDITAR =========================
    private void mostrarEdicion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("producto", service.obtenerPorId(id));

        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }

    // ========================= GUARDAR (CON VALIDACIÓN) =========================
    private void guardar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");

        Map<String, String> errores = new LinkedHashMap<>();

        // VALIDACIÓN NOMBRE
        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        }

        // VALIDACIÓN PRECIO
        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) errores.put("precio", "No puede ser negativo");
        } catch (Exception e) {
            errores.put("precio", "Debe ser número válido");
        }

        // VALIDACIÓN STOCK
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) errores.put("stock", "No puede ser negativo");
        } catch (Exception e) {
            errores.put("stock", "Debe ser entero válido");
        }

        // SI HAY ERRORES → VOLVER FORMULARIO
        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("nombre", nombre);
            req.setAttribute("categoria", categoria);
            req.setAttribute("precio", precioStr);
            req.setAttribute("stock", stockStr);

            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        service.guardar(new Producto(0, nombre.trim(), categoria, precio, stock));

        resp.sendRedirect(req.getContextPath()
                + "/productos?mensaje=Producto+guardado");
    }

    // ========================= ACTUALIZAR =========================
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Producto p = extraerProducto(req, id);
        service.actualizar(p);

        resp.sendRedirect(req.getContextPath()
                + "/productos?mensaje=Producto+actualizado");
    }

    // ========================= ELIMINAR =========================
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        service.eliminar(id);

        resp.sendRedirect(req.getContextPath()
                + "/productos?mensaje=Producto+eliminado");
    }

    // ========================= EXTRAER PRODUCTO =========================
    private Producto extraerProducto(HttpServletRequest req, int id) {

        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");

        double precio = Double.parseDouble(req.getParameter("precio"));
        int stock = Integer.parseInt(req.getParameter("stock"));

        return new Producto(id, nombre, categoria, precio, stock);
    }

    // ========================= FORWARD =========================
    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {

        req.getRequestDispatcher(path).forward(req, resp);
    }

    // ========================= SESIÓN =========================
    private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession s = req.getSession(false);

        if (s == null || s.getAttribute("usuarioActual") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }

        return true;
    }
}