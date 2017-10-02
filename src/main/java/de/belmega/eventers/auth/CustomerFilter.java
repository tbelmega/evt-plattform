package de.belmega.eventers.auth;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "CustomerFilter", urlPatterns = {"/customer/*"})
public class CustomerFilter implements Filter {

    @Inject
    @ConfigurationValue("urls.pages.login")
    String loginPage;


    public static final String ATTRIBUTE_USER_ID = "userId";

    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(true);
        chain.doFilter(request, response);
    }


    public void destroy() {
        // do nothing
    }
}
