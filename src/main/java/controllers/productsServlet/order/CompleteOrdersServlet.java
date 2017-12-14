package controllers.productsServlet.order;


import dao.GunDAO;
import dao.OrderDAO;
import helpers.JdbcDAO;
import model.Gun;
import model.Order;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products/CompleteOrdersServlet")
public class CompleteOrdersServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private GunDAO gunDAO;

    @Override
    public void init() throws ServletException {
        orderDAO =new OrderDAO((JdbcDAO) getServletContext().getAttribute("database"));
        gunDAO =new GunDAO((JdbcDAO) getServletContext().getAttribute("database"));


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/login.html");
            out.println("<font color=red>Login first.</font>");
            requestDispatcher.include(req, resp);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            Order userOrder = orderDAO.getOrderContent(String.valueOf(user.getId())).get();
            List<Integer> idList = userOrder.getGunList();
            if (idList.size() == 0) {
                req.setAttribute("value", "Order ");
                req.getRequestDispatcher("/products/empty.jsp").forward(req, resp);
            } else {
                String []arr = new String[idList.size()];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = String.valueOf(idList.get(i));
                }
                List<Gun> gunList = gunDAO.getSelectedGuns(arr);
                double price = 0;
                for (Gun gun : gunList) {
                    price += gun.getPrice();
                }
                //delete
                //gunDAO.deleteOrderGuns(arr);
                userOrder.setPrice(price);
                req.setAttribute("order_products", gunList);
                req.setAttribute("order_price", userOrder.getPrice());
                req.getRequestDispatcher("/products/complete.jsp").forward(req, resp);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
