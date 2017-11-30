package filters;

import controllers.LoginServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



@WebFilter ("/pages/LoginServlet")
public class LoginServletFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }


        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) servletResponse;

            System.out.println("Hello, I`m servlet filter!");
            System.out.println(req.getRequestURI() + " i`m here!");

            //resp.sendRedirect("/pages/register.html");

            filterChain.doFilter(servletRequest, servletResponse);
        }



    public void destroy() {

    }
}
