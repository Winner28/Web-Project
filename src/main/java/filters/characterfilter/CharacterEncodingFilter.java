package filters.characterfilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.*;

import java.io.IOException;


@WebFilter(
        urlPatterns = "/*",
        initParams = @WebInitParam(name = "characterEncoding", value = "UTF-8"),
        description = "Setting utf-8 for response && request"
)
public class CharacterEncodingFilter implements Filter {

    private String encoding;
    private ServletContext context;


    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("characterEncoding");
        context = filterConfig.getServletContext();
        context.log("CharacterEncodingFilter initialized");


    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);

        context.log("Character was set");
        filterChain.doFilter(servletRequest, servletResponse);
    }


    public void destroy() {
    }


}
