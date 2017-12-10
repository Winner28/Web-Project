package controllers.productsServlet;

import dao.GunDAO;
import helpers.JdbcDAO;
import model.Gun;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@WebServlet("/products/BucketServlet")
public class BucketServlet extends HttpServlet {

    private GunDAO gunDAO;

    @Override
    public void init() throws ServletException {
        gunDAO = new GunDAO((JdbcDAO) getServletContext().getAttribute("database"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher("/pages/login.html").forward(req, resp);
        } else {
            String []chosen_products = req.getParameterValues("product");
            System.out.println(gunDAO.getSelectedGuns(chosen_products));
            req.setAttribute("bucket_products", gunDAO.getSelectedGuns(chosen_products));

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
