package lev.filippov.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*", ""})
public class SimpleFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       //TODO: java.lang.IllegalStateException: UT010006: Cannot call getWriter(), getOutputStream() already called

//        filterConfig.getServletContext().getRequestDispatcher("/header.html").include(servletRequest,servletResponse);
        servletResponse.setContentType("text/html; charset=utf-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
