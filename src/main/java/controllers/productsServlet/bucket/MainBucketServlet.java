package controllers.productsServlet.bucket;

import dao.BucketDAO;
import dao.GunDAO;
import helpers.JdbcDAO;
import model.Bucket;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@WebServlet("/products/MainBucketServlet")
public class MainBucketServlet extends HttpServlet {


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
            System.out.println(user.getId());
            Bucket bucket = bucketDAO.getBucketContent(String.valueOf(user.getId())).get();
            List<Integer> list = bucket.getGunList();
            if (list.size() == 0) {
                req.getRequestDispatcher("/product/empty.html").forward(req, resp);

            } else {
                String [] strArr = new String[list.size()];
                for (int i = 0; i < strArr.length; i++) {
                    strArr[i] = String.valueOf(list.get(i));
                }

                req.setAttribute("bucket_products", gunDAO.getSelectedGuns(strArr));
                req.getRequestDispatcher("/products/bucket.jsp").forward(req, resp);
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
