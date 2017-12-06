package controllers;

import dao.UserDAO;
import helpers.ApplicationServletContext;
import helpers.JdbcDAO;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;


@WebServlet("/pages/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;
    private ServletContext servletContext;

    @Override
    public void init() throws ServletException {
        ApplicationServletContext applicationServletContex = new ApplicationServletContext();
        userDAO = new UserDAO(applicationServletContex.take());
        servletContext = getServletContext();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("userName");
        String password = req.getParameter("password");

        Optional<User> userOptional = userDAO.checkUser(username, password);
        if (userOptional.isPresent()) {
            servletContext.log("Login success for users: " + userOptional.get());
            req.getSession(true).setAttribute("user", userOptional.get());
            req.setAttribute("user", userOptional.get());
            req.getRequestDispatcher("/profile/profile.jsp").forward(req, resp);

        } else {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/login.html");
            out.println("<font color=red>Either user name or password is wrong.</font>");
            requestDispatcher.include(req, resp);
        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}

