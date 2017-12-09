package controllers.pagesServlets;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet("/pages/LogoutServlet")
public class LogoutServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if ( session!=null && session.getAttribute("user") != null) {
            getServletContext().log("User" + session.getAttribute("user").toString() +"has left!");
            session.invalidate();
            resp.sendRedirect("/pages/login.html");
        }

       // req.getRequestDispatcher("/pages/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
