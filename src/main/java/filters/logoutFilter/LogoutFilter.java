package filters.logoutFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/pages/LogoutServlet")
public class LogoutFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession(false).getAttribute("user") == null) {
            response.sendRedirect("/pages/login.html");
        } else {
            //do nothing
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }




    @Override
    public void destroy() {

    }
}
