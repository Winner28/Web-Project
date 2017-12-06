package filters.loginFilters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebFilter("/pages/login.html")
public class LoginURLFilter implements Filter {

    private ServletContext servletContext;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        if ( session!=null && session.getAttribute("user") != null) {
            response.sendRedirect("/profile/profile.jsp");
            servletContext.log("Session is not null, redirected to profile");
        } else {
            //do nothing
            filterChain.doFilter(request, response);
            servletContext.log("Session is null");
        }





    }

    @Override
    public void destroy() {

    }
}
