package filters.loginFilters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter ("/pages/LoginServlet")
public class LoginServletFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }


        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
           /* HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) servletResponse;

            HttpSession session = req.getSession(false);

 if (session != null && session.getAttribute("user") != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resp.sendRedirect("/pages/login.html");

            }*/

            filterChain.doFilter(servletRequest, servletResponse);


        }



    public void destroy() {

    }
}
