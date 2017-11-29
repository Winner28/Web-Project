package controllers;

import dao.UserDAO;
import helpers.ApplicationServletContext;
import helpers.JdbcDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/pages/LoginServlet")
public class LoginServlet extends HttpServlet {



    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationServletContext applicationServletContext = new ApplicationServletContext();

         UserDAO userDAO = new UserDAO(applicationServletContext.take());
        String username = req.getParameter("userName");
        String password = req.getParameter("password");

        if (userDAO.checkUserLogin(username, password)) {
            System.out.println("Login is success!!!");
        } else {
            System.out.println("Login is incorrect!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}

