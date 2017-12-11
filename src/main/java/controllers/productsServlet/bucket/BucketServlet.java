package controllers.productsServlet.bucket;

import dao.BucketDAO;
import dao.GunDAO;
import helpers.JdbcDAO;
import model.Gun;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@WebServlet("/products/ActionBucketServlet")
public class BucketServlet extends HttpServlet {

    private BucketDAO bucketDAO;

    @Override
    public void init() throws ServletException {
        bucketDAO =new BucketDAO((JdbcDAO) getServletContext().getAttribute("database"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher("/pages/login.html").forward(req, resp);
        } else {

            String []chosen_products = req.getParameterValues("product");
            User user = (User) req.getSession().getAttribute("user");
            bucketDAO.addGuns(String.valueOf(user.getId()), chosen_products);
            req.getRequestDispatcher("/products/MainBucketServlet").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
