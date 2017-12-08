package controllers.pagesServlets;

import dao.UserDAO;
import helpers.ApplicationServletContext;
import helpers.JdbcDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/pages/RegisterServlet")
public class RegisterServlet extends HttpServlet {


    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO((JdbcDAO) getServletContext().getAttribute("database"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String username = req.getParameter("login");
        String password = req.getParameter("password");
        String name     = req.getParameter("name");


        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/register.html");
            out.println("<font color=red>Username, password or name is empty.</font>");
            requestDispatcher.include(req, resp);
        }


        else if (userDAO.registerUser(name, username, password)) {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/login.html");
            out.println("<font color=green>Register success, Login please!</font>");
            requestDispatcher.include(req, resp);
        } else {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/register.html");
            out.println("<font color=red>That username is already taken</font>");
            requestDispatcher.include(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
