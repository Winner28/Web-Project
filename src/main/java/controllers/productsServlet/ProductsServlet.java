package controllers.productsServlet;

import dao.GunDAO;
import dao.UserDAO;
import helpers.JdbcDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/products/ProductsServlet")
public class ProductsServlet extends HttpServlet {

    private GunDAO gunDAO;

    @Override
    public void init() throws ServletException {
        gunDAO = new GunDAO((JdbcDAO) getServletContext().getAttribute("database"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().log("Products Servlet is visited");
        System.out.println(gunDAO.getAllGuns());
        req.setAttribute("products", gunDAO.getAllGuns());
        req.getRequestDispatcher("/products/products.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
