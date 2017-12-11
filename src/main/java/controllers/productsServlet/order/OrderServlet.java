package controllers.productsServlet.order;

import dao.BucketDAO;
import dao.GunDAO;
import helpers.JdbcDAO;
import model.Bucket;
import model.Gun;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/products/OrderServlet")
public class OrderServlet extends HttpServlet {


    private GunDAO gunDAO;
    private BucketDAO bucketDAO;

    @Override
    public void init() throws ServletException {
        gunDAO = new GunDAO((JdbcDAO) getServletContext().getAttribute("database"));
        bucketDAO =new BucketDAO((JdbcDAO) getServletContext().getAttribute("database"));


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher("/pages/login.html").forward(req, resp);
        } else {

            User user = (User) req.getSession().getAttribute("user");
            Bucket bucket = bucketDAO.getBucketContent(String.valueOf(user.getId())).get();

            List<Integer> list = bucket.getGunList();
            if (list.size() == 0) {
                req.getRequestDispatcher("/products/empty.html").forward(req, resp);
            } else {

                if (bucketDAO.deleteAllData(String.valueOf(user.getId()))) {
                    req.setAttribute("order_price",req.getParameter("order_price"));
                    req.getRequestDispatcher("/products/order.jsp").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_GATEWAY);
                }


            }







        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
