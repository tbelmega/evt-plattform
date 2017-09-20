package de.belmega.eventers.auth;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Use this filter for all requests that contain the .xhtml file extension.
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*/internal/*.xhtml"})
public class AuthFilter implements Filter {

    @Inject
    @ConfigurationValue("urls.pages.login")
    String loginPage;


    public static final String ATTRIBUTE_USER_ID = "userId";

    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    /**
     * This method is executed for each HttpRequest that matches the @WebFilter(urlPatterns = {"*.xhtml"}).
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        if (!userIsLoggedIn(session)) {
            System.out.println("Kein User eingeloggt, leite weiter zu Login");
            redirectToLogin((HttpServletResponse) response);
        } else {
            chain.doFilter(request, response);
        }
    }


    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(loginPage);
    }

    /**
     * Check if the HttpSession has the LOGGED_IN_USER attribute.
     */
    private boolean userIsLoggedIn(HttpSession session) {
        boolean sessionHasAttributeUserLoggedIn = session != null && session.getAttribute(ATTRIBUTE_USER_ID) != null;
        return sessionHasAttributeUserLoggedIn;
    }


    public void destroy() {
        // do nothing
    }
}
