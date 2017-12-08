package controllers.adminServlet;

import dao.UserDAO;
import helpers.JdbcDAO;
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


@WebServlet("/pages/admin")
public class AdminServlet extends HttpServlet {


    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO((JdbcDAO) getServletContext().getAttribute("database"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (req.getSession() != null && req.getSession().getAttribute("user") != null) {
            if (checkIfAdmin(((User) req.getSession().getAttribute("user")).getUserName())) {
                List<User> userList = userDAO.getAll();
                PrintWriter out = resp.getWriter();
                out.println("<table>");
                out.println("<head>\n" +
                        "    <title>Admin Page</title>\n" +
                        "    <style>\n" +
                        "        #user {\n" +
                        "            border: 3px solid #444;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>");


                out.println("<tr>" +
                        "<th>id</th>" +
                        "<th>username</th>" +
                        "</tr>");
                for (User user : userList) {
                    out.println("<tr>" +
                            "<td>" + user.getId() +"</td>" +
                            "<td>" + user.getUserName() + "</td>" +
                            "</tr>");
                }

                out.println("</table>");
                out.close();

            } else {
                PrintWriter out = resp.getWriter();
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/profile/profile.jsp");
                out.println("<font color=red>Bad User access, sorry.</font>");
                requestDispatcher.include(req, resp);
            }
        } else {
            PrintWriter out = resp.getWriter();
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/pages/login.html");
            out.println("<font color=red>Login, please</font>");
            requestDispatcher.include(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    private boolean checkIfAdmin(String userName) {
        return userName.equals("admin");
    }
}
